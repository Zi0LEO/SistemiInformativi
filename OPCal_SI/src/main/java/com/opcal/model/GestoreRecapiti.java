package com.opcal.model;

import org.apache.torque.TorqueException;
import org.apache.torque.criteria.Criteria;
import org.apache.torque.om.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class GestoreRecapiti {
    private static int recapitiGestiti;

    public static boolean creaSpedizione(Cliente mittente, Cliente destinatario, int peso){
        InCorso inCorso = new InCorso();
        String codice = generaCodice();
        Spedizione spedizione = new Spedizione(codice, mittente.getEmail(), destinatario.getEmail(), peso, calcolaPrezzo(peso));
        try {
            inCorso.save();
            spedizione.addInCorso(inCorso);
            spedizione.save();
            creaRicevuta(spedizione);
        } catch (TorqueException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean creaRitiro(Cliente mittente, Cliente destinatario, int peso){
        Prenotata prenotata = new Prenotata();
        String codice = generaCodice();
        Spedizione spedizione = new Spedizione(codice, mittente.getEmail(), destinatario.getEmail(), peso, calcolaPrezzo(peso));
        try {
            prenotata.save();
            spedizione.addPrenotata(prenotata);
            spedizione.save();
            creaRicevuta(spedizione);
        } catch (TorqueException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    private synchronized static String generaCodice() {
        recapitiGestiti++;
        Integer recapiti = recapitiGestiti;
        String ret = recapiti.toString();
        StringBuilder sb = new StringBuilder();
        sb.repeat("0", 6 - ret.length());
        sb.append(ret);
        return sb.toString();
    }

    private static void creaRicevuta(Spedizione spedizione) throws TorqueException {
        Ricevuta ricevuta = new Ricevuta();
        ricevuta.setData(new Date(System.currentTimeMillis()));
        ricevuta.setSpedizione(spedizione);
        ricevuta.save();
    }

    //temporaneo
    private static int calcolaPrezzo(int peso) {
        return peso * 5;
    }

    public static Indirizzo visualizzaIndirizzo(Cliente cliente){
        Criteria criteria = new Criteria();
        criteria.where(IndirizzoPeer.EMAIL_CLIENTE, cliente.getEmail());
        try {
            return (Indirizzo) IndirizzoPeer.doSelect(criteria);
        } catch (TorqueException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<Indirizzo> visualizzaIndirizzi(int criterio, String options){
       Criteria criteria = new Criteria();
        try {
            switch (criterio){
                case 1:
                    criteria.where(IndirizzoPeer.COMUNE, options);
                    break;
                case 2:
                    criteria.where(IndirizzoPeer.VIA, options);
                    break;
                case 3:
                    criteria.where(IndirizzoPeer.ORARIO, Integer.valueOf(options));
                    break;
            }
            return IndirizzoPeer.doSelect(criteria);

        } catch (TorqueException e) {
            System.out.println(e.getMessage());
            return new ArrayList<Indirizzo>();
        }
    }

}
