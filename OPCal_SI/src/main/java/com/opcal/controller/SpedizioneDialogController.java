package com.opcal.controller;

import com.opcal.Indirizzo;
import com.opcal.model.GestoreRecapiti;

import javax.swing.*;


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

  public static void conferma(String emailMittente, String emailDestinatario, String peso) {
    if(!correctFields(emailDestinatario, peso))
      return;

    int pesoInt = Integer.parseInt(peso);
    if(GestoreRecapiti.creaRitiro(emailMittente, emailDestinatario, pesoInt))
      JOptionPane.showMessageDialog(null, "Ritiro prenotato con successo", "Ritiro effettuato", JOptionPane.INFORMATION_MESSAGE);
    else
      JOptionPane.showMessageDialog(null, "Errore durante il ritiro", "Errore", JOptionPane.ERROR_MESSAGE);
    }

  private static boolean correctFields(String emailDestinatario, String peso) {
    int pesoInt;
    try {
      pesoInt = Integer.parseInt(peso);
    } catch (NumberFormatException e) {
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

      return false;
  }
}
