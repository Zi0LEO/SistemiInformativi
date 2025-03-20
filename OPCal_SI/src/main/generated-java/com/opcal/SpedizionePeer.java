package com.opcal;

import java.util.HashMap;
import java.util.Map;

public class SpedizionePeer
    extends BaseSpedizionePeer {

  public static Map<String, String> getFields(){
    String tableName = SpedizionePeer.TABLE_NAME;
    Map ret = new HashMap();
    ret.put("Codice", tableName);
    ret.put("Mittente", tableName);
    ret.put("Destinatario", tableName);
    ret.put("Stato", tableName);
    ret.put("Peso", tableName);
    ret.put("Prezzo", tableName);
    ret.put("Corriere", tableName);

    return ret;
  }
}
