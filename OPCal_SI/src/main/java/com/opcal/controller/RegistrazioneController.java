package com.opcal.controller;

import com.opcal.Cliente;
import com.opcal.Indirizzo;
import com.opcal.model.GestoreRecapiti;
import com.opcal.model.DatiCliente;
import com.opcal.model.GestoreClienti;

public class RegistrazioneController {

  public static void registrazione(String nome, String cognome, String comune, String via, String civico, String email, String password) throws CloneNotSupportedException {
    Cliente cliente = GestoreClienti.creaCliente(new DatiCliente(nome, cognome, email, password));
    GestoreRecapiti.creaIndirizzo(comune, via, civico, cliente);
  }
}
