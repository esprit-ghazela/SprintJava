/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;
import models.Reclamation;
import utils.DataSource;

/**
 *
 * @author ACER
 */
public class ReclamationService implements IserviceR<Reclamation> {

    private Connection cnx;
    private Statement st;
    private PreparedStatement pre;

    public ReclamationService() {
        cnx = DataSource.getInstance().getCnx();
    }

    @Override
    public void insert(Reclamation e) {
        Date dd = new java.sql.Date(e.getDate().getTime());
        String req = "INSERT INTO `velo`.`reclamation` (`reclamation`, `categorie`, `nom`, `prenom`, `date`) VALUES ('" + e.getReclamation() + "','" + e.getCategorie() + "','" + e.getNom() + "','" + e.getPrenom() + "','" + dd + "');";
        try {

            st = cnx.createStatement();
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public boolean update(Reclamation e, int id) {
        if (chercher(id)) {
            Date dd = new java.sql.Date(e.getDate().getTime());

            try {
                pre = cnx.prepareStatement("UPDATE reclamation SET reclamation = ? ,categorie = ?,nom =? ,prenom =?,date =? WHERE id=?");
                pre.setString(1, e.getReclamation());
                pre.setInt(2, e.getCategorie());
                pre.setString(3, e.getNom());
                pre.setString(4, e.getPrenom());
                pre.setDate(5, dd);
                pre.setInt(6, id);
                pre.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("update valide");
            return true;
        }
        System.out.println("update invalid: event nexiste pas");
        return false;
    }

    @Override
    public boolean delete(int id) {
        if (chercher(id)) {
            try {

                pre = cnx.prepareStatement("delete from reclamation where id= ?;");
                pre.setInt(1, id);
                pre.execute();
            } catch (SQLException ex) {
                Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("delete valide");
            return true;
        }
        System.out.println("reclamation nexiste pas");
        return false;
    }

    @Override
    public List<Reclamation> displayAll() {
        String req = "select * from reclamation";
        List<Reclamation> list = new ArrayList<>();
        try {
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                java.util.Date dd = new java.util.Date(rs.getDate(6).getTime());

                Reclamation pp = new Reclamation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), dd);
                list.add(pp);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public Boolean chercher(int id) {
        String req = "select * from reclamation";
        List<Integer> list = new ArrayList<>();
        try {
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                java.util.Date dd = new java.util.Date(rs.getDate(6).getTime());

                list.add(rs.getInt(1));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list.contains(id);
    }

    public List<Reclamation> trieParDate() {
        String req = "select * from reclamation ORDER BY date";
        List<Reclamation> list = new ArrayList<>();
        try {
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                java.util.Date dd = new java.util.Date(rs.getDate(6).getTime());
                list.add(new Reclamation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), dd));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public List<Reclamation> trieParId() {
        String req = "select * from reclamation ORDER BY categorie";
        List<Reclamation> list = new ArrayList<>();
        try {
            st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                java.util.Date dd = new java.util.Date(rs.getDate(6).getTime());
                list.add(new Reclamation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), dd));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean envoyerMail(String fromMail, String toMail) throws AddressException {
       
        try {
            String subject = "RECLAMATION";
            String messageText = "VOTRE RECLAMATION EST PRISE EN CHARGE , MERCI ";
            
            
            String from = "velotunisie5@gmail.com";
            String pass = "velotn2020";
            String[] to = {toMail};
            String host = "smtp.gmail.com";

            Properties prop = System.getProperties();
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", host);
            prop.put("mail.smtp.user", from);
            prop.put("mail.smtp.password", pass);
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            prop.put("mail.smtp.host", "smtp.gmail.com");

            Session session = Session.getDefaultInstance(prop);
            MimeMessage meg = new MimeMessage(session);
            meg.setFrom(new InternetAddress(from));
            InternetAddress[] toadress = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++) {
                toadress[i] = new InternetAddress(to[i]);
            }

            for (int i = 0; i < to.length; i++) {
                meg.setRecipient(Message.RecipientType.TO, toadress[i]);
            }

            meg.setSubject(subject);
            meg.setContent(messageText, "text/html; charset=utf-8");
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(meg, meg.getAllRecipients());
            transport.close();
             return true;
        } catch (MessagingException ex) {
            Logger.getLogger(ReclamationService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

}

    
