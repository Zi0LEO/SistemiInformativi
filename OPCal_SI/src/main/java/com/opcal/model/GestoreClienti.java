package com.opcal.model;

import com.opcal.*;
import org.apache.torque.TorqueException;
import org.apache.torque.criteria.Criteria;
import org.apache.torque.util.Transaction;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GestoreClienti {

    public static DatiCliente autentica(String email, String password) {
        Utente utente;
        Connection connection = null;
        try{
            utente = UtentePeer.retrieveByPK(email);
            if(utente.getPassword().equals(password))
                return new DatiCliente(utente.getNome(), utente.getCognome(), utente.getEmail());
        }catch (TorqueException e){
            return null;
        }
        return null;
    }

    /**
     * Permette di creare un'oggetto di tipo Cliente e inserirlo all'interno della base di dati
     *
     * @return true se l'operazione va a buon fine, <br> false altrimenti.
     * @throws CloneNotSupportedException Nel caso in cui il Cliente che si sta cercando di creare è gia presente
     */
    public static boolean creaCliente(DatiCliente datoCliente) throws CloneNotSupportedException {
        if (esiste(datoCliente.getEmail())) throw new CloneNotSupportedException("Il cliente è già esistente");

        Connection connection = null;
        Cliente cliente;
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
            return false;
        }
        return true;
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

    public static Dati trovaUtente(String email) {
        Criteria criteria = new Criteria();
        criteria.where(UtentePeer.EMAIL, email);
        criteria.addJoin(UtentePeer.EMAIL, DipendentePeer.EMAIL);
        Utente utente;
        try {
            utente = UtentePeer.doSelect(criteria).getFirst();
            return new DatiDipendente(utente.getNome(), utente.getCognome(), email);
        } catch (Exception e) {}
        criteria = new Criteria();
        criteria.where(UtentePeer.EMAIL, email);
        criteria.addJoin(UtentePeer.EMAIL, ClientePeer.EMAIL);
        try {
            utente = UtentePeer.doSelect(criteria).getFirst();
            return new DatiCliente(utente.getNome(), utente.getCognome(), email);
        } catch (TorqueException e) {
            e.printStackTrace();
            return null;
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




    public static List<Object[]> getListaRicevute(String email) {
        //SELECT *
        // FROM Ricevuta JOIN sedizione s ON r.codiceSpedizione=s.codice
        // WHERE s.email_mittente = cliente.email()

        Criteria criteria = new Criteria();
        criteria.addJoin(RicevutaPeer.CODICE, SpedizionePeer.CODICE);
        if(email != null)
            criteria.where(SpedizionePeer.EMAIL_MITTENTE, email);

        List<Ricevuta> ris;
        try {
            ris = RicevutaPeer.doSelect(criteria);
        } catch (TorqueException e) {
            System.out.println("Errore nella query");
            return null;
        }
        return buildReturn(ris);
    }

    private static List<Object[]> buildReturn(List<Ricevuta> ris) {
        List<Object[]> returnList = new ArrayList<>();
        for (Ricevuta r : ris) {
            Object[] obj = new Object[4];
            obj[0] = r.getCodice();
            obj[1] = r.getData();
            obj[2] = r.getStato();
            obj[3] = associaPrezzo(r.getCodice());
            returnList.add(obj);
        }
        return returnList;
    }

    private static Object associaPrezzo(Integer codice) {
        try {
            return SpedizionePeer.retrieveByPK(codice).getPrezzo() + " EUR";
        }catch (TorqueException e){
            return 0;
        }
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

    public static List<Cliente> getListaClienti() {
        try{
            return ClientePeer.doSelect(new Criteria());
        }catch (TorqueException e){
            e.printStackTrace();
            return null;
        }
    }

    public static List<Object[]> listaClienti() {
        Criteria criteria = new Criteria();
        criteria.addJoin(UtentePeer.EMAIL, ClientePeer.EMAIL);

        List<Utente> partialRis = new ArrayList<>();

        try {
            partialRis = UtentePeer.doSelect(criteria);
        } catch (TorqueException e) {
            System.out.println("Errore nella query");
        }

        return costruisciClienti(partialRis);
    }

    private static List<Object[]> costruisciClienti(List<Utente> partialRet) {
        List<Object[]> result = new LinkedList<>();
        for(Utente u: partialRet) {
            Object[] row = new Object[3];
            row[0] = u.getNome();
            row[1] = u.getCognome();
            row[2] = u.getEmail();
            result.add(row);
        }
        return result;
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