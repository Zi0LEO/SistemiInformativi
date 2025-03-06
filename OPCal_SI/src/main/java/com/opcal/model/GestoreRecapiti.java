package com.opcal.model;

import org.apache.torque.TorqueException;
import org.apache.torque.criteria.Criteria;
import org.apache.torque.om.*;

import java.sql.Date;
import java.util.List;

public class GestoreRecapiti {
    private static int recapitiGestiti;
    private static final int MILLS_IN_DAY = 24 * 60 * 60 * 1000;

    public static boolean creaSpedizione(Cliente mittente, Cliente destinatario, int peso){
        InCorso inCorso = new InCorso();
        inCorso.setStato("Presa in carico");
        inCorso.setDataSpedizione(new Date(System.currentTimeMillis()));
        Spedizione spedizione = new Spedizione();
        try {
            inCorso.save();
            spedizione.addInCorso(inCorso);
        } catch (TorqueException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return creaOperazione(mittente, destinatario, peso, spedizione);
    }

    private static boolean creaOperazione(Cliente mittente, Cliente destinatario, int peso, Spedizione spedizione) {
        spedizione.setCodice(Integer.valueOf(recapitiGestiti).toString());
        recapitiGestiti++;
        spedizione.setPeso(peso);
        spedizione.setEmailMittente(mittente.getEmail());
        spedizione.setEmailDestinatario(destinatario.getEmail());
        spedizione.setPrezzo(calcolaPrezzo(peso));
        try {
            spedizione.save();
        } catch (TorqueException e) {
            System.out.println(e.getMessage());
            return false;
        }
        creaRicevuta(spedizione);
        return true;
    }

    private static void creaRicevuta(Spedizione spedizione) {
        Ricevuta ricevuta = new Ricevuta();
        ricevuta.setData(new Date(System.currentTimeMillis()));
        ricevuta.setSpedizione(spedizione);
        try {
            ricevuta.save();
        } catch (TorqueException e) {
            System.out.println(e.getMessage());
        }
    }

    //temporaneo
    private static int calcolaPrezzo(int peso) {
        return peso * 5;
    }

    public static boolean creaRitiro(Cliente mittente, Cliente destinatario, int peso){
        Prenotata prenotata = new Prenotata();
        prenotata.setDataPrenotazione(new Date(System.currentTimeMillis()));

        //ritiro di default 7 giorni dopo, in real dovrebbe confrontarsi con il gestore magazzino
        prenotata.setDataRitiro(new Date(System.currentTimeMillis()+MILLS_IN_DAY*7));

        Spedizione spedizione = new Spedizione();
        try {
            prenotata.save();
            spedizione.addPrenotata(prenotata);
        } catch (TorqueException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return creaOperazione(mittente, destinatario, peso, spedizione);
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

    public static List<Indirizzo> visualizzaIndirizzi(int criterio){
       Criteria criteria = new Criteria();
       switch (criterio){
           case 1: criteria.where()
       }
    }

    public static List<Indirizzo> visualizzaIndirizzo(){
        Criteria crit = new Criteria();
        crit.addAscendingOrderByColumn(IndirizzoPeer.COMUNE);
        crit.addAscendingOrderByColumn(IndirizzoPeer.VIA);
        crit.addAscendingOrderByColumn(IndirizzoPeer.CIVICO);
        try {
            List<Indirizzo> indirizzi = IndirizzoPeer.doSelect(crit);
        } catch (TorqueException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
