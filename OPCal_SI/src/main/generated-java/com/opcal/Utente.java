package com.opcal;


/**
 * The skeleton for this class was autogenerated by Torque on:
 * <p>
 * [Tue Mar 04 17:46:13 CET 2025]
 * <p>
 * You should add additional methods to this class to meet the
 * application requirements.  This class will only be generated as
 * long as it does not already exist in the output directory.
 */

public class Utente
    extends BaseUtente {
  /**
   * Serial version
   */
  private static final long serialVersionUID = 1741106773841L;

  public Utente() {
    super();
  }

  public Utente(String nome, String cognome, String email, String password) {
    super();
    setNome(nome);
    setCognome(cognome);
    setEmail(email);
    setPassword(password);
  }
}
