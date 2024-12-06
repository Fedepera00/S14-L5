package it.epicode.esercizio.entities_model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Libri extends Elemento {
    @Column(nullable = false)
    private String autore;

    @Column(nullable = false)
    private String genere;
}