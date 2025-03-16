package com.opcal.controller;

import com.opcal.Indirizzo;
import com.opcal.model.GestoreRecapiti;

public class MainController {
  public static Indirizzo trovaIndirizzo(String email) {
    return GestoreRecapiti.visualizzaIndirizzo(email);
  }
}
