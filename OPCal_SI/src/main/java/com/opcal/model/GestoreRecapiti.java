package com.opcal.model;

import com.opcal.*;
import org.apache.torque.TorqueException;
import org.apache.torque.criteria.Criteria;
import org.apache.torque.util.Transaction;

import java.sql.Connection;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class GestoreRecapiti {

    public static boolean creaSpedizione(String emailMittente, String emailDestinatario, int peso) {
        Connection connection = null;
        try{
            ClientePeer.retrieveByPK(emailMittente);
            ClientePeer.retrieveByPK(emailDestinatario);
        }catch (Exception E){
            return false;
        }

        Spedizione spedizione = new Spedizione(emailMittente, emailDestinatario, peso, calcolaPrezzo(peso, emailDestinatario));
        InCorso inCorso = new InCorso(spedizione);
        try {
            connection = Transaction.begin();
            inCorso.setStato("Presa in carico");
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
            return 0;
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

    public static Indirizzo visualizzaIndirizzo(String email) {
        Criteria criteria = new Criteria();
        try{
            ClientePeer.retrieveByPK(email);
            criteria.where(IndirizzoPeer.EMAIL_CLIENTE, email);
        }catch (Exception e){}
        try {
            return IndirizzoPeer.doSelect(criteria).getFirst();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<Object[]> listaIndirizzi() {
      Criteria criteria = new Criteria();
      List<Indirizzo> partialRet;
      try {
          partialRet = IndirizzoPeer.doSelect(criteria);
      } catch (TorqueException e) {
        return null;
      }
      return costruisciIndirizzi(partialRet);
    }

    private static List<Object[]> costruisciIndirizzi(List<Indirizzo> partialRet) {
        List<Object[]> result = new LinkedList<>();
        for(Indirizzo indirizzo : partialRet) {
            Object[] row = new Object[5];
            row[0] = indirizzo.getEmailCliente();
            row[1] = indirizzo.getComune();
            row[2] = indirizzo.getVia();
            row[3] = indirizzo.getCivico();
            row[4] = indirizzo.getOrario();
            result.add(row);
        }
        return result;
    }

    public static boolean cambiaStato(Integer codice, int stato) {
        Spedizione spedizione;
        try{
            spedizione = SpedizionePeer.retrieveByPK(codice);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        switch (stato) {
            case 1:
                return prendiInCarico(spedizione);
            case 2:
                return cambiaStato(spedizione, "spedita");
            case 3:
                return cambiaStato(spedizione, "in consegna");
            case 4:
                return consegna(spedizione);
            case 5:
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
            inCorso.setStato("presa in carico");
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
            inCorso.save();
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
        return costruisciSpedizioni(partialResult, tipo);
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
                if (email != null) {
                    criteria.where(SpedizionePeer.EMAIL_MITTENTE, email);
                    criteria.or(SpedizionePeer.EMAIL_DESTINATARIO, email);
                }
                criteria.addJoin(SpedizionePeer.CODICE, PrenotataPeer.CODICE, Criteria.INNER_JOIN);
                break;
            case 4:
                if (email != null) {
                    criteria.where(SpedizionePeer.EMAIL_MITTENTE, email);
                    criteria.or(SpedizionePeer.EMAIL_DESTINATARIO, email);
                }
                criteria.addJoin(SpedizionePeer.CODICE, InCorsoPeer.CODICE, Criteria.INNER_JOIN);
                break;
            case 5:
                if (email != null) {
                    criteria.where(SpedizionePeer.EMAIL_MITTENTE, email);
                    criteria.or(SpedizionePeer.EMAIL_DESTINATARIO, email);
                }
                criteria.addJoin(SpedizionePeer.CODICE, EffettuataPeer.CODICE, Criteria.INNER_JOIN);
        }
        return criteria;
    }

    private static List<Object[]> costruisciSpedizioni(List<Spedizione> partialResult, int tipo) {
        int rowLength = SpedizionePeer.numColumns + 1;
        if (tipo == 3) rowLength += 2;
        if (tipo == 4) rowLength += 1;
        if (tipo == 5) rowLength += 2;
        List<Object[]> result = new LinkedList<>();
        for(Spedizione singola : partialResult) {
            Object[] row = new Object[rowLength];
            row[0] = singola.getCodice();
            row[1] = singola.getEmailMittente();
            row[2] = singola.getEmailDestinatario();
            row[3] = getStato(singola.getCodice());
            row[4] = singola.getPeso();
            row[5] = singola.getPrezzo();
            row[6] = safeCorriere(singola);
            try {
                switch (tipo) {
                    case 3:
                        Prenotata p = PrenotataPeer.retrieveByPK(singola.getCodice());
                        row[7] = p.getDataPrenotazione();
                        row[8] = p.getDataRitiro();
                        break;
                    case 4:
                        InCorso ic = InCorsoPeer.retrieveByPK(singola.getCodice());
                        row[7] = ic.getDataSpedizione();
                        break;
                    case 5:
                        Effettuata e = EffettuataPeer.retrieveByPK(singola.getCodice());
                        row[7] = e.getDataSpedizione();
                        row[8] = e.getDataConsegna();
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
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
        Indirizzo indirizzo;
        try{
            indirizzo = new Indirizzo(comune, via, civico, ClientePeer.retrieveByPK(email));
            indirizzo.save();
        }catch(TorqueException e){
            e.printStackTrace();
            return null;
        }
        return indirizzo;
    }

    public static List<Object[]> listaCorrieri() {
        Criteria criteria = new Criteria();
        criteria.addSelectColumn(CorrierePeer.NOME)
            .addSelectColumn(CorrierePeer.IVA)
            .addSelectColumn(CorrierePeer.SITO)
            .addSelectColumn(CorrierePeer.TELEFONO)
            .addSelectColumn(CorrierePeer.PREZZO1)
            .addSelectColumn(CorrierePeer.PREZZO10)
            .addSelectColumn(CorrierePeer.PREZZO100);
        List<Corriere> partialRet;
        try{
            partialRet = CorrierePeer.doSelect(criteria);
        }catch (TorqueException e){
            e.printStackTrace();
            return null;
        }
        return costruisciCorriere(partialRet);

    }

    private static List<Object[]> costruisciCorriere(List<Corriere> partialRet) {
        int rowLength = CorrierePeer.numColumns;
        List<Object[]> result = new LinkedList<>();
        for(Corriere singolo : partialRet) {
            Object[] row = new Object[rowLength];
            row[0] = singolo.getNome();
            row[1] = singolo.getIva();
            row[2] = singolo.getSito();
            row[3] = singolo.getTelefono();
            row[4] = singolo.getPrezzo1();
            row[5] = singolo.getPrezzo10();
            row[6] = singolo.getPrezzo100();
            result.add(row);
        }
        return result;
    }

    public static String getStato(Integer codiceSpedizione) {
        Spedizione spedizione;
        try{
            spedizione = SpedizionePeer.retrieveByPK(codiceSpedizione);
        } catch (TorqueException e) {
            e.printStackTrace();
            return null;
        }
        try{
            InCorso ic = spedizione.getInCorsos().getFirst();
            return ic.getStato();
        }catch (Exception e){}
        try{
            Effettuata e = spedizione.getEffettuatas().getFirst();
            return "consegnata";
        }catch (Exception e){}
        try{
            Prenotata p = spedizione.getPrenotatas().getFirst();
            return "in attesa di ritiro";
        }catch (Exception e){
            return null;
        }
    }
}
