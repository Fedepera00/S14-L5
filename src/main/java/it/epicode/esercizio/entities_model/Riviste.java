package it.epicode.esercizio.entities_model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Riviste extends Elemento {

    @Enumerated(EnumType.STRING)
    private Periodicita periodicita;

    public Periodicita getPeriodicita() {
        return periodicita;
    }

    public void setPeriodicita(Periodicita periodicita) {
        this.periodicita = periodicita;
    }
}