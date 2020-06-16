/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.itextpdf.text.DocumentException;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.CategorieRec;
import services.CategorieRecService;
import utils.DataSource;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class AffichercategorieController implements Initializable {

    @FXML
    private TextField search;
    private Connection con;
    @FXML
    private ScrollPane scroll;
    @FXML
    private VBox actualitecontainer;
    @FXML
    private Button retouner;
    @FXML
    private AnchorPane ap1;
    @FXML
    private Button ajoutCa;
    public AffichercategorieController() {
        con = DataSource.getInstance().getCnx();}
        private Statement ste;
    private PreparedStatement pre;
    String s1 = "";

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        actualitecontainer.setSpacing(4);
            try {
                displayCategorie();
            } catch (SQLException ex) {
                Logger.getLogger(AffichereclamationController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }    
    private void displayCategorie() throws SQLException {
        CategorieRecService pa = new CategorieRecService();
        String req = "select * from categorierec ";
        List<VBox> list = new ArrayList<>();
        Statement ste = con.createStatement();
        ResultSet rs = ste.executeQuery(req);
        while (rs.next()) {
            
           

             CategorieRec a1 = new CategorieRec(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
            
          
            Label type = new Label("type: " + a1.getType());
            Label nom= new Label("nom : " + a1.getNom());
            Label description = new Label("description: " + a1.getDescription());
            
            

            HBox h1 = new HBox();
            h1.setSpacing(10);
            h1.setAlignment(Pos.CENTER);
            h1.getChildren().addAll(type);
            HBox h2 = new HBox();
            h2.setSpacing(10);
            h2.setAlignment(Pos.CENTER);
            h2.getChildren().addAll(nom);
            HBox h3 = new HBox();
            h3.setSpacing(10);
            h3.setAlignment(Pos.CENTER);
            h3.getChildren().addAll(description);
            

            VBox v = new VBox();
            v.setAlignment(Pos.CENTER);
            v.setSpacing(10);
            v.getChildren().addAll(h1, h2,h3);

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
private void displayCategorieAvancee(String req) throws SQLException {
        CategorieRecService pa = new CategorieRecService();
//        
        List<VBox> list = new ArrayList<>();
        Statement ste = con.createStatement();
        ResultSet rs = ste.executeQuery(req);
        while (rs.next()) {
            
           

            CategorieRec a1 = new CategorieRec(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
            
          
            Label type = new Label("type: " + a1.getType());
            Label nom= new Label("nom : " + a1.getNom());
            Label description = new Label("description: " + a1.getDescription());
            
            

            HBox h1 = new HBox();
            h1.setSpacing(10);
            h1.setAlignment(Pos.CENTER);
            h1.getChildren().addAll(type);
            HBox h2 = new HBox();
            h2.setSpacing(10);
            h2.setAlignment(Pos.CENTER);
            h2.getChildren().addAll(nom);
             HBox h3 = new HBox();
            h3.setSpacing(10);
            h3.setAlignment(Pos.CENTER);
            h3.getChildren().addAll(description);
            

            VBox v = new VBox();
            v.setAlignment(Pos.CENTER);
            v.setSpacing(10);
            v.getChildren().addAll(h1, h2,h3);

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
      String req ="select * from categorierec where  nom = '"+search11+"'";
        displayCategorieAvancee(req);
        if(search11.equals("")){
             req ="select * from categorierec  ";
        displayCategorieAvancee(req);
    }
    }

    @FXML
    private void retounerid(ActionEvent event) throws IOException {
        AnchorPane pane=FXMLLoader.load(getClass().getResource("/views/affichereclamation.fxml"));
        ap1.getChildren().setAll(pane);
    }

    @FXML
    private void ajouterCategorie(ActionEvent event) throws IOException {
         AnchorPane pane=FXMLLoader.load(getClass().getResource("/views/GestionCategorieRec.fxml"));
        ap1.getChildren().setAll(pane);
    }
    
}
