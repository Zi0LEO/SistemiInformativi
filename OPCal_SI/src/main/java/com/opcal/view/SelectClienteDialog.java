package com.opcal.view;

import com.opcal.controller.DialogController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SelectClienteDialog extends JDialog {

  public SelectClienteDialog(MainFrame parentFrame) {
    super(parentFrame, "Seleziona cliente", true);
    setLayout(new GridLayout(5, 1));
    add(new JLabel("Inserisci l'email del cliente da modificare"));
    JTextField textField = new JTextField();
    List<String> campi = new ArrayList<>() {
      {
        add("Nome");
        add("Cognome");
        add("Comune");
        add("Via");
        add("Civico");
        add("Orario");
      }
    };
    add(textField);
    add(MyButton.createButton("Conferma", () -> new EditDataDialog(parentFrame, textField.getText(), campi)));
    add(MyButton.createButton(("Annulla"), () -> DialogController.annulla(this)));
    setSize(300, 200);
    setVisible(true);
  }
}
