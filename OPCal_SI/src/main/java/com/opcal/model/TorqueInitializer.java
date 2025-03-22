package com.opcal.model;

import org.apache.torque.Torque;
import org.apache.torque.TorqueException;

public class TorqueInitializer {
  public static void initTorque() {
    try {
      Torque.init("src/main/resources/torque.properties");
    } catch (TorqueException e) {
      e.printStackTrace();
    }
  }
}
