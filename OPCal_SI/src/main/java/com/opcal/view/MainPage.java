package com.opcal.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

class MainPage extends JPanel {
  // Campi per visualizzare/modificare i dati
  private final JTextField nomeField;
  private final JTextField cognomeField;
  private final JTextField emailField;

  // Pulsanti per le azioni
  private final JButton modificaNomeButton;
  private final JButton modificaCognomeButton;
  private final JButton eliminaButton;
  private final JButton storicoButton;

  // Area per visualizzare lo storico delle consegne
  private final JTextArea storicoArea;

  public MainPage() {
    setLayout(new BorderLayout());
    setBackground(new Color(240, 240, 240)); // Colore di sfondo

    // Pannello superiore: Dati cliente
    JPanel datiPanel = new JPanel(new GridLayout(3, 2, 10, 10));
    datiPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    datiPanel.setBackground(new Color(200, 220, 240)); // Colore di sfondo

    // Campo Nome
    datiPanel.add(new JLabel("Nome:"));
    nomeField = new JTextField();
    nomeField.setFont(new Font("Arial", Font.PLAIN, 16));
    datiPanel.add(nomeField);

    // Campo Cognome
    datiPanel.add(new JLabel("Cognome:"));
    cognomeField = new JTextField();
    cognomeField.setFont(new Font("Arial", Font.PLAIN, 16));
    datiPanel.add(cognomeField);

    // Campo Email
    datiPanel.add(new JLabel("Email:"));
    emailField = new JTextField();
    emailField.setFont(new Font("Arial", Font.PLAIN, 16));
    emailField.setEditable(false); // L'email non è modificabile
    datiPanel.add(emailField);

    add(datiPanel, BorderLayout.NORTH);

    // Pannello centrale: Pulsanti
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    buttonPanel.setBackground(new Color(240, 240, 240));

    // Pulsante Modifica Nome
    modificaNomeButton = new JButton("Modifica Nome");
    modificaNomeButton.setFont(new Font("Arial", Font.BOLD, 16));
    modificaNomeButton.setPreferredSize(new Dimension(200, 40));
    buttonPanel.add(modificaNomeButton);

    // Pulsante Modifica Cognome
    modificaCognomeButton = new JButton("Modifica Cognome");
    modificaCognomeButton.setFont(new Font("Arial", Font.BOLD, 16));
    modificaCognomeButton.setPreferredSize(new Dimension(200, 40));
    buttonPanel.add(modificaCognomeButton);

    // Pulsante Elimina Account
    eliminaButton = new JButton("Elimina Account");
    eliminaButton.setFont(new Font("Arial", Font.BOLD, 16));
    eliminaButton.setPreferredSize(new Dimension(200, 40));
    eliminaButton.setBackground(new Color(255, 100, 100)); // Rosso per indicare pericolo
    buttonPanel.add(eliminaButton);

    // Pulsante Visualizza Storico
    storicoButton = new JButton("Visualizza Storico");
    storicoButton.setFont(new Font("Arial", Font.BOLD, 16));
    storicoButton.setPreferredSize(new Dimension(200, 40));
    buttonPanel.add(storicoButton);

    add(buttonPanel, BorderLayout.CENTER);

    // Pannello inferiore: Storico consegne
    JPanel storicoPanel = new JPanel(new BorderLayout());
    storicoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    storicoPanel.setBackground(new Color(240, 240, 240));

    storicoArea = new JTextArea();
    storicoArea.setFont(new Font("Arial", Font.PLAIN, 16));
    storicoArea.setEditable(false); // L'area di testo è di sola lettura
    JScrollPane scrollPane = new JScrollPane(storicoArea);
    storicoPanel.add(scrollPane, BorderLayout.CENTER);

    add(storicoPanel, BorderLayout.SOUTH);
  }

  // Metodi per aggiornare i dati nella GUI
  public void setNome(String nome) {
    nomeField.setText(nome);
  }

  public void setCognome(String cognome) {
    cognomeField.setText(cognome);
  }

  public void setEmail(String email) {
    emailField.setText(email);
  }

  public void setStorico(String storico) {
    storicoArea.setText(storico);
  }

  // Metodi per ottenere i dati dalla GUI
  public String getNome() {
    return nomeField.getText();
  }

  public String getCognome() {
    return cognomeField.getText();
  }

  // Metodi per aggiungere listener ai pulsanti
  public void addModificaNomeListener(ActionListener listener) {
    modificaNomeButton.addActionListener(listener);
  }

  public void addModificaCognomeListener(ActionListener listener) {
    modificaCognomeButton.addActionListener(listener);
  }

  public void addEliminaListener(ActionListener listener) {
    eliminaButton.addActionListener(listener);
  }

  public void addStoricoListener(ActionListener listener) {
    storicoButton.addActionListener(listener);
  }
}
