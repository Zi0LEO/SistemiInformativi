package com.opcal.view;

import javax.swing.*;
import java.awt.*;


class RegistrationPage extends JPanel {

  public RegistrationPage(MainFrame mainFrame) {
    setBackground(new Color(45, 45, 48));
    setLayout(new GridBagLayout());

    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(new Color(100, 100, 105));
    formPanel.setPreferredSize(new Dimension(500, 450)); // Più alto per i campi aggiuntivi
    formPanel.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 130), 3, true));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 20, 10, 20);
    gbc.anchor = GridBagConstraints.WEST;

    // Campo Nome
    gbc.gridx = 0;
    gbc.gridy = 0;
    JLabel nameLabel = new JLabel("Nome:");
    nameLabel.setForeground(Color.WHITE);
    nameLabel.setFont(new Font("Cantarell", Font.BOLD, 20));
    formPanel.add(nameLabel, gbc);

    gbc.gridx = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    JTextField nameField = new JTextField(15);
    nameField.setPreferredSize(new Dimension(250, 35));
    nameField.setFont(new Font("Cantarell", Font.PLAIN, 18));
    formPanel.add(nameField, gbc);

    // Campo Cognome
    gbc.gridx = 0;
    gbc.gridy++;
    JLabel surnameLabel = new JLabel("Cognome:");
    surnameLabel.setForeground(Color.WHITE);
    surnameLabel.setFont(new Font("Cantarell", Font.BOLD, 20));
    formPanel.add(surnameLabel, gbc);

    gbc.gridx = 1;
    JTextField surnameField = new JTextField(15);
    surnameField.setPreferredSize(new Dimension(250, 35));
    surnameField.setFont(new Font("Cantarell", Font.PLAIN, 18));
    formPanel.add(surnameField, gbc);

    // Campo Email
    gbc.gridx = 0;
    gbc.gridy++;
    JLabel emailLabel = new JLabel("E-mail:");
    emailLabel.setForeground(Color.WHITE);
    emailLabel.setFont(new Font("Cantarell", Font.BOLD, 20));
    formPanel.add(emailLabel, gbc);

    gbc.gridx = 1;
    JTextField emailField = new JTextField(15);
    emailField.setPreferredSize(new Dimension(250, 35));
    emailField.setFont(new Font("Cantarell", Font.PLAIN, 18));
    formPanel.add(emailField, gbc);

    // Campo Password
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

    // Pulsante Registrati
    gbc.gridx = 0;
    gbc.gridy++;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.CENTER;
    JButton registerButton = new JButton("Registrati");
    registerButton.setPreferredSize(new Dimension(200, 50));
    registerButton.setFont(new Font("Cantarell", Font.BOLD, 20));
    registerButton.addActionListener(e -> {
      // Qui puoi aggiungere la logica di registrazione
      String nome = nameField.getText();
      String cognome = surnameField.getText();
      String email = emailField.getText();
      String password = new String(passField.getPassword());

      JOptionPane.showMessageDialog(RegistrationPage.this, "Registrazione completata!", "Successo", JOptionPane.INFORMATION_MESSAGE);
      mainFrame.showPage("LOGIN"); // Torna al login dopo la registrazione
    });
    formPanel.add(registerButton, gbc);

    // Pulsante Torna al Login
    gbc.gridy++;
    JButton backButton = new JButton("Torna al Login");
    backButton.setPreferredSize(new Dimension(200, 50));
    backButton.setFont(new Font("Cantarell", Font.BOLD, 20));
    backButton.addActionListener(e -> {
      mainFrame.showPage("LOGIN"); // Torna al login
    });
    formPanel.add(backButton, gbc);

    add(formPanel);
  }
}
