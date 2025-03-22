package com.opcal.model;

import com.opcal.Cliente;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

import java.util.LinkedList;
import java.util.List;

import static com.opcal.model.GestoreClienti.getListaClienti;

public class GestoreMailing {

    /** Permette di ricevere la lista delle email dei clienti.
     *
     * @return  Un'oggetto di tipo List che è la lista delle email, è inizializzata come ArrayList.
     */
    public static List<String> getMailingList(){
        LinkedList<Cliente> lc = (LinkedList<Cliente>) getListaClienti();
        LinkedList<String> mailingList = new LinkedList<>();

        for (Cliente cliente : lc)
            mailingList.add(cliente.getEmail());

        return mailingList;
    }

    public static void main(String[] args) {
            send(costruisciMail(null,"Prova","Pizza"));
        }


    public static void aggiornaCliente(String email){
        Email e = costruisciMail(email,"Spedizione","ciao bro");
        send(e);

    }

    private static Email costruisciMail(String email,String oggetto, String corpo){
        String nomeCognome = "Umberto Frega";

        return EmailBuilder.startingBlank()
                .from("Servizio Clienti OPCAL","opcalsi@libero.it")
                .to(nomeCognome,"umbertofrega@gmail.com")
                .withSubject(oggetto)
                .withPlainText(corpo)
                .buildEmail();
    }

    public static void newsLetter(String corpo, String oggetto){
        LinkedList<String> lista = (LinkedList<String>) getMailingList();
        Email e = EmailBuilder.startingBlank()
                .from("Servizio Clienti OPCAL","opcalsi@libero.it")
                .toWithDefaultName("Gentile Cliente",lista)
                .withSubject(oggetto)
                .withPlainText(corpo)
                .buildEmail();
        send(e);
    }

    private static void send(Email e){
        MailerBuilder.withSMTPServer("smtp.libero.it",587,"opcalsi@libero.it","EmailBuilder12!")
                .buildMailer().sendMail(e);

    }
    //EmailBuilder12!
}
