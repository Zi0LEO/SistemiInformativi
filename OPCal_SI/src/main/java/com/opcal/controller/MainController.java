package com.opcal.controller;

import com.opcal.Indirizzo;
import com.opcal.SpedizionePeer;
import com.opcal.model.GestoreRecapiti;
import com.opcal.view.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;


public class MainController {
  public static Indirizzo trovaIndirizzo(String email) {

    return GestoreRecapiti.visualizzaIndirizzo(email);
  }

  public static void logout(MainFrame parentFrame) {
    parentFrame.showPage("LOGIN");
  }
  public static void eliminaAccount(MainFrame parentFrame, String email){
    DeleteDialog dialog = new DeleteDialog(parentFrame, email);
  }

  public static boolean modificaDatiButton(MainFrame parentFrame, String email) {
    String[] campi = new String[]{"Nome", "Cognome", "Comune", "Via", "Civico", "Orario"};
    EditDataDialog editDialog = new EditDataDialog(parentFrame, email, campi, false);
    return false;
  }

  public static void creaSpedizione(String email, Frame frame){
    SpedizioneDialog dialog = new SpedizioneDialog(email, frame);
  }

  public static void mostraSpedizioniInviate(String email, QueryResultsTable table) {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(email, 2);
    String[] campi = SpedizionePeer.getFields();
    table.setTableData(data, campi);
  }

  public static void mostraSpedizioniRicevute(String email, QueryResultsTable table) {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(email, 1);
    String[] campi = SpedizionePeer.getFields();
    table.setTableData(data, campi);
  }


}

