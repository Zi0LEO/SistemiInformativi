package com.opcal.controller;

import com.opcal.model.Dati;
import com.opcal.model.GestoreClienti;
import com.opcal.view.MainFrame;
import com.opcal.view.MainPage;



public class LoginController {
  public static void login(MainFrame frame, String email, char[] password, MainPage mainPage){
    String pass = new String(password);
    Dati dati = null;
//    dati = GestoreDipendenti.autentica(email,pass);
    if (dati != null) {
      frame.setLoggedUser(dati);
      frame.showPage("Main");
      mainPage.updateContent();
      return;
    }
    dati = GestoreClienti.autentica(email, pass);
    if (dati != null) {
      frame.setLoggedUser(dati);
      frame.showPage("Main");
      mainPage.updateContent();
    }
  }
}
