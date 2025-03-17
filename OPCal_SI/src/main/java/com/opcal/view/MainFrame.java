package com.opcal.view;

import com.opcal.model.Dati;

import javax.swing.*;
import java.awt.*;

class MainFrame extends JFrame {
  private final CardLayout cardLayout;
  private final JPanel cardPanel;
  private final MainPage mainPage;

  public Dati getLoggedUser() {
    return loggedUser;
  }

  public void setLoggedUser(Dati loggedUser) {
    this.loggedUser = loggedUser;
  }

  private Dati loggedUser;

  public MainFrame() {
    setTitle("Sistema Informativo OPCal");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // Creazione del CardLayout e del pannello principale
    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);

    // Aggiungi le pagine
    mainPage = new MainPage(this);
    cardPanel.add(createPageWithHeader(new LoginPage(this)), "LOGIN");
    cardPanel.add(createPageWithHeader(new RegistrationPage(this)), "REGISTRATION");
    cardPanel.add(createPageWithHeader(mainPage), "Main");

    // Mostra la prima pagina (Login)
    cardLayout.show(cardPanel, "LOGIN");

    // Aggiungi il pannello principale al frame
    add(cardPanel);
    setVisible(true);
  }

  // Metodo per creare una pagina con l'header
  private JPanel createPageWithHeader(JPanel contentPanel) {
    JPanel pageWithHeader = new JPanel(new BorderLayout());
    pageWithHeader.setBackground(new Color(45, 45, 48));

    // Header Panel
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

    // Aggiungi header e contenuto alla pagina
    pageWithHeader.add(headerPanel, BorderLayout.NORTH);
    pageWithHeader.add(contentPanel, BorderLayout.CENTER);

    return pageWithHeader;
  }

  // Metodo per cambiare pagina
  public void loggedIn() {
    cardLayout.show(cardPanel, "Main");
    mainPage.updateContent();
  }

  public void showPage(String pageName) {
    cardLayout.show(cardPanel, pageName);
  }
}
