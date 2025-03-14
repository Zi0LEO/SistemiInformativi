package com.opcal.controller;

import com.opcal.Indirizzo;
import com.opcal.model.DatiCliente;
import com.opcal.model.GestoreClienti;

public class RegistrazioneController {

  public static void registrazione(String nome, String cognome, String comune, String via, String civico, String email, String password) throws CloneNotSupportedException {
    Indirizzo indirizzo = new Indirizzo(comune, via, civico);
    GestoreClienti.creaCliente(new DatiCliente(nome, cognome, email, password), indirizzo);
  }
}
