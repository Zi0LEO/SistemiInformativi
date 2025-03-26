package com.opcal.model;

import com.opcal.Reso;
import com.opcal.ResoPeer;
import com.opcal.SpedizionePeer;
import org.apache.torque.TorqueException;
import org.apache.torque.criteria.Criteria;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class GestoreResi {

    /**
     * Permette di creare un'oggetto di tipo reso e inserirlo all'interno della base di dati, se è già esistente non fa nulla
     *
     * @param codice           Il codice del reso da inserire
     * @param emailRichiedente L'indirizzo email del richiedente
     *
     */
    public static void creaReso(Integer codice, String emailRichiedente) throws TorqueException {
        if (esiste(codice)) return;
        if (!emailRichiedente.equals(SpedizionePeer.retrieveByPK(codice).getEmailDestinatario()))
            throw new IllegalArgumentException("Richiesta non valida");

        Reso r = new Reso();
        r.setCodice(codice);
        r.setStato("RICHIESTO");
        r.setData(new Date(System.currentTimeMillis()));

        r.save();
    }

    /**
     * Permette di modificare l'attributo stato di un reso già esistente all'interno della base di dati
     *
     * @param reso     Il reso a cui bisogna modificare lo stato
     * @param newStato Il nuovo stato
     * @return True se l'operazione va a buon fine <br> False Nel caso in cui non esiste il reso inserito
     */
    public static boolean modificaStatoReso(Reso reso, String newStato) {
       return modificaStatoReso(reso.getCodice(), newStato);
    }


    /**
     * Permette di modificare l'attributo stato di un reso già esistente all'interno della base di dati
     *
     * @param codice   Il codice del reso a cui bisogna modificare lo stato
     * @param newStato Il nuovo stato
     * @return True se l'operazione va a buon fine <br> False Nel caso in cui non esiste il reso inserito <br>
     */
    public static boolean modificaStatoReso(int codice, String newStato){
        if (!StatoReso.statoPossibile(newStato)) throw new IllegalArgumentException("Stato non valido");

        try {
            Reso r = ResoPeer.retrieveByPK(codice);
            r.setStato(newStato);
            r.save();
        } catch (TorqueException e) {
            return false;
        }
        return true;
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

    /**Permette di visualizzare l'intera lista dei resi di un cliente
     *
     * @param email l'email del cliente
     * @return la lista degli array dei resi
     */
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
