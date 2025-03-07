package com.opcal.model;

public abstract class Dati{
    private final int ID;
    private final String nome;
    private final String cognome;
    private final String email;
    private final int passwd;

    public Dati(int ID, String nome, String cognome, String email, String passwd) {
        this.ID = ID;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.passwd = hash(passwd);
    }
    public int getID() {
        return ID;
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