package com.opcal.view;

import com.opcal.controller.MainController;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainPage extends JPanel{

  public final JPanel datiPanel;
  private final MainFrame parentFrame;

  private Map<String, String> currentlyShownData;
  public final MainController controller;

  public QueryResultsTable table;

  public void setData(Map<String, String> data){
    currentlyShownData = data;
  }
  public Map<String, String> getData(){
    return currentlyShownData;
  }

  public MainFrame getParentFrame() {
    return parentFrame;
  }


  public MainPage(MainFrame mainFrame) {
    controller = new MainController(this);
    setLayout(new BorderLayout(5,5));
    setBackground(new Color(240, 240, 240));
    parentFrame = mainFrame;

    // Pannello superiore: Dati cliente
    datiPanel = new JPanel();
    datiPanel.add(new JLabel("I tuoi dati:"));
    datiPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    datiPanel.setBackground(new Color(200, 220, 240)); // Colore di sfondo

    add(datiPanel, BorderLayout.NORTH);

    // Pannello centrale: Pulsanti
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    buttonPanel.setBackground(new Color(240, 240, 240));

    // Pulsante modifica dati
    buttonPanel.add(MyButton.createButton("Modifica dati", controller::modificaDatiPropri));

    // Pulsante Elimina Account
    buttonPanel.add(MyButton.createButton("Elimina Account", controller::eliminaAccount));


    // Pulsante Logout
    buttonPanel.add(MyButton.createButton("Logout", controller::logout));

    JPanel queryButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

    //Pulsanti spedizioni
    queryButtonPanel.add(MyButton.createButton("Crea Spedizione", controller::creaSpedizione));

    queryButtonPanel.add(MyButton.createButton("Spedizioni in arrivo", controller::mostraSpedizioniRicevute));

    queryButtonPanel.add(MyButton.createButton("Mie spedizioni inviate", controller::mostraSpedizioniInviate));

    queryButtonPanel.add(MyButton.createButton("Spedizioni in corso", controller::mostraSpedizioniInCorso));

    queryButtonPanel.add(MyButton.createButton("Spedizioni consegnate", controller::mostraSpedizioniEffettuate));

    queryButtonPanel.add(MyButton.createButton("Spedizioni prenotate", controller::mostraSpedizioniPrenotate));

    //Ricevute
    queryButtonPanel.add(MyButton.createButton("Visualizza ricevute pagamenti", controller::mostraRicevute));

    //Resi
    queryButtonPanel.add(MyButton.createButton("Crea reso", controller::creaReso));

    queryButtonPanel.add(MyButton.createButton("Visualizza resi effettuati", controller::visualizzaResi));

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
    searchPanel.add(MyButton.createButton("Cerca", () -> controller.cercaInTable(toSearch.getText())));

    table = new QueryResultsTable();
    JScrollPane resultsScrollPane = new JScrollPane(table.getTable());
    tablePanel.add(searchPanel, BorderLayout.NORTH);
    tablePanel.add(resultsScrollPane, BorderLayout.SOUTH);

    add(tablePanel, BorderLayout.SOUTH);
    }

    public void updateContent(){
    controller.updateContent(this);
    }

}
