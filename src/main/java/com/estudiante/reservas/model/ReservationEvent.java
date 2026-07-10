package com.estudiante.reservas.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Evento de reserva de la compania aerea.
 * Clase 100% inmutable: clase y atributos final, sin setters,
 * con copias defensivas en el constructor y en el getter de la lista de correos.
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
        // Copia defensiva: guardamos nuestra propia copia para que nadie
        // pueda mutar la lista original que nos pasaron desde afuera
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
        // Copia defensiva: entregamos una copia para que quien la reciba
        // no pueda modificar el estado interno de la clase
        return new ArrayList<>(emails);
    }

    @Override
    public String toString() {
        return "ReservationEvent{" +
                "id='" + id + '\'' +
                ", passengerName='" + passengerName + '\'' +
                ", price=" + price +
                ", emails=" + emails +
                '}';
    }
}
