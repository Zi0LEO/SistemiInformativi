package com.opcal.view;

import com.opcal.controller.DataDialogController;
import com.opcal.controller.EditDataDialogController;

import javax.swing.*;
import java.awt.*;

import static com.opcal.view.MyButton.createButton;

public class EditDataDialog extends JDialog {

  public EditDataDialog(Frame parent, String email, String[] fields){
    super(parent,"Modifica Dati",true);
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 5, 10, 5);
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel area = new JLabel("Inserisci solo i dati che vuoi modificare.");
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    add(area, gbc);

    gbc.gridwidth = 1;

    JTextField[] values = new JTextField[fields.length];
    for(int i = 0; i < fields.length; i++){
      gbc.gridy = i + 1;
      gbc.gridx = 0;
      add(new JLabel(fields[i]), gbc);

      gbc.gridx = 1;
      values[i] = new JTextField();
      add(values[i], gbc);
    }

    gbc.gridy++;
    gbc.gridx = 0;

    add(MyButton.createButton("Salva", () -> {
      String[] valuesString = new String[fields.length];
      for(int i = 0; i < fields.length; i++)
        valuesString[i] = values[i].getText();
      EditDataDialogController.editData(fields, valuesString);
    }), gbc);

    gbc.gridx = 1;

    add(createButton("Annulla", () -> DataDialogController.annulla(this)), gbc);
    pack();
    setVisible(true);
    }
  }