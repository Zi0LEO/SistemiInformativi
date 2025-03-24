package com.opcal.view;

import com.opcal.controller.MainController;
import com.opcal.model.DatiCliente;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class MainPage extends JPanel{

  public JPanel datiPanel;
  private final MainFrame parentFrame;
  private final PageState pageState;

  private Map<String, String> currentlyShownData;
  public final MainController controller;

  public QueryResultsTable table;

  public void setData(Map<String, String> data){
    currentlyShownData = data;
  }
  public Map<String, String> getData(){
    return currentlyShownData;
  }

  public MainFrame getParentFrame() {
    return parentFrame;
  }


  public MainPage(MainFrame parentFrame) {
    this.parentFrame = parentFrame;
    controller = new MainController(this);
    if(parentFrame.getLoggedUser() instanceof DatiCliente){
      this.pageState = new ClienteState();
    }else{
      this.pageState = new DipendenteState();
    }
    buildPage();
    }


  public void buildPage() {
    pageState.buildPage(this);
  }
}
