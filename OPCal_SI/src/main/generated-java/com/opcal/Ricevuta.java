package com.opcal;


import org.apache.torque.TorqueException;

import java.io.Serial;
import java.sql.Date;

public class Ricevuta
    extends BaseRicevuta {
  @Serial
  private static final long serialVersionUID = 1741196585681L;

  public Ricevuta() {
    super();
  }

  public Ricevuta(Spedizione spedizione, boolean pagato) throws TorqueException {
    super();
    if (pagato) {
     setStato("Pagamento confermato");
    }
    else
      setStato("Non regolarizzata");
    setData(new Date(System.currentTimeMillis()));
    setSpedizione(spedizione);
  }

}
