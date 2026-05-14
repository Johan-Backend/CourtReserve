# ADR-0003: Identificador interno de RefreshToken como Long autogenerado

- **Status**: Accepted
- **Date**: 2026-05-14

## Context
El proyecto sigue un enfoque DDD táctico aplicado de forma pragmática. Uno de los principios de DDD establece que una entidad de dominio tiene **identidad propia**, idealmente generada en el momento de creación del agregado y no asignada por la infraestructura. Aplicado estrictamente, esto sugiere que los identificadores de entidades deberían ser UUID generados en el dominio, no `Long` autogenerados por la base de datos.

Para la entidad `RefreshToken` se evalúa qué tipo de identificador usar como clave primaria:

- **`UUID` generado en el dominio**: cumple estrictamente el principio DDD de identidad propia desde el momento de creación. El objeto está completo antes del primer `save`.
- **`Long` autogenerado por la base de datos**: deja la entidad en estado "incompleto" (`id == null`) hasta el primer `save`, pero es más eficiente en almacenamiento e índices.

Las fuerzas en juego son:

- **Exposición del identificador**: el id del `RefreshToken` no se expone al cliente. No aparece en URLs públicas, no viaja en el JWT, no se devuelve en respuestas de API. El cliente solo conoce el token raw (vía cookie HttpOnly), nunca su id en base de datos.
- **Coherencia con el resto del modelo**: la entidad `User` ya utiliza `Long id` interno autogenerado para uso en base de datos, complementado con `UUID publicId` solo donde el identificador necesita exponerse públicamente.
- **Identidad de dominio dentro de auth**: la rotación de refresh tokens introduce el concepto de "familia de tokens" (todos los descendientes de un mismo login). Este concepto sí requiere identidad de dominio generada en el momento del login.
- **Coste vs beneficio del UUID como PK**: UUIDs son menos eficientes que `Long` para índices y joins en base de datos (16 bytes vs 8, peor localidad). Para identificadores no expuestos, esta penalización no se compensa con ningún beneficio funcional.

## Decision

El identificador interno de `RefreshToken` es `Long` autogenerado por la base de datos mediante `@GeneratedValue(strategy = IDENTITY)`. La entidad de dominio admite `id == null` hasta el primer `save`, momento en el que la base de datos asigna su valor.

La identidad de dominio relevante para la lógica de negocio (rotación, detección de reuso, revocación masiva) se modela mediante `familyId` (`UUID` generado en el dominio en el momento del login). Esto satisface el principio DDD de identidad propia para el concepto de "familia de tokens", que es la unidad de identidad significativa en el dominio de auth.

```java
public class RefreshToken {
    private final Long id;              // null hasta persistir
    private final UUID familyId;        // identidad de dominio, generada al crear
    private final UUID userPublicId;
    private final String tokenHash;
    // ...
}
```

## Consequences

### Positivas

- **Coherencia con `User`**: ambas entidades usan `Long` interno autogenerado para almacenamiento, evitando criterios distintos para entidades análogas.
- **Eficiencia en base de datos**: índices más compactos y joins más rápidos que con UUID. Aunque el impacto sea marginal en el volumen esperado, no se asume coste sin beneficio asociado.
- **El principio DDD se respeta donde aporta valor**: la identidad de dominio se materializa en `familyId`, que es el concepto que la lógica de negocio realmente manipula (rotación, revocación). El `id` individual es detalle de persistencia.
- **Simplicidad del modelo**: la entidad no necesita lógica de generación de identificadores ni decisiones sobre versiones de UUID (v4, v7, ULID).

### Negativas

- **El objeto de dominio queda incompleto hasta el primer `save`**: `id == null` durante la fase de creación. En la práctica esto no causa problemas porque `RefreshToken` no se manipula entre instancias antes de ser persistido.
- **No se cumple el principio DDD estricto** de "entidad con identidad propia desde la creación" para el `id` individual. Se asume conscientemente esta desviación: el principio se aplica en el concepto significativo (`familyId`), no en el detalle técnico del id de fila.
- **`equals/hashCode` basados en `id` requieren cuidado**: con `id == null` antes de persistir, las comparaciones por identidad pueden fallar. Mitigado por no implementar `equals/hashCode` en la entidad hasta que un caso concreto lo requiera (ver ADR-0XXX si se documenta).

## Alternatives Considered

### Alternativa 1: UUID generado en dominio como PK

Descartada porque el id de `RefreshToken` no se expone al exterior, por lo que el UUID no aporta los beneficios típicos (no enumerabilidad, identidad portable entre sistemas). La penalización en almacenamiento e índices no se compensa con ningún beneficio funcional. Aplicar DDD estricto en este punto sería ceremonia sin valor.

### Alternativa 2: Long interno + UUID público (como `User`)

Descartada porque, a diferencia de `User`, el `RefreshToken` no tiene ningún caso de uso donde necesite ser referenciado públicamente. Añadir un `UUID publicId` que nunca se expone duplicaría columnas e índices sin justificación.