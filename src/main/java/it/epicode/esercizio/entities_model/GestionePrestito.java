package it.epicode.esercizio.entities_model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class GestionePrestito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // IDENTIFICATIVO UNICO PRESTITO

    @ManyToOne
    @JoinColumn(nullable = false) // ASSOCIO A UTENTE
    private Utente utente;

    @ManyToOne
    @JoinColumn(nullable = false) // ASSOCIO A LIBRO O RIVISTA
    private Elemento elementoPrestato;

    @Column(nullable = false) // DATA IN CUI E' REGISTRATO
    private LocalDate dataInizioPrestito;

    @Column(nullable = false) // DATA RESTITUZIONE A 30 GG MAX
    private LocalDate dataRestituzionePrevista;

    // DATA EFFETTIVA RESTITUZIONE PUO' ESSERE NULL
    private LocalDate dataRestituzioneEffettiva;

    // MI CREO COSTRUTTORE PERSONALIZZATO PER RICHIEDERE IL PRESTITO
    public GestionePrestito(Utente utente, Elemento elementoPrestato) {
        this.utente = utente;
        this.elementoPrestato = elementoPrestato;
        this.dataInizioPrestito = LocalDate.now(); // Data corrente
        this.dataRestituzionePrevista = LocalDate.now().plusDays(30); // 30 giorni dopo
    }
    // COSTRUTTORE VUOTO
    public GestionePrestito() {
    }
}