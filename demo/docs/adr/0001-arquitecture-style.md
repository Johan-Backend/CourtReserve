# ADR-0001: Lightweight Hexagonal Architecture

- **Status**: Accepted
- **Date**: 2026-05-07

## Context

CourtReserve is a portfolio project intended to demonstrate backend design
quality during interviews for internship positions. I needed to choose an
architectural style that:

1. Is defensible and recognisable to interviewers familiar with modern
   backend patterns.
2. Keeps the codebase testable and the domain rules explicit.
3. Does not introduce ceremony disproportionate to the problem size
   (~4 modules, ~10 entities).

## Decision
 
I use **lightweight hexagonal architecture with package-by-feature**.
 
Each bounded context (`auth`, `user`, `court`, `reservation`) is a top-level
package, and each context follows the same internal layout:
 
```
<context>/
├── domain/           # Aggregates, value objects, repository interfaces
├── application/      # Use cases, commands, output ports
├── infrastructure/   # JPA adapters, external service adapters, config
└── interfaces/rest/  # Controllers, DTOs, validation
```
 
The "lightweight" qualifier means I take two specific shortcuts compared to
canonical hexagonal:
 
1. Domain aggregates carry `@Entity` and JPA annotations directly. There is
   no separate `ReservationJpaEntity` + mapper.
2. `@Service` and `@Transactional` may appear in the application layer.
Use cases receive the data they need as explicit parameters
(e.g. `userId: UUID`) and never read `SecurityContextHolder`.