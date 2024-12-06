package it.epicode.esercizio.Main_Application;

import it.epicode.esercizio.dao.*;
import it.epicode.esercizio.entities_model.*;
import com.github.javafaker.Faker;

import java.time.LocalDate;

public class MainFaker {
    public static void main(String[] args) {

        // INIZIALIZZAZIONE DAO

        UtenteDAO utenteDAO = new UtenteDAO();
        LibriDAO libriDAO = new LibriDAO();
        RivisteDAO rivisteDAO = new RivisteDAO();
        GestionePrestitoDAO gestionePrestitoDAO = new GestionePrestitoDAO();
        ElementoDAO elementoDAO = new ElementoDAO();

        Faker faker = new Faker(); // INIZIALIZZO ANCHE IL FAKER

        // GENERO 10 UTENTI FAKE
        for (int i = 0; i < 10; i++) {
            Utente utente = new Utente();
            utente.setNome(faker.name().firstName());
            utente.setCognome(faker.name().lastName());
            utente.setDataNascita(faker.date().birthday().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate());
            utente.setNumeroTessera(faker.idNumber().valid());
            utenteDAO.save(utente);
        }
        System.out.println("👥 10 Utenti finti generati con successo!");

        // QUI GENERO 10 LIBRI FAKE

        for (int i = 0; i < 10; i++) {
            Libri libro = new Libri();
            libro.setIsbn(faker.code().isbn13());
            libro.setTitolo(faker.book().title());
            libro.setAutore(faker.book().author());
            libro.setGenere(faker.book().genre());
            libro.setAnnoPubblicazione(faker.number().numberBetween(1900, 2023));
            libro.setNumeroPagine(faker.number().numberBetween(100, 1000));
            libriDAO.save(libro);
        }
        System.out.println("📚 10 Libri random generati con successo!");

        // ANCHE 10 RIVISTE

        for (int i = 0; i < 10; i++) {
            Riviste rivista = new Riviste();
            rivista.setIsbn(faker.code().isbn10());
            rivista.setTitolo(faker.book().title());
            rivista.setAnnoPubblicazione(faker.number().numberBetween(1900, 2023));
            rivista.setNumeroPagine(faker.number().numberBetween(20, 200));
            rivista.setPeriodicita(Periodicita.values()[faker.number().numberBetween(0, Periodicita.values().length)]);
            rivisteDAO.save(rivista);
        }
        System.out.println("📰 10 Riviste random generate con successo!");

        // GENERO 10 PRESTITI COMPRESI ANCHE I PRESTITI SCADUTI

        for (int i = 0; i < 10; i++) {
            Utente utente = utenteDAO.findAll().get(faker.number().numberBetween(0, 10));
            Elemento elemento = elementoDAO.findAll().get(faker.number().numberBetween(0, 20)); // Libri o Riviste
            GestionePrestito prestito = new GestionePrestito();
            prestito.setUtente(utente);
            prestito.setElementoPrestato(elemento);

            // IMPOSTO DATE
            LocalDate dataInizio = LocalDate.now().minusDays(faker.number().numberBetween(1, 60));
            prestito.setDataInizioPrestito(dataInizio);
            prestito.setDataRestituzionePrevista(dataInizio.plusDays(30));

            // PRESTITI NON RESTITUITI ALCUNI RANDOM
            if (i % 2 == 0) {
                prestito.setDataRestituzioneEffettiva(dataInizio.plusDays(faker.number().numberBetween(15, 45)));
            }

            gestionePrestitoDAO.save(prestito);
        }
        System.out.println("📦 10 Prestiti random generati con successo!");
    }
}