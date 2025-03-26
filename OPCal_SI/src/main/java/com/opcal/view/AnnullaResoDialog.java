package com.opcal.view;
import com.opcal.controller.DialogController;

import javax.swing.*;
import java.awt.*;

import static com.opcal.controller.AnnullaResoController.confermaAnnullamento;

public class AnnullaResoDialog extends JDialog {
    public AnnullaResoDialog() {
        setTitle("Annulla Reso");
        setModal(true);
        setResizable(false);
        setSize(300, 100);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0;
        add(new JLabel("Codice Reso: "), c);
        c.gridx++;
        JTextField codiceReso = new JTextField();
        add(codiceReso, c);

        c.gridx = 0; c.gridy++;
        add(MyButton.createButton("Annulla",() -> DialogController.annulla(this)), c);
        c.gridx++;
        add(MyButton.createButton("Conferma",() -> confermaAnnullamento(this,codiceReso.getText())), c);
        setVisible(true);

    }
}
