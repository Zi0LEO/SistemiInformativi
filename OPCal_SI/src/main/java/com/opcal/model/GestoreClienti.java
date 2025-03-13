package main.java.com.opcal.model;

import org.apache.torque.TorqueException;
import org.apache.torque.criteria.Criteria;
import org.apache.torque.map.ColumnMap;
import org.apache.torque.om.*;

import java.util.ArrayList;
import java.util.List;

public class GestoreClienti {

    /**
     * Permette di creare un'oggetto di tipo Cliente e inserirlo all'interno della base di dati
     *
     * @throws CloneNotSupportedException Nel caso in cui il Cliente che si sta cercando di creare è gia presente
     * @return true se l'operazione va a buon fine, <br> false altrimenti.
     */
    public boolean creaCliente(DatiCliente datoCliente, Indirizzo indirizzoCliente) throws CloneNotSupportedException{
        if (esiste(datoCliente.getEmail())) throw new CloneNotSupportedException("Il cliente è già esistente");

        Cliente cliente = new Cliente();
        try {
            cliente.addIndirizzo(indirizzoCliente);
            cliente.setEmail(datoCliente.getEmail());
            cliente.setNome(datoCliente.getNome());
            cliente.setCognome(datoCliente.getCognome());
            cliente.save();
        } catch (TorqueException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Permette di modificare il nome di un cliente già presente all'interno della base di dati.
     *
     * @param email L'email del cliente da modificare
     * @param nome Il nuovo nome
     * @throws ClassNotFoundException Nel caso in cui il cliente che si cerca di modificare non esiste
     */
    public void modificaNomeCliente(String email, String nome) throws ClassNotFoundException{
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
     * @throws ClassNotFoundException nel caso in cui il cliente che si cerca di modificare non esiste
     * @param email l'email del cliente da modificare,
     */
    public void modificaCognomeCliente(String email, String cognome) throws ClassNotFoundException{
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
    public void modificaNomeCliente(Cliente cliente, String nome) throws ClassNotFoundException{
        modificaNomeCliente(cliente.getEmail(), nome);
    }
    /**
     * Permette di modificare il cognome di un cliente già presente all'interno della base di dati.
     * Nel caso in cui il cliente da modificare non esiste lo crea.
     *
     * @param cliente Il cliente da modificare.
     * @param cognome Il nuovo cognome
     * @throws ClassNotFoundException Nel caso in cui il cliente che si cerca di modificare non esiste
     *
     */
    public void modificaCognomeCliente(Cliente cliente, String cognome) throws ClassNotFoundException{
        modificaCognomeCliente(cliente.getEmail(), cognome);
    }

    /**
     * Permette di cancellare un cliente già esistente all'interno della base di dati
     *
     * @param cliente L'oggetto di tipo cliente che dovrà essere eliminato
     * @throws ClassNotFoundException Nel caso in cui il cliente non è presente nella classe
     */
    public void cancellaCliente(Cliente cliente) throws ClassNotFoundException {
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
    public void cancellaCliente(String email) throws ClassNotFoundException {
        try {
            cancellaCliente(ClientePeer.retrieveByPK(email));
        } catch (TorqueException e) {
            throw new ClassNotFoundException("Il cliente non è stato trovato");
        }
    }

    /**Permette di ricevere la lista delle spedizioni a consegnate a un cliente
     *
     *
     * @param cliente Il cliente che richiede le sue consegne
     * @return La lista delle consegne in ordine alfabetico, è inizializzata come ArrayList.
     */
    public List<Spedizione> storicoConsegne(Cliente cliente){
        return storicoConsegneImpl(cliente,new Criteria().addAscendingOrderByColumn(EffettuataPeer.DATA_CONSEGNA));
    }



    /**Permette di ricevere la lista delle spedizioni a consegnate a un cliente
     *
     *
     * @param cliente Il cliente che richiede le sue consegne
     * @param tipo Un intero tra 0 e 3 che indica il tipo di ordinamento, <br> 0 ritorna l'ordinamento crescente per data, <br> 1 decrescente per data,
     * <br> 2 crescente per codice, <br> 3 decrescente per codice.
     * @return Un'oggetto di tipo List che è la lista delle spedizioni, è inizializzata come ArrayList.
     */
    public List<Spedizione> storicoConsegne(Cliente cliente,int tipo){

        if(tipo == 0) return storicoConsegneImpl(cliente,new Criteria().addAscendingOrderByColumn(EffettuataPeer.DATA_CONSEGNA));
        if(tipo == 1) return storicoConsegneImpl(cliente,new Criteria().addDescendingOrderByColumn(EffettuataPeer.DATA_CONSEGNA));
        if(tipo == 2) return storicoConsegneImpl(cliente,new Criteria().addAscendingOrderByColumn(SpedizionePeer.CODICE));
        if(tipo == 3) return storicoConsegneImpl(cliente,new Criteria().addDescendingOrderByColumn(SpedizionePeer.CODICE));

        throw new IllegalArgumentException("Tipo non ammesso");
    }


    private List<Spedizione> storicoConsegneImpl(Cliente cliente,Criteria criteria){
        //SELECT codice FROM spedizione s JOIN effettuata e ON s.codice=e.codice JOIN cliente c ON s.emailDestinatario=c.email
        criteria.addJoin(SpedizionePeer.CODICE, EffettuataPeer.CODICE);
        criteria.addJoin(SpedizionePeer.EMAIL_DESTINATARIO,ClientePeer.EMAIL);

        criteria.addSelectColumn(SpedizionePeer.CODICE);

        List<Spedizione> results = new ArrayList<>();

        try {
            results = SpedizionePeer.doSelect(criteria);
        } catch (TorqueException e) {
            System.out.println("Errore nella query");
        }

        return results;
    }


    private boolean esiste(String email){
        try {
            ClientePeer.retrieveByPK(email);
        } catch (TorqueException e){
            return false;
        }
        return true;
    }
}
