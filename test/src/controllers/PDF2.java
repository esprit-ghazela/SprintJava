/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import models.Reclamation;

/**
 *
 * @author asus
 */
public class PDF2 {

    public void pdf(Reclamation p) throws SQLException, FileNotFoundException, DocumentException, BadElementException, IOException {
        try {
            // System.out.println("Haouet------------------------------------->"+nom);

            // nextInt is normally exclusive of the top value,
            // so add 1 to make it inclusive
            int randomNum = ThreadLocalRandom.current().nextInt(1, 100 + 1);

            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("choose title");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
                System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
                PdfWriter.getInstance(document, new FileOutputStream(chooser.getSelectedFile() + "\\" + randomNum + ".pdf"));
                document.open();
                Paragraph adrr = new Paragraph(new Phrase(" nom  : " + p.getNom(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
                Paragraph adrr1 = new Paragraph(new Phrase("prenom  : " + p.getPrenom(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
                Paragraph adrr11 = new Paragraph(new Phrase("reclamation  : " + p.getReclamation(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
                Paragraph par = new Paragraph(" votre reclamation  ", FontFactory.getFont(FontFactory.TIMES));
                par.setAlignment(Element.ALIGN_CENTER);
                document.add(par);

                document.add(adrr);
                document.add(adrr1);
                document.add(adrr11);

                document.close();
            } else {
                System.out.println("No Selection ");
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(PDF2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
