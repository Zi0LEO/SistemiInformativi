package com.opcal.controller;

import com.opcal.Indirizzo;
import com.opcal.model.GestoreClienti;
import com.opcal.model.GestoreRecapiti;
import com.opcal.view.DeleteDialog;
import com.opcal.view.EditDataDialog;
import com.opcal.view.MainFrame;

import javax.swing.*;
import java.awt.*;

public class MainController {
  public static Indirizzo trovaIndirizzo(String email) {

    return GestoreRecapiti.visualizzaIndirizzo(email);
  }

  public static void logout(MainFrame parentFrame) {
    parentFrame.showPage("LOGIN");
  }
  public static void eliminaAccount(MainFrame parentFrame, String email){
    DeleteDialog dialog = new DeleteDialog(parentFrame, "Eliminazione account", email);
  }

  public static boolean modificaDatiButton(MainFrame parentFrame, String email) {
    String[] campi = new String[]{"Nome", "Cognome", "Comune", "Via", "Civico", "Orario"};
    EditDataDialog editDialog = new EditDataDialog(parentFrame, email, campi, false);
    return false;
  }
}

