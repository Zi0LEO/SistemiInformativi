package com.opcal.controller;

import com.opcal.Indirizzo;
import com.opcal.Ricevuta;
import com.opcal.SpedizionePeer;
import com.opcal.model.GestoreClienti;
import com.opcal.model.GestoreRecapiti;
import com.opcal.view.*;

import java.awt.*;
import java.util.List;


public class MainController {
  public static Indirizzo trovaIndirizzo(String email) {

    return GestoreRecapiti.visualizzaIndirizzo(email);
  }

  public static void logout(MainFrame parentFrame) {
    parentFrame.setLoggedUser(null);
    parentFrame.showPage("LOGIN");
  }
  public static void eliminaAccount(MainFrame parentFrame, String email){
    DeleteDialog dialog = new DeleteDialog(parentFrame, email);
  }

  public static boolean modificaDati(MainPage mainPage, MainFrame parentFrame, String email, String[] campi) {
    EditDataDialog editDialog = new EditDataDialog(parentFrame, email, campi);
    mainPage.repaint();
    mainPage.revalidate();
    return false;
  }

  public static void creaSpedizione(String email, Frame frame){
    SpedizioneDialog dialog = new SpedizioneDialog(email, frame);
  }

  public static void mostraSpedizioniRicevute(String email, QueryResultsTable table) {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(email, 1);
    String[] campi = SpedizionePeer.getFields();
    table.setTableData(data, campi);
  }

  public static void mostraSpedizioniInviate(String email, QueryResultsTable table) {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(email, 2);
    String[] campi = SpedizionePeer.getFields();
    table.setTableData(data, campi);
  }

  public static void mostraSpedizioniInCorso(String email, QueryResultsTable table) {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(email, 3);
    String[] campi = SpedizionePeer.getFields();
    table.setTableData(data, campi);
  }

  public static void mostraSpedizioniEffettuate(String email, QueryResultsTable table) {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(email, 4);
    String[] campi = SpedizionePeer.getFields();
    table.setTableData(data, campi);
  }

  public static void mostraSpedizioniPrenotate(String email, QueryResultsTable table) {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(email, 5);
    String[] campi = SpedizionePeer.getFields();
    table.setTableData(data, campi);
  }

  public static void mostraRicevute(String email, QueryResultsTable table) {
    List<Object[]> data = GestoreClienti.listaRicevute(email);
    List<String> tempCampi = Ricevuta.getFieldNames();
    String[] campi = new String[tempCampi.size() + 1];
    campi = tempCampi.toArray(campi);
    campi[campi.length - 1] = "Pagamento";
    table.setTableData(data, campi);
  }

  public static void creaReso(String email){
    //todo
  }

  public static void visualizzaResi(String email) {
    //todo
  }

  public static void cercaInTable(String text) {
    //todo
  }
}

