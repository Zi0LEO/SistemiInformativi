package main.java.com.opcal.model;

import com.opcal.*;
import org.apache.torque.TorqueException;
import org.apache.torque.criteria.Criteria;
import org.apache.torque.util.Transaction;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class GestoreRecapiti {
    private static int recapitiGestiti;

    public static boolean creaSpedizione(Cliente mittente, Cliente destinatario, int peso) {
        String codice = generaCodice();
        Connection connection = null;
        InCorso inCorso = new InCorso(codice);
        Spedizione spedizione = new Spedizione(codice, mittente.getEmail(), destinatario.getEmail(), peso, calcolaPrezzo(peso));
        try {
            connection = Transaction.begin();
            inCorso.save();
            spedizione.addInCorso(inCorso);
            spedizione.save();
            creaRicevuta(spedizione);
            Transaction.commit(connection);
        } catch (TorqueException e) {
            Transaction.safeRollback(connection);
            return false;
        }
        return true;
    }

    public static boolean creaRitiro(Cliente mittente, Cliente destinatario, int peso) {
        Prenotata prenotata = new Prenotata();
        String codice = generaCodice();
        Connection connection = null;
        Spedizione spedizione = new Spedizione(codice, mittente.getEmail(), destinatario.getEmail(), peso, calcolaPrezzo(peso));
        try {
            connection = Transaction.begin();
            prenotata.save();
            spedizione.addPrenotata(prenotata);
            spedizione.save();
            creaRicevuta(spedizione);
            Transaction.commit(connection);
        } catch (TorqueException e) {
            Transaction.safeRollback(connection);
            return false;
        }
        return true;
    }

    private synchronized static String generaCodice() {
        recapitiGestiti++;
        Integer recapiti = recapitiGestiti;
        String ret = recapiti.toString();
        StringBuilder sb = new StringBuilder();
        sb.repeat("0", 6 - ret.length());
        sb.append(ret);
        return sb.toString();
    }

    private static void creaRicevuta(Spedizione spedizione) throws TorqueException {
        Ricevuta ricevuta = new Ricevuta(spedizione);
        ricevuta.save();
    }

    //temporaneo
    private static int calcolaPrezzo(int peso) {
        return peso * 5;
    }

    public static Indirizzo visualizzaIndirizzo(Cliente cliente) {
        Criteria criteria = new Criteria();
        criteria.where(IndirizzoPeer.EMAIL_CLIENTE, cliente.getEmail());
        try {
            return IndirizzoPeer.doSelect(criteria).getFirst();
        } catch (TorqueException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<Indirizzo> visualizzaIndirizzi(int criterio, String options) {
        Criteria criteria = new Criteria();
        try {
            switch (criterio) {
                case 1:
                    criteria.where(IndirizzoPeer.COMUNE, options);
                    break;
                case 2:
                    criteria.where(IndirizzoPeer.VIA, options);
                    break;
                case 3:
                    criteria.where(IndirizzoPeer.ORARIO, Integer.valueOf(options));
                    break;
            }
            return IndirizzoPeer.doSelect(criteria);

        } catch (TorqueException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean cambiaStato(Spedizione spedizione, int stato) {
        switch (stato) {
            case 1:
                return prendiInCarico(spedizione);
            case 2:
                return cambiaStato(spedizione, "Spedita");
            case 3:
                return cambiaStato(spedizione, "Arrivata alla filiale");
            case 4:
                return cambiaStato(spedizione, "In Consegna");
            case 5:
                return consegna(spedizione);
            case 6:
                return cambiaStato(spedizione, "Tentato recapito");
            default:
                return false;

        }
    }

    private static boolean prendiInCarico(Spedizione spedizione) {
        Connection con = null;
        try {
            con = Transaction.begin();
            Prenotata prenotata = PrenotataPeer.retrieveByPK(spedizione.getCodice(), con);
            PrenotataPeer.doDelete(prenotata, con);
            InCorso inCorso = new InCorso(spedizione.getCodice());
            inCorso.save(con);
            spedizione.resetPrenotata();
            spedizione.addInCorso(inCorso, con);
            spedizione.save(con);
            Transaction.commit(con);
        } catch (Exception e) {
            Transaction.safeRollback(con);
            return false;
        }
        return true;
    }

    private static boolean consegna(Spedizione spedizione) {
        Connection con = null;
        try {
            con = Transaction.begin();
            InCorso inCorso = new InCorso(spedizione.getCodice());
            Date dataSpedizione = (Date) inCorso.getDataSpedizione();
            InCorsoPeer.doDelete(inCorso);
            Effettuata effettuata = new Effettuata(spedizione.getCodice(), dataSpedizione);
            effettuata.save();
            spedizione.resetInCorso();
            spedizione.addEffettuata(effettuata);
            spedizione.save();
            Transaction.commit(con);
        } catch (TorqueException e) {
            Transaction.safeRollback(con);
            return false;
        }
        return true;
    }

    private static boolean cambiaStato(Spedizione spedizione, String stato) {
        try {
            InCorso inCorso = InCorsoPeer.retrieveByPK(spedizione.getCodice());
            inCorso.setStato(stato);
        } catch (TorqueException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public static List<Indirizzo> ordinaIndirizzi(int criterio, boolean crescente) {
        try {
            return IndirizzoPeer.ordinaIndirizzi(criterio, crescente);
        } catch (TorqueException e) {
            return null;
        }

    }

}
