/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.jfoenix.controls.JFXButton;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.annonce;
import services.ServicePublicite;
import utils.DataSource;

/**
 * FXML Controller class
 *
 * @author walid
 */
public class AnnonceFrontController implements Initializable {

    @FXML
    private ScrollPane scroll;
    @FXML
    private VBox vbocAnnonce;
    private Connection con;
    @FXML
    private JFXButton retour;
    @FXML
    private AnchorPane annonceF;

    public AnnonceFrontController() {
        con = DataSource.getInstance().getCnx();

    }
    private Statement ste;
    private PreparedStatement pre;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            afficherAnnonce();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AnnonceFrontController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void afficherAnnonce() throws FileNotFoundException {
        String req = "select * from annonce ";
        VBox v1 = new VBox();

        v1.setSpacing(5);
        v1.setAlignment(Pos.CENTER);

        try {
            ste = con.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while (rs.next()) {

                Label nom = new Label(rs.getString(2));

                Font font = new Font(Font.FontFamily.COURIER, 12, Font.BOLD);
                nom.setStyle("-fx-font-size: 2em;");
                nom.setStyle("-fx-font-weight: bold;");

                Label descrip = new Label(rs.getString(3));
                Label date = new Label(rs.getString(4));
                //file:\
                String pa = rs.getString(5).substring(6, rs.getString(5).length());
                FileInputStream input = new FileInputStream(pa);
                
                Image image = new Image(input);
                ImageView v = new ImageView(image);

                v.setFitHeight(150);
                v.setFitWidth(250);
                java.util.Date d1 = new java.util.Date(rs.getDate(4).getTime());
                annonce a1 = new annonce(rs.getString(2), rs.getString(3), d1, rs.getString(5));
                
                FileInputStream input2 = new FileInputStream("./src/images/imprimer.png");
                Image image2 = new Image(input2);
                ImageView imageView2 = new ImageView(image2);
                 imageView2.setFitHeight(30);
                imageView2.setFitWidth(30);
                
                Button bt2 = new Button("",imageView2);
                bt2.setStyle("-fx-background-color: transparent;");
                
                //bt2.setStyle("-fx-background-image: url(@../images/add.png)");
                bt2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            //bitha heki chas

                            PDF pdf = new PDF();
                            System.out.println("c button mouhage tsir creation houniiii  ");
                            pdf.pdf(a1);

                            bt2.setDisable(true);
                        } catch (SQLException ex) {
                            Logger.getLogger(AnnonceFrontController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (DocumentException ex) {
                            Logger.getLogger(AnnonceFrontController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(AnnonceFrontController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                });
                Label l1 = new Label("       ");
                Label l2 = new Label("       ");
                Label l3 = new Label("       ");
                Label l4 = new Label("       ");

                VBox v2 = new VBox(nom, l3, descrip, l4, date);
                v2.setAlignment(Pos.CENTER);
                HBox h1 = new HBox(v, l1, v2, l2, bt2);

                h1.setAlignment(Pos.CENTER);
                HBox cc = new HBox();
                // v1.getChildren().addAll(null)
                v1.getChildren().addAll(h1, cc);
                v1.setAlignment(Pos.CENTER);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicePublicite.class.getName()).log(Level.SEVERE, null, ex);
        }

        vbocAnnonce.getChildren().add(v1);

    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/GUI/Front.fxml"));
        annonceF.getChildren().setAll(pane);
    }
}
