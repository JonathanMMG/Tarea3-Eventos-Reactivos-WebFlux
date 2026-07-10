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
 * Controlador REST reactivo que expone el flujo de eventos de reserva.
 * No bloquea el hilo principal: el Flux emite los eventos conforme llegan.
 */
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ReservationEvent> streamReservations() {
        // Reserva generica por defecto si el flujo queda vacio tras el filtro
        ReservationEvent defaultReservation = new ReservationEvent(
                UUID.randomUUID().toString(),
                "Pasajero Generico",
                1.0,
                List.of("reservas@aerolinea.com"));

        // Flujo de 5 reservas en memoria: 3 validas y 2 invalidas
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
                // Emite un evento por segundo para evidenciar el flujo reactivo no bloqueante
                .delayElements(Duration.ofSeconds(1))
                .filter(ReservationFilters.isValidReservation)
                .doOnNext(ReservationFilters.printReservation)
                .defaultIfEmpty(defaultReservation);
    }
}
