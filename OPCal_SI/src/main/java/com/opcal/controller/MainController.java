package com.opcal.controller;

import com.opcal.Indirizzo;
import com.opcal.SpedizionePeer;
import com.opcal.model.GestoreRecapiti;
import com.opcal.view.DeleteDialog;
import com.opcal.view.MainFrame;
import com.opcal.view.QueryResultsTable;

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

//  public static boolean modificaDatiButton(MainFrame parentFrame, String email) {
//    String[] campi = new String[]{"Nome", "Cognome", "Comune", "Via", "Civico", "Orario"};
//    EditDataDialog editDialog = new EditDataDialog(parentFrame, email, campi, false);
//    return false;
//  }

  public static void mostraSpedizioniInviate(String email, QueryResultsTable table) {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioniInviate(email);
    String[] campi = SpedizionePeer.getFields();
    updateTable(campi, data, table);
  }
  private static void updateTable(String[] campi, List<Object[]> data, QueryResultsTable table) {
    table.setColumnNames(campi);
    table.updateTableData(data);
    table.repaint();
    table.revalidate();
  }
}

