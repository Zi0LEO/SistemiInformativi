package com.opcal.model;

import com.opcal.Reso;
import com.opcal.ResoPeer;
import com.opcal.SpedizionePeer;
import org.apache.torque.TorqueException;
import org.apache.torque.criteria.Criteria;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static main.java.com.opcal.model.StatoReso.statoPossibile;

public class GestoreResi {

    /**
     * Permette di creare un'oggetto di tipo reso e inserirlo all'interno della base di dati
     *
     * @param codice           Il codice del reso da inserire
     * @param emailRichiedente L'indirizzo email del richiedente
     * @throws CloneNotSupportedException nel caso in cui il reso che si sta cercando di creare è gia presente all'interno della base di dati
     */
    public static void creaReso(Integer codice, String emailRichiedente) throws CloneNotSupportedException, TorqueException {
        if (esiste(codice)) throw new CloneNotSupportedException("Il reso è già esistente");
        if (!emailRichiedente.equals(SpedizionePeer.retrieveByPK(codice).getEmailDestinatario()))
            throw new IllegalArgumentException("Richiesta non valida");

        Reso r = new Reso();
        r.setCodice(codice);
        r.setStato("RICHIESTO");
        r.setData(new Date(System.currentTimeMillis()));

        try {
            r.save();
        } catch (TorqueException e) {
        }
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
    public static void modificaStatoReso(int codice, String newStato) throws ClassNotFoundException {
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
     * Se un reso non è già stato processato o terminato permette di annullarlo
     *
     * @param codiceReso Il reso da annullare
     * @return True se la cancellazione è andata a buon fine <br> false altrimenti.
     */
    public static boolean annullaReso(Integer codiceReso){
        Reso reso;
        try {
            reso = ResoPeer.retrieveByPK(codiceReso);
        } catch (TorqueException e) {
            return false;
        }
        if (reso.getStato().equals("PROCESSATO") | reso.getStato().equals("TERMINATO"))
            return false;

        try {
            ResoPeer.doDelete(reso);
        } catch (TorqueException e) {
            return false;
        }
        return true;
    }

    private static boolean esiste(Integer codice) {
        try {
            ResoPeer.retrieveByPK(codice);
        } catch (TorqueException e) {
            return false;
        }
        return true;
    }

    public static List<Object[]> listaResi(String email) {
        Criteria criteria = new Criteria();
        criteria.addSelectColumn(ResoPeer.CODICE);
        criteria.addSelectColumn(ResoPeer.STATO);
        criteria.addSelectColumn(ResoPeer.DATA);
        criteria.addJoin(ResoPeer.CODICE, SpedizionePeer.CODICE);
        if(email != null)
            criteria.where(SpedizionePeer.EMAIL_DESTINATARIO, email);

      try {
        return buildResoReturn(ResoPeer.doSelect(criteria));
      } catch (TorqueException e) {
          return null;
      }
    }

    private static List<Object[]> buildResoReturn(List<Reso> resi) {
        List<Object[]> returnList = new ArrayList<>();
        for (Reso r : resi) {
            Object[] obj = new Object[3];
            obj[0] = r.getCodice();
            obj[1] = r.getStato();
            obj[2] = r.getData();
            returnList.add(obj);
        }
        return returnList;
    }
}
