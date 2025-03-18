package main.java.com.opcal.model;

import com.itextpdf.layout.Document;
import com.opcal.Reso;
import com.opcal.ResoPeer;
import org.apache.torque.TorqueException;

import java.util.Date;

import static main.java.com.opcal.model.GeneratoreEtichette.creaEtichetta;
import static main.java.com.opcal.model.StatoReso.statoPossibile;

public class GestoreResi {

    /**
     * Permette di creare un'oggetto di tipo reso e inserirlo all'interno della base di dati
     *
     * @param codice Il codice del reso da inserire
     * @param stato  Lo stato del reso da inserire
     * @param data   La data del reso da inserire
     * @return true se l'operazion va a buon fine <br> false se l'operazione ha incontrato degli errrori
     * @throws CloneNotSupportedException nel caso in cui il reso che si sta cercando di creare è gia presente all'interno della base di dati
     */
    public static boolean creaReso(String codice, String stato, Date data) throws CloneNotSupportedException {
        if (esiste(codice)) throw new CloneNotSupportedException("Il reso è già esistente");
        if (!statoPossibile(stato)) throw new IllegalArgumentException("Stato non valido");

        Reso r = new Reso();
        r.setCodice(codice);
        r.setStato(stato);
        r.setData(data);

        try {
            r.save();
        } catch (TorqueException e) {
            return false;
        }
        return true;
    }

    /**
     * Permette di modificare l'attributo stato di un reso già esistente all'interno della base di dati
     *
     * @param reso     Il reso a cui bisogna modificare lo stato
     * @param newStato Il nuovo stato
     * @throws ClassNotFoundException Nel caso in cui non esiste il reso inserito
     */
    public static void modificaStatoReso(Reso reso, String newStato) throws ClassNotFoundException {
        modificaStatoReso(reso.getCodice(), newStato);
    }


    /**
     * Permette di modificare l'attributo stato di un reso già esistente all'interno della base di dati
     *
     * @param codice   Il codice del reso a cui bisogna modificare lo stato
     * @param newStato Il nuovo stato
     * @throws ClassNotFoundException Nel caso in cui non esiste il reso inserito
     */
    public static void modificaStatoReso(String codice, String newStato) throws ClassNotFoundException {
        if (!statoPossibile(newStato)) throw new IllegalArgumentException("Stato non valido");

        try {
            Reso r = ResoPeer.retrieveByPK(codice);
            r.setStato(newStato);
            r.save();
        } catch (TorqueException e) {
            throw new ClassNotFoundException("Il Reso non esiste");
        }
    }

    /**
     * Permette di creare un'etichetta di reso per la spedizione innserita nel parametro
     *
     * @param reso il reso di cui si deve creare l'etichetta
     * @return Un'oggetto di tipo Document che è l'etichetta.
     */
    public static Document stampaEtichettaReso(Reso reso) {
        try {
            return creaEtichetta(reso.getSpedizione());
        } catch (TorqueException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Se un reso non è già stato processato o terminato permette di annullarlo
     *
     * @param reso Il reso da annullare
     * @throws ClassNotFoundException Nel caso in cui il reso non è trovato nella base di dati
     */
    public static void annullaReso(Reso reso) throws ClassNotFoundException {
        if (reso.getStato().equals("PROCESSATO") | reso.getStato().equals("TERMINATO"))
            throw new IllegalArgumentException("Stato non valido");

        try {
            ResoPeer.doDelete(reso);
        } catch (TorqueException e) {
            throw new ClassNotFoundException("Il reso non è stato trovato");
        }
    }


    private static boolean esiste(String codice) {
        try {
            ResoPeer.retrieveByPK(codice);
        } catch (TorqueException e) {
            return false;
        }
        return true;
    }

}
