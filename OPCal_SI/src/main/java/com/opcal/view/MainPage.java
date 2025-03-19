package com.opcal.view;

import com.opcal.Indirizzo;
import com.opcal.controller.MainController;
import com.opcal.model.Dati;
import com.opcal.model.DatiCliente;

import javax.swing.*;
import java.awt.*;

public class MainPage extends JPanel{

  // Campi per visualizzare/modificare i dati
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
    buttonPanel.add(MyButton.createButton("Modifica dati", () -> MainController.modificaDatiButton(parentFrame, dati.getEmail())));

    // Pulsante Elimina Account
    buttonPanel.add(MyButton.createButton("Elimina Account", () -> MainController.eliminaAccount(parentFrame, dati.getEmail())));

    // Pulsante Logout
    buttonPanel.add(MyButton.createButton("Logout", () -> MainController.logout(parentFrame)));

    JPanel queryButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

    //Pulsanti spedizioni
    queryButtonPanel.add(MyButton.createButton("Crea Spedizione", () -> MainController.creaSpedizione(dati.getEmail(), parentFrame)));

    queryButtonPanel.add(MyButton.createButton("Spedizioni ricevute", () -> MainController.mostraSpedizioniRicevute(dati.getEmail(), table)));

    queryButtonPanel.add(MyButton.createButton("Spedizioni inviate", () -> MainController.mostraSpedizioniInviate(dati.getEmail(), table)));

    queryButtonPanel.add(MyButton.createButton("Spedizioni in corso", () -> MainController.mostraSpedizioniInCorso(dati.getEmail(), table)));

    JButton spedizioniPrenotateButton = new JButton("Spedizioni prenotate");
    queryButtonPanel.add(spedizioniPrenotateButton);

    //Ricevute
    JButton ricevuteButton = new JButton("Ricevute");
    queryButtonPanel.add(ricevuteButton);

    //Resi
    JButton resiButton = new JButton("Resi effettuati");
    queryButtonPanel.add(resiButton);

    JPanel wrapperButtonPanel = new JPanel(new BorderLayout(5,5));
    wrapperButtonPanel.add(buttonPanel, BorderLayout.NORTH);
    wrapperButtonPanel.add(queryButtonPanel, BorderLayout.SOUTH);
    add(wrapperButtonPanel, BorderLayout.CENTER);

    JPanel queryPanel = new JPanel(new BorderLayout(5,5));
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    JTextField toSearch = new JTextField(20);
    toSearch.setPreferredSize(new Dimension(200, 30));
    JButton searchButton = new JButton("Cerca");
    searchButton.setPreferredSize(new Dimension(100, 30));
    searchPanel.add(toSearch);
    searchPanel.add(searchButton);

    table = new QueryResultsTable();
    JScrollPane resultsScrollPane = new JScrollPane(table.getTable());
    resultsScrollPane.setPreferredSize(new Dimension(600, 400));
    queryPanel.add(searchPanel, BorderLayout.NORTH);
    queryPanel.add(resultsScrollPane, BorderLayout.SOUTH);

    add(queryPanel, BorderLayout.SOUTH);

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
