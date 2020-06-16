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
import models.annonce;

/**
 *
 * @author asus
 */
public class PDF {

    public void pdf(annonce p) throws SQLException, FileNotFoundException, DocumentException, BadElementException, IOException {
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
            PdfWriter.getInstance(document, new FileOutputStream(chooser.getSelectedFile()+"\\"+randomNum+".pdf"));
            document.open();
            Paragraph adrr = new Paragraph(new Phrase("l titre  : " + p.getNom(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
            Paragraph par = new Paragraph(" votre annonce  ", FontFactory.getFont(FontFactory.TIMES));
            par.setAlignment(Element.ALIGN_CENTER);
            document.add(par);
            document.add(adrr);
            document.add(new Paragraph("date d'annonce  : " + p.getDate(), FontFactory.getFont(FontFactory.TIMES)));
            document.close();
        } else {
            System.out.println("No Selection ");
        }
    }
}
