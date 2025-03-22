package com.opcal.view;

import com.opcal.controller.CreaResoDialogController;
import com.opcal.controller.DialogController;

import javax.swing.*;
import java.awt.*;

public class CreaResoDialog extends JDialog {
  public CreaResoDialog(MainFrame parentFrame) {
    super(parentFrame, "Crea nuovo reso", true);
    setSize(300, 150);
    setLocationRelativeTo(parentFrame);
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();

    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    add(new JLabel("Inserisci il codice della spedizione di cui fare il reso"), gbc);
    JTextField textField = new JTextField();

    gbc.gridy++;
    add(textField, gbc);
    gbc.gridwidth = 1;
    gbc.gridy++;
    add(MyButton.createButton("Annulla", () -> CreaResoDialogController.annulla(this)), gbc);
    gbc.gridx++;
    add(MyButton.createButton("Inizia reso", () -> CreaResoDialogController.creaReso(this, textField.getText(), parentFrame.getLoggedUser().getEmail())), gbc);
    setVisible(true);

  }
}
