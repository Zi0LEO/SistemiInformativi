package com.opcal.controller;

import com.opcal.*;
import com.opcal.model.*;
import com.opcal.view.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainController {

  private static final Logger log = LoggerFactory.getLogger(MainController.class);
  private final MainPage mainPage;
  private Dati dati;

  public MainController(MainPage mainPage) {
    this.mainPage = mainPage;
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
    System.out.println("test");
    mainPage.controller.updateContent(mainPage);
  }

  public void creaSpedizione(){
    SpedizioneDialog dialog = new SpedizioneDialog(dati.getEmail(), mainPage.getParentFrame());
  }

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

  public void mostraSpedizioniInCorso() {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(dati.getEmail(), 3);

    mainPage.table.setTableData(data, retrieveCampi(2));
  }

  public void mostraSpedizioniEffettuate() {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(dati.getEmail(), 4);
    //SpedizionePeer.getFields()
    mainPage.table.setTableData(data, retrieveCampi(3));
  }

  public void mostraSpedizioniPrenotate() {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(dati.getEmail(), 5);
    mainPage.table.setTableData(data, retrieveCampi(1));
  }

  public void mostraRicevute() {
    List<Object[]> data = GestoreClienti.getListaRicevute(dati.getEmail());
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

  public void cercaInTable(String text) {
    mainPage.table.search(text);
  }

  public Dati trovaUtente(String email) {
    return GestoreClienti.trovaUtente(email);
  }

  //temporary
  public void updateContent(MainPage mainPage) {
    Dati dati = trovaUtente(mainPage.getParentFrame().getLoggedUser().getEmail());
    this.dati = dati;
    mainPage.datiPanel.removeAll();
    mainPage.datiPanel.add(new JLabel(dati.getNome()));
    mainPage.datiPanel.add(new JLabel(dati.getCognome()));
    mainPage.datiPanel.add(new JLabel(dati.getEmail()));
    if (dati instanceof DatiCliente) {
      mainPage.datiPanel.setLayout(new GridLayout(5, 1));
      Indirizzo indirizzo = trovaIndirizzo();
      mainPage.datiPanel.add(new JLabel(indirizzo.toString()));
    } else {
      mainPage.datiPanel.setLayout(new GridLayout(4, 1));
    }
    mainPage.revalidate();
    mainPage.repaint();
  }
}

