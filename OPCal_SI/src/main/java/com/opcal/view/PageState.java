package com.opcal.view;

import javax.swing.*;
import java.awt.*;

public interface PageState {


  default void buildPage(MainPage page){
    page.setLayout(new BorderLayout(5,5));

    page.datiPanel = new JPanel();
    page.datiPanel.add(new JLabel("I tuoi dati:"));
    page.datiPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    page.datiPanel.setBackground(new Color(200, 220, 240)); // Colore di sfondo
    page.datiPanel.add(new JLabel(page.getParentFrame().getLoggedUser().getNome()));
    page.datiPanel.add(new JLabel(page.getParentFrame().getLoggedUser().getCognome()));
    page.datiPanel.add(new JLabel(page.getParentFrame().getLoggedUser().getEmail()));

    page.add(page.datiPanel, BorderLayout.NORTH);

  }
}
