package com.opcal.view;

import com.opcal.model.TorqueInitializer;
import org.apache.torque.TorqueException;

import javax.swing.*;

public class MultiPageApp {
   public static void main(String[] args) {

     TorqueInitializer.initTorque();

     SwingUtilities.invokeLater(MainFrame::new);
     }
  }

