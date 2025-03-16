package com.opcal.view;

import com.opcal.controller.LoginController;
import com.opcal.model.Dati;
import com.opcal.model.DatiCliente;
import com.opcal.model.DatiDipendente;

import javax.swing.*;
import java.awt.*;

class LoginPage extends JPanel {

  public LoginPage(MainFrame mainFrame) {
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

    JButton loginButton = getJButton(mainFrame, userField, passField);
    formPanel.add(loginButton, gbc);

    // Pulsante Registrati
    gbc.gridy++;
    JButton registerButton = new JButton("Registrati");
    registerButton.setPreferredSize(new Dimension(200, 50));
    registerButton.setFont(new Font("Cantarell", Font.BOLD, 20));
    registerButton.addActionListener(e -> {
      mainFrame.showPage("REGISTRATION"); // Cambia pagina
    });
    formPanel.add(registerButton, gbc);

    add(formPanel);
  }

  private JButton getJButton(MainFrame mainFrame, JTextField userField, JPasswordField passField) {
    JButton loginButton = new JButton("Login");
    loginButton.setPreferredSize(new Dimension(200, 50));
    loginButton.setFont(new Font("Cantarell", Font.BOLD, 20));
    loginButton.addActionListener(e -> {
      String email = userField.getText();
      String password = new String(passField.getPassword());

      Dati dati = LoginController.login(email, password);

      if (dati == null) {
        JOptionPane.showMessageDialog(LoginPage.this, "Credenziali non valide!", "Errore", JOptionPane.ERROR_MESSAGE);
      }
      else {
        ((MainFrame) SwingUtilities.getWindowAncestor(this)).setLoggedUser(dati);
        mainFrame.loggedIn();
      }
    });
    return loginButton;
  }
}
