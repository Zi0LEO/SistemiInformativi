package com.opcal.model;

import com.opcal.*;
import org.apache.torque.TorqueException;
import org.apache.torque.criteria.Criteria;
import org.apache.torque.util.Transaction;

import java.sql.Connection;
import java.sql.Date;
import java.util.*;

public class GestoreRecapiti {

    public static boolean creaSpedizione(Cliente mittente, Cliente destinatario, int peso) {
        Connection connection = null;

        Spedizione spedizione = new Spedizione(mittente.getEmail(), destinatario.getEmail(), peso, calcolaPrezzo(peso, destinatario.getEmail()));
        InCorso inCorso = new InCorso(spedizione);
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

    public static boolean creaRitiro(String mittente, String destinatario, int peso) {
        Connection connection = null;
        try {
            connection = Transaction.begin();
            Spedizione spedizione = new Spedizione(mittente, destinatario, peso, calcolaPrezzo(peso, destinatario));
            spedizione.save();
            Prenotata prenotata = new Prenotata(spedizione);
            prenotata.save();
            creaRicevuta(spedizione);
            Transaction.commit(connection);
        } catch (TorqueException e) {
            e.printStackTrace();
            Transaction.safeRollback(connection);
            return false;
        }
        return true;
    }

    private static void creaRicevuta(Spedizione spedizione) throws TorqueException {
        Ricevuta ricevuta = new Ricevuta(spedizione);
        ricevuta.save();
    }

    public static Integer calcolaPrezzo(int peso, String emailDestinatario) {
        Indirizzo indirizzo;
        try {
            Criteria criteria = new Criteria();
            criteria.where(IndirizzoPeer.EMAIL_CLIENTE, emailDestinatario);
            indirizzo = IndirizzoPeer.doSelect(criteria).getFirst();
        }catch (TorqueException e){
            e.printStackTrace();
            return null;
        }
        if (indirizzo.getComune().equals("Cosenza") || indirizzo.getComune().equals("Rende") || indirizzo.getComune().equals("Castrolibero"))
            return prezzoSuddiviso(peso, true);

        return prezzoSuddiviso(peso, false);
    }

    private static Integer prezzoSuddiviso(int peso, boolean spedizioneInterna) {
        int centinaia = peso / 100;
        int decine = (peso % 100) / 10;
        int unita = peso % 10;

        int media;
        if (spedizioneInterna)
            return centinaia * 350 + decine * 45 + unita * 5;
        else{
            media = calcolaMediaCorrieri(centinaia, decine, unita);
        }
        return (int) Math.floor(media + (media * 0.2));
    }

    private static Integer calcolaMediaCorrieri(int centinaia, int decine, int unita) {
        Criteria criteria = new Criteria();
        List<Corriere> corrieri;
      try {
          criteria.addSelectColumn(CorrierePeer.PREZZO1)
              .addSelectColumn(CorrierePeer.PREZZO10)
              .addSelectColumn(CorrierePeer.PREZZO100);
          corrieri = CorrierePeer.doSelect(criteria);
      } catch (TorqueException e) {
          return null;
      }
      ListIterator<Corriere> iterator = corrieri.listIterator();
      if (!iterator.hasNext()) return null;
      Corriere corriere = iterator.next();
          int media1 = corriere.getPrezzo1();
          int media10 = corriere.getPrezzo10();
          int media100 = corriere.getPrezzo100();
      while(iterator.hasNext()) {
          corriere = iterator.next();
          media1 = (media1 + corriere.getPrezzo1()) / 2;
          media10 = (media10 + corriere.getPrezzo10()) / 2;
          media100 = (media10 + corriere.getPrezzo100()) / 2;
      }
          return centinaia * media100 + decine * media10 + unita * media1;
      }

    public static Indirizzo visualizzaIndirizzo(String emailCliente) {
        Criteria criteria = new Criteria();
        criteria.where(IndirizzoPeer.EMAIL_CLIENTE, emailCliente);
        try {
            return IndirizzoPeer.doSelect(criteria).getFirst();
        } catch (Exception e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            InCorso inCorso = new InCorso(spedizione);
            inCorso.save(con);
            spedizione.resetPrenotata();
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
            InCorso inCorso = InCorsoPeer.retrieveByPK(spedizione.getCodice());
            Date dataSpedizione = (Date) inCorso.getDataSpedizione();
            InCorsoPeer.doDelete(inCorso);
            Effettuata effettuata = new Effettuata(spedizione, dataSpedizione);
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
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static List<Object[]> mostraSpedizioni(String email, int tipo) {
        Criteria criteria = buildCriteria(email, tipo);
        List<Spedizione> partialResult = fetchSpedizioni(criteria);
        if (partialResult == null)
            return null;
        return costruisciSpedizioni(partialResult);
    }

    private static Criteria buildCriteria(String email, int tipo) {
        Criteria criteria = new Criteria();
        criteria.addSelectColumn(SpedizionePeer.CODICE)
            .addSelectColumn(SpedizionePeer.EMAIL_MITTENTE)
            .addSelectColumn(SpedizionePeer.EMAIL_DESTINATARIO)
            .addSelectColumn(SpedizionePeer.PESO)
            .addSelectColumn(SpedizionePeer.PREZZO);
        switch (tipo) {
            case 1:
                criteria.where(SpedizionePeer.EMAIL_DESTINATARIO, email);
                break;
            case 2:
                criteria.where(SpedizionePeer.EMAIL_MITTENTE, email);
                break;
            case 3:
                criteria.where(SpedizionePeer.EMAIL_MITTENTE, email);
                criteria.or(SpedizionePeer.EMAIL_DESTINATARIO, email);
                criteria.addJoin(SpedizionePeer.CODICE, InCorsoPeer.CODICE, Criteria.INNER_JOIN);
                break;
            case 4:
                criteria.where(SpedizionePeer.EMAIL_MITTENTE, email);
                criteria.or(SpedizionePeer.EMAIL_DESTINATARIO, email);
                criteria.addJoin(SpedizionePeer.CODICE, EffettuataPeer.CODICE, Criteria.INNER_JOIN);
                break;
            case 5:
                criteria.where(SpedizionePeer.EMAIL_MITTENTE, email);
                criteria.or(SpedizionePeer.EMAIL_DESTINATARIO, email);
                criteria.addJoin(SpedizionePeer.CODICE, PrenotataPeer.CODICE, Criteria.INNER_JOIN);
        }
        return criteria;
    }

    private static List<Object[]> costruisciSpedizioni(List<Spedizione> partialResult) {
        int rowLength = SpedizionePeer.numColumns + 2;
        List<Object[]> result = new LinkedList<>();
        for(Spedizione singola : partialResult) {
            Object[] row = new Object[rowLength];
            row[0] = singola.getCodice();
            row[1] = singola.getEmailMittente();
            row[2] = singola.getEmailDestinatario();
            row[3] = mostraStato(singola.getCodice());
            row[4] = getRelevantDate(singola.getCodice());
            row[5] = singola.getPeso();
            row[6] = singola.getPrezzo();
            row[7] = safeCorriere(singola);
            result.add(row);
        }
        return result;
    }

    private static Date getRelevantDate(Integer codice) {
        Criteria criteria = new Criteria();
        criteria.where(SpedizionePeer.CODICE, codice);
        try {
            return (Date) InCorsoPeer.doSelect(criteria).getFirst().getDataSpedizione();
        } catch (Exception e) {}
        try {
            System.out.println(PrenotataPeer.doSelect(criteria).getFirst().getDataPrenotazione());
            return (Date) PrenotataPeer.doSelect(criteria).getFirst().getDataPrenotazione();

        }catch (Exception e){}
        try{
            return (Date) EffettuataPeer.doSelect(criteria).getFirst().getDataConsegna();
        }catch (Exception e){
            return new Date(0);
        }
    }

    private static Object safeCorriere(Spedizione singola) {
        try {
            return singola.getCorriere().getNome();
        } catch (Exception e) {
            return "Nessuno";
        }
    }

    private static List<Spedizione> fetchSpedizioni(Criteria criteria) {
        List<Spedizione> partialResult;
        try {
            partialResult = SpedizionePeer.doSelect(criteria);
        } catch (TorqueException e) {
            return null;
        }
        return partialResult;
    }

    private static String mostraStato(Integer codice) {
        Criteria criteria = new Criteria();
        criteria.where(InCorsoPeer.CODICE, codice);
        try {
            return InCorsoPeer.doSelect(criteria).getFirst().getStato();
        } catch (Exception e) {
        }
        try {
            PrenotataPeer.doSelect(criteria);
            return "In attesa di ritiro";
        }catch (Exception e){}
        return "Consegnata";
    }

    public static Indirizzo modificaIndirizzo(String emailCliente, String value, int campo) {
        Indirizzo indirizzo = GestoreRecapiti.visualizzaIndirizzo(emailCliente);
        Indirizzo newIndirizzo;
        try{
            switch (campo) {
                case 1:
                    newIndirizzo = new Indirizzo(value, indirizzo.getVia(), indirizzo.getCivico(), ClientePeer.retrieveByPK(emailCliente));
                    IndirizzoPeer.doDelete(indirizzo);
                    indirizzo = newIndirizzo;
                    break;
                case 2:
                    newIndirizzo = new Indirizzo(indirizzo.getComune(), value, indirizzo.getCivico(), ClientePeer.retrieveByPK(emailCliente));
                    IndirizzoPeer.doDelete(indirizzo);
                    indirizzo = newIndirizzo;
                    break;
                case 3:
                    newIndirizzo = new Indirizzo(indirizzo.getComune(), indirizzo.getVia(), value, ClientePeer.retrieveByPK(emailCliente));
                    IndirizzoPeer.doDelete(indirizzo);
                    indirizzo = newIndirizzo;
                case 4:
                    indirizzo.setOrario(value);
                    break;
            }
            indirizzo.save();
        }catch(TorqueException e){
            e.printStackTrace();
            return null;
        }
        return indirizzo;
    }

    public static boolean cancellaIndirizzo(String emailCliente) {
        try {
            Indirizzo indirizzo = GestoreRecapiti.visualizzaIndirizzo(emailCliente);
            IndirizzoPeer.doDelete(indirizzo);
            return true;
        } catch (TorqueException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static Indirizzo creaIndirizzo(String comune, String via, String civico, String email) {
        Indirizzo indirizzo = null;
        try{
            indirizzo = new Indirizzo(comune, via, civico, ClientePeer.retrieveByPK(email));
            indirizzo.save();
        }catch(TorqueException e){
            e.printStackTrace();
            return null;
        }
        return indirizzo;
    }
}
