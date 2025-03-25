package com.opcal.view;

import com.opcal.controller.MainController;
import com.opcal.model.Dati;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
  private final CardLayout cardLayout;
  private final JPanel cardPanel;

  public Dati getLoggedUser() {
    return loggedUser;
  }

  public void setLoggedUser(String email)
  {
    if(email == null) {
      cardLayout.show(cardPanel, "Login");
      loggedUser = null;
      return;
    }

    if(loggedUser != null && loggedUser.getEmail().equals(email)) {
      loggedUser = MainController.trovaUtente(email);
    }
    if(loggedUser == null) {
      loggedUser = MainController.trovaUtente(email);
      MainPage mainPage = new MainPage(this);
      cardPanel.add(mainPage, "Main");
      cardLayout.show(cardPanel, "Main");
    }

  }

  private Dati loggedUser;

  public MainFrame() {
    setTitle("Sistema Informativo OPCal");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
    headerPanel.setBackground(new Color(80, 80, 85));

    // Logo
    JLabel iconLabel = new JLabel();
    ImageIcon icon = new ImageIcon("../report/assets/logo.png");
    Image img = icon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
    iconLabel.setIcon(new ImageIcon(img));

    // Titolo
    JLabel titleLabel = new JLabel("OPCAL");
    titleLabel.setForeground(Color.WHITE);
    titleLabel.setFont(new Font("Cantarell", Font.BOLD, 40));

    // Aggiungi logo e titolo all'header
    headerPanel.add(iconLabel);
    headerPanel.add(titleLabel);
    add(headerPanel, BorderLayout.NORTH);

    // Creazione del CardLayout e del pannello principale
    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);

    // Aggiungi le pagine
    cardPanel.add(new LoginPage(this), "Login");
    cardPanel.add(new RegistrationPage(this), "Registration");
    cardPanel.add(new JPanel(), "Main");

    // Mostra la prima pagina (Login)
    cardLayout.show(cardPanel, "Login");

    // Aggiungi il pannello principale al frame
    add(cardPanel);
    setVisible(true);
  }

  public void showPage(String pageName) {
    cardLayout.show(cardPanel, pageName);
    cardPanel.invalidate();
    cardPanel.revalidate();
    cardPanel.repaint();
  }
}
