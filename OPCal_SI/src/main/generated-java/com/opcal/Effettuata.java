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

  public Effettuata(String codice, Date dataSpedizione) {
    super();
    setCodice(codice);
    setDataConsegna(new Date(System.currentTimeMillis()));
    setDataSpedizione(new Date(dataSpedizione.getTime()));
  }


}
