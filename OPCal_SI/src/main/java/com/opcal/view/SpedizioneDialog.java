package com.opcal.view;

import com.opcal.controller.SpedizioneDialogController;

import javax.swing.*;
import java.awt.*;

public class SpedizioneDialog extends JDialog {

      private JLabel prezzoLabel;
  public SpedizioneDialog(String email, Frame owner) {
        super(owner, "Spedizione", true);
        setSize(300, 600);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel emailLabel = new JLabel("Email spedizione: ");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(emailLabel, gbc);

        gbc.gridx = 1;
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 20));
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel pesoLabel = new JLabel("Peso totale: ");
        pesoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(pesoLabel, gbc);

        gbc.gridx = 1;
        JTextField pesoField = new JTextField();
        pesoField.setPreferredSize(new Dimension(200, 20));
        add(pesoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        prezzoLabel = new JLabel("Entra i valori per calcolare il prezzo");
        add(prezzoLabel,gbc);

        gbc.gridy = 3;
        add(MyButton.createButton("Controlla Prezzo", () -> {
              int prezzo = SpedizioneDialogController.preventivo(pesoField.getText(), emailField.getText());
              if (prezzo > 0) {
                    prezzoLabel.setText("Prezzo: " + prezzo + " EUR");
              }
        }), gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(new JLabel("Sei sicuro di voler effettuare la spedizione?"), gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        add(MyButton.createButton("Conferma", () -> SpedizioneDialogController.conferma(email, emailField.getText(), pesoField.getText())), gbc);

        gbc.gridx = 1;
        add(MyButton.createButton("Annulla", () -> SpedizioneDialogController.annulla(this)), gbc);
        pack();
        setVisible(true);
    }
}