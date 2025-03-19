package com.opcal.view;

import com.opcal.Indirizzo;
import com.opcal.controller.MainController;
import com.opcal.model.Dati;
import com.opcal.model.DatiCliente;

import javax.swing.*;
import java.awt.*;

public class MainPage extends JPanel{

  private final JPanel datiPanel;

  public QueryResultsTable table;
  private Dati dati;

  public MainPage(MainFrame parentFrame) {
    setLayout(new BorderLayout(5,5));
    setBackground(new Color(240, 240, 240)); // Colore di sfondo

    // Pannello superiore: Dati cliente
    datiPanel = new JPanel();
    datiPanel.add(new JLabel("I tuoi dati:"));
    add(datiPanel, BorderLayout.NORTH);

    // Pannello centrale: Pulsanti
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    buttonPanel.setBackground(new Color(240, 240, 240));

    // Pulsante modifica dati
    buttonPanel.add(MyButton.createButton("Modifica dati", () -> MainController.modificaDati(this, parentFrame, dati.getEmail(), new String[]{})));

    // Pulsante Elimina Account
    buttonPanel.add(MyButton.createButton("Elimina Account", () -> MainController.eliminaAccount(parentFrame, dati.getEmail())));

    // Pulsante Logout
    buttonPanel.add(MyButton.createButton("Logout", () -> MainController.logout(parentFrame)));

    JPanel queryButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

    //Pulsanti spedizioni
    queryButtonPanel.add(MyButton.createButton("Crea Spedizione", () -> MainController.creaSpedizione(dati.getEmail(), parentFrame)));

    queryButtonPanel.add(MyButton.createButton("Spedizioni in arrivo", () -> MainController.mostraSpedizioniRicevute(dati.getEmail(), table)));

    queryButtonPanel.add(MyButton.createButton("Mie spedizioni inviate", () -> MainController.mostraSpedizioniInviate(dati.getEmail(), table)));

    queryButtonPanel.add(MyButton.createButton("Spedizioni in corso", () -> MainController.mostraSpedizioniInCorso(dati.getEmail(), table)));

    queryButtonPanel.add(MyButton.createButton("Spedizioni consegnate", () -> MainController.mostraSpedizioniEffettuate(dati.getEmail(), table)));

    queryButtonPanel.add(MyButton.createButton("Spedizioni prenotate", () -> MainController.mostraSpedizioniPrenotate(dati.getEmail(), table)));

    //Ricevute
    queryButtonPanel.add(MyButton.createButton("Visualizza ricevute pagamenti", () -> MainController.mostraRicevute(dati.getEmail(), table)));

    //Resi
    queryButtonPanel.add(MyButton.createButton("Crea reso",() -> MainController.creaReso(dati.getEmail())));

    queryButtonPanel.add(MyButton.createButton("Visualizza resi effettuati", () -> MainController.visualizzaResi(dati.getEmail())));

    JPanel wrapperButtonPanel = new JPanel(new BorderLayout(5,5));
    wrapperButtonPanel.add(buttonPanel, BorderLayout.NORTH);
    wrapperButtonPanel.add(queryButtonPanel, BorderLayout.CENTER);
    add(wrapperButtonPanel, BorderLayout.CENTER);

    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    JPanel tablePanel = new JPanel(new BorderLayout(5,5));

    //Campo di ricerca
    JTextField toSearch = new JTextField(20);
    toSearch.setPreferredSize(new Dimension(300, 30));
    searchPanel.add(toSearch);
    searchPanel.add(MyButton.createButton("Cerca", () -> MainController.cercaInTable(toSearch.getText())));

    table = new QueryResultsTable();
    JScrollPane resultsScrollPane = new JScrollPane(table.getTable());
    tablePanel.add(searchPanel, BorderLayout.NORTH);
    tablePanel.add(resultsScrollPane, BorderLayout.SOUTH);

    add(tablePanel, BorderLayout.SOUTH);

  }


  public void updateContent() {
    dati = ((MainFrame) SwingUtilities.getWindowAncestor(this)).getLoggedUser();
    datiPanel.removeAll();
    datiPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    datiPanel.setBackground(new Color(200, 220, 240)); // Colore di sfondo
    datiPanel.add(new JLabel(dati.getNome()));
    datiPanel.add(new JLabel(dati.getCognome()));
    datiPanel.add(new JLabel(dati.getEmail()));
    if (dati instanceof DatiCliente) {
      datiPanel.setLayout(new GridLayout(5, 1));
      Indirizzo indirizzo = MainController.trovaIndirizzo(dati.getEmail());
      datiPanel.add(new JLabel(indirizzo.toString()));
    }
    else {
      datiPanel.setLayout(new GridLayout(4, 1));
    }
  }
}
