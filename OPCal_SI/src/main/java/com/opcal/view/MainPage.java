package com.opcal.view;

import com.opcal.Indirizzo;
import com.opcal.controller.MainController;
import com.opcal.model.Dati;
import com.opcal.model.DatiCliente;

import javax.swing.*;
import java.awt.*;

public class MainPage extends JPanel{

  // Campi per visualizzare/modificare i dati
  private JPanel datiPanel;

  private Dati dati;

  // Pulsanti per le azioni
  private final JButton modificaButton;
  private final JButton eliminaButton;
  private final JButton storicoButton;

  // Area per visualizzare lo storico delle consegne
  private final JTextArea storicoArea;

  public MainPage() {
    setLayout(new BorderLayout());
    setBackground(new Color(240, 240, 240)); // Colore di sfondo

    // Pannello superiore: Dati cliente
    datiPanel = new JPanel();


    datiPanel.add(new JLabel("I tuoi dati:"));
    add(datiPanel, BorderLayout.NORTH);

    // Pannello centrale: Pulsanti
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    buttonPanel.setBackground(new Color(240, 240, 240));

    // Pulsante modifica dati
    modificaButton = new JButton("Modifica i tuoi dati");
    modificaButton.setFont(new Font("Arial", Font.BOLD, 16));
    modificaButton.setPreferredSize(new Dimension(200, 40));
    modificaButton.addActionListener(e -> {
      EditDataDialog editDialog = new EditDataDialog(
          (MainFrame) SwingUtilities.getWindowAncestor(this), dati.getEmail());
    });
    buttonPanel.add(modificaButton);

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
