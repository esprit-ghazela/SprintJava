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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import models.Publicite;
import services.ServicePublicite;

/**
 * FXML Controller class
 *
 * @author yousra
 */
public class GestionPubController implements Initializable {

    @FXML
    private TableView<Publicite> tab_pub;
    @FXML
    private TableColumn<Publicite, Integer> id;
    @FXML
    private TableColumn<Publicite, String> nom;
    @FXML
    private TableColumn<Publicite, String> img_P;
    @FXML
    private TableColumn<Publicite, Date> dateD;
    @FXML
    private TableColumn<Publicite, Date> dateF;
    @FXML
    private TableColumn<Publicite, String> description;
    @FXML
    private TableColumn<Publicite, Double> note;
    @FXML
    private JFXTextField nomP;
    @FXML
    private DatePicker date_d;
    @FXML
    private DatePicker date_f;
    @FXML
    private ImageView imageviw1;
    @FXML
    private JFXButton image;
    @FXML
    private JFXButton ajout;
    @FXML
    private JFXButton update;
    @FXML
    private JFXButton delete;
    @FXML
    private JFXTextField nom_P;
    @FXML
    private JFXTextField rechercher;
    @FXML
    private RadioButton tri1;
    @FXML
    private RadioButton tri;

    ObservableList<Publicite> data = FXCollections.observableArrayList();
    List<String> type;
    String imgG = "";
    String imgG2 = "";
    Publicite cc = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO

            type = new ArrayList<>();
            type.add("*.jpg");
            type.add("*.png");
            displayAll_pub();
            //displayAll_annonce();
            rechercher.setOnKeyTyped((event) -> {

                try {
                    ServicePublicite cs = new ServicePublicite();
                    ArrayList AL = (ArrayList) cs.readAll_withImage();
                    ObservableList OReservation = FXCollections.observableArrayList(AL);
                    FilteredList<Publicite> filteredData = new FilteredList<>(OReservation, p -> true);
                    rechercher.textProperty().addListener((observable, oldValue, newValue) -> {
                        filteredData.setPredicate(myObject -> {
                            if (newValue == null || newValue.isEmpty()) {
                                return true;
                            }
                            String lowerCaseFilter = newValue.toLowerCase();

                            if (String.valueOf(myObject.getNom()).toLowerCase().contains(lowerCaseFilter)) {
                                return true;

                            }
                            return false;
                        });
                    });
                    SortedList<Publicite> sortedData = new SortedList<>(filteredData);
                    sortedData.comparatorProperty().bind(tab_pub.comparatorProperty());
                    tab_pub.setItems(sortedData);
                } catch (SQLException ex) {
                    Logger.getLogger(GestionPubController.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

            tab_pub.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    cc = (Publicite) tab_pub.getSelectionModel().getSelectedItem();
                    System.out.println(cc);

                    nomP.setText(cc.getNom());

                    System.out.println(imageviw1);
                    LocalDate d1 = cc.getDate_debut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate d2 = cc.getDate_fin().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    date_d.setValue(d1);
                    date_f.setValue(d2);
                    imageviw1.setImage(new Image(cc.getImage()));
                    nom_P.setText(cc.getDescription());
                    double cprix = cc.getNote();
                    String prix_PPP = String.valueOf(cprix);

                }

            }
            );
        } catch (SQLException ex) {
            Logger.getLogger(GestionPubController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void import_image(ActionEvent event) {
        FileChooser f = new FileChooser();
        f.getExtensionFilters().add(new FileChooser.ExtensionFilter("jpeg,png files", type));
        File fc = f.showOpenDialog(null);
        if (f != null) {
            System.out.println(fc.getName());
            imgG = fc.getAbsoluteFile().toURI().toString();
            Image i = new Image(imgG);
            imageviw1.setImage(i);
        }
        fc.exists();
    }

    @FXML
    private void ajouter(ActionEvent event) throws SQLException {
        ServicePublicite ps = new ServicePublicite();
        LocalDate dd = date_d.getValue();
        LocalDate df = date_f.getValue();
        java.util.Date d1 = java.sql.Date.valueOf(dd);
        java.util.Date d2 = java.sql.Date.valueOf(df);
        if (imgG.length() == 0) {
            ps.ajouter(new Publicite(nomP.getText(), cc.getImage(), d1, d2, nom_P.getText(), 0));
        }
        ps.ajouter(new Publicite(nomP.getText(), imgG, d1, d2, nom_P.getText(), 0));
        displayAll_pub();
        JOptionPane.showMessageDialog(null, "Ajout effectué");
        nomP.clear();
        imageviw1.setImage(null);
        date_d.setValue(null);
        date_f.setValue(null);
        nom_P.clear();
    }

    @FXML
    private void modifier(ActionEvent event) throws SQLException {
        ServicePublicite cs = new ServicePublicite();
        System.out.println(cc);
        if (cc == null) {
            JOptionPane.showMessageDialog(null, "choisir pub");
        } else {
            LocalDate dd = date_d.getValue();
            LocalDate df = date_f.getValue();
            java.util.Date d1 = java.sql.Date.valueOf(dd);
            java.util.Date d2 = java.sql.Date.valueOf(df);
            System.out.println("import" + imgG);
            if (imgG.length() == 0) {
                cs.update(new Publicite(nomP.getText(), cc.getImage(), d1, d2, nom_P.getText(), 0), cc.getId_publicite());
            } else {
                cs.update(new Publicite(nomP.getText(), imgG, d1, d2, nom_P.getText(), 0), cc.getId_publicite());
            }
            displayAll_pub();
            JOptionPane.showMessageDialog(null, "pub modifier");
            nomP.clear();
            imageviw1.setImage(null);
            date_d.setValue(null);
            date_f.setValue(null);
            nom_P.clear();
        }
    }

    @FXML
    private void supprimer(ActionEvent event) throws SQLException {
        ServicePublicite cs = new ServicePublicite();
        Publicite cc = (Publicite) tab_pub.getSelectionModel().getSelectedItem();
        System.out.println(cc);
        if (cc == null) {
            JOptionPane.showMessageDialog(null, "choisir pub");
        } else {
            cs.delete(cc.getId_publicite());
            nomP.clear();
            imageviw1.setImage(null);
            date_d.setValue(null);
            date_f.setValue(null);
            nom_P.clear();
            displayAll_pub();
            JOptionPane.showMessageDialog(null, "pub supprimer");
        }
    }

    @FXML
    private void rechercher(ActionEvent event) throws SQLException {
        ServicePublicite cs = new ServicePublicite();
        ArrayList listcs = (ArrayList) cs.readAll_withImage();
        ObservableList OReservation = FXCollections.observableArrayList(listcs);
        FilteredList<Publicite> filteredData = new FilteredList<>(OReservation, p -> true);
        rechercher.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(myObject.getNom()).toLowerCase().contains(lowerCaseFilter) || String.valueOf(myObject.getDescription()).toLowerCase().contains(lowerCaseFilter) || String.valueOf(myObject.getId_publicite()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;

                }
                return false;
            });
        });
        SortedList<Publicite> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tab_pub.comparatorProperty());
        tab_pub.setItems(sortedData);
    }

    @FXML
    private void tri_pub_parNOM(ActionEvent event) throws SQLException {
        ServicePublicite cs = new ServicePublicite();
        List listcs = cs.trieParNom();
        //listcs.forEach(System.out::println);
        ObservableList rob2 = FXCollections.observableArrayList(listcs);
        tab_pub.setItems(rob2);
        id.setCellValueFactory(new PropertyValueFactory<>("id_publicite"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        img_P.setCellValueFactory(new PropertyValueFactory<>("photo"));
        dateD.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        dateF.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        note.setCellValueFactory(new PropertyValueFactory<>("note"));
    }

    @FXML
    private void tri_pub_parNote(ActionEvent event) throws SQLException {
        ServicePublicite cs = new ServicePublicite();
        List listcs = cs.trieParNOTE();
        //listcs.forEach(System.out::println);
        ObservableList rob2 = FXCollections.observableArrayList(listcs);
        tab_pub.setItems(rob2);
        id.setCellValueFactory(new PropertyValueFactory<>("id_publicite"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        img_P.setCellValueFactory(new PropertyValueFactory<>("photo"));
        dateD.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        dateF.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        note.setCellValueFactory(new PropertyValueFactory<>("note"));

    }

    public void displayAll_pub() throws SQLException {

        ServicePublicite cs = new ServicePublicite();
        List listcs = cs.readAll_withImage();
        //listcs.forEach(System.out::println);
        ObservableList rob2 = FXCollections.observableArrayList(listcs);
        tab_pub.setItems(rob2);
        id.setCellValueFactory(new PropertyValueFactory<>("id_publicite"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        img_P.setCellValueFactory(new PropertyValueFactory<>("photo"));
        dateD.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        dateF.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        note.setCellValueFactory(new PropertyValueFactory<>("note"));
    }

}
