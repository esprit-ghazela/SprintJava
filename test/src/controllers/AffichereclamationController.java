/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.itextpdf.text.DocumentException;
import static com.itextpdf.text.pdf.BidiOrder.PDF;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javax.activation.DataSource;
import models.Reclamation;
import services.ReclamationService;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class AffichereclamationController implements Initializable {

    @FXML
    private TextField search;
    private Connection con;
    @FXML
    private Button bt1;
    @FXML
    private ScrollPane scroll;
    @FXML
    private VBox actualitecontainer;
    @FXML
    private Button categorie;
    @FXML
    private AnchorPane ap;
    @FXML
    private Button retouner;

    /**
     * Initializes the controller class.
     */
    public AffichereclamationController() {
        con = utils.DataSource.getInstance().getCnx();
    }
    private Statement ste;
    private PreparedStatement pre;
    String s1 = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        actualitecontainer.setSpacing(5);
        try {
            displayReclamation();
        } catch (SQLException ex) {
            Logger.getLogger(AffichereclamationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void displayReclamation() throws SQLException {
        ReclamationService pa = new ReclamationService();
        String req = "select * from reclamation ";
        List<VBox> list = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery(req);
        while (rs.next()) {

            java.sql.Date dd = new java.sql.Date(rs.getDate(6).getTime());

            Reclamation a1 = new Reclamation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), dd);

            Label nom = new Label("nom : " + a1.getNom());
            Label prenom = new Label("prenom : " + a1.getPrenom());
            Label reclamation = new Label("reclamation : " + a1.getReclamation());
            Label dateajout = new Label("cette reclamation est ajouter le : " + a1.getDate());

            HBox h1 = new HBox();
            h1.setSpacing(10);
            h1.setAlignment(Pos.CENTER);
            h1.getChildren().addAll(nom);
            HBox h2 = new HBox();
            h2.setSpacing(10);
            h2.setAlignment(Pos.CENTER);
            h2.getChildren().addAll(prenom);
            HBox h3 = new HBox();
            h3.setSpacing(10);
            h3.setAlignment(Pos.CENTER);
            h3.getChildren().addAll(reclamation);
            HBox h4 = new HBox();
            h4.setSpacing(10);
            h4.setAlignment(Pos.CENTER);
            h4.getChildren().addAll(dateajout);
            HBox h5 = new HBox();
            h5.setSpacing(10);
            h5.setAlignment(Pos.CENTER);

            Button bt2 = new Button("imprimer");
            bt2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) { //bitha heki chas

                    PDF2 pdf = new PDF2();

                    try {
                        pdf.pdf(a1);
                    } catch (SQLException ex) {
                        Logger.getLogger(AffichereclamationController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (DocumentException ex) {
                        Logger.getLogger(AffichereclamationController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(AffichereclamationController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    bt2.setDisable(true);

                }
            });

            VBox v = new VBox();
            v.setAlignment(Pos.CENTER);
            v.setSpacing(10);
            v.getChildren().addAll(h1, h2, h3, h4, bt2);

            VBox vv = new VBox();
            vv.setAlignment(Pos.CENTER);
            vv.setSpacing(10);

            HBox No = new HBox();
            No.setSpacing(10);
            No.setAlignment(Pos.CENTER);
            No.getChildren().addAll(vv, v);

            VBox v1 = new VBox();
            v1.setAlignment(Pos.CENTER);
            v1.setSpacing(10);
            v1.getChildren().addAll(No);
            list.add(v1);

        }
        actualitecontainer.getChildren().addAll(list);
    }

    private void displayReclamationAvancee(String req) throws SQLException {
        ReclamationService pa = new ReclamationService();
//        
        List<VBox> list = new ArrayList<>();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery(req);
        while (rs.next()) {

            java.sql.Date dd = new java.sql.Date(rs.getDate(6).getTime());

            Reclamation a1 = new Reclamation(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), dd);

            Label nom = new Label("nom : " + a1.getNom());
            Label prenom = new Label("prenom : " + a1.getPrenom());
            Label reclamation = new Label("reclamation : " + a1.getReclamation());
            Label dateajout = new Label("cette reclamation est ajouter le : " + a1.getDate());

            HBox h1 = new HBox();
            h1.setSpacing(10);
            h1.setAlignment(Pos.CENTER);
            h1.getChildren().addAll(nom);
            HBox h2 = new HBox();
            h2.setSpacing(10);
            h2.setAlignment(Pos.CENTER);
            h2.getChildren().addAll(prenom);
            HBox h3 = new HBox();
            h3.setSpacing(10);
            h3.setAlignment(Pos.CENTER);
            h3.getChildren().addAll(reclamation);
            HBox h4 = new HBox();
            h4.setSpacing(10);
            h4.setAlignment(Pos.CENTER);
            h4.getChildren().addAll(dateajout);
            HBox h5 = new HBox();
            h5.setSpacing(10);
            h5.setAlignment(Pos.CENTER);

            Button bt2 = new Button("imprimer");
            bt2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) { //bitha heki chas

                    PDF2 pdf = new PDF2();

                    try {
                        pdf.pdf(a1);
                    } catch (SQLException ex) {
                        Logger.getLogger(AffichereclamationController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (DocumentException ex) {
                        Logger.getLogger(AffichereclamationController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(AffichereclamationController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    bt2.setDisable(true);

                }
            });

            VBox v = new VBox();
            v.setAlignment(Pos.CENTER);
            v.setSpacing(10);
            v.getChildren().addAll(h1, h2, h3, h4, bt2);

            VBox vv = new VBox();
            vv.setAlignment(Pos.CENTER);
            vv.setSpacing(10);

            HBox No = new HBox();
            No.setSpacing(10);
            No.setAlignment(Pos.CENTER);
            No.getChildren().addAll(vv, v);

            VBox v1 = new VBox();
            v1.setAlignment(Pos.CENTER);
            v1.setSpacing(10);
            v1.getChildren().addAll(No);
            list.add(v1);

        }
        actualitecontainer.getChildren().addAll(list);
    }

    @FXML
    private void recherche(ActionEvent event) throws SQLException {
        actualitecontainer.getChildren().removeAll(actualitecontainer.getChildren());
        String search11 = search.getText();
        String req = "select * from reclamation where  nom = '" + search11 + "'";
        displayReclamationAvancee(req);
        if (search11.equals("")) {
            req = "select * from reclamation  ";
            displayReclamationAvancee(req);
        }
    }

    @FXML
    private void triedate(ActionEvent event) throws SQLException {
        actualitecontainer.getChildren().removeAll(actualitecontainer.getChildren());
        String req = "select * from reclamation ORDER BY date";
        displayReclamationAvancee(req);
    }

    @FXML
    private void idcategorie(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/views/GestionCategorieRec.fxml"));
        ap.getChildren().setAll(pane);
        
    }

    @FXML
    private void retounerid(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("menu.fxml"));
        ap.getChildren().setAll(pane);
    }

}
