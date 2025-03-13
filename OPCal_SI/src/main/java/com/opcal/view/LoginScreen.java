package main.java.com.opcal.view;

import javax.swing.*;
import java.awt.*;

public class LoginScreen {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new LoginFrame());
  }
}

class LoginFrame extends JFrame {
  public LoginFrame() {
    setTitle("Sistema Informativo OPCAL");
    setSize(450, 400);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBackground(new Color(60, 63, 65));

    JPanel titleBar = new JPanel(new BorderLayout());
    titleBar.setBackground(new Color(45, 45, 45));

    JLabel iconLabel = new JLabel();
    ImageIcon icon = new ImageIcon("/home/umberto/Documents/primo_semestre/laboratorio_sis/SistemiInformativi/report/assets/logo.png");
    Image img = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
    iconLabel.setIcon(new ImageIcon(img));
    titleBar.add(iconLabel, BorderLayout.CENTER);

    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
    buttonsPanel.setOpaque(false);

    titleBar.add(buttonsPanel, BorderLayout.EAST);
    mainPanel.add(titleBar, BorderLayout.NORTH);

    JPanel centerPanel = new JPanel();
    centerPanel.setLayout(new GridBagLayout());
    centerPanel.setBackground(new Color(60, 63, 65));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(15, 15, 15, 15);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.gridx = 0;
    gbc.gridy = 0;

    JLabel userLabel = new JLabel("Username:");
    userLabel.setForeground(Color.WHITE);
    centerPanel.add(userLabel, gbc);

    gbc.gridy++;
    JTextField userField = new JTextField(15);
    userField.setPreferredSize(new Dimension(250, 40));
    centerPanel.add(userField, gbc);   

    gbc.gridy++;
    JLabel passLabel = new JLabel("Password:");
    passLabel.setForeground(Color.WHITE);
    centerPanel.add(passLabel, gbc);

    gbc.gridy++;
    JPasswordField passField = new JPasswordField(15);
    passField.setPreferredSize(new Dimension(250, 40));
    centerPanel.add(passField, gbc);

    gbc.gridy++;
    JButton loginButton = new JButton("Login");
    loginButton.setPreferredSize(new Dimension(250, 50));

    centerPanel.add(loginButton, gbc);
    mainPanel.add(centerPanel, BorderLayout.CENTER);
    getContentPane().add(mainPanel);
    setVisible(true);
  }
}
