package org.apache.torque.om;


import org.apache.torque.TorqueException;

import java.io.Serial;
import java.sql.Date;

public  class Ricevuta
    extends org.apache.torque.om.BaseRicevuta
{
    @Serial
    private static final long serialVersionUID = 1741196585681L;

    public Ricevuta(Spedizione spedizione) throws TorqueException {
        super();
        setData(new Date(System.currentTimeMillis()));
        setSpedizione(spedizione);
    }

}
