package com.opcal.view;

import com.opcal.controller.EditDataDialogController;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;


public class EditDataDialog extends JDialog {

  public EditDataDialog(Frame parent, String email, List<String> fields){
    super(parent,"Modifica Dati",true);
    setLayout(new GridBagLayout());
    setSize(500, 400);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel area = new JLabel("Inserisci solo i dati che vuoi modificare.");
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    add(area, gbc);

    gbc.gridwidth = 1;
    gbc.gridy++;

    List<JTextField> values = new java.util.LinkedList<>();
    fields.forEach((field) -> createModifier(gbc, field, values));

    gbc.gridx = 0;
    gbc.gridy++;

    add(MyButton.createButton("Salva", () -> EditDataDialogController.salva(
        this,
        email,
        values.stream().map(JTextComponent::getText).collect(Collectors.toList()
        ))), gbc);

    gbc.gridx = 1;

    add(MyButton.createButton("Annulla", () -> EditDataDialogController.annulla(this)), gbc);

    pack();
    setVisible(true);
  }

  private void createModifier(GridBagConstraints gbc, String field, List<JTextField> values) {
    gbc.gridx = 0;
    JLabel label = new JLabel(field);
    add(label, gbc);
    gbc.gridx = 1;
    JTextField value = new JTextField();
    add(value, gbc);
    values.add(value);
    gbc.gridy++;
  }
}