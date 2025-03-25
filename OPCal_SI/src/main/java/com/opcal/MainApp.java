package com.opcal;

import com.opcal.model.TorqueInitializer;
import com.opcal.view.MainFrame;

import javax.swing.*;

public class MainApp {
   public static void main(String[] args) {

     TorqueInitializer.initTorque();

     SwingUtilities.invokeLater(MainFrame::new);
     }
  }

