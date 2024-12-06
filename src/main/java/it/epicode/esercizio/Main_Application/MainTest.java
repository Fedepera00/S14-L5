package it.epicode.esercizio.Main_Application;

import it.epicode.esercizio.dao.*;
import it.epicode.esercizio.entities_model.*;

import java.util.Scanner;
import java.util.List;
import java.time.LocalDate;

public class MainTest {
    public static void main(String[] args) {

        // INIZIALIZZO DAO
        ElementoDAO elementoDAO = new ElementoDAO();
        LibriDAO libriDAO = new LibriDAO();
        RivisteDAO rivisteDAO = new RivisteDAO();
        UtenteDAO utenteDAO = new UtenteDAO();
        GestionePrestitoDAO gestionePrestitoDAO = new GestionePrestitoDAO();

        Scanner scanner = new Scanner(System.in); // Scanner per input utente

        while (true) {
            // MENU' INTERATTIVO
            System.out.println("\n-------------------------------- APP CATALOGO --------------------------------");
            System.out.println("1. Aggiungi un nuovo libro");
            System.out.println("2. Aggiungi una nuova rivista");
            System.out.println("3. Elimina un elemento tramite ISBN");
            System.out.println("4. Cerca un elemento per ISBN");
            System.out.println("5. Cerca un elemento per il titolo");
            System.out.println("6. Cerca libri per il suo autore");
            System.out.println("7. Cerca libri per il genere");
            System.out.println("8. Cerca riviste per la periodicità");
            System.out.println("9. Registra un nuovo prestito");
            System.out.println("10. Visualizza prestiti scaduti");
            System.out.println("11. Visualizza prestiti per utente");
            System.out.println("12. Crea un nuovo utente nel Database");
            System.out.println("13. Esci dal programma");
            System.out.print("Scegli un'opzione prima di continuare: ");

            int scelta = scanner.nextInt();
            scanner.nextLine(); // CONSUMA LA LINEA ALLA FINE

            switch (scelta) {
                // AGGIUNGI UN LIBRO
                case 1 -> {
                    System.out.print("Inserisci ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Inserisci il titolo: ");
                    String titolo = scanner.nextLine();
                    System.out.print("Inserisci l'autore: ");
                    String autore = scanner.nextLine();
                    System.out.print("Inserisci il genere: ");
                    String genere = scanner.nextLine();
                    System.out.print("Inserisci l'anno di pubblicazione: ");
                    int anno = scanner.nextInt();
                    System.out.print("Inserisci il numero di pagine: ");
                    int pagine = scanner.nextInt();

                    Libri libro = new Libri();
                    libro.setIsbn(isbn);
                    libro.setTitolo(titolo);
                    libro.setAutore(autore);
                    libro.setGenere(genere);
                    libro.setAnnoPubblicazione(anno);
                    libro.setNumeroPagine(pagine);

                    libriDAO.save(libro);
                    System.out.println("📖 Libro aggiunto con successo al DB!");
                }
                // AGGIUNGI UNA RIVISTA
                case 2 -> {
                    System.out.print("Inserisci ISBN: ");
                    String isbn = scanner.nextLine();
                    System.out.print("Inserisci il titolo: ");
                    String titolo = scanner.nextLine();
                    System.out.print("Inserisci la periodicità scegli tra SETTIMANALE, MENSILE e/o SEMESTRALE: ");
                    String periodicitaInput = scanner.nextLine();
                    Periodicita periodicita = Periodicita.valueOf(periodicitaInput.toUpperCase());
                    System.out.print("Inserisci l'anno di pubblicazione: ");
                    int anno = scanner.nextInt();
                    System.out.print("Inserisci il numero di pagine: ");
                    int pagine = scanner.nextInt();

                    Riviste rivista = new Riviste();
                    rivista.setIsbn(isbn);
                    rivista.setTitolo(titolo);
                    rivista.setPeriodicita(periodicita);
                    rivista.setAnnoPubblicazione(anno);
                    rivista.setNumeroPagine(pagine);

                    rivisteDAO.save(rivista);
                    System.out.println("🗞️ Rivista aggiunta con successo al DB!");
                }
                // ELIMINA ELEMENTO TRAMITE ISBN
                case 3 -> {
                    System.out.print("Inserisci ISBN dell'elemento da eliminare: ");
                    String isbn = scanner.nextLine();
                    Elemento elemento = elementoDAO.findByISBN(isbn);
                    if (elemento != null) {
                        elementoDAO.delete(elemento.getId());
                        System.out.println("🗑️ Elemento eliminato con successo!");
                    } else {
                        System.out.println("⚠️ Elemento non trovato!");
                    }
                }
                // CERCA ELEMENTO PER ISBN
                case 4 -> {
                    System.out.print("Inserisci ISBN: ");
                    String isbn = scanner.nextLine();
                    Elemento elemento = elementoDAO.findByISBN(isbn);
                    System.out.println(elemento != null ? elemento : "⚠️ Elemento non trovato!");
                }
                // CERCA UN ELEMENTO PER TITOLO
                case 5 -> {
                    System.out.print("Inserisci il titolo o una sua parte: ");
                    String titolo = scanner.nextLine();
                    List<Elemento> elementi = elementoDAO.findByTitle(titolo);
                    if (elementi.isEmpty()) {
                        System.out.println("⚠️ Nessun elemento trovato!");
                    } else {
                        elementi.forEach(System.out::println);
                    }
                }
                // CERCA LIBRI PER AUTORE
                case 6 -> {
                    System.out.print("Inserisci l'autore: ");
                    String autore = scanner.nextLine();
                    List<Libri> libri = libriDAO.findByAuthor(autore);
                    if (libri.isEmpty()) {
                        System.out.println("⚠️ Nessun libro trovato!");
                    } else {
                        libri.forEach(System.out::println);
                    }
                }
                // CERCA LIBRI PER GENERE
                case 7 -> {
                    System.out.print("Inserisci il genere: ");
                    String genere = scanner.nextLine();
                    List<Libri> libri = libriDAO.findByGenre(genere);
                    if (libri.isEmpty()) {
                        System.out.println("⚠️ Nessun libro trovato!");
                    } else {
                        libri.forEach(System.out::println);
                    }
                }
                // CERCA RIVISTE PER IL PERIODO ENUM
                case 8 -> {
                    System.out.print("Inserisci la periodicità (SETTIMANALE, MENSILE, SEMESTRALE): ");
                    String periodicitaInput = scanner.nextLine();
                    Periodicita periodicita = Periodicita.valueOf(periodicitaInput.toUpperCase());
                    List<Riviste> riviste = rivisteDAO.findByPeriodicita(periodicita);
                    if (riviste.isEmpty()) {
                        System.out.println("⚠️ Nessuna rivista trovata!");
                    } else {
                        riviste.forEach(System.out::println);
                    }
                }
                // REGISTRA UN NUOVO PRESTITO
                case 9 -> {
                    System.out.print("Inserisci il numero tessera utente del DB: ");
                    String numeroTessera = scanner.nextLine();
                    Utente utente = utenteDAO.findAll().stream()
                            .filter(u -> u.getNumeroTessera().equals(numeroTessera))
                            .findFirst()
                            .orElse(null);

                    if (utente == null) {
                        System.out.println("⚠️ Utente non trovato!");
                        break;
                    }

                    System.out.print("Inserisci ISBN dell'elemento da prestare: ");
                    String isbn = scanner.nextLine();
                    Elemento elemento = elementoDAO.findByISBN(isbn);

                    if (elemento == null) {
                        System.out.println("⚠️ Elemento non trovato!");
                        break;
                    }

                    // CREO IL PRESTITO CON DATE CALCOLA IN AUTOMATICO
                    GestionePrestito prestito = new GestionePrestito(utente, elemento);

                    // MESSAGGIO DI SALVATAGGIO NEL DATABASE
                    gestionePrestitoDAO.save(prestito);

                    System.out.println("📦 Prestito registrato con successo!");
                    System.out.println("Dettagli del prestito:");
                    System.out.println("📅 Data inizio prestito: " + prestito.getDataInizioPrestito());
                    System.out.println("📅 Data restituzione prevista: " + prestito.getDataRestituzionePrevista());
                }
                // PRODOTTI SCADUTI

                case 10 -> {
                    List<GestionePrestito> prestitiScaduti = gestionePrestitoDAO.findExpiredLoans();
                    if (prestitiScaduti.isEmpty()) {
                        System.out.println("⚠️ Nessun prestito scaduto trovato!");
                    } else {
                        prestitiScaduti.forEach(System.out::println);
                    }
                }
                // PRESTITI PER UTENTE

                case 11 -> {
                    System.out.print("Inserisci il numero tessera utente: ");
                    String numeroTessera = scanner.nextLine();
                    List<GestionePrestito> prestiti = gestionePrestitoDAO.findCurrentLoansByUser(numeroTessera);
                    if (prestiti.isEmpty()) {
                        System.out.println("⚠️ Nessun prestito trovato!");
                    } else {
                        prestiti.forEach(System.out::println);
                    }
                }
                // CREA UN NUOVO UTENTE DOVE ASSOCIAMO UNA NUOVA TESSERA

                case 12 -> {
                    System.out.print("Inserisci il nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Inserisci il cognome: ");
                    String cognome = scanner.nextLine();
                    System.out.print("Inserisci la data di nascita (formato: AAAA-MM-GG): ");
                    String dataNascitaInput = scanner.nextLine();
                    LocalDate dataNascita;
                    try {
                        dataNascita = LocalDate.parse(dataNascitaInput); // CONVERTO STRINGA IN LOCALDATE
                    } catch (Exception e) {
                        System.out.println("⚠️ Data non valida. Riprova.");
                        break;
                    }
                    System.out.print("Inserisci il numero di tessera (univoco): ");
                    String numeroTessera = scanner.nextLine();

                    // CREAZIONE OGGETTO UTENTE
                    Utente utente = new Utente();
                    utente.setNome(nome);
                    utente.setCognome(cognome);
                    utente.setDataNascita(dataNascita);
                    utente.setNumeroTessera(numeroTessera);

                    // LO SALVO E AGGIUNGO AL NOSTRO DATABASE
                    utenteDAO.save(utente);
                    System.out.println("👤 Utente aggiunto con successo al DB: " + utente);
                }
                case 13 -> { // BYE BYE
                    System.out.println("👋 Arrivederci!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("⚠️ Scelta non valida. Riprova.");
            }
        }
    }
}