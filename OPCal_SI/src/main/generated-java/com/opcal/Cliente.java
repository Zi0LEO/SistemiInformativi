package com.opcal;


import org.apache.torque.TorqueException;

/**
 * The skeleton for this class was autogenerated by Torque on:
 * <p>
 * [Wed Mar 05 18:43:05 CET 2025]
 * <p>
 * You should add additional methods to this class to meet the
 * application requirements.  This class will only be generated as
 * long as it does not already exist in the output directory.
 */

public class Cliente
    extends BaseCliente {
  /**
   * Serial version
   */
  private static final long serialVersionUID = 1741196585640L;

  public Cliente() {
    super();
  }

  public Cliente(String email){
    super();
    this.setEmail(email);
  }

  public String getNome() throws TorqueException {

    return this.getUtente().getNome();

  }

  public String getCognome() throws TorqueException {

    return this.getUtente().getCognome();

  }

  public void setNome(String nome) throws TorqueException {

    this.getUtente().setNome(nome);

  }

  public void setCognome(String cognome) throws TorqueException {

    this.getUtente().setCognome(cognome);

  }

  public void setPassword(String password) throws TorqueException {
    this.getUtente().setPassword(password);
  }

}
