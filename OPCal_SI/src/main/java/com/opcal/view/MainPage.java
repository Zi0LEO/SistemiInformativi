package com.opcal.view;

import com.opcal.Indirizzo;
import com.opcal.controller.MainController;
import com.opcal.model.Dati;
import com.opcal.model.DatiCliente;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainPage extends JPanel{

  // Campi per visualizzare/modificare i dati
  private final JPanel datiPanel;

  private Dati dati;

  private final JButton eliminaButton;

  public MainPage(MainFrame parentFrame) {
    setLayout(new BorderLayout(5,5));
    setBackground(new Color(240, 240, 240)); // Colore di sfondo

    // Pannello superiore: Dati cliente
    datiPanel = new JPanel();
    datiPanel.add(new JLabel("I tuoi dati:"));
    add(datiPanel, BorderLayout.NORTH);

    // Pannello centrale: Pulsanti
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
    buttonPanel.setBackground(new Color(240, 240, 240));

    // Pulsante modifica dati
    JButton modificaButton = new JButton("Modifica i tuoi dati");
    modificaButton.setFont(new Font("Arial", Font.BOLD, 16));
    modificaButton.setPreferredSize(new Dimension(200, 40));
    modificaButton.addActionListener(e -> {
      EditDataDialog editDialog = new EditDataDialog(parentFrame, dati.getEmail());
    });
    buttonPanel.add(modificaButton);

    // Pulsante Elimina Account
    eliminaButton = new JButton("Elimina Account");
    eliminaButton.setFont(new Font("Arial", Font.BOLD, 16));
    eliminaButton.setPreferredSize(new Dimension(200, 40));
    eliminaButton.setBackground(new Color(255, 100, 100)); // Rosso per indicare pericolo

    eliminaButton.addActionListener(actionEvent -> {
      JDialog dialog = new JDialog();
      dialog.setLayout(new FlowLayout());
      dialog.setSize(300, 150);
      dialog.setLocationRelativeTo(eliminaButton);
      dialog.setModal(true);
      dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      dialog.setTitle("Elimina Account");
      dialog.setResizable(false);
      dialog.add(new JLabel("Sei sicuro di voler eliminare l'account?"));

      JButton confirmButton = new JButton("Conferma");
      confirmButton.addActionListener(confirmActionEvent -> {
        if (dati == null || dati.getEmail() == null) {
          JOptionPane.showMessageDialog(dialog, "Errore: Nessun dato utente trovato.", "Errore", JOptionPane.ERROR_MESSAGE);
          dialog.dispose();
          return;
        }
        //boolean success = MainController.eliminaAccount(dati.getEmail());
//        if (success) {
//          JOptionPane.showMessageDialog(dialog, "Account eliminato con successo!", "Conferma", JOptionPane.INFORMATION_MESSAGE);
          parentFrame.showPage("LOGIN");
//        } else {
//          JOptionPane.showMessageDialog(dialog, "Errore durante l'eliminazione dell'account!", "Errore", JOptionPane.ERROR_MESSAGE);
//        }
        dialog.dispose();
      });

      JButton cancelButton = new JButton("Annulla");
      cancelButton.addActionListener(cancelActionEvent -> dialog.dispose());

      dialog.add(confirmButton);
      dialog.add(cancelButton);
      dialog.setVisible(true);
    });

    buttonPanel.add(eliminaButton);

    // Pulsante Visualizza Storico
    JButton storicoButton = new JButton("Visualizza Storico");
    storicoButton.setFont(new Font("Arial", Font.BOLD, 16));
    storicoButton.setPreferredSize(new Dimension(200, 40));
    buttonPanel.add(storicoButton);

    add(buttonPanel, BorderLayout.CENTER);


    QueryResultsTable resultsTable = new QueryResultsTable();
    JScrollPane resultsScrollPane = new JScrollPane(resultsTable);
    resultsScrollPane.setPreferredSize(new Dimension(600, 400));
    add(resultsScrollPane, BorderLayout.SOUTH);

    String[] columnNames = {"ID", "Name", "Value", "Date"}; //aggiungi i campi necessari qui
    resultsTable.setColumnNames(columnNames);

    //aggiungi i risultati delle query
    List<Object[]> queryResults = List.of(
        new Object[]{1, "John", 123.45, "2023-10-20"},
        new Object[]{2, "Jane", 678.90, "2023-10-21"},
        new Object[]{3, "Doe", 345.00, "2023-10-22"}
    );
    resultsTable.updateTableData(queryResults);

    //dai l'ordine
    resultsTable.enableSortByColumn();
  }


  public void updateContent() {
    dati = ((MainFrame) SwingUtilities.getWindowAncestor(this)).getLoggedUser();
    datiPanel.removeAll();
    datiPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    datiPanel.setBackground(new Color(200, 220, 240)); // Colore di sfondo
    datiPanel.add(new JLabel(dati.getNome()));
    datiPanel.add(new JLabel(dati.getCognome()));
    datiPanel.add(new JLabel(dati.getEmail()));
    if (dati instanceof DatiCliente) {
      datiPanel.setLayout(new GridLayout(5, 1));
      Indirizzo indirizzo = MainController.trovaIndirizzo(dati.getEmail());
      datiPanel.add(new JLabel(indirizzo.toString()));
    }
    else {
      datiPanel.setLayout(new GridLayout(4, 1));
    }
  }
}
