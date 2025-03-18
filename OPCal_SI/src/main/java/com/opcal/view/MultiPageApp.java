package com.opcal.view;

import com.opcal.IndirizzoPeer;
import com.opcal.model.TorqueInitializer;
import org.apache.torque.TorqueException;
import org.apache.torque.map.ColumnMap;

import javax.swing.*;

public class MultiPageApp {
   public static void main(String[] args) throws TorqueException {

     TorqueInitializer.initTorque();

     SwingUtilities.invokeLater(MainFrame::new);
     }
  }

