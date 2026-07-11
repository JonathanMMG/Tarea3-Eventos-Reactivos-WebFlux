package com.estudiante.reservas.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Evento de reserva de la compania aerea.
 * Esta clase la hice 100% inmutable como vimos en clase: le puse final
 * a la clase y a todos los atributos, no le cree setters y trabajo con
 * copias defensivas en el constructor y en el getter de la lista de correos.
 */
public final class ReservationEvent {

    private final String id;
    private final String passengerName;
    private final Double price;
    private final List<String> emails;

    public ReservationEvent(String id, String passengerName, Double price, List<String> emails) {
        this.id = id;
        this.passengerName = passengerName;
        this.price = price;
        // Aqui hice la copia defensiva: guardo mi propia copia de la lista
        // para que nadie pueda modificarme la original desde afuera
        this.emails = new ArrayList<>(emails);
    }

    public String getId() {
        return id;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public Double getPrice() {
        return price;
    }

    public List<String> getEmails() {
        // Aqui tambien entrego una copia y no la lista real, asi quien
        // la reciba no puede cambiar los datos internos de mi clase
        return new ArrayList<>(emails);
    }

    @Override
    public String toString() {
        // Este toString lo hice para poder imprimir la reserva por consola
        return "ReservationEvent{" +
                "id='" + id + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", price=" + price +
                ", emails=" + emails +
                '}';
    }
}
