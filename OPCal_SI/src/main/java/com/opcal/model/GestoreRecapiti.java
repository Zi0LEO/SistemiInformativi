package com.opcal.model;

import org.apache.torque.TorqueException;
import org.apache.torque.om.Cliente;
import org.apache.torque.om.InCorso;
import org.apache.torque.om.Ricevuta;
import org.apache.torque.om.Spedizione;

import java.sql.Date;

public class GestoreRecapiti {
    private static int recapitiGestiti;

    public static boolean creaSpedizione(Cliente mittente, Cliente destinatario, int peso){
        InCorso inCorso = new InCorso();
        inCorso.setStato("Presa in carico");
        inCorso.setDataSpedizione(new Date(System.currentTimeMillis()));
        Spedizione spedizione = new Spedizione();
        try {
            spedizione.addInCorso(inCorso);
        }catch (TorqueException e){
            System.out.println(e.getMessage());
            return false;
        }
        spedizione.setCodice(Integer.valueOf(recapitiGestiti).toString());
        recapitiGestiti++;
        spedizione.setPeso(peso);
        spedizione.setIdMittente(mittente.getId());
        spedizione.setIdDestinatario(destinatario.getId());
        spedizione.setPrezzo(calcolaPrezzo(peso));
        creaRicevuta(spedizione);
        return true;
    }

    private static void creaRicevuta(Spedizione spedizione) {
        Ricevuta ricevuta = new Ricevuta();
        ricevuta.setData(new Date(System.currentTimeMillis()));
        ricevuta.setSpedizione(spedizione);
    }

    //temporaneo
    private static int calcolaPrezzo(int peso) {
        return peso * 5;
    }
}
