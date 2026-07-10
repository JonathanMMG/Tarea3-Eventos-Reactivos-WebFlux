# Tarea 3: Procesamiento de Eventos Asincrono con Spring WebFlux

Modulo sin conexion a base de datos que procesa un flujo continuo de **Eventos de Reserva**
de una compania aerea, usando programacion funcional y la libreria **Project Reactor**
(Spring Boot WebFlux).

## Tecnologias

- Java 21
- Spring Boot 3.4.5 (Spring Reactive Web / WebFlux)
- Project Reactor (Flux)
- Gradle 8.12.1

## Estructura

| Actividad | Rama | Clase |
|---|---|---|
| 1. Modelo inmutable | `feature/modelo-inmutable` | `ReservationEvent` (final, sin setters, copias defensivas) |
| 2. Logica funcional | `feature/logica-funcional` | `ReservationFilters` (Predicate y Consumer como lambdas) |
| 3. Flujo reactivo | `feature/api-reactiva` | `ReservationController` (Flux + filter + doOnNext + defaultIfEmpty) |

## Ejecucion

```bash
./gradlew bootRun
```

## Prueba del endpoint reactivo

```bash
curl -N http://localhost:8080/api/reservations/stream
```

El flujo emite 5 reservas en memoria (3 validas, 2 invalidas) con un retardo de 1 segundo
entre eventos. El `curl` recibe cada reserva valida conforme se emite (comportamiento
no bloqueante), el `Predicate` descarta las invalidas (precio <= 0 o sin correos) y el
`Consumer` imprime cada evento procesado en la consola del servidor. Si el filtro
dejara vacio el flujo, `defaultIfEmpty` entrega una reserva generica.
