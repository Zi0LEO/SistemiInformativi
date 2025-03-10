package org.apache.torque.om;


import java.io.Serial;

public class Spedizione
    extends org.apache.torque.om.BaseSpedizione
{
    @Serial
    private static final long serialVersionUID = 1741196585693L;

    public Spedizione(String codice, String emailMittente, String emailDestinatario, int peso, int prezzo){
        super();
        setCodice(codice);
        setEmailMittente(emailMittente);
        setEmailDestinatario(emailDestinatario);
        setPeso(peso);
        setPrezzo(prezzo);
    }

}
