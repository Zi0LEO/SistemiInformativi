package com.opcal;

import java.io.Serial;
import java.sql.Date;

public class Effettuata
    extends BaseEffettuata {
  @Serial
  private static final long serialVersionUID = 1741196585721L;

  public Effettuata() {
    super();
  }

  public Effettuata(Spedizione spedizione, Date dataSpedizione) {
    setSpedizione(spedizione);
    setDataConsegna(new Date(System.currentTimeMillis()));
    setDataSpedizione(new Date(dataSpedizione.getTime()));
  }


}
