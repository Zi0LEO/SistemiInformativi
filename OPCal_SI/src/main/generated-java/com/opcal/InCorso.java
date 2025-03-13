package com.opcal;

import java.io.Serial;
import java.sql.Date;

public class InCorso
    extends BaseInCorso {
  @Serial
  private static final long serialVersionUID = 1741196585711L;

  public InCorso(String codice) {
    super();
    setDataSpedizione(new Date(System.currentTimeMillis()));
    setCodice(codice);
    setStato("Presa in carico");
  }


  public InCorso() {
    super();
  }
}
