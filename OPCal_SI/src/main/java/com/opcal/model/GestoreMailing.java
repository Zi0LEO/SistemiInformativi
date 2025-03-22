package com.opcal.model;

import com.opcal.Cliente;
import com.opcal.ClientePeer;
import jakarta.activation.DataSource;
import jakarta.mail.util.ByteArrayDataSource;
import org.apache.torque.TorqueException;
import org.simplejavamail.api.email.Email;
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

//    public static void main(String[] args) {
//        java.io.ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        PdfWriter writer = new PdfWriter(stream);
//        PdfDocument pdf = new PdfDocument(writer);
//        Document document = new Document(pdf, PageSize.A6);
//
//        document.add(new Paragraph("ETICHETTA DI RESO").setFontSize(14).setFontColor(ColorConstants.RED));
//
//        document.add(new Paragraph("Mittente: " + "Pizza" + "Micini"));
//        document.add(new Paragraph("Destinatario: " + "Lollo" + "Gay"));
//
//
//        document.add(new Paragraph("Tracking: " + "1234"));
//
//        Barcode128 barcode = new Barcode128(pdf);
//        barcode.setCode("1234");
//        barcode.setBarHeight(50);
//        barcode.setFont(null);
//
//        Image barcodeImage = new Image(barcode.createFormXObject(pdf));
//        document.add(barcodeImage);
//
//        document.close();
//
//        DataSource ds = new ByteArrayDataSource(stream.toByteArray(),"application/pdf");
//
//
//        send(EmailBuilder.startingBlank()
//                .from("Servizio Clienti OPCAL","opcalsi@libero.it")
//                .to("Leo Napoli","leonardo.npl.02@gmail.com")
//                .withSubject("etichetta")
//                .withAttachment("Etichetta di reso",ds)
//                .buildEmail());
//    }

    /**Permette di mandare una email a uno specifico cliente.
     *
     *
     * @param email l'email del cliente
     * @param corpo Il corpo dell'email
     * @param oggetto L'oggetto dell'email
     */
    public static void mandaACliente(String email, String oggetto, String corpo){
        send(costruisciMail(email,oggetto,corpo));
    }

    /**Permette di inviare una mail con un allegato file a uno specifico cliente.
     *
     * @param email l'email del cliiente
     * @param oggetto l'oggetto dell'email
     * @param file un'array di tipo byte[] che è il documento da inviare
     * @param nomeFile il nome che il file avrò una volta inviato
     */
    public static void mandaACliente(String email, String oggetto,byte[] file, String nomeFile){
        send(costruisciMail(email,oggetto,file,nomeFile));

    }

    private static Email costruisciMail(String email, String oggetto,byte[] file, String nomeFile){
        String nomeCognome;
        try {
            Cliente c = ClientePeer.retrieveByPK(email);
            nomeCognome = c.getNome() + " " + c.getCognome();
        }catch(TorqueException e){
            e.printStackTrace();
            System.out.println("Errore nella ricerca del cliente");
            return null;
        }

        DataSource ds = new ByteArrayDataSource(file,"application/pdf");

        return EmailBuilder.startingBlank()
                .from("Servizio Clienti OPCAL","opcalsi@libero.it")
                .to(nomeCognome,email)
                .withSubject(oggetto)
                .withAttachment(nomeFile,ds)
                .buildEmail();
    }

    private static Email costruisciMail(String email,String oggetto, String corpo){
        String nomeCognome;
        try {
            Cliente c = ClientePeer.retrieveByPK(email);
            nomeCognome = c.getNome() + " " + c.getCognome();
        }catch(TorqueException e){
            e.printStackTrace();
            System.out.println("Errore nella ricerca del cliente");
            return null;
        }

        return EmailBuilder.startingBlank()
                .from("Servizio Clienti OPCAL","opcalsi@libero.it")
                .to(nomeCognome,email)
                .withSubject(oggetto)
                .withPlainText(corpo)
                .buildEmail();
    }


    /** Permette di inviare una email a tutti i clienti registrati
     *
     * @param corpo Il corpo dell'email
     * @param oggetto L'oggetto dell'email
     */
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
}
