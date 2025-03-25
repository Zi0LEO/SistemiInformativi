package com.opcal.controller;
import javax.swing.*;

import static com.opcal.model.GestoreMailing.mandaACliente;

public class EmailController extends DialogController{

    public static void manda(JDialog dialog,String email,String oggetto,String corpo) {
        if (email.isEmpty()){
            JOptionPane.showMessageDialog(null, "Devi inserire obblgatoriamente un indirizzo Email", "Errore", JOptionPane.ERROR_MESSAGE);
            dialog.dispose();
            return;
        }
        if (oggetto.isEmpty())
            mandaACliente(email,"Aggiornamento OPCAL",corpo);
        if (corpo.isEmpty())
            mandaACliente(email,oggetto,"");
        mandaACliente(email,oggetto,corpo);
        dialog.dispose();
        JOptionPane.showMessageDialog(null, "Email inviata con successo", "Notifica invio", JOptionPane.INFORMATION_MESSAGE);

    }

}
