package com.opcal.controller;

import com.opcal.model.GestoreResi;

import javax.swing.*;

public class AnnullaResoController extends DialogController{

    public static void confermaAnnullamento(JDialog dialog,String codiceReso){
        if(!codiceReso.isEmpty()){
            dialog.dispose();
            return;
        }
        if(GestoreResi.annullaReso(Integer.parseInt(codiceReso))){
            dialog.dispose();
            JOptionPane.showMessageDialog(null,"Errore nell'annullamento del reso");
            return;
        }
        dialog.dispose();
        JOptionPane.showMessageDialog(null,"Reso annullato con successo");
    }
}
