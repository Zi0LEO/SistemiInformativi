package com.opcal.model;

import com.opcal.Cliente;
import com.opcal.Spedizione;
import org.apache.torque.TorqueException;

import java.util.ArrayList;
import java.util.List;

import static com.opcal.model.GestoreClienti.getListaClienti;

public class GestoreMailing {

    /** Permette di ricevere la lista delle email dei clienti.
     *
     * @return  Un'oggetto di tipo List che è la lista delle email, è inizializzata come ArrayList.
     */
    public static List<String> getMailingList(){
        ArrayList<Cliente> lc = (ArrayList<Cliente>) getListaClienti();
        ArrayList<String> mailingList = new ArrayList<>();

        for (Cliente cliente : lc)
            mailingList.add(cliente.getEmail());

        return mailingList;
    }

    public static void mandaAggiornamento(Cliente c){
        List<Spedizione> spedizioni = new ArrayList<>();
        try {
            spedizioni = c.getSpedizioneRelatedByEmailDestinatarios();
        } catch (TorqueException e) {
            throw new RuntimeException(e);
        }

        
    }

}
