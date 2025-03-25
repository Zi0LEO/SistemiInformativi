package com.opcal.controller;

import com.opcal.model.GestoreRecapiti;
import com.opcal.view.MyButton;

import javax.swing.*;
import java.awt.*;

public class ModificaSpedizioneController extends DialogController {
  public static void avanza(JDialog parentDialog, Integer codiceSpedizione){
    String stato = GestoreRecapiti.getStato(codiceSpedizione);
    System.out.println(stato);
    switch(stato){
      case "in attesa di ritiro":
        if (!GestoreRecapiti.cambiaStato(codiceSpedizione, 1))
          JOptionPane.showMessageDialog(null, "Errore durante la modifica", "Errore", JOptionPane.ERROR_MESSAGE);
        break;
      case "presa in carico":
        if (!GestoreRecapiti.cambiaStato(codiceSpedizione, 2))
          JOptionPane.showMessageDialog(null, "Errore durante la modifica", "Errore", JOptionPane.ERROR_MESSAGE);
        break;
      case "spedita":
        if (!GestoreRecapiti.cambiaStato(codiceSpedizione, 3))
          JOptionPane.showMessageDialog(null, "Errore durante la modifica", "Errore", JOptionPane.ERROR_MESSAGE);
        break;
      case "tentato recapito":
        if (!GestoreRecapiti.cambiaStato(codiceSpedizione, 4))
          JOptionPane.showMessageDialog(null, "Errore durante la modifica", "Errore", JOptionPane.ERROR_MESSAGE);
        break;
      case "in consegna":
        parentDialog.dispose();
        JDialog dialog = new JDialog((Frame) parentDialog.getOwner(),"Conferma", true);
        dialog.setSize(300, 100);
        dialog.setLayout(new BoxLayout(dialog.getContentPane(), BoxLayout.Y_AXIS));
        dialog.setLocationRelativeTo(null);
        dialog.add(new JLabel("La spedizione è stata consegnata?"));
        dialog.add(MyButton.createButton("Si", () -> {
          if (!GestoreRecapiti.cambiaStato(codiceSpedizione, 4))
            JOptionPane.showMessageDialog(null, "Errore durante la modifica", "Errore", JOptionPane.ERROR_MESSAGE);
          dialog.dispose();
        }));
        dialog.add(MyButton.createButton("No", () -> {
          if (!GestoreRecapiti.cambiaStato(codiceSpedizione, 5))
            JOptionPane.showMessageDialog(null, "Errore durante la modifica", "Errore", JOptionPane.ERROR_MESSAGE);
          dialog.dispose();
        }));
        dialog.pack();
        dialog.setVisible(true);
        break;
      default:
        JOptionPane.showMessageDialog(null, "Stato non valido", "Errore", JOptionPane.ERROR_MESSAGE);
        return;
    }
    parentDialog.dispose();
    JOptionPane.showMessageDialog(null, "Stato modificato con successo!", "Modifica", JOptionPane.INFORMATION_MESSAGE);
  }
}
