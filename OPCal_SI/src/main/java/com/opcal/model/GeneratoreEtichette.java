package main.java.com.opcal.model;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import com.itextpdf.barcodes.*;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.Image;
import org.apache.torque.TorqueException;
import com.opcal.*;

import java.io.IOException;

public class GeneratoreEtichette {

    /**Permette di creare un'etichetta di reso per la spedizione innserita nel parametro
     *
     *
     *
     * @param spedizione la spedizione di cui creare l'etichetta
     * @return Un'oggetto di tipo Document che è l'etichetta.
     */
    public static Document creaEtichetta(Spedizione spedizione) {
        String dest = "etichettaDiReso.pdf";
        Cliente[] cl = mittenteDestinatario(spedizione);
        PdfWriter writer;
        try {
            writer = new PdfWriter(dest);
        } catch (IOException e) {
            System.out.println("C'è stato un problema nella creazione del PDF, riprovare più tardi");
            return null;
        }

        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A6);

        document.add(new Paragraph("ETICHETTA DI RESO").setFontSize(14).setFontColor(ColorConstants.RED));
        try {
            document.add(new Paragraph("Mittente:" + cl[0].getNome() + cl[0].getCognome()));
            document.add(new Paragraph("Destinatario:" + cl[1].getNome() + cl[1].getCognome()));
        } catch(TorqueException te){
            System.out.println("Errore nella ricerca dell'anagradica");
        }

        document.add(new Paragraph("Tracking: " + spedizione.getCodice()));

        Barcode128 barcode = new Barcode128(pdf);
        barcode.setCode(spedizione.getCodice());
        barcode.setBarHeight(50);
        barcode.setFont(null);

        Image barcodeImage = new Image(barcode.createFormXObject(pdf));
        document.add(barcodeImage);

        document.close();
        return document;
    }

    private static Cliente[] mittenteDestinatario(Spedizione s){
        Cliente[] cl = new Cliente[1];

        try {
        cl[0] = ClientePeer.retrieveByPK(s.getEmailDestinatario());
        cl[1] = ClientePeer.retrieveByPK(s.getEmailMittente());
        } catch (TorqueException e) {
            throw new RuntimeException(e);
        }

        return cl;
    }
}

