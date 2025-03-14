package com.opcal.model;

public abstract class Dati {
    private final String nome;
    private final String cognome;
    private final String email;
    private final int passwd;

    public Dati(String nome, String cognome, String email, String passwd) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.passwd = hash(passwd);
    }

    private static int hash(String passwd) {
        return passwd.hashCode();
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
}