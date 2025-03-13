package com.opcal.model;

public class DatiCliente extends Dati {

  public DatiCliente(int ID, String nome, String cognome, String email, String passwd) {
    super(nome, cognome, email, passwd);
  }

  public DatiCliente(String email, String passwd) {
    super(null,null,email,passwd);
  }

}
