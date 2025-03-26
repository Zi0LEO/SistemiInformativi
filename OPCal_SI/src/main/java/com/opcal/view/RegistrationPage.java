package com.opcal.view;

import com.opcal.controller.RegistrationController;

import javax.swing.*;
import java.awt.*;

class RegistrationPage extends JPanel {

  public RegistrationPage(MainFrame mainFrame) {
    setBackground(new Color(45, 45, 48));
    setLayout(new GridBagLayout());

    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(new Color(100, 100, 105));
    formPanel.setPreferredSize(new Dimension(500, 750)); // Più alto per i campi aggiuntivi
    formPanel.setBorder(BorderFactory.createLineBorder(new Color(120, 120, 130), 3, true));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 20, 10, 20);
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.NONE;

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
    // Campo Comune
    gbc.gridx = 0;
    gbc.gridy++;
    JLabel comuneLabel = new JLabel("Comune:");
    comuneLabel.setForeground(Color.WHITE);
    comuneLabel.setFont(new Font("Cantarell", Font.BOLD, 20));
    formPanel.add(comuneLabel, gbc);

    gbc.gridx = 1;
    JTextField comuneField = new JTextField(15);
    comuneField.setPreferredSize(new Dimension(250, 35));
    comuneField.setFont(new Font("Cantarell", Font.PLAIN, 18));
    formPanel.add(comuneField, gbc);

    // Campo Via
    gbc.gridx = 0;
    gbc.gridy++;
    JLabel viaLabel = new JLabel("Via:");
    viaLabel.setForeground(Color.WHITE);
    viaLabel.setFont(new Font("Cantarell", Font.BOLD, 20));
    formPanel.add(viaLabel, gbc);

    gbc.gridx = 1;
    JTextField viaField = new JTextField(15);
    viaField.setPreferredSize(new Dimension(250, 35));
    viaField.setFont(new Font("Cantarell", Font.PLAIN, 18));
    formPanel.add(viaField, gbc);

    // Campo Civico
    gbc.gridx = 0;
    gbc.gridy++;
    JLabel civicoLabel = new JLabel("Civico:");
    civicoLabel.setForeground(Color.WHITE);
    civicoLabel.setFont(new Font("Cantarell", Font.BOLD, 20));
    formPanel.add(civicoLabel, gbc);

    gbc.gridx = 1;
    JTextField civicoField = new JTextField(15);
    civicoField.setPreferredSize(new Dimension(250, 35));
    civicoField.setFont(new Font("Cantarell", Font.PLAIN, 18));
    formPanel.add(civicoField, gbc);

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

    gbc.anchor = GridBagConstraints.CENTER;

    formPanel.add(MyButton.createButton("Registrati", () ->
        RegistrationController.registrazione(
            mainFrame,
            nameField.getText(),
            surnameField.getText(),
            comuneField.getText(),
            viaField.getText(),
            civicoField.getText(),
            emailField.getText(),
            passField.getPassword())
    ), gbc);

    // Pulsante Torna al Login
    gbc.gridy++;
    formPanel.add(MyButton.createButton("Torna al Login", () -> mainFrame.showPage("Login")), gbc);

    add(formPanel);
  }
}
