package com.opcal.controller;

import javax.swing.*;

public class EditDataDialogController extends DialogController{
  public static void editData(String[] fields, String[] dati){
  JOptionPane.showMessageDialog(null, fields, "Modifica dati", JOptionPane.INFORMATION_MESSAGE);

  }
}
