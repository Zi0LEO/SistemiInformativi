package com.opcal.model;

public class DatiCliente extends Dati {

  public DatiCliente(String nome, String cognome, String email, String passwd) {
    super(nome, cognome, email, passwd);
  }

  public DatiCliente(String nome, String cognome, String email){
    super(nome, cognome, email);
  }

}
