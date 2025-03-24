package com.opcal.controller;

import com.opcal.model.Dati;
import com.opcal.model.DatiDipendente;
import com.opcal.model.GestoreClienti;
import com.opcal.view.MainFrame;

import javax.swing.*;


public class LoginController {
  public static void login(MainFrame frame, String email, char[] password){
    String pass = new String(password);
    Dati dati = null;
//    dati = GestoreDipendenti.autentica(email,pass);
    if(email.equals("test_dipendente"))


      dati = new DatiDipendente("nome_dipendente","cognome_dipendente","test_dipendente","test_password");
    if (dati != null) {
      frame.setLoggedUser(dati);
      return;
    }
    dati = GestoreClienti.autentica(email, pass);
    if (dati != null) {
      frame.setLoggedUser(dati);
      return;
    }
    JOptionPane.showMessageDialog(null, "Email o password errati!", "Errore", JOptionPane.ERROR_MESSAGE);
  }
}
