package com.opcal.controller;

import com.opcal.Indirizzo;
import com.opcal.model.GestoreClienti;
import com.opcal.model.GestoreRecapiti;

public class MainController {
  public static Indirizzo trovaIndirizzo(String email) {

    return GestoreRecapiti.visualizzaIndirizzo(email);
  }

  public static boolean modificaDati(String email, String nome, String cognome, String comune, String via, String civico, String orario) {
    Indirizzo indirizzo = null;
    if (!nome.isEmpty())
      try {
        GestoreClienti.modificaNomeCliente(email, nome);
      } catch (ClassNotFoundException e) {
        return false;
      }
    if (!cognome.isEmpty())
      try {
        GestoreClienti.modificaCognomeCliente(email, cognome);
      } catch (ClassNotFoundException e) {
        return false;
      }
    if (!comune.isEmpty()) {
      indirizzo = GestoreRecapiti.modificaIndirizzo(email, comune, 1);
      if (indirizzo == null)
        return false;
    }

    if (!via.isEmpty()) {
      indirizzo = GestoreRecapiti.modificaIndirizzo(email, via, 2);
      if (indirizzo == null)
        return false;
    }

    if (!civico.isEmpty()){
      indirizzo = GestoreRecapiti.modificaIndirizzo(email, civico, 3);
      System.out.println(indirizzo);
      if (indirizzo == null)
        return false;
    }

    if (!orario.equals("non modificare")) {
      indirizzo = GestoreRecapiti.modificaIndirizzo(email, orario, 4);
      if (indirizzo == null)
        return false;
    }
    System.out.println(indirizzo);
    return true;
  }
}

