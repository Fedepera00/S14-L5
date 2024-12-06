package it.epicode.esercizio.entities_model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED) // TABELLE SEPARATE
public abstract class Elemento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String titolo;

    private int annoPubblicazione;
    private int numeroPagine;
}