package com.opcal;

import com.opcal.model.TorqueInitializer;
import com.opcal.view.MainFrame;
import org.apache.torque.TorqueException;

import javax.swing.*;

public class MainApp {
   public static void main(String[] args) throws TorqueException {

     TorqueInitializer.initTorque();

     SwingUtilities.invokeLater(MainFrame::new);
     }
  }

