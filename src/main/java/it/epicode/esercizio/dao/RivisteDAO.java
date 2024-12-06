package it.epicode.esercizio.dao;

import it.epicode.esercizio.entities_model.Riviste;
import it.epicode.esercizio.entities_model.Periodicita;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class RivisteDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("libraryPU");

    public void save(Riviste rivista) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(rivista);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Riviste findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Riviste.class, id);
        } finally {
            em.close();
        }
    }

    public List<Riviste> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Riviste r", Riviste.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Riviste rivista = em.find(Riviste.class, id);
            if (rivista != null) {
                em.remove(rivista);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // CERCA PER PERIODICITA' ENUM
    public List<Riviste> findByPeriodicita(Periodicita periodicita) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT r FROM Riviste r WHERE r.periodicita = :periodicita", Riviste.class)
                    .setParameter("periodicita", periodicita)
                    .getResultList();
        } finally {
            em.close();
        }
    }
}