package com.opcal.view;

import com.opcal.controller.DataDialogController;
import com.opcal.controller.MainController;

import javax.swing.*;
import java.awt.*;

import static com.opcal.view.MyButton.createButton;

public class EditDataDialog extends JDialog {

  public EditDataDialog(Frame parent, String email, String[] fields, boolean gestitoDaRecapito){
    super(parent,"Modifica Dati",true);
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 5, 10, 5); // Add some padding

    JLabel area = new JLabel("Inserisci solo i dati che vuoi modificare.");
    gbc.gridx = 0; // Start at column 0
    gbc.gridy = 0; // Start at row 0
    gbc.gridwidth = 2; // Span two columns
    gbc.anchor = GridBagConstraints.CENTER; // Center the label
    gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally
    add(area, gbc);

    gbc.gridwidth = 1; // Set it back to 1 for other components

    JTextField[] values = new JTextField[fields.length];
    for(int i = 0; i < fields.length; i++){
      gbc.gridy = i + 1;
      gbc.gridx = 0;
      add(new JLabel(fields[i]), gbc);

      gbc.gridx = 1;
      values[i] = new JTextField();
      add(values[i], gbc);
    }

    //needs button to send data

    gbc.gridx = 1;

    add(createButton("Salva", () -> DataDialogController.annulla(this)), gbc);
    pack();
    setVisible(true);
    }
  }