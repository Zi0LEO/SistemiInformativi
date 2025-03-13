package com.opcal.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiPageApp {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(MainFrame::new);
  }
}

class MainFrame extends JFrame {
  private CardLayout cardLayout;
  private JPanel cardPanel;

  public MainFrame() {
    setTitle("Applicazione Multi-Pagina");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    // Creazione del CardLayout e del pannello principale
    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);

    // Aggiungi le pagine
    cardPanel.add(createPageWithHeader(new LoginPage(this)), "LOGIN");
    cardPanel.add(createPageWithHeader(new RegistrationPage(this)), "REGISTRATION");
    cardPanel.add(createPageWithHeader(new MainPage()), "MAIN");

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
    ImageIcon icon = new ImageIcon("/home/umberto/Documents/primo_semestre/laboratorio_sis/SistemiInformativi/report/assets/logo.png");
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
  public void showPage(String pageName) {
    cardLayout.show(cardPanel, pageName);
  }
}

class LoginPage extends JPanel {
  private MainFrame mainFrame;

  public LoginPage(MainFrame mainFrame) {
    this.mainFrame = mainFrame;
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
    JButton loginButton = new JButton("Login");
    loginButton.setPreferredSize(new Dimension(200, 50));
    loginButton.setFont(new Font("Cantarell", Font.BOLD, 20));
    loginButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String email = userField.getText();
        String password = new String(passField.getPassword());

        if (email.equals("test@example.com") && password.equals("password")) {
          mainFrame.showPage("MAIN"); // Cambia pagina
        } else {
          JOptionPane.showMessageDialog(LoginPage.this, "Credenziali non valide!", "Errore", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
    formPanel.add(loginButton, gbc);

    // Pulsante Registrati
    gbc.gridy++;
    JButton registerButton = new JButton("Registrati");
    registerButton.setPreferredSize(new Dimension(200, 50));
    registerButton.setFont(new Font("Cantarell", Font.BOLD, 20));
    registerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        mainFrame.showPage("REGISTRATION"); // Cambia pagina
      }
    });
    formPanel.add(registerButton, gbc);

    add(formPanel);
  }
}

class RegistrationPage extends JPanel {
  private MainFrame mainFrame;

  public RegistrationPage(MainFrame mainFrame) {
    this.mainFrame = mainFrame;
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
    registerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Qui puoi aggiungere la logica di registrazione
        String nome = nameField.getText();
        String cognome = surnameField.getText();
        String email = emailField.getText();
        String password = new String(passField.getPassword());

        JOptionPane.showMessageDialog(RegistrationPage.this, "Registrazione completata!", "Successo", JOptionPane.INFORMATION_MESSAGE);
        mainFrame.showPage("LOGIN"); // Torna al login dopo la registrazione
      }
    });
    formPanel.add(registerButton, gbc);

    // Pulsante Torna al Login
    gbc.gridy++;
    JButton backButton = new JButton("Torna al Login");
    backButton.setPreferredSize(new Dimension(200, 50));
    backButton.setFont(new Font("Cantarell", Font.BOLD, 20));
    backButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        mainFrame.showPage("LOGIN"); // Torna al login
      }
    });
    formPanel.add(backButton, gbc);

    add(formPanel);
  }
}
class MainPage extends JPanel {
  // Campi per visualizzare/modificare i dati
  private JTextField nomeField;
  private JTextField cognomeField;
  private JTextField emailField;

  // Pulsanti per le azioni
  private JButton modificaNomeButton;
  private JButton modificaCognomeButton;
  private JButton eliminaButton;
  private JButton storicoButton;

  // Area per visualizzare lo storico delle consegne
  private JTextArea storicoArea;

  public MainPage() {
    setLayout(new BorderLayout());
    setBackground(new Color(240, 240, 240)); // Colore di sfondo

    // Pannello superiore: Dati cliente
    JPanel datiPanel = new JPanel(new GridLayout(3, 2, 10, 10));
    datiPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    datiPanel.setBackground(new Color(200, 220, 240)); // Colore di sfondo

    // Campo Nome
    datiPanel.add(new JLabel("Nome:"));
    nomeField = new JTextField();
    nomeField.setFont(new Font("Arial", Font.PLAIN, 16));
    datiPanel.add(nomeField);

    // Campo Cognome
    datiPanel.add(new JLabel("Cognome:"));
    cognomeField = new JTextField();
    cognomeField.setFont(new Font("Arial", Font.PLAIN, 16));
    datiPanel.add(cognomeField);

    // Campo Email
    datiPanel.add(new JLabel("Email:"));
    emailField = new JTextField();
    emailField.setFont(new Font("Arial", Font.PLAIN, 16));
    emailField.setEditable(false); // L'email non è modificabile
    datiPanel.add(emailField);

    add(datiPanel, BorderLayout.NORTH);

    // Pannello centrale: Pulsanti
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    buttonPanel.setBackground(new Color(240, 240, 240));

    // Pulsante Modifica Nome
    modificaNomeButton = new JButton("Modifica Nome");
    modificaNomeButton.setFont(new Font("Arial", Font.BOLD, 16));
    modificaNomeButton.setPreferredSize(new Dimension(200, 40));
    buttonPanel.add(modificaNomeButton);

    // Pulsante Modifica Cognome
    modificaCognomeButton = new JButton("Modifica Cognome");
    modificaCognomeButton.setFont(new Font("Arial", Font.BOLD, 16));
    modificaCognomeButton.setPreferredSize(new Dimension(200, 40));
    buttonPanel.add(modificaCognomeButton);

    // Pulsante Elimina Account
    eliminaButton = new JButton("Elimina Account");
    eliminaButton.setFont(new Font("Arial", Font.BOLD, 16));
    eliminaButton.setPreferredSize(new Dimension(200, 40));
    eliminaButton.setBackground(new Color(255, 100, 100)); // Rosso per indicare pericolo
    buttonPanel.add(eliminaButton);

    // Pulsante Visualizza Storico
    storicoButton = new JButton("Visualizza Storico");
    storicoButton.setFont(new Font("Arial", Font.BOLD, 16));
    storicoButton.setPreferredSize(new Dimension(200, 40));
    buttonPanel.add(storicoButton);

    add(buttonPanel, BorderLayout.CENTER);

    // Pannello inferiore: Storico consegne
    JPanel storicoPanel = new JPanel(new BorderLayout());
    storicoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    storicoPanel.setBackground(new Color(240, 240, 240));

    storicoArea = new JTextArea();
    storicoArea.setFont(new Font("Arial", Font.PLAIN, 16));
    storicoArea.setEditable(false); // L'area di testo è di sola lettura
    JScrollPane scrollPane = new JScrollPane(storicoArea);
    storicoPanel.add(scrollPane, BorderLayout.CENTER);

    add(storicoPanel, BorderLayout.SOUTH);
  }

  // Metodi per aggiornare i dati nella GUI
  public void setNome(String nome) {
    nomeField.setText(nome);
  }

  public void setCognome(String cognome) {
    cognomeField.setText(cognome);
  }

  public void setEmail(String email) {
    emailField.setText(email);
  }

  public void setStorico(String storico) {
    storicoArea.setText(storico);
  }

  // Metodi per ottenere i dati dalla GUI
  public String getNome() {
    return nomeField.getText();
  }

  public String getCognome() {
    return cognomeField.getText();
  }

  // Metodi per aggiungere listener ai pulsanti
  public void addModificaNomeListener(ActionListener listener) {
    modificaNomeButton.addActionListener(listener);
  }

  public void addModificaCognomeListener(ActionListener listener) {
    modificaCognomeButton.addActionListener(listener);
  }

  public void addEliminaListener(ActionListener listener) {
    eliminaButton.addActionListener(listener);
  }

  public void addStoricoListener(ActionListener listener) {
    storicoButton.addActionListener(listener);
  }
}