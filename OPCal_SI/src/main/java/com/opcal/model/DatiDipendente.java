package com.opcal.model;

public class DatiDipendente extends Dati {

  public DatiDipendente(String nome, String cognome, String email, String passwd) {
    super(nome, cognome, email, passwd);
  }

  public DatiDipendente(String nome, String cognome, String email) {
    super(nome, cognome, email);
  }
}
