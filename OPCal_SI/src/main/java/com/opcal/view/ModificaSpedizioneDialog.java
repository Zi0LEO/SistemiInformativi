package com.opcal.view;

import com.opcal.controller.ModificaSpedizioneController;

import javax.swing.*;
import java.awt.*;


public class ModificaSpedizioneDialog extends JDialog {
  public ModificaSpedizioneDialog(MainFrame parentFrame) {
    super(parentFrame, "Modifica spedizione", true);
    setSize(300, 200);
    setLocationRelativeTo(null);
    setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(5, 5, 5, 5);
    c.anchor = GridBagConstraints.CENTER;
    c.fill = GridBagConstraints.HORIZONTAL;

    add(new JLabel("Codice spedizione da avanzare"), c);
    c.gridy++;

    JTextField codiceSpedizione = new JTextField();

    add(codiceSpedizione, c);
    c.gridy++;
    c.gridx = 0;
    add(MyButton.createButton("Avanza", () -> ModificaSpedizioneController.avanza(this, Integer.valueOf(codiceSpedizione.getText()))),c);
    c.gridx++;
    add(MyButton.createButton("Annulla", () -> ModificaSpedizioneController.annulla(this)),c);
    setVisible(true);
  }
}
