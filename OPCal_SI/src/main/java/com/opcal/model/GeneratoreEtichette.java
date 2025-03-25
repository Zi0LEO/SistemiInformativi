package com.opcal.model;

import com.itextpdf.barcodes.Barcode128;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.opcal.Cliente;
import com.opcal.ClientePeer;
import com.opcal.Spedizione;
import com.opcal.SpedizionePeer;
import org.apache.torque.TorqueException;

import java.io.ByteArrayOutputStream;

public class GeneratoreEtichette {


    /**
     * Permette di creare un'etichetta di reso per la spedizione inserita nel parametro
     *
     * @param codiceSpedizione la spedizione di cui creare l'etichetta
     * @return Un'oggetto di tipo Document che è l'etichetta.
     */
    public static byte[] creaEtichetta(String codiceSpedizione) throws TorqueException {
        return creaEtichetta(SpedizionePeer.retrieveByPK(Integer.parseInt(codiceSpedizione)));
    }


    /**
     * Permette di creare un'etichetta di reso per la spedizione inserita nel parametro
     *
     * @param spedizione la spedizione di cui creare l'etichetta
     * @return Un'oggetto di tipo Document che è l'etichetta.
     */
    public static byte[] creaEtichetta(Spedizione spedizione) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Cliente[] cl = mittenteDestinatario(spedizione);
        PdfWriter writer = new PdfWriter(stream);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A6);

        document.add(new Paragraph("ETICHETTA DI RESO").setFontSize(14).setFontColor(ColorConstants.RED));
        try {
            document.add(new Paragraph("Mittente: " + cl[0].getNome() +" "+ cl[0].getCognome()));
            document.add(new Paragraph("Destinatario: " + cl[1].getNome() +" "+ cl[1].getCognome()));
        } catch (TorqueException te) {
            System.out.println("Errore nella ricerca dell'anagradica");
        }

        document.add(new Paragraph("Tracking: " + spedizione.getCodice()));

        Barcode128 barcode = new Barcode128(pdf);
        barcode.setCode(spedizione.getCodice().toString());
        barcode.setBarHeight(50);
        barcode.setFont(null);

        Image barcodeImage = new Image(barcode.createFormXObject(pdf));
        document.add(barcodeImage);

        document.close();
        return stream.toByteArray();
    }

    private static Cliente[] mittenteDestinatario(Spedizione s) {
        Cliente[] cl = new Cliente[2];

        try {
            cl[0] = ClientePeer.retrieveByPK(s.getEmailDestinatario());
            cl[1] = ClientePeer.retrieveByPK(s.getEmailMittente());
        } catch (TorqueException e) {
            throw new RuntimeException(e);
        }

        return cl;
    }
}

