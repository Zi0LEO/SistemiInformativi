package com.opcal.controller;

import com.opcal.model.DatiCliente;
import com.opcal.view.MainFrame;
import com.opcal.view.MainPage;

import javax.swing.*;


public class LoginController {
  public static void login(MainFrame frame, String email, char[] password, MainPage mainPage){
    String pass = new String(password);
    DatiCliente dati = null;
//    dati = GestoreDipendenti.autentica(email,pass);
    if (dati != null) {
      frame.setLoggedUser(dati);
      frame.showPage("Main");
      mainPage.updateContent();
      return;
    }
    dati = new DatiCliente("umberto","frega","umbertofrega@gmail.com");
    if (dati != null) {
      frame.setLoggedUser(dati);
      frame.showPage("Main");
      mainPage.updateContent();
      return;
    }
    JOptionPane.showMessageDialog(null, "Email o password errati!", "Errore", JOptionPane.ERROR_MESSAGE);
  }
}
