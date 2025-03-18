package com.opcal.controller;

import com.opcal.model.GestoreClienti;
import com.opcal.view.MainFrame;

import javax.swing.*;
import java.awt.*;

public class DeleteDialogController extends DialogController {

  public static void elimina(String emailCliente, MainFrame frame, Dialog dialog) {

    try {
      GestoreClienti.cancellaCliente(emailCliente);
      dialog.dispose();
      frame.showPage("LOGIN");
      JOptionPane.showMessageDialog(null, "Cliente eliminato con successo!", "Eliminazione", JOptionPane.INFORMATION_MESSAGE);
    }catch(Exception e){
      JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione", "Errore", JOptionPane.ERROR_MESSAGE);
    }
  }

}
