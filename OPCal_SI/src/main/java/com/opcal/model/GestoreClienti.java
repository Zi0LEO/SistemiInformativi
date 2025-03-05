package com.opcal.model;

import org.apache.torque.TorqueException;
import org.apache.torque.om.Cliente;
import org.apache.torque.om.ClientePeerImpl;
import org.apache.torque.om.Indirizzo;

public class GestoreClienti {

    /**
     * Permette di creare un'oggetto di tipo Cliente e inserirlo all'interno della base di dati
     *
     * @throws CloneNotSupportedException Nel caso in cui il Cliente che si sta cercando di creare è gia presente
     * @return true se l'operazione va a buon fine, <br> false altrimenti.
     */
    public boolean creaCliente(DatiCliente datoCliente, Indirizzo indirizzoCliente) throws CloneNotSupportedException{
        if (!nonEsiste(datoCliente.getEmail())) throw new CloneNotSupportedException();

        Cliente cliente = new Cliente();
        try {
            cliente.addIndirizzo(indirizzoCliente);
        } catch (TorqueException e) {
            System.out.println(e.getMessage());
            return false;
        }
        cliente.setEmail(datoCliente.getEmail());
        cliente.setNome(datoCliente.getNome());
        cliente.setCognome(datoCliente.getCognome());

        try {
            cliente.save();
        } catch (TorqueException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    /**
     * Permette di modificare il nome di un cliente già presente all'interno della base di dati.
     *
     * @throws ClassNotFoundException nel caso in cui il cliente che si cerca di modificare non esiste
     * @param Cliente il cliente da modificare.
     */
    public void modificaNomeCliente(Cliente Cliente, String nome) throws ClassNotFoundException{
        Cliente.setNome(nome);
        try {
            new ClientePeerImpl().retrieveByPK(Cliente.getEmail());
        } catch (TorqueException e) {
            throw new ClassNotFoundException();
        }
        try {
            Cliente.save();
        } catch (TorqueException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Permette di modificare il cognome di un cliente già presente all'interno della base di dati.
     * Nel caso in cui il cliente da modificare non esiste lo crea.
     *
     * @throws ClassNotFoundException nel caso in cui il cliente che si cerca di modificare non esiste
     * @param Cliente il cliente da modificare.
     */
    public void modificaCognomeCliente(Cliente Cliente, String cognome) throws ClassNotFoundException{
        try {
            new ClientePeerImpl().retrieveByPK(Cliente.getEmail());
        } catch (TorqueException e) {
            throw new ClassNotFoundException();
        }
        Cliente.setNome(cognome);
        try {
            Cliente.save();
        } catch (TorqueException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Permette di modificare il nome di un cliente già presente all'interno della base di dati.
     *
     * @throws ClassNotFoundException nel caso in cui il cliente che si cerca di modificare non esiste
     * @param email l'email del cliente da modificare,
     */
    public void modificaNomeCliente(String email, String nome) throws ClassNotFoundException{
        Cliente c;
        try {
            c = new ClientePeerImpl().retrieveByPK(email);
        } catch (TorqueException e) {
            throw new ClassNotFoundException();
        }
        c.setNome(nome);
        try {
            c.save();
        } catch (TorqueException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Permette di modificare il cognome di un cliente già presente all'interno della base di dati.
     *
     * @throws ClassNotFoundException nel caso in cui il cliente che si cerca di modificare non esiste
     * @param email l'email del cliente da modificare,
     */
    public void modificaCognomeCliente(String email, String cognome) throws ClassNotFoundException{
        Cliente c;
        try {
            c = new ClientePeerImpl().retrieveByPK(email);
        } catch (TorqueException e) {
            throw new ClassNotFoundException();
        }
        c.setCognome(cognome);
        try {
            c.save();
        } catch (TorqueException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean nonEsiste(String email){
        ClientePeerImpl clientePeer = new ClientePeerImpl();
        try {
            clientePeer.retrieveByPK(email);
        } catch (TorqueException e){
            return false;
        }
        return true;
    }
}
