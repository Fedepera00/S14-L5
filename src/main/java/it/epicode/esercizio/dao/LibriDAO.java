package it.epicode.esercizio.dao;

import it.epicode.esercizio.entities_model.Libri;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class LibriDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("libraryPU");

    public void save(Libri libro) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Libri findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Libri.class, id);
        } finally {
            em.close();
        }
    }

    public List<Libri> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM Libri l", Libri.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Libri libro = em.find(Libri.class, id);
            if (libro != null) {
                em.remove(libro);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // CERCA PER AUTORE
    public List<Libri> findByAuthor(String autore) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM Libri l WHERE l.autore = :autore", Libri.class)
                    .setParameter("autore", autore)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    // CERCA PER GENERE
    public List<Libri> findByGenre(String genere) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT l FROM Libri l WHERE l.genere = :genere", Libri.class)
                    .setParameter("genere", genere)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}