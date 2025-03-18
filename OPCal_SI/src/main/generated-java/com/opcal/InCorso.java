package com.opcal;

import java.io.Serial;
import java.sql.Date;

public class InCorso
    extends BaseInCorso {
  @Serial
  private static final long serialVersionUID = 1741196585711L;

  public InCorso() {
    super();
  }
  public InCorso(Spedizione spedizione) {
    setDataSpedizione(new Date(System.currentTimeMillis()));
    setStato("Presa in carico");
    setSpedizione(spedizione);
  }
}
