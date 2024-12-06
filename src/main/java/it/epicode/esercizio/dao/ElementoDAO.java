package it.epicode.esercizio.dao;

import it.epicode.esercizio.entities_model.Elemento;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class ElementoDAO {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("libraryPU");

    public void save(Elemento elemento) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(elemento);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Elemento findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Elemento.class, id);
        } finally {
            em.close();
        }
    }

    public List<Elemento> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Elemento e", Elemento.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Elemento elemento = em.find(Elemento.class, id);
            if (elemento != null) {
                em.remove(elemento);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    // RICERCA PER ISBN
    public Elemento findByISBN(String isbn) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Elemento e WHERE e.isbn = :isbn", Elemento.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    // RICERCA PER TITOLO O UNA SUA PARTE
    public List<Elemento> findByTitle(String title) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Elemento e WHERE e.titolo LIKE :title", Elemento.class)
                    .setParameter("title", "%" + title + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }
}