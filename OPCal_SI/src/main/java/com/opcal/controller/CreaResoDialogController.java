package com.opcal.controller;

import com.opcal.model.GestoreResi;

import javax.swing.*;

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
    JOptionPane.showMessageDialog(null, "Reso creato con successo!", "Creazione", JOptionPane.INFORMATION_MESSAGE);
  }
}
