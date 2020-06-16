/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXComboBox;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXTextField;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javax.mail.internet.AddressException;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import models.CategorieRec;
import models.Reclamation;
import services.CategorieRecService;
import services.ReclamationService;

/**
 * FXML Controller class
 *
 * @author ACER
 */
public class ReclamationController implements Initializable {

    Reclamation cc = null;
    @FXML
    private TextField TFNom;
    @FXML
    private TextField TFPrenom;
    @FXML
    private TextField TFReclamation;
    @FXML
    private DatePicker Date;
    @FXML
    private Button Rajouter;
    @FXML
    private Button Rmodifier;
    @FXML
    private Button supprimer;
    @FXML
    private AnchorPane page;
    @FXML
    private Button ConsulterCat;
    private TextField TFCategorie;

    @FXML
    private TableView<Reclamation> table_reclamation;
    @FXML
    private TableColumn<Reclamation, Integer> id;
    @FXML
    private TableColumn<Reclamation, String> reclamation;
    @FXML
    private TableColumn<Reclamation, Integer> categorie;
    @FXML
    private TableColumn<Reclamation, String> nom;
    @FXML
    private TableColumn<Reclamation, String> prenom;
    @FXML
    private TableColumn<Reclamation, Date> date;
    @FXML
    private ComboBox<CategorieRec> listcateg;
    @FXML
    private Button trier;

    @FXML
    private Button trier1;

    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afficher1();
        // TODO

        Callback<ListView<CategorieRec>, ListCell<CategorieRec>> cellFactory = new Callback<ListView<CategorieRec>, ListCell<CategorieRec>>() {

            @Override
            public ListCell<CategorieRec> call(ListView<CategorieRec> l) {
                return new ListCell<CategorieRec>() {

                    @Override
                    protected void updateItem(CategorieRec item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getNom());
                        }
                    }
                };
            }
        };

        table_reclamation.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cc = (Reclamation) table_reclamation.getSelectionModel().getSelectedItem();
                System.out.println(cc);

                TFNom.setText(cc.getNom());
                TFPrenom.setText(cc.getPrenom());
                TFReclamation.setText(cc.getReclamation());

                LocalDate d1 = cc.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Date.setValue(d1);

            }

        });

        listcateg.setButtonCell(cellFactory.call(null));
        listcateg.setCellFactory(cellFactory);

//remplissage du combobox
        CategorieRecService rec = new CategorieRecService();
        List<CategorieRec> arr = new ArrayList<>();

        arr = rec.displayAll();

        for (CategorieRec u : arr) {
            listcateg.getItems().add(u);
        }

    }

    @FXML
    private void AjouterRec(ActionEvent event) throws AddressException {

        
        
        
        
        String nom = TFNom.getText();
        String prenom = TFPrenom.getText();
        String reclamation = TFReclamation.getText();
        CategorieRec cat = listcateg.getValue();

        
        
        int categorie = cat.getIdcategorie();

        LocalDate dd = Date.getValue();
        Date date = java.sql.Date.valueOf(dd);

        System.out.println("Reclamation ://");
        System.out.println(""+TFNom.getText());
        System.out.println(""+ TFPrenom.getText());
        System.out.println(""+ TFReclamation.getText());
        System.out.println(""+categorie);
        System.out.println(""+date);
        
        
        ReclamationService rp = new ReclamationService();
        Reclamation r = new Reclamation(reclamation, categorie, nom, prenom, date);
        if (rp.envoyerMail("VELO.TN", "yousra.abid@esprit.tn")) {
            rp.insert(r);

            TFReclamation.clear();
            TFNom.clear();

            TFPrenom.clear();
            afficher1();
            JOptionPane.showMessageDialog(null, "ajout avec succes");

        }
    }

    private void afficher1() {
        ReclamationService sp = new ReclamationService();
        List events = sp.displayAll();
        ObservableList et = FXCollections.observableArrayList(events);
        table_reclamation.setItems(et);

        id.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));

        reclamation.setCellValueFactory(new PropertyValueFactory<>("reclamation"));
        categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));

    }

    @FXML
    private void ModifierRec(ActionEvent event) {
        ReclamationService cs = new ReclamationService();

        if (cc == null) {
            JOptionPane.showMessageDialog(null, "choisir une reclamation");

        } else {
            LocalDate dd = Date.getValue();
            java.util.Date d1 = java.sql.Date.valueOf(dd);
            int categorie = cc.getCategorie();
            cs.update(new Reclamation(TFReclamation.getText(), categorie, TFPrenom.getText(), TFNom.getText(), d1), cc.getId_reclamation());

            afficher1();
            JOptionPane.showMessageDialog(null, "event modifier");
            TFReclamation.clear();
            TFNom.clear();

            TFPrenom.clear();

            Date.setValue(null);

        }
    }

    @FXML
    private void SupprimerRec(ActionEvent event) {
        ReclamationService cs = new ReclamationService();
        Reclamation cc = (Reclamation) table_reclamation.getSelectionModel().getSelectedItem();
        System.out.println(cc);
        if (cc == null) {
            JOptionPane.showMessageDialog(null, "choisir categorie");

        } else {
            cs.delete(cc.getId_reclamation());

            afficher1();

            JOptionPane.showMessageDialog(null, "Categorie supprimer");
            TFReclamation.clear();
            TFNom.clear();
            TFPrenom.clear();
            listcateg.setValue(null);
            Date.setValue(null);

        }

    }

    @FXML
    private void ConsulterCat(ActionEvent event) throws IOException {
        AnchorPane panee = FXMLLoader.load(getClass().getResource("/views/affichercategorie.fxml"));
        page.getChildren().setAll(panee);

    }

    @FXML
    private void trier(ActionEvent event) {
        ReclamationService sp = new ReclamationService();
        List events = sp.trieParDate();
        ObservableList et = FXCollections.observableArrayList(events);
        table_reclamation.setItems(et);

        id.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));
        reclamation.setCellValueFactory(new PropertyValueFactory<>("reclamation"));
        categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @FXML
    private void trier1(ActionEvent event) {
        ReclamationService sp = new ReclamationService();
        List events = sp.trieParId();
        ObservableList et = FXCollections.observableArrayList(events);
        table_reclamation.setItems(et);

        id.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));
        reclamation.setCellValueFactory(new PropertyValueFactory<>("reclamation"));
        categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    private void retourner1(ActionEvent event) throws IOException {
        AnchorPane panee = FXMLLoader.load(getClass().getResource("menu.fxml"));
        page.getChildren().setAll(panee);
    }

}
