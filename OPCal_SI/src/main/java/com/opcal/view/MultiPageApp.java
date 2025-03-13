import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiPageApp {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new MainFrame());
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
        // Simula il login (qui puoi aggiungere la logica di autenticazione)
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
  public MainPage() {
    setBackground(new Color(45, 45, 48));
    setLayout(new BorderLayout());

    JLabel titleLabel = new JLabel("Benvenuto nella schermata principale!", SwingConstants.CENTER);
    titleLabel.setForeground(Color.WHITE);
    titleLabel.setFont(new Font("Cantarell", Font.BOLD, 30));
    add(titleLabel, BorderLayout.CENTER);
  }
}