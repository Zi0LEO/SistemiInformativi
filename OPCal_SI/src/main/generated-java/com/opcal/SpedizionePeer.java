package com.opcal;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class SpedizionePeer
    extends BaseSpedizionePeer {

  public static List<String> getFields(){
    List<String> ret = new LinkedList();
    ret.add("Codice");
    ret.add("Mittente");
    ret.add("Destinatario");
    ret.add("Stato");
    ret.add("Peso");
    ret.add("Prezzo");
    ret.add("Corriere");
    return ret;
  }
}
