package com.opcal.view;

import com.opcal.controller.DialogController;
import javax.swing.*;
import java.awt.*;

import static com.opcal.controller.EmailController.manda;

public class EmailDialog extends JDialog {
    public EmailDialog(MainFrame parentFrame) {
        super(parentFrame, "Email a Cliente", true);
        setSize(400, 300); // Dimensioni più sensate
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(5, 5, 5, 5); // Aggiunge spaziatura
        c.weightx = 1.0;

        // Campi
        JTextField emailField = new JTextField(20);
        JTextField oggettoField = new JTextField(20);
        JTextArea corpoField = new JTextArea(5, 20);
        corpoField.setLineWrap(true);
        corpoField.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(corpoField);

        // Aggiunta componenti
        c.gridx = 0; c.gridy = 0;
        add(new JLabel("Email:"), c);
        c.gridx = 1;
        add(emailField, c);

        c.gridx = 0; c.gridy++;
        add(new JLabel("Oggetto:"), c);
        c.gridx = 1;
        add(oggettoField, c);

        c.gridx = 0; c.gridy++;
        add(new JLabel("Corpo:"), c);
        c.gridx = 1;
        add(scrollPane, c);

        // Pulsanti
        c.gridx = 0; c.gridy++;
        add(MyButton.createButton("Annulla", () -> DialogController.annulla(this)), c);
        c.gridx = 1;
        add(MyButton.createButton("Invia", () -> manda(this,emailField.getText(), oggettoField.getText(), corpoField.getText())), c);

        setVisible(true); // Ora lo mettiamo alla fine
    }
}
