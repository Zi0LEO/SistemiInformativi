package com.opcal.controller;

import com.opcal.Indirizzo;
import com.opcal.Ricevuta;
import com.opcal.SpedizionePeer;
import com.opcal.model.GestoreClienti;
import com.opcal.model.GestoreRecapiti;
import com.opcal.view.*;

import java.util.List;
import java.util.Map;


public class MainController {

  private final MainPage mainPage;

  public MainController(MainPage mainPage) {
    this.mainPage = mainPage;
  }

  public Indirizzo trovaIndirizzo() {

    return GestoreRecapiti.visualizzaIndirizzo(mainPage.getDati().getEmail());
  }

  public void logout() {
    mainPage.getParentFrame().setLoggedUser(null);
    mainPage.getParentFrame().showPage("Login");
  }

  public void eliminaAccount(){
    DeleteDialog dialog = new DeleteDialog(mainPage.getParentFrame(), mainPage.getDati().getEmail());
  }

  public boolean modificaDati() {
    //EditDataDialog editDialog = new EditDataDialog(mainPage.getParentFrame(), email, campi);
    mainPage.repaint();
    mainPage.revalidate();
    return false;
  }

  public void creaSpedizione(){
    SpedizioneDialog dialog = new SpedizioneDialog(mainPage.getDati().getEmail(), mainPage.getParentFrame());
  }

  public void mostraSpedizioniRicevute() {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(mainPage.getDati().getEmail(), 1);
    Map<String, String> fieldsWithTable = SpedizionePeer.getFields();
//    mainPage.table.setTableData(data, campi);
  }

  public void mostraSpedizioniInviate() {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(mainPage.getDati().getEmail(), 2);
//    mainPage.table.setTableData(data, campi);
  }

  public void mostraSpedizioniInCorso() {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(mainPage.getDati().getEmail(), 3);

//    mainPage.table.setTableData(data, campi);
  }

  public void mostraSpedizioniEffettuate() {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(mainPage.getDati().getEmail(), 4);
    //SpedizionePeer.getFields()
//    mainPage.table.setTableData(data, campi);
  }

  public void mostraSpedizioniPrenotate() {
    List<Object[]> data = GestoreRecapiti.mostraSpedizioni(mainPage.getDati().getEmail(), 5);
//    mainPage.table.setTableData(data, campi);
  }

  public void mostraRicevute() {
    List<Object[]> data = GestoreClienti.listaRicevute(mainPage.getDati().getEmail());
    List<String> tempCampi = Ricevuta.getFieldNames();
    String[] campi = new String[tempCampi.size() + 1];
    campi = tempCampi.toArray(campi);
    campi[campi.length - 1] = "Pagamento";
    mainPage.table.setTableData(data, campi);
  }

  public void creaReso(){
    //todo
  }

  public void visualizzaResi() {
    //todo
  }

  public void cercaInTable(String text) {
    //todo
  }
}

