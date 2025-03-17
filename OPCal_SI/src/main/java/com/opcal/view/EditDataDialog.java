package com.opcal.view;

import com.opcal.controller.MainController;

import javax.swing.*;
import java.awt.*;

public class EditDataDialog extends JDialog {
  private JTextField nome, cognome, comune, via, civico;
  private JComboBox<String> orario;
  private JButton saveButton, cancelButton;

  public EditDataDialog(Frame parent, String email){
    super(parent,"Modifica Dati",true);
    setSize(500, 400);
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5); // Add some padding

    JLabel area = new JLabel("Inserisci solo i dati che vuoi modificare.");
    gbc.gridx = 0; // Start at column 0
    gbc.gridy = 0; // Start at row 0
    gbc.gridwidth = 2; // Span two columns
    gbc.anchor = GridBagConstraints.CENTER; // Center the label
    gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally
    add(area, gbc);

    gbc.gridwidth = 1; // Set it back to 1 for other components

    gbc.gridy = 1;
    gbc.gridx = 0;
    add(new JLabel("Nome:"), gbc);

    gbc.gridx = 1;
    nome = new JTextField();
    add(nome, gbc);

    gbc.gridy = 2;
    gbc.gridx = 0;
    add(new JLabel("Cognome:"), gbc);

    gbc.gridx = 1;
    cognome = new JTextField();
    add(cognome, gbc);

    gbc.gridy = 3;
    gbc.gridx = 0;
    add(new JLabel("Comune:"), gbc);

    gbc.gridx = 1;
    comune = new JTextField();
    add(comune, gbc);

    gbc.gridy = 4;
    gbc.gridx = 0;
    add(new JLabel("Via:"), gbc);

    gbc.gridx = 1;
    via = new JTextField();
    add(via, gbc);

    gbc.gridy = 5;
    gbc.gridx = 0;
    add(new JLabel("Civico:"), gbc);

    gbc.gridx = 1;
    civico = new JTextField();
    add(civico, gbc);

    gbc.gridy = 6;
    gbc.gridx = 0;
    add(new JLabel("Orario:"), gbc);

    gbc.gridx = 1;
    orario = new JComboBox<>(new String[]{"non modificare","9.00", "10.00", "11.00", "12.00", "13.00", "14.00", "15.00", "16.00", "17.00", "18.00", "19.00", "20.00"});
    add(orario, gbc);

    gbc.gridy = 7;
    gbc.gridx = 0;
    gbc.gridwidth = 1;
    saveButton = new JButton("Salva");
    add(saveButton, gbc);

    gbc.gridx = 1;
    cancelButton = new JButton("Annulla");
    add(cancelButton, gbc);

    saveButton.addActionListener(e -> {
      boolean done = MainController.modificaDati(email, nome.getText(), cognome.getText(), comune.getText(), via.getText(), civico.getText(), (String) orario.getSelectedItem());
      if (done) {
        JOptionPane.showMessageDialog(this, "Dati modificati con successo!", "Attenzione", JOptionPane.WARNING_MESSAGE);
      }
      else {
        JOptionPane.showMessageDialog(this, "Errore durante la modifica dei dati!", "Attenzione", JOptionPane.WARNING_MESSAGE);
      }
      this.dispose();


    });
    cancelButton.addActionListener(e -> {
      setVisible(false);
      JOptionPane.showMessageDialog(this, "Modifiche annullate!", "Attenzione", JOptionPane.WARNING_MESSAGE);
      this.dispose();
    });

    setVisible(true);
  }
}
