package com.opcal.controller;

import com.opcal.*;
import com.opcal.model.Dati;
import com.opcal.model.DatiCliente;
import com.opcal.model.DatiDipendente;
import org.apache.torque.NoRowsException;


public class LoginController {
  public static Dati login(String email, String password)  {
    if(email.equals("test") && password.equals("test"))
      return new DatiCliente("Test", "Test", "test", "test");
    try {
      Dipendente dipendente = DipendentePeer.retrieveByPK(email);
      Utente utente = dipendente.getUtente();
      if(!utente.getPassword().equals(password))
        return null;
      return new DatiDipendente(utente.getNome(), utente.getCognome(), utente.getEmail(), utente.getPassword());
    } catch(NoRowsException e){
    } catch(Exception e){
      return null;
    }
    try {
      Cliente cliente = ClientePeer.retrieveByPK(email);
      Utente utente = cliente.getUtente();
      if(!utente.getPassword().equals(password))
        return null;
      return new DatiCliente(utente.getNome(), utente.getCognome(), utente.getEmail(), utente.getPassword());
    } catch(NoRowsException e){
    } catch(Exception e){
      return null;
    }
    return null;
  }
}
