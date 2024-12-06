package it.epicode.esercizio.Main_Application;

import it.epicode.esercizio.entities_model.GestionePrestito;
import it.epicode.esercizio.entities_model.Utente;
import it.epicode.esercizio.entities_model.Elemento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;

public class GestionePrestiti {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("libraryPU");

    // REGISTRO UN NUOVO PRESTITO
    public static void registraPrestito(Utente utente, Elemento elemento) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();

            GestionePrestito prestito = new GestionePrestito();
            prestito.setUtente(utente);
            prestito.setElementoPrestato(elemento);
            prestito.setDataInizioPrestito(LocalDate.now());
            prestito.setDataRestituzionePrevista(LocalDate.now().plusDays(30));

            em.persist(prestito);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // CERCA PRESTITI SCADUTI E NON RESTITUITI
    public static List<GestionePrestito> cercaPrestitiScadutiNonRestituiti() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM GestionePrestito p WHERE p.dataRestituzionePrevista < :oggi AND p.dataRestituzioneEffettiva IS NULL", GestionePrestito.class)
                    .setParameter("oggi", LocalDate.now())
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // CERCA PRESTITI PER UTENTE
    public static List<GestionePrestito> cercaPrestitiPerUtente(String numeroTessera) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM GestionePrestito p WHERE p.utente.numeroTessera = :numeroTessera", GestionePrestito.class)
                    .setParameter("numeroTessera", numeroTessera)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}