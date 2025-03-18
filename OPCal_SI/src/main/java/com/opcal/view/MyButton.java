package com.opcal.view;

import javax.swing.JButton;
import java.awt.*;

public class MyButton extends JButton {

  private MyButton(String text) {
    super(text);
    this.setFont(new Font("Arial", Font.BOLD, 16));
    this.setPreferredSize(new Dimension(200, 40));
  }

  public static MyButton createButton(String text, Runnable action) {
    MyButton button = new MyButton(text);
    button.addActionListener(e -> action.run());
    return button;
  }
}
