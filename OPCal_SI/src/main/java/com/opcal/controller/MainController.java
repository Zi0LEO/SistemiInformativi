package com.opcal.controller;

import com.opcal.*;
import com.opcal.model.*;
import com.opcal.view.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainController {

  private final MainPage mainPage;
  private final Dati dati;

  public MainController(MainPage mainPage) {
    this.mainPage = mainPage;
    dati = mainPage.getParentFrame().getLoggedUser();
  }

  public Indirizzo trovaIndirizzo() {

    return GestoreRecapiti.visualizzaIndirizzo(dati.getEmail());
  }

  public void logout() {
    mainPage.getParentFrame().setLoggedUser(null);
    mainPage.getParentFrame().showPage("Login");
  }

  public void eliminaAccount(){
    DeleteDialog dialog = new DeleteDialog(mainPage.getParentFrame(), dati.getEmail());
  }

  public void modificaDatiPropri() {
    List<String> campi = new ArrayList<>(){
      {
        add("Nome");
        add("Cognome");
        add("Comune");
        add("Via");
        add("Civico");
        add("Orario");
      }};
    EditDataDialog dialog = new EditDataDialog(mainPage.getParentFrame(), dati.getEmail(), campi);
    mainPage.buildPage();
  }

  public void creaSpedizione(){
    SpedizioneDialog dialog = new SpedizioneDialog(dati.getEmail(), mainPage.getParentFrame());
  }

  //hardcoded at the moment
  private String[] retrieveCampi(int tipo) {
    Map<String, String> fieldsWithTable = SpedizionePeer.getFields();
    switch (tipo) {
      case 1:
        fieldsWithTable.put("Data prenotazione", PrenotataPeer.TABLE_NAME);
        fieldsWithTable.put("Data prevista di ritiro", PrenotataPeer.TABLE_NAME);
        break;
      case 2:
        fieldsWithTable.put("Data spedizione", InCorsoPeer.TABLE_NAME);
        break;
      case 3:
        fieldsWithTable.put("Data spedizione", EffettuataPeer.TABLE_NAME);
        fieldsWithTable.put("Data consegna", EffettuataPeer.TABLE_NAME);
        break;
    }
    mainPage.setData(fieldsWithTable);
    return fieldsWithTable.keySet().toArray(new String[0]);
  }

  public void mostraSpedizioniRicevute() {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(dati.getEmail(), 1);
    mainPage.table.setTableData(data, retrieveCampi(0));
  }

  public void mostraSpedizioniInviate() {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(dati.getEmail(), 2);
    mainPage.table.setTableData(data, retrieveCampi(0));
  }

  public void mostraSpedizioniPrenotate() {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(dati.getEmail(), 3);
    mainPage.table.setTableData(data, retrieveCampi(1));
  }
  public void mostraSpedizioniPrenotateDip() {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(null, 3);
    mainPage.table.setTableData(data, retrieveCampi(1));
  }

  public void mostraSpedizioniInCorso() {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni("%", 4);
    mainPage.table.setTableData(data, retrieveCampi(2));
  }

  public void mostraSpedizioniInCorsoDip() {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(null, 4);
    mainPage.table.setTableData(data, retrieveCampi(2));
  }

  public void mostraSpedizioniEffettuate() {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(dati.getEmail(), 5);
    //SpedizionePeer.getFields()
    mainPage.table.setTableData(data, retrieveCampi(3));
  }
  public void mostraSpedizioniEffettuateDip() {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(null, 5);
    mainPage.table.setTableData(data, retrieveCampi(3));
  }



  public void mostraRicevute() {
    List<Object[]> data = GestoreClienti.getListaRicevute(dati.getEmail());
    List<String> tempCampi = Ricevuta.getFieldNames();
    String[] campi = new String[tempCampi.size() + 1];
    campi = tempCampi.toArray(campi);
    campi[campi.length - 1] = "Pagamento";
    mainPage.table.setTableData(data, campi);
  }
  public void mostraRicevuteDip() {
    List<Object[]> data = GestoreClienti.getListaRicevute(null);
    List<String> tempCampi = Ricevuta.getFieldNames();
    String[] campi = new String[tempCampi.size() + 1];
    campi = tempCampi.toArray(campi);
    campi[campi.length - 1] = "Pagamento";
    mainPage.table.setTableData(data, campi);
  }

  public void creaReso(){
    JDialog dialog = new CreaResoDialog(mainPage.getParentFrame());
    dialog.add(new JLabel("Inserisci il codice della spedizione"));
  }

  public void visualizzaResi() {
    List<Object[]> data = GestoreResi.listaResi(dati.getEmail());
    List<String> campi = Reso.getFieldNames();
    String[] campiArr = campi.toArray(new String[0]);
    mainPage.table.setTableData(data, campiArr);
  }
  public void visualizzaResiDip() {
    List<Object[]> data = GestoreResi.listaResi(null);
    List<String> campi = Reso.getFieldNames();
    String[] campiArr = campi.toArray(new String[0]);
    mainPage.table.setTableData(data, campiArr);
  }

  public void listaClienti(){
    List<Object[]> data = GestoreClienti.listaClienti();
    String[] campi = List.of("Nome", "Cognome", "Email").toArray(new String[0]);
    mainPage.table.setTableData(data, campi);
  }

  public void cercaInTable(String text) {
    mainPage.table.search(text);
  }

  public Dati trovaUtente(String email) {
    return GestoreClienti.trovaUtente(email);
  }

  public void creaSpedizioneDipendente() {
    Dialog dialog = new SpedizioneDialog(mainPage.getParentFrame());
  }

  public void modificaDati() {
    JDialog dialog = new SelectClienteDialog(mainPage.getParentFrame());
  }
}

