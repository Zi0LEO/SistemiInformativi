package com.opcal.controller;

import javax.swing.*;
import java.awt.*;

public abstract class DialogController {

  public static void annulla(Dialog dialog){
    JOptionPane.showMessageDialog(dialog, "Operazione annullata", "Operazione annullata", JOptionPane.INFORMATION_MESSAGE);
    dialog.dispose();
  }
}
