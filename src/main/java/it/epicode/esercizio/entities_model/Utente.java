package it.epicode.esercizio.entities_model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    private LocalDate dataNascita;

    @Column(unique = true, nullable = false)
    private String numeroTessera;
}