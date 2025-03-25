package com.opcal.view;

import com.opcal.Indirizzo;

import javax.swing.*;
import java.awt.*;

public class ClienteState implements PageState {

  @Override
  public void buildPage(MainPage page) {
    PageState.super.buildPage(page);

    page.datiPanel = new JPanel();
    page.datiPanel.setLayout(new GridLayout(5, 1));
    page.datiPanel.add(new JLabel("I tuoi dati:"));
    page.datiPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    page.datiPanel.setBackground(new Color(200, 220, 240)); // Colore di sfondo
    page.datiPanel.add(new JLabel(page.getParentFrame().getLoggedUser().getNome()));
    page.datiPanel.add(new JLabel(page.getParentFrame().getLoggedUser().getCognome()));
    page.datiPanel.add(new JLabel(page.getParentFrame().getLoggedUser().getEmail()));
    page.datiPanel.setLayout(new GridLayout(5, 1));
    Indirizzo indirizzo = page.controller.trovaIndirizzo();
    page.datiPanel.add(new JLabel(indirizzo.toString()));
    page.add(page.datiPanel, BorderLayout.NORTH);

    // Pannello centrale: Pulsanti
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    buttonPanel.setBackground(new Color(240, 240, 240));

    // Pulsante modifica dati
    buttonPanel.add(MyButton.createButton("Modifica dati", page.controller::modificaDatiPropri));

    // Pulsante Elimina Account
    buttonPanel.add(MyButton.createButton("Elimina Account", page.controller::eliminaAccount));

    // Pulsante Logout
    buttonPanel.add(MyButton.createButton("Logout", page.controller::logout));

    JPanel queryButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

    //Pulsanti spedizioni
    queryButtonPanel.add(MyButton.createButton("Crea Spedizione", page.controller::creaSpedizione));

    queryButtonPanel.add(MyButton.createButton("Spedizioni in arrivo", page.controller::mostraSpedizioniRicevute));

    queryButtonPanel.add(MyButton.createButton("Mie spedizioni inviate", page.controller::mostraSpedizioniInviate));

    queryButtonPanel.add(MyButton.createButton("Spedizioni in corso", page.controller::mostraSpedizioniInCorso));

    queryButtonPanel.add(MyButton.createButton("Spedizioni consegnate", page.controller::mostraSpedizioniEffettuate));

    queryButtonPanel.add(MyButton.createButton("Spedizioni prenotate", page.controller::mostraSpedizioniPrenotate));

    //Ricevute
    queryButtonPanel.add(MyButton.createButton("Visualizza ricevute pagamenti", page.controller::mostraRicevute));

    //Resi
    queryButtonPanel.add(MyButton.createButton("Crea reso", page.controller::creaReso));
    queryButtonPanel.add(MyButton.createButton("Annulla reso",page.controller::annullaReso));
    queryButtonPanel.add(MyButton.createButton("Visualizza resi effettuati", page.controller::visualizzaResi));

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
