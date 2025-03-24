package com.opcal.controller;

import com.opcal.model.GestoreResi;
import org.apache.torque.TorqueException;

import javax.swing.*;

import static com.opcal.model.GeneratoreEtichette.creaEtichetta;
import static com.opcal.model.GestoreMailing.mandaACliente;

public class CreaResoDialogController extends DialogController{
  public static void creaReso(JDialog dialog, String codiceSpedizione, String email){
    if(!codiceSpedizione.isEmpty())
      try{
        Integer codiceInt = Integer.parseInt(codiceSpedizione);
        GestoreResi.creaReso(codiceInt, email);
      }catch(Exception e) {
        dialog.dispose();
        JOptionPane.showMessageDialog(null, "Codice spedizione non valido", "Errore", JOptionPane.ERROR_MESSAGE);
        return;
      }
    dialog.dispose();
    if(mandaReso(codiceSpedizione, email)) {
      JOptionPane.showMessageDialog(null, "Reso creato con successo!,\n Documenti spediti all'indirizzo " + email, "Creazione", JOptionPane.INFORMATION_MESSAGE);
    }
    else {
      JOptionPane.showMessageDialog(null,"Errore nella creazione del documento di reso");
    }
  }

  private static boolean mandaReso(String codiceSpedizione,String email){
      byte[] documento;
      try {
            documento = creaEtichetta(codiceSpedizione);
      } catch (TorqueException e) {
          return false;
      }
      mandaACliente(email,"Documentazione Reso",documento,"Etichetta di reso");
      return true;
  }
}
