package com.opcal.controller;

import com.opcal.Indirizzo;
import com.opcal.model.GestoreRecapiti;
import com.opcal.view.DeleteDialog;
import com.opcal.view.EditDataDialog;
import com.opcal.view.MainFrame;


public class MainController {
  public static Indirizzo trovaIndirizzo(String email) {

    return GestoreRecapiti.visualizzaIndirizzo(email);
  }

  public static void logout(MainFrame parentFrame) {
    parentFrame.showPage("LOGIN");
  }
  public static void eliminaAccount(MainFrame parentFrame, String email){
    DeleteDialog dialog = new DeleteDialog(parentFrame, email);
  }

  public static boolean modificaDatiButton(MainFrame parentFrame, String email) {
    String[] campi = new String[]{"Nome", "Cognome", "Comune", "Via", "Civico", "Orario"};
    EditDataDialog editDialog = new EditDataDialog(parentFrame, email, campi, false);
    return false;
  }
}

