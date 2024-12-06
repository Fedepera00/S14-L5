package it.epicode.esercizio.Main_Application;

import it.epicode.esercizio.entities_model.Elemento;
import it.epicode.esercizio.entities_model.Utente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class Catalogo {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("libraryPU");

    public static <T extends Elemento> void aggiungiElemento(T elemento) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(elemento);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public static void aggiungiUtente(Utente utente) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(utente);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public static <T extends Elemento> List<T> cercaElementiPerAnno(int anno, Class<T> tipo) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM " + tipo.getSimpleName() + " e WHERE e.annoPubblicazione = :anno", tipo)
                    .setParameter("anno", anno)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public static <T extends Elemento> T cercaElementoPerISBN(String isbn, Class<T> tipo) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM " + tipo.getSimpleName() + " e WHERE e.isbn = :isbn", tipo)
                    .setParameter("isbn", isbn)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}