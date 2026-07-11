package com.estudiante.reservas.controller;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estudiante.reservas.functional.ReservationFilters;
import com.estudiante.reservas.model.ReservationEvent;

import reactor.core.publisher.Flux;

/**
 * Este es mi controlador REST reactivo. Aqui expongo el endpoint
 * GET /api/reservations/stream que retorna un Flux de eventos de reserva.
 * Al ser reactivo no bloquea el hilo principal: los eventos se van
 * emitiendo y el cliente los recibe conforme llegan.
 */
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ReservationEvent> streamReservations() {
        // Esta es mi reserva generica: la dejo lista por si el filtro
        // llegara a dejar el flujo vacio, defaultIfEmpty la entrega
        ReservationEvent defaultReservation = new ReservationEvent(
                UUID.randomUUID().toString(),
                "Pasajero Generico",
                1.0,
                List.of("reservas@aerolinea.com"));

        // Aqui genere mis 5 reservas en memoria con Flux.just():
        // 3 validas y 2 invalidas (Luis tiene precio negativo y Carlos no tiene correos)
        return Flux.just(
                        new ReservationEvent(UUID.randomUUID().toString(), "Ana Torres", 350.50,
                                List.of("ana.torres@gmail.com")),
                        new ReservationEvent(UUID.randomUUID().toString(), "Luis Perez", -120.00,
                                List.of("luis.perez@gmail.com")),
                        new ReservationEvent(UUID.randomUUID().toString(), "Maria Salazar", 480.00,
                                List.of("maria.salazar@gmail.com", "msalazar@empresa.com")),
                        new ReservationEvent(UUID.randomUUID().toString(), "Carlos Vera", 275.25,
                                List.of()),
                        new ReservationEvent(UUID.randomUUID().toString(), "Elena Rios", 610.00,
                                List.of("elena.rios@gmail.com")))
                // Le puse 1 segundo entre eventos para que en la prueba con curl
                // se vea que el flujo llega poco a poco sin bloquear el hilo
                .delayElements(Duration.ofSeconds(1))
                // Aqui aplico mi Predicate: solo pasan las reservas validas
                .filter(ReservationFilters.isValidReservation)
                // Aqui aplico mi Consumer: imprime en consola cada evento procesado
                .doOnNext(ReservationFilters.printReservation)
                // Y si el filtro dejara todo vacio, entrego la reserva generica
                .defaultIfEmpty(defaultReservation);
    }
}
