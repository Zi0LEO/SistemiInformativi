package com.opcal.controller;

import com.opcal.*;
import com.opcal.model.*;
import com.opcal.view.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


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
  private List<String> retrieveCampi(int tipo) {
    List<String> fields = SpedizionePeer.getFields();
    switch (tipo) {
      case 1:
        fields.add("Data prenotazione");
        fields.add("Data prevista di ritiro");
        break;
      case 2:
        fields.add("Data spedizione");
        break;
      case 3:
        fields.add("Data spedizione");
        fields.add("Data consegna");
        break;
    }
    return fields;
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
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(dati.getEmail(), 4);
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
    List<String> campi = RicevutaPeer.getFields();
    campi.add("Pagamento");
    mainPage.table.setTableData(data, campi);
  }
  public void mostraRicevuteDip() {
    List<Object[]> data = GestoreClienti.getListaRicevute(null);
    List<String> campi = RicevutaPeer.getFields();
    campi.add("Pagamento");
    mainPage.table.setTableData(data, campi);
  }

  public void creaReso(){
    JDialog dialog = new CreaResoDialog(mainPage.getParentFrame());
    dialog.add(new JLabel("Inserisci il codice della spedizione"));
  }

  public void visualizzaResi() {
    List<Object[]> data = GestoreResi.listaResi(dati.getEmail());
    List<String> campi = Reso.getFieldNames();
    mainPage.table.setTableData(data, campi);
  }
  public void visualizzaResiDip() {
    List<Object[]> data = GestoreResi.listaResi(null);
    List<String> campi = Reso.getFieldNames();
    mainPage.table.setTableData(data, campi);
  }

  public void listaClienti(){
    List<Object[]> data = GestoreClienti.listaClienti();
    List campi = List.of("Nome", "Cognome", "Email");
    mainPage.table.setTableData(data, campi);
  }

  public void cercaInTable(String text) {
    mainPage.table.search(text);
  }

  public static Dati trovaUtente(String email) {
    return GestoreClienti.trovaUtente(email);
  }

  public void creaSpedizioneDipendente() {
    Dialog dialog = new SpedizioneDialog(mainPage.getParentFrame());
  }

  public void modificaDati() {
    JDialog dialog = new SelectClienteDialog(mainPage.getParentFrame());
  }

  public void listaIndirizzi() {
    List<Object[]> data = GestoreRecapiti.listaIndirizzi();
    List campi = List.of("Email", "Comune", "Via", "Civico", "Orario");
    mainPage.table.setTableData(data, campi);
  }

  public void visualizzaCorrieri() {
    List<Object[]> data = GestoreRecapiti.listaCorrieri();
    List campi = List.of("Nome", "Partita Iva", "Sito", "Numero di telefono", "prezzo 1kg", "prezzo 10kg", "prezzo 100kg");
    mainPage.table.setTableData(data, campi);
  }

  public void avanzaSpedizione() { new ModificaSpedizioneDialog(mainPage.getParentFrame());}

  public void mandaEmail() { new EmailDialog(mainPage.getParentFrame());}

  public void annullaReso() { new AnnullaResoDialog(); }
}

