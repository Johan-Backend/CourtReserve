# ADR-0002: Referencia de RefreshToken al User mediante publicId (UUID)

- **Status**: Accepted
- **Date**: 2026-05-14

## Context
El proyecto está estructurado siguiendo package-by-feature, donde cada feature (`user`, `auth`, `reservation`, `court`) contiene sus propios paquetes `domain`, `application` e `infrastructure`. Internamente cada feature respeta los principios fundamentales de la arquitectura hexagonal, pero se aplican de forma pragmática: se simplifican detalles cuando la pureza arquitectónica no aporta valor proporcional al coste.

El modelo `User` tiene dos identificadores: un `Long id` autogenerado por la base de datos (clave primaria interna, eficiente para joins) y un `UUID publicId` generado en el dominio (identificador público que se expone en JWTs, URLs y respuestas de API; protege frente a enumeración).

Al modelar `RefreshToken` como entidad de dominio dentro de la feature `auth`, surge la cuestión de cómo referenciar al usuario propietario del token. Existen tres opciones técnicas:

1. Relación JPA bidireccional con `@ManyToOne UserJpaEntity user` en la capa de infraestructura/persintence de `auth`.
2. Referencia por `Long userId` (el id interno autogenerado de `users`).
3. Referencia por `UUID userPublicId` (el id público del user).

Las fuerzas en juego son:

- **Aislamiento entre features**: en package-by-feature, evitar que la infraestructura de `auth` importe la infraestructura de `user`.
- **Coherencia dominio-persistencia**: mantener un mapper sencillo sin traducciones de identificadores entre ambos lados.
- **Rendimiento en base de datos**: los joins por `Long` son ligeramente más eficientes que por `UUID` (8 bytes vs 16, mejor localidad de índice).
- **Coste de mantenimiento**: cuantas menos traducciones y dependencias cruzadas, menos puntos de fallo.

## Decision

`RefreshToken` referencia al usuario mediante `UUID userPublicId`, tanto en la entidad de dominio como en la entidad de persistencia. La clave foránea física en base de datos apunta a `users.public_id` (columna `unique`), no a `users.id`.

```sql
ALTER TABLE refresh_tokens
ADD CONSTRAINT fk_refresh_tokens_user
FOREIGN KEY (user_public_id) REFERENCES users(public_id);
```

El mapper entre `RefreshToken` (dominio) y `RefreshTokenJpaEntity` (persistencia) es trivial: el campo `userPublicId` se pasa directamente sin transformación.

## Consequences

### Positivas

- **Coherencia total entre dominio y persistencia**: ambos lados usan el mismo identificador (`UUID userPublicId`). El mapper no necesita queries adicionales para traducir entre `UUID` y `Long`.
- **Aislamiento real entre features**: `auth/infrastructure/persistence/RefreshTokenJpaEntity` no importa `user/infrastructure/persistence/UserJpaEntity`. La feature `auth` puede evolucionar sin conocer detalles de implementación de `user`.
- **Coherencia con DDD**: se sigue el principio "Reference Other Aggregates by Identity" (Vaughn Vernon). Los aggregates se referencian por id, no por objeto.
- **Integridad referencial garantizada por la base de datos**, sin depender de validaciones a nivel de aplicación.
- **El dominio nunca necesita conocer el `Long id` interno de `User`**, manteniendo el dominio libre de detalles de infraestructura.

### Negativas

- **Joins ligeramente menos eficientes** que con `Long` (16 bytes vs 8, peor localidad). Para el volumen esperado de la aplicación, el impacto es despreciable.
- **No hay navegación automática `refreshToken.getUser()`** desde Hibernate: si el servicio necesita datos del user, debe hacer una query explícita por `UserRepository.findByPublicId(...)`. Esto se considera una ventaja en este contexto (evita carga lazy involuntaria y refuerza la separación entre aggregates), pero rompe el patrón tradicional de JPA.
- **Requiere índice único sobre `users.public_id`** para que la FK sea válida y las búsquedas eficientes. Ya existe por diseño del modelo.

## Alternatives Considered

### Alternativa 1: `@ManyToOne UserJpaEntity` en `RefreshTokenJpaEntity`

Descartada porque acopla las capas de infraestructura de `auth` y `user`. La feature `auth` tendría que importar `UserJpaEntity`, rompiendo el aislamiento entre features que persigue package-by-feature. Es la opción más idiomática en JPA tradicional, pero no encaja con la decisión arquitectónica del proyecto.

### Alternativa 2: `Long userId` como FK

Descartada por la incoherencia que introduciría entre dominio y persistencia. El dominio `RefreshToken` referencia al user por `UUID userPublicId` (decisión coherente con el resto del modelo). Si la JpaEntity usara `Long userId`, el mapper tendría que traducir en cada `save` y cada `findBy*`, requiriendo una query adicional contra `users` para obtener el `Long id` correspondiente. El coste de la traducción y la complejidad añadida no compensa los pequeños beneficios de rendimiento en joins.