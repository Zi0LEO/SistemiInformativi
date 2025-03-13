package com.opcal.model;

public abstract class Dati {
  private String nome;
  private String cognome;
  private String email;
  private int passwd;

  public Dati(String nome, String cognome, String email, String passwd) {
    this.nome = nome;
    this.cognome = cognome;
    this.email = email;
    this.passwd = hash(passwd);
  }

  public String getNome() {
    return nome;
  }

  public String getCognome() {
    return cognome;
  }

  public String getEmail() {
    return email;
  }

  private static int hash(String passwd) {
    return passwd.hashCode();
  }
}