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
            c.setNome(nome);
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
            c.setCognome(cognome);
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
     * @throws ClassNotFoundException Nel caso in cui il cliente non è presente nella classe
     */
    public static void cancellaCliente(Cliente cliente) throws ClassNotFoundException {
        try {
            ClientePeer.doDelete(cliente);
        } catch (TorqueException e) {
            throw new ClassNotFoundException("Il cliente non è stato trovato");
        }
    }

    /**
     * Permette di cancellare un cliente già esistente all'interno della base di dati
     *
     * @param email L'identificatore del cliente che dovrà essere eliminato
     * @throws ClassNotFoundException Nel caso in cui il cliente non è presente nella classe
     */
    public static void cancellaCliente(String email) throws ClassNotFoundException {
        try {
            cancellaCliente(ClientePeer.retrieveByPK(email));
        } catch (TorqueException e) {
            throw new ClassNotFoundException("Il cliente non è stato trovato");
        }
    }

    /**
     * Permette di ricevere la lista delle spedizioni a consegnate a un cliente ordinate in ordine ascendente per data.
     *
     * @param cliente Il cliente che richiede le sue consegne
     * @return La lista delle consegne in ordine alfabetico, è inizializzata come ArrayList.
     */
    public List<Spedizione> getStoricoConsegne(Cliente cliente) {
        return getStoricoConsegne(cliente, 1);
    }

    /**
     * Permette di ricevere la lista delle spedizioni a consegnate a un cliente
     *
     * @param cliente Il cliente che richiede le sue consegne
     * @param tipo    Un intero tra 0 e 3 che indica il tipo di ordinamento, <br> 0 ritorna l'ordinamento crescente per data, <br> 1 decrescente per data,
     *                <br> 2 crescente per codice, <br> 3 decrescente per codice.
     * @return Un'oggetto di tipo List che è la lista delle spedizioni, è inizializzata come ArrayList.
     */
    public static List<Spedizione> getStoricoConsegne(Cliente cliente, int tipo) {
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

    private static List<Spedizione> storicoConsegneImpl(Cliente cliente, Criteria criteria) {
        //SELECT codice
        // FROM spedizione s JOIN effettuata e ON s.codice=e.codice
        // WHERE s.email_destinatario = cliente.email

        criteria.addJoin(SpedizionePeer.CODICE, EffettuataPeer.CODICE);
        criteria.where(SpedizionePeer.EMAIL_DESTINATARIO,cliente.getEmail());

        return getSpedizioni(criteria);
    }


    /**
     * Permette di ricevere la lista delle ricevute di un cliente ordinate in ordine ascendente per data.
     *
     * @param cliente Il cliente che richiede le sue ricevute
     * @return Un'oggetto di tipo List che è la lista delle ricevute     , è inizializzata come ArrayList.
     */

    public static List<Ricevuta> getListaRicevute(Cliente cliente){
        return getListaRicevute(cliente,1);
    }

   /**
    * Permette di ricevere la lista delle ricevute di un cliente.
    *
    * @param cliente Il cliente che richiede le sue ricevute
    * @param tipo    Un intero tra 0 e 3 che indica il tipo di ordinamento, <br> 0 ritorna l'ordinamento crescente per data, <br> 1 decrescente per data,
    *                <br> 2 crescente per codice, <br> 3 decrescente per codice, <br> 4 crescente per stato, <br> 5 decrescente per stato.
    * @return Un'oggetto di tipo List che è la lista delle ricevute     , è inizializzata come ArrayList.
    */
    public static List<Ricevuta> getListaRicevute(Cliente cliente, int tipo){
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


    private static List<Spedizione> storicoSpedizioniImpl(Cliente cliente, Criteria criteria) {
        //SELECT codice
        // FROM spedizione s
        // WHERE s.email_destinatario = cliente.email and s.codice NOT IN ( Select codice FROM Effettuata )

        criteria.where(SpedizionePeer.EMAIL_DESTINATARIO,cliente.getEmail());
        criteria.where(SpedizionePeer.CODICE,EffettuataPeer.CODICE,Criteria.NOT_IN);

        return getSpedizioni(criteria);
    }

    private static List<Spedizione> getSpedizioni(Criteria criteria) {
        criteria.addSelectColumn(SpedizionePeer.CODICE);

        List<Spedizione> ris = new ArrayList<>();

        try {
            ris = SpedizionePeer.doSelect(criteria);
        } catch (TorqueException e) {
            System.out.println("Errore nella query");
        }

        return ris;
    }

    public static List<Cliente> getListaClienti(Dipendente dipendente,int tipo){
        if(tipo == 1)
            return listaClientiImpl(dipendente,new Criteria().addAscendingOrderByColumn(UtentePeer.COGNOME));
        else if (tipo == 2)
            return listaClientiImpl(dipendente,new Criteria().addDescendingOrderByColumn(UtentePeer.COGNOME));

        else
            throw new IllegalArgumentException("Tipo non ammesso");
    }

    private static List<Cliente> listaClientiImpl(Dipendente dipendente,Criteria criteria) {

        criteria.addJoin(UtentePeer.EMAIL,ClientePeer.EMAIL);
        criteria.addSelectColumn(ClientePeer.EMAIL);
        criteria.addDescendingOrderByColumn(UtentePeer.COGNOME);

        List<Cliente> ris = new ArrayList<>();

        try {
            ris = ClientePeer.doSelect(criteria);
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