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
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout());

        JLabel reso = new JLabel("Codice Reso");
        JTextField codiceReso = new JTextField();
        add(reso);
        add(codiceReso);
        add(MyButton.createButton("Annulla",() -> DialogController.annulla(this)));
        add(MyButton.createButton("Conferma",() -> confermaAnnullamento(this,codiceReso.getText())));

    }
}
