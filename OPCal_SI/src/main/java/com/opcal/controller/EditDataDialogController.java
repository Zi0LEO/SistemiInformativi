package com.opcal.controller;

import com.opcal.Indirizzo;
import com.opcal.model.GestoreClienti;
import com.opcal.model.GestoreRecapiti;

import javax.swing.*;
import java.util.List;

public class EditDataDialogController extends DialogController{

  //temporaneo
  public static void salva(JDialog dialog, String email, List<String> values) {
    Indirizzo indirizzo;
    System.out.println(values.toString());
    if (!values.get(0).isEmpty())
      try {
        GestoreClienti.modificaNomeCliente(email, values.get(0));
      } catch (ClassNotFoundException e) {
        JOptionPane.showMessageDialog(null, "Errore durante la modifica", "Errore", JOptionPane.ERROR_MESSAGE);
        dialog.dispose();
        return;
      }
    if (!values.get(1).isEmpty())
      try {
        GestoreClienti.modificaCognomeCliente(email, values.get(1));
      } catch (ClassNotFoundException e) {
        JOptionPane.showMessageDialog(null, "Errore durante la modifica", "Errore", JOptionPane.ERROR_MESSAGE);
        dialog.dispose();
        return;
      }
    if (!values.get(2).isEmpty()) {
      indirizzo = GestoreRecapiti.modificaIndirizzo(email, values.get(2), 1);
      if (indirizzo == null) {
        JOptionPane.showMessageDialog(null, "Errore durante la modifica", "Errore", JOptionPane.ERROR_MESSAGE);
        dialog.dispose();
        return;
      }
    }
    if (!values.get(3).isEmpty()) {
      indirizzo = GestoreRecapiti.modificaIndirizzo(email, values.get(3), 2);
      if (indirizzo == null) {
        JOptionPane.showMessageDialog(null, "Errore durante la modifica", "Errore", JOptionPane.ERROR_MESSAGE);
        dialog.dispose();
        return;
      }
    }
    if (!values.get(4).isEmpty()) {
      indirizzo = GestoreRecapiti.modificaIndirizzo(email, values.get(4), 3);
      if (indirizzo == null) {
        JOptionPane.showMessageDialog(null, "Errore durante la modifica", "Errore", JOptionPane.ERROR_MESSAGE);
        dialog.dispose();
        return;
      }
    }
    if (!values.get(5).isEmpty()){
      indirizzo = GestoreRecapiti.modificaIndirizzo(email, values.get(5), 4);
    if (indirizzo == null) {
      JOptionPane.showMessageDialog(null, "Errore durante la modifica", "Errore", JOptionPane.ERROR_MESSAGE);
      dialog.dispose();
      return;
    }
  }
    dialog.dispose();
    JOptionPane.showMessageDialog(null, "Modifica completata con successo", "Modifica", JOptionPane.INFORMATION_MESSAGE);
  }
}
