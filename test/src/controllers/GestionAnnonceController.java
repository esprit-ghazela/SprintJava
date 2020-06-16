/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import models.annonce;
import services.ServiceAnnonce;

/**
 * FXML Controller class
 *
 * @author yousra
 */
public class GestionAnnonceController implements Initializable {

    @FXML
    private TableView<?> tab_annonce;
    @FXML
    private TableColumn<annonce, Integer> id1;
    @FXML
    private TableColumn<annonce, String> nom1;
    @FXML
    private TableColumn<annonce, String> img_P1;
    @FXML
    private TableColumn<annonce, Date> dateD1;
    @FXML
    private TableColumn<annonce, String> description1;
    @FXML
    private JFXTextField nomP1;
    @FXML
    private DatePicker date_d1;
    @FXML
    private ImageView imageviw11;
    @FXML
    private JFXButton image1;
    @FXML
    private JFXButton ajout1;
    @FXML
    private JFXButton update1;
    @FXML
    private JFXButton delete1;
    @FXML
    private JFXTextField nom_P1;
    @FXML
    private RadioButton tri11;
    @FXML
    private RadioButton tri2;

    ObservableList<annonce> data2 = FXCollections.observableArrayList();
    List<String> type;
    String imgG = "";
    String imgG2 = "";
    annonce cc1 = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        type = new ArrayList<>();
        type.add("*.jpg");
        type.add("*.png");
        try {
            displayAll_annonce();
        } catch (SQLException ex) {
            Logger.getLogger(GestionAnnonceController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tab_annonce.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cc1 = (annonce) tab_annonce.getSelectionModel().getSelectedItem();
                System.out.println(cc1);

                nomP1.setText(cc1.getNom());

                System.out.println(imageviw11);
                LocalDate d1 = cc1.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                date_d1.setValue(d1);

                imageviw11.setImage(new Image(cc1.getImage()));
                nom_P1.setText(cc1.getDescription());

            }

        });
    }

    @FXML
    private void import_image_annonce(ActionEvent event) {
        FileChooser f = new FileChooser();
        f.getExtensionFilters().add(new FileChooser.ExtensionFilter("jpeg,png files", type));
        File fc = f.showOpenDialog(null);
        if (f != null) {
            System.out.println(fc.getName());
            imgG2 = fc.getAbsoluteFile().toURI().toString();
            Image i = new Image(imgG2);
            imageviw11.setImage(i);
        }
        fc.exists();
    }

    @FXML
    private void ajouter_annonce(ActionEvent event) throws SQLException {
        ServiceAnnonce ps = new ServiceAnnonce();
        LocalDate dd = date_d1.getValue();

        java.util.Date d1 = java.sql.Date.valueOf(dd);
        System.out.println(nomP1.getText());
        System.out.println(date_d1.getValue());
        System.out.println(d1);
        System.out.println(imgG);
        System.out.println(nom_P1.getText());

        if (imgG2.length() == 0) {
            ps.ajouter(new annonce(nomP1.getText(), cc1.getImage(), d1, nom_P1.getText()));
        }
        ps.ajouter(new annonce(nomP1.getText(), imgG2, d1, nom_P1.getText()));
        displayAll_annonce();
        JOptionPane.showMessageDialog(null, "Ajout effectué");
        nomP1.clear();
        imageviw11.setImage(null);
        date_d1.setValue(null);

        nom_P1.clear();
    }

    @FXML
    private void modifier_annonce(ActionEvent event) throws SQLException {
        ServiceAnnonce cs = new ServiceAnnonce();

        System.out.println(cc1);
        if (cc1 == null) {
            JOptionPane.showMessageDialog(null, "choisir annonce");

        } else {

            LocalDate dd = date_d1.getValue();
            java.util.Date d1 = java.sql.Date.valueOf(dd);
            System.out.println("import" + imgG2);
            if (imgG2.length() == 0) {
                cs.update(new annonce(nomP1.getText(), cc1.getImage(), d1, nom_P1.getText()), cc1.getId_annonce());
            } else {
                cs.update(new annonce(nomP1.getText(), imgG2, d1, nom_P1.getText()), cc1.getId_annonce());
            }
            displayAll_annonce();

            JOptionPane.showMessageDialog(null, "annonce modifié");
            nomP1.clear();
            imageviw11.setImage(null);
            date_d1.setValue(null);
            nom_P1.clear();

        }
    }

    @FXML
    private void supprimer_annonce(ActionEvent event) throws SQLException {
        ServiceAnnonce cs = new ServiceAnnonce();
        annonce cc = (annonce) tab_annonce.getSelectionModel().getSelectedItem();
        System.out.println(cc);
        if (cc == null) {
            JOptionPane.showMessageDialog(null, "choisir annonce");

        } else {

            cs.delete(cc.getId_annonce());
            nom_P1.clear();
            nomP1.clear();
            imageviw11.setImage(null);
            date_d1.setValue(null);

            displayAll_annonce();
            JOptionPane.showMessageDialog(null, "annonce supprimé");
        }
    }

    @FXML
    private void tri_annonce_parNom(ActionEvent event) throws SQLException {
        ServiceAnnonce cs = new ServiceAnnonce();
        List listcs = cs.trieParNom();

        //listcs.forEach(System.out::println);
        ObservableList rob2 = FXCollections.observableArrayList(listcs);

        tab_annonce.setItems(rob2);

        id1.setCellValueFactory(new PropertyValueFactory<>("id_annonce"));
        nom1.setCellValueFactory(new PropertyValueFactory<>("nom"));
        img_P1.setCellValueFactory(new PropertyValueFactory<>("image"));
        dateD1.setCellValueFactory(new PropertyValueFactory<>("date"));
        //dateF.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        description1.setCellValueFactory(new PropertyValueFactory<>("description"));
        //note.setCellValueFactory(new PropertyValueFactory<>("note"));
    }

    @FXML
    private void tri_annonce_parDate(ActionEvent event) throws SQLException {
        ServiceAnnonce cs = new ServiceAnnonce();
        List listcs = cs.trieParDate();

        //listcs.forEach(System.out::println);
        ObservableList rob2 = FXCollections.observableArrayList(listcs);

        tab_annonce.setItems(rob2);
        
        id1.setCellValueFactory(new PropertyValueFactory<>("id_annonce"));
        nom1.setCellValueFactory(new PropertyValueFactory<>("nom"));
        img_P1.setCellValueFactory(new PropertyValueFactory<>("image"));
        dateD1.setCellValueFactory(new PropertyValueFactory<>("date"));
        //dateF.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        description1.setCellValueFactory(new PropertyValueFactory<>("description"));
       // note.setCellValueFactory(new PropertyValueFactory<>("note"));

    }

    public void displayAll_annonce() throws SQLException {

        ServiceAnnonce cs = new ServiceAnnonce();
        List listcs = cs.readAll_withImage();

        //listcs.forEach(System.out::println);
        ObservableList rob2 = FXCollections.observableArrayList(listcs);

        tab_annonce.setItems(rob2);

        id1.setCellValueFactory(new PropertyValueFactory<>("id_annonce"));
        nom1.setCellValueFactory(new PropertyValueFactory<>("nom"));
        img_P1.setCellValueFactory(new PropertyValueFactory<>("image"));
        dateD1.setCellValueFactory(new PropertyValueFactory<>("date"));
        description1.setCellValueFactory(new PropertyValueFactory<>("description"));

    }

}
