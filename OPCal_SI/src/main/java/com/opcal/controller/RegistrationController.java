package com.opcal.controller;

import com.opcal.model.DatiCliente;
import com.opcal.model.GestoreClienti;
import com.opcal.model.GestoreRecapiti;

import javax.swing.*;

public class RegistrationController {

  public static void registrazione(String nome, String cognome, String comune, String via, String civico, String email, char[] pass) {

    String password = String.valueOf(pass);
    if (nome.isEmpty() || cognome.isEmpty() || email.isEmpty() || password.isEmpty() || comune.isEmpty() || via.isEmpty() || civico.isEmpty()) {
      JOptionPane.showMessageDialog(null, "Completa tutti i campi!", "Errore", JOptionPane.ERROR_MESSAGE);
      return;
    }
    if (!email.contains("@") || !email.contains(".")) {
      JOptionPane.showMessageDialog(null, "Inserisci un email valida!", "Errore", JOptionPane.ERROR_MESSAGE);
      return;
    }

    try {
      if (!GestoreClienti.creaCliente(new DatiCliente(nome, cognome, email, password))) {
        JOptionPane.showMessageDialog(null, "Errore durante la registrazione", "Errore", JOptionPane.ERROR_MESSAGE);
        return;
      }
    }catch(CloneNotSupportedException e){
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "L'indirizzo email è già registrato", "Errore", JOptionPane.ERROR_MESSAGE);
      return;
    }

    if(GestoreRecapiti.creaIndirizzo(comune, via, civico, email) == null)
      {
      JOptionPane.showMessageDialog(null, "Errore durante la registrazione", "Errore", JOptionPane.ERROR_MESSAGE);
    }
  }
}
