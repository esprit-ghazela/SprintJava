/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import models.CategorieRec;
import services.CategorieRecService;

/**
 *
 * @author ACER
 */
public class GestionCategorieRecController implements Initializable {

    CategorieRec cc = null;
    @FXML
    private AnchorPane pag;
    @FXML
    private TableView<?> table_categorie;

    @FXML
    private TextField TFNom;
    @FXML
    private TextField TFDescription;

    @FXML
    private Button Cajouter;
    @FXML
    private Button Cmodifier;
    @FXML
    private Button supprimer;

    @FXML
    private ComboBox<String> type_cat;
    @FXML
    private TableColumn<?, ?> type;
    @FXML
    private TableColumn<?, ?> nom;
    @FXML
    private TableColumn<?, ?> desc;

    @FXML
    private Button consulter;

    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        afficher();
        type_cat.getItems().addAll(
                "Produit",
                "Livraison",
                "Club");
        table_categorie.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cc = (CategorieRec) table_categorie.getSelectionModel().getSelectedItem();
                System.out.println(cc);

                TFNom.setText(cc.getNom());
                type_cat.setValue(cc.getType());
                TFDescription.setText(cc.getDescription());
            }

            // TODO
        });
    }

    /*JOptionPane.showMessageDialog(null, "ajout avec succes");***afficher une interface avec message****/
    @FXML
    private void AjouterCat(ActionEvent event) {
        String type = type_cat.getValue();
        String nom = TFNom.getText();
        String description = TFDescription.getText();

        CategorieRecService sp = new CategorieRecService();
        CategorieRec e = new CategorieRec(type, nom, description);

        sp.insert(e);
        JOptionPane.showMessageDialog(null, "ajout avec succes");
        afficher();
    }

    private void afficher() {
        CategorieRecService sp = new CategorieRecService();
        List events = sp.displayAll();
        ObservableList et = FXCollections.observableArrayList(events);
        table_categorie.setItems(et);

        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        desc.setCellValueFactory(new PropertyValueFactory<>("description"));

    }

    @FXML
    private void ModifierCat(ActionEvent event) {
        CategorieRecService cs = new CategorieRecService();
        CategorieRec cc = (CategorieRec) table_categorie.getSelectionModel().getSelectedItem();
        System.out.println(cc);
        if (cc == null) {
            JOptionPane.showMessageDialog(null, "choisir event");

        } else {
            String type = type_cat.getValue();
            String nom = TFNom.getText();
            String description = TFDescription.getText();
            cs.update(new CategorieRec(type, nom, description), cc.getIdcategorie());

            afficher();
            JOptionPane.showMessageDialog(null, "event modifier");
            type_cat.setValue(null);
            TFNom.clear();
            TFDescription.clear();

        }

    }

    @FXML
    private void SupprimerCat(ActionEvent event) {
        CategorieRecService cs = new CategorieRecService();
        CategorieRec cc = (CategorieRec) table_categorie.getSelectionModel().getSelectedItem();
        System.out.println(cc);
        if (cc == null) {
            JOptionPane.showMessageDialog(null, "choisir categorie");

        } else {
            cs.delete(cc.getIdcategorie());

            afficher();

            JOptionPane.showMessageDialog(null, "Categorie supprimer");
            type_cat.setValue(null);
            TFNom.clear();
            TFDescription.clear();
        }

    }

    @FXML
    private void Consulter(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AccueilPartenaire.fxml"));
        AccueilPartenaireController controller = loader.getController();
        controller.loadUI("/views/affichereclamation.fxml");

       
    }

}
