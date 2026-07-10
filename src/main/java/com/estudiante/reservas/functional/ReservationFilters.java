package com.estudiante.reservas.functional;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.estudiante.reservas.model.ReservationEvent;

/**
 * Clase utilitaria con la logica funcional de las reservas.
 * Las reglas de negocio se definen como variables usando lambdas.
 */
public final class ReservationFilters {

    private ReservationFilters() {
        // Clase utilitaria: no se instancia
    }

    /**
     * Predicate: una reserva es valida si el precio es mayor a 0
     * y la lista de correos no esta vacia. Retorna true o false.
     */
    public static final Predicate<ReservationEvent> isValidReservation =
            event -> event.getPrice() > 0 && !event.getEmails().isEmpty();

    /**
     * Consumer: recibe el evento procesado y lo imprime por consola.
     */
    public static final Consumer<ReservationEvent> printReservation =
            event -> System.out.println("Reserva procesada: " + event);
}
