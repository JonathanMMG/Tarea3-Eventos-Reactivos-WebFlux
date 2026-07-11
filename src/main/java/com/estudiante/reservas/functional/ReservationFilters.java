package com.estudiante.reservas.functional;

import java.util.function.Consumer;
import java.util.function.Predicate;

import com.estudiante.reservas.model.ReservationEvent;

/**
 * En esta clase utilitaria defini mi logica funcional.
 * Aqui escribi las reglas de negocio como variables usando lambdas,
 * tal como lo trabajamos en las clases de programacion funcional.
 */
public final class ReservationFilters {

    private ReservationFilters() {
        // Le quite el constructor publico porque esta clase solo la uso
        // como utilitaria, no necesito crear instancias de ella
    }

    /**
     * Este es mi Predicate: aqui valido que el precio sea mayor a 0
     * y que la reserva tenga al menos un correo. Me devuelve true o false.
     */
    public static final Predicate<ReservationEvent> isValidReservation =
            event -> event.getPrice() > 0 && !event.getEmails().isEmpty();

    /**
     * Este es mi Consumer: aqui recibo cada evento que se va procesando
     * y lo imprimo por consola para poder verlo en la evidencia.
     */
    public static final Consumer<ReservationEvent> printReservation =
            event -> System.out.println("Reserva procesada: " + event);
}
