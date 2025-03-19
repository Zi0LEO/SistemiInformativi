package com.opcal.view;

import com.opcal.controller.LoginController;

import javax.swing.*;
import java.awt.*;

class LoginPage extends JPanel {

  public LoginPage(MainFrame mainFrame, MainPage mainPage) {
    setBackground(new Color(45, 45, 48));
    setLayout(new GridBagLayout());

    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(new Color(100, 100, 105));
    formPanel.setPreferredSize(new Dimension(500, 350));
    formPanel.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 130), 3, true));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(15, 20, 15, 20);
    gbc.anchor = GridBagConstraints.WEST;

    // Campo email
    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel userLabel = new JLabel("E-mail:");
    userLabel.setForeground(Color.WHITE);
    userLabel.setFont(new Font("Cantarell", Font.BOLD, 20));
    formPanel.add(userLabel, gbc);

    gbc.gridx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    JTextField userField = new JTextField(15);
    userField.setPreferredSize(new Dimension(250, 35));
    userField.setFont(new Font("Cantarell", Font.PLAIN, 18));
    formPanel.add(userField, gbc);

    // Campo password
    gbc.gridx = 0;
    gbc.gridy++;
    JLabel passLabel = new JLabel("Password:");
    passLabel.setForeground(Color.WHITE);
    passLabel.setFont(new Font("Cantarell", Font.BOLD, 20));
    formPanel.add(passLabel, gbc);

    gbc.gridx = 1;
    JPasswordField passField = new JPasswordField(15);
    passField.setPreferredSize(new Dimension(250, 35));
    passField.setFont(new Font("Cantarell", Font.PLAIN, 18));
    formPanel.add(passField, gbc);

    // Pulsante Login
    gbc.gridx = 0;
    gbc.gridy++;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.CENTER;

    formPanel.add(MyButton.createButton("Login", () -> LoginController.login(mainFrame, userField.getText(), passField.getPassword(), mainPage)),gbc);

    // Pulsante Registrati
    gbc.gridy++;
    formPanel.add(MyButton.createButton("Registrati", () -> mainFrame.showPage("Registration")), gbc);

    add(formPanel);
  }
}
