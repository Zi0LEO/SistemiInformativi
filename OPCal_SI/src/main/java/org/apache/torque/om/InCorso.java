package org.apache.torque.om;

import java.io.Serial;
import java.sql.Date;

public  class InCorso
    extends org.apache.torque.om.BaseInCorso
{
    @Serial
    private static final long serialVersionUID = 1741196585711L;

    public InCorso(){
        super();
        setDataSpedizione(new Date(System.currentTimeMillis()));
        setStato("Presa in carico");
    }



}
