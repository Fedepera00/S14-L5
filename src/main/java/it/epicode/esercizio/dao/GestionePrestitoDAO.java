package it.epicode.esercizio.dao;

import it.epicode.esercizio.entities_model.GestionePrestito;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;

public class GestionePrestitoDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("libraryPU");

    public void save(GestionePrestito prestito) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(prestito);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public GestionePrestito findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(GestionePrestito.class, id);
        } finally {
            em.close();
        }
    }

    public List<GestionePrestito> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM GestionePrestito p", GestionePrestito.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            GestionePrestito prestito = em.find(GestionePrestito.class, id);
            if (prestito != null) {
                em.remove(prestito);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // RICERCA DEI PRESTITI IN CORSO PER UN UTENTE
    public List<GestionePrestito> findCurrentLoansByUser(String numeroTessera) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM GestionePrestito p WHERE p.utente.numeroTessera = :numeroTessera AND p.dataRestituzioneEffettiva IS NULL", GestionePrestito.class)
                    .setParameter("numeroTessera", numeroTessera)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // RICERCA PRESTITI SCADUTI O NON RESTITUITI
    public List<GestionePrestito> findExpiredLoans() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM GestionePrestito p WHERE p.dataRestituzionePrevista < :oggi AND p.dataRestituzioneEffettiva IS NULL", GestionePrestito.class)
                    .setParameter("oggi", LocalDate.now())
                    .getResultList();
        } finally {
            em.close();
        }
    }
}