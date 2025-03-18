package com.opcal.model;

import com.opcal.*;
import org.apache.torque.TorqueException;
import org.apache.torque.criteria.Criteria;
import org.apache.torque.util.Transaction;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class GestoreClienti {

    /**
     * Permette di creare un'oggetto di tipo Cliente e inserirlo all'interno della base di dati
     *
     * @return true se l'operazione va a buon fine, <br> false altrimenti.
     * @throws CloneNotSupportedException Nel caso in cui il Cliente che si sta cercando di creare è gia presente
     */
    public static Cliente creaCliente(DatiCliente datoCliente) throws CloneNotSupportedException {
        if (esiste(datoCliente.getEmail())) throw new CloneNotSupportedException("Il cliente è già esistente");

        Connection connection = null;
        Cliente cliente = null;
        try {
            connection = Transaction.begin();
            Utente utente = new Utente(datoCliente.getNome(), datoCliente.getCognome(), datoCliente.getEmail(), datoCliente.getPassword());
            utente.save();
            cliente = new Cliente(datoCliente.getEmail());
            cliente.setUtente(utente);
            cliente.save();
            Transaction.commit(connection);
        } catch (TorqueException e) {
            Transaction.safeRollback(connection);
            e.printStackTrace();
        }
        return cliente;
    }

    /**
     * Permette di modificare il nome di un cliente già presente all'interno della base di dati.
     *
     * @param email L'email del cliente da modificare
     * @param nome  Il nuovo nome
     * @throws ClassNotFoundException Nel caso in cui il cliente che si cerca di modificare non esiste
     */
    public static void modificaNomeCliente(String email, String nome) throws ClassNotFoundException {
        try {
            Cliente c = ClientePeer.retrieveByPK(email);
            Utente u = c.getUtente();
            u.setNome(nome);
            u.save();
            c.save();
        } catch (TorqueException e) {
            throw new ClassNotFoundException("Il cliente non esiste");
        }
    }

    /**
     * Permette di modificare il cognome di un cliente già presente all'interno della base di dati.
     *
     * @param email l'email del cliente da modificare,
     * @throws ClassNotFoundException nel caso in cui il cliente che si cerca di modificare non esiste
     */
    public static void modificaCognomeCliente(String email, String cognome) throws ClassNotFoundException {
        try {
            Cliente c = ClientePeer.retrieveByPK(email);
            Utente u = c.getUtente();
            u.setCognome(cognome);
            u.save();
            c.save();
        } catch (TorqueException e) {
            throw new ClassNotFoundException("Il cliente non esiste");
        }
    }

    /**
     * Permette di modificare il nome di un cliente già presente all'interno della base di dati.
     *
     * @param cliente il cliente da modificare.
     * @throws ClassNotFoundException nel caso in cui il cliente che si cerca di modificare non esiste
     */
    public static void modificaNomeCliente(Cliente cliente, String nome) throws ClassNotFoundException {
        modificaNomeCliente(cliente.getEmail(), nome);
    }

    /**
     * Permette di modificare il cognome di un cliente già presente all'interno della base di dati.
     * Nel caso in cui il cliente da modificare non esiste lo crea.
     *
     * @param cliente Il cliente da modificare.
     * @param cognome Il nuovo cognome
     * @throws ClassNotFoundException Nel caso in cui il cliente che si cerca di modificare non esiste
     */
    public static void modificaCognomeCliente(Cliente cliente, String cognome) throws ClassNotFoundException {
        modificaCognomeCliente(cliente.getEmail(), cognome);
    }

    /**
     * Permette di cancellare un cliente già esistente all'interno della base di dati
     *
     * @param cliente L'oggetto di tipo cliente che dovrà essere eliminato
     */
    public static boolean cancellaCliente(Cliente cliente) {
        try {
            System.out.println("qui");
            ClientePeer.doDelete(cliente);
            System.out.println("utente eliminato");
            UtentePeer.doDelete(cliente.getUtente());
            System.out.println("cliente eliminato");
            return true;
        } catch (TorqueException e) {
            return false;
        }

    }

    /**
     * Permette di cancellare un cliente già esistente all'interno della base di dati
     *
     * @param email L'identificatore del cliente che dovrà essere eliminato
     */
    public static boolean cancellaCliente(String email){
        try{
            Criteria criteria1 = new Criteria();
            Criteria criteria2 = new Criteria();

            criteria1.where(ClientePeer.EMAIL, email);
            criteria2.where(UtentePeer.EMAIL, email);

            ClientePeer.doDelete(criteria1);
            UtentePeer.doDelete(criteria2);
            return true;
        } catch (TorqueException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Permette di ricevere la lista delle spedizioni a consegnate a un cliente
     *
     * @param cliente Il cliente che richiede le sue consegne
     * @return La lista delle consegne in ordine alfabetico, è inizializzata come ArrayList.
     */
    public List<Spedizione> storicoConsegne(Cliente cliente) {
        return storicoConsegneImpl(cliente, new Criteria().addAscendingOrderByColumn(EffettuataPeer.DATA_CONSEGNA));
    }


    /**
     * Permette di ricevere la lista delle spedizioni a consegnate a un cliente
     *
     * @param cliente Il cliente che richiede le sue consegne
     * @param tipo    Un intero tra 0 e 3 che indica il tipo di ordinamento, <br> 0 ritorna l'ordinamento crescente per data, <br> 1 decrescente per data,
     *                <br> 2 crescente per codice, <br> 3 decrescente per codice.
     * @return Un'oggetto di tipo List che è la lista delle spedizioni, è inizializzata come ArrayList.
     */
    public static List<Spedizione> storicoConsegne(Cliente cliente, int tipo) {
        switch (tipo) {
            case 1:
                return storicoConsegneImpl(cliente, new Criteria().addAscendingOrderByColumn(EffettuataPeer.DATA_CONSEGNA));
            case 2:
                return storicoConsegneImpl(cliente, new Criteria().addDescendingOrderByColumn(EffettuataPeer.DATA_CONSEGNA));
            case 3:
                return storicoConsegneImpl(cliente, new Criteria().addAscendingOrderByColumn(SpedizionePeer.CODICE));
            case 4:
                return storicoConsegneImpl(cliente, new Criteria().addDescendingOrderByColumn(SpedizionePeer.CODICE));
            default:
                throw new IllegalArgumentException("Tipo non ammesso");
        }
    }


    /**
     * Permette di ricevere la lista delle ricevute di un cliente
     *
     * @param cliente Il cliente che richiede le sue ricevute
     * @param tipo    Un intero tra 0 e 3 che indica il tipo di ordinamento, <br> 0 ritorna l'ordinamento crescente per data, <br> 1 decrescente per data,
     *                <br> 2 crescente per codice, <br> 3 decrescente per codice, <br> 4 crescente per stato, <br> 5 decrescente per stato.
     * @return Un'oggetto di tipo List che è la lista delle ricevute     , è inizializzata come ArrayList.
     */
    public static List<Ricevuta> listaRicevute(Cliente cliente, int tipo){
        switch (tipo) {
            case 1:
                return listaRicevuteImpl(cliente, new Criteria().addAscendingOrderByColumn(RicevutaPeer.DATA));
            case 2:
                return listaRicevuteImpl(cliente, new Criteria().addDescendingOrderByColumn(RicevutaPeer.DATA));
            case 3:
                return listaRicevuteImpl(cliente, new Criteria().addAscendingOrderByColumn(RicevutaPeer.CODICE));
            case 4:
                return listaRicevuteImpl(cliente, new Criteria().addDescendingOrderByColumn(RicevutaPeer.CODICE));
            case 5:
                return listaRicevuteImpl(cliente, new Criteria().addAscendingOrderByColumn(RicevutaPeer.STATO));
            case 6:
                return listaRicevuteImpl(cliente, new Criteria().addDescendingOrderByColumn(RicevutaPeer.STATO));
            default:
                throw new IllegalArgumentException("Tipo non ammesso");
        }
    }

    private static List<Ricevuta> listaRicevuteImpl(Cliente cliente, Criteria criteria) {
        //SELECT codice
        // FROM Ricevuta JOIN sedizione s ON r.codiceSpedizione=s.codice
        // WHERE s.email_mittente = cliente.email()

        criteria.addJoin(RicevutaPeer.CODICE, SpedizionePeer.CODICE);
        criteria.where(SpedizionePeer.EMAIL_MITTENTE, cliente.getEmail());

        criteria.addSelectColumn(RicevutaPeer.CODICE);

        List<Ricevuta> ris = new ArrayList<>();

        try {
            ris = RicevutaPeer.doSelect(criteria);
        } catch (TorqueException e) {
            System.out.println("Errore nella query");
        }
        return ris;
    }
    
    private static List<Spedizione> storicoConsegneImpl(Cliente cliente, Criteria criteria) {
        //SELECT codice
        // FROM spedizione s JOIN effettuata e ON s.codice=e.codice
        // WHERE s.email_destinatario = cliente.email

        criteria.addJoin(SpedizionePeer.CODICE, EffettuataPeer.CODICE);
        criteria.where(SpedizionePeer.EMAIL_DESTINATARIO,cliente.getEmail());

        criteria.addSelectColumn(SpedizionePeer.CODICE);

        List<Spedizione> ris = new ArrayList<>();

        try {
            ris = SpedizionePeer.doSelect(criteria);
        } catch (TorqueException e) {
            System.out.println("Errore nella query");
        }

        return ris;
    }

    private static boolean esiste(String email) {
        try {
            ClientePeer.retrieveByPK(email);
        } catch (TorqueException e) {
            return false;
        }
        return true;
    }
}
