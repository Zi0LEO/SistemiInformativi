package com.opcal.model;

import org.apache.torque.TorqueException;
import org.apache.torque.om.ClientePeer;
import org.apache.torque.om.Reso;
import org.apache.torque.om.ResoPeer;

import java.util.Date;

import static com.opcal.model.StatoReso.statoPossibile;

public class GestoreResi {

    /** Permette di creare un'oggetto di tipo reso e inserirlo all'interno della base di dati
     *
     *
     * @param codice Il codice del reso da inserire
     * @param stato Lo stato del reso da inserire
     * @param data La data del reso da inserire
     * @return true se l'operazion va a buon fine <br> false se l'operazione ha incontrato degli errrori
     * @throws CloneNotSupportedException
     */
    public boolean creaReso(String codice, String stato, Date data) throws CloneNotSupportedException {
        if(esiste(codice)) throw new CloneNotSupportedException("Il reso è già esistente");
        if(!statoPossibile(stato)) throw new IllegalArgumentException("Stato non valido");

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

    /**Permette di modificare l'attributo stato di un reso già esistente all'interno della base di dati
     *
     * @param reso Il reso a cui bisogna modificare lo stato
     * @param newStato Il nuovo stato
     * @throws ClassNotFoundException Nel caso in cui non esiste il reso inserito
     */
    public void modificaStatoReso(Reso reso, String newStato) throws ClassNotFoundException {
        modificaStatoReso(reso.getCodice(),newStato);
    }


    /**Permette di modificare l'attributo stato di un reso già esistente all'interno della base di dati
     *
     * @param codice Il codice del reso a cui bisogna modificare lo stato
     * @param newStato Il nuovo stato
     * @throws ClassNotFoundException Nel caso in cui non esiste il reso inserito
     */
    public void modificaStatoReso(String codice, String newStato) throws ClassNotFoundException {
        if (!statoPossibile(newStato)) throw new IllegalArgumentException("Stato non valido");

        try {
            Reso r = ResoPeer.retrieveByPK(codice);
            r.setStato(newStato);
            r.save();
        } catch (TorqueException e) {
            throw new ClassNotFoundException("Il Reso non esiste");
        }
    }

    public void stampaEtichettaReso(Reso reso){

    }


    private boolean esiste(String codice){
        try {
            ResoPeer.retrieveByPK(codice);
        } catch (TorqueException e){
            return false;
        }
        return true;
    }

}
