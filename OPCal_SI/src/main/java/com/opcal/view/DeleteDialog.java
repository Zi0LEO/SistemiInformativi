package com.opcal.view;

import com.opcal.controller.DeleteDialogController;
import com.opcal.controller.DialogController;

import javax.swing.*;
import java.awt.*;

public class DeleteDialog extends JDialog {

  public DeleteDialog(MainFrame parentFrame, String message, String email) {
    super();
    setLayout(new FlowLayout());
    setSize(300, 150);
    setModal(true);
    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    setBackground(new Color(255, 100, 100));
    setTitle("Elimina Account");
    setResizable(false);
    add(new JLabel("Sei sicuro di voler eliminare l'account?"));

    add(MyButton.createButton("Conferma", () -> DeleteDialogController.elimina(email, parentFrame, this)));
    add(MyButton.createButton("Annulla", () -> DialogController.annulla(this)));

  }

}
