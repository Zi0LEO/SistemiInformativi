package com.opcal.view;

import javax.swing.*;
import java.awt.*;

public class DipendenteState implements PageState {

  @Override
  public void buildPage(MainPage page) {
    PageState.super.buildPage(page);

    page.datiPanel = new JPanel();
    page.datiPanel.setLayout(new GridLayout(4, 1));
    page.datiPanel.add(new JLabel("I tuoi dati:"));
    page.datiPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    page.datiPanel.setBackground(new Color(200, 220, 240)); // Colore di sfondo
    page.datiPanel.add(new JLabel(page.getParentFrame().getLoggedUser().getNome()));
    page.datiPanel.add(new JLabel(page.getParentFrame().getLoggedUser().getCognome()));
    page.datiPanel.add(new JLabel(page.getParentFrame().getLoggedUser().getEmail()));
    page.add(page.datiPanel, BorderLayout.NORTH);

    // Pannello centrale: Pulsanti
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    buttonPanel.setBackground(new Color(240, 240, 240));

    // Pulsante Logout
    buttonPanel.add(MyButton.createButton("Logout", page.controller::logout));

    JPanel queryButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

    queryButtonPanel.add(MyButton.createButton("Lista Clienti", page.controller::listaClienti));

    queryButtonPanel.add(MyButton.createButton("Modifica dati clienti", page.controller::modificaDati));

    queryButtonPanel.add(MyButton.createButton("Visualizza indirizzi", page.controller::listaIndirizzi));

    //Pulsanti spedizioni
    queryButtonPanel.add(MyButton.createButton("Crea Spedizione", page.controller::creaSpedizioneDipendente));

    queryButtonPanel.add(MyButton.createButton("Spedizioni in corso", page.controller::mostraSpedizioniInCorsoDip));

    queryButtonPanel.add(MyButton.createButton("Spedizioni consegnate", page.controller::mostraSpedizioniEffettuateDip));

    queryButtonPanel.add(MyButton.createButton("Spedizioni prenotate", page.controller::mostraSpedizioniPrenotateDip));

    queryButtonPanel.add(MyButton.createButton("Avanza spedizione", page.controller::avanzaSpedizione));

    //Ricevute
    queryButtonPanel.add(MyButton.createButton("Visualizza ricevute pagamenti", page.controller::mostraRicevuteDip));

    queryButtonPanel.add(MyButton.createButton("Visualizza resi effettuati", page.controller::visualizzaResiDip));

    queryButtonPanel.add(MyButton.createButton("Visualizza corrieri", page.controller::visualizzaCorrieri));

    //Manda Email
    queryButtonPanel.add(MyButton.createButton("Manda email a cliente",page.controller::mandaEmail));


    JPanel wrapperButtonPanel = new JPanel(new BorderLayout(5,5));
    wrapperButtonPanel.add(buttonPanel, BorderLayout.NORTH);
    wrapperButtonPanel.add(queryButtonPanel, BorderLayout.CENTER);
    page.add(wrapperButtonPanel, BorderLayout.CENTER);

    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    JPanel tablePanel = new JPanel(new BorderLayout(5,5));

    //Campo di ricerca
    JTextField toSearch = new JTextField(20);
    toSearch.setPreferredSize(new Dimension(300, 30));
    searchPanel.add(toSearch);
    searchPanel.add(MyButton.createButton("Cerca", () -> page.controller.cercaInTable(toSearch.getText())));

    page.table = new QueryResultsTable();
    JScrollPane resultsScrollPane = new JScrollPane(page.table.getTable());
    tablePanel.add(searchPanel, BorderLayout.NORTH);
    tablePanel.add(resultsScrollPane, BorderLayout.SOUTH);

    page.add(tablePanel, BorderLayout.SOUTH);
  }
}
