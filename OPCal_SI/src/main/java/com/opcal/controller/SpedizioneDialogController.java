package com.opcal.controller;

import com.opcal.Indirizzo;
import com.opcal.model.GestoreRecapiti;

import javax.swing.*;
import java.awt.*;


public class SpedizioneDialogController extends DialogController{

  public static int preventivo(String peso, String emailDestinatario) {
    if(!correctFields(emailDestinatario, peso))
      return 0;

    int pesoInt = Integer.parseInt(peso);
    Integer prezzo = GestoreRecapiti.calcolaPrezzo(pesoInt, emailDestinatario);
    if (prezzo != null)
      return prezzo;
    return 0;
  }

  public static void confermaDip(Dialog dialog, String emailMittente, String emailDestinatario, String peso) {
    if(!correctFields(emailDestinatario, peso))
      return;

    int pesoInt = Integer.parseInt(peso);
    boolean successo = GestoreRecapiti.creaSpedizione(emailMittente, emailDestinatario, pesoInt);
    if(successo) {
      JOptionPane.showMessageDialog(null, "Spedizione prenotata con successo", "Ritiro prenotato", JOptionPane.INFORMATION_MESSAGE);
      dialog.dispose();
    }
    else
      JOptionPane.showMessageDialog(null, "Errore durante la creazione della spedizione", "Errore", JOptionPane.ERROR_MESSAGE);
  }

  public static void conferma(Dialog dialog, String emailMittente, String emailDestinatario, String peso) {
    if(!correctFields(emailDestinatario, peso))
      return;

    int pesoInt = Integer.parseInt(peso);
    boolean successo = GestoreRecapiti.creaRitiro(emailMittente, emailDestinatario, pesoInt);
    if(successo) {
      JOptionPane.showMessageDialog(null, "Ritiro prenotato con successo", "Ritiro prenotato", JOptionPane.INFORMATION_MESSAGE);
      dialog.dispose();
    }
    else
      JOptionPane.showMessageDialog(null, "Errore durante il ritiro", "Errore", JOptionPane.ERROR_MESSAGE);
    }

  private static boolean correctFields(String emailDestinatario, String peso) {
    int pesoInt;
    try {
      pesoInt = Integer.parseInt(peso);
    } catch (NumberFormatException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(null, "Inserire un peso valido", "Errore", JOptionPane.ERROR_MESSAGE);
      return false;
    }

    if (emailDestinatario.isEmpty() || pesoInt < 1) {
      JOptionPane.showMessageDialog(null, "Inserire un email e un peso validi", "Errore", JOptionPane.ERROR_MESSAGE);
      return false;
    }

    Indirizzo indirizzo = GestoreRecapiti.visualizzaIndirizzo(emailDestinatario);
    if (indirizzo == null) {
      JOptionPane.showMessageDialog(null, "Il destinatario non ha un indirizzo associato o non esiste", "Errore", JOptionPane.ERROR_MESSAGE);
    }
      return true;
  }
}
