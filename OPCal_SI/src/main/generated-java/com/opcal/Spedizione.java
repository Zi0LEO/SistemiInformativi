package com.opcal;


import java.io.Serial;

public class Spedizione
    extends BaseSpedizione {
  @Serial
  private static final long serialVersionUID = 1741196585693L;

  public Spedizione() {
    super();
  }

  public Spedizione(String codice, String emailMittente, String emailDestinatario, int peso, int prezzo) {
    super();
    setCodice(codice);
    setEmailMittente(emailMittente);
    setEmailDestinatario(emailDestinatario);
    setPeso(peso);
    setPrezzo(prezzo);
  }

}
