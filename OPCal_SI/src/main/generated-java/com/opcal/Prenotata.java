package com.opcal;

import java.io.Serial;
import java.sql.Date;

public class Prenotata
    extends BasePrenotata {
  @Serial
  private static final long serialVersionUID = 1741196585703L;
  private static final long MILLS_IN_WEEK = 1000 * 60 * 60 * 24 * 7;

  public Prenotata() {
    super();
  }

  public Prenotata(Spedizione spedizione) {
    setSpedizione(spedizione);
    setDataPrenotazione(new Date(System.currentTimeMillis()));

    //data di ritiro di default una settimana dopo la prenotazione
    setDataRitiro(new Date(System.currentTimeMillis() + MILLS_IN_WEEK));
  }


}
