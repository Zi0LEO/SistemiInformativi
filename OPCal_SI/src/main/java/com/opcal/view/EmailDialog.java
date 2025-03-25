package com.opcal.view;

import com.opcal.controller.DialogController;
import com.opcal.controller.EmailController;
import javax.swing.*;
import java.awt.*;

import static com.opcal.controller.EmailController.manda;

public class EmailDialog extends JDialog {
    public EmailDialog(MainFrame parentFrame) {
        super(parentFrame,"Email a Cliente",true);
        setSize(300, 700);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField emailField = new JTextField();
        JTextField oggettoField = new JTextField();
        JTextField corpoField = new JTextField();
        add(new JLabel("Email:"), c);
        c.gridx++;
        add(emailField,c);
        c.gridx = 0;
        c.gridy++;
        add(new JLabel("Oggetto:"), c);
        c.gridx++;
        add(oggettoField,c);
        c.gridx = 0;
        c.gridy++;
        add(new JLabel("Corpo:"), c);
        c.gridx++;
        add(corpoField,c);
        c.gridx = 0;
        c.gridy++;

        add(MyButton.createButton("Annulla",() -> DialogController.annulla(this)),c);
        c.gridx++;
        add(MyButton.createButton("Invia",() -> manda(emailField.getText(), oggettoField.getText(), corpoField.getText())),c);

        setVisible(true);
    }
}
