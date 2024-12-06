package it.epicode.esercizio.dao;

import it.epicode.esercizio.entities_model.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class UtenteDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("libraryPU");

    public void save(Utente utente) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(utente);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Utente findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Utente.class, id);
        } finally {
            em.close();
        }
    }

    public List<Utente> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Utente u", Utente.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Utente utente = em.find(Utente.class, id);
            if (utente != null) {
                em.remove(utente);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}