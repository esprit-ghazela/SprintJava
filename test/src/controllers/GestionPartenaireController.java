/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.jfoenix.controls.JFXTextField;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.StageStyle;
import javax.swing.JFileChooser;
import models.Utilisateur;
import services.ServiceLogin;
import utils.ConnectionUtil;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class GestionPartenaireController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private JFXTextField filterField;
    @FXML
    private TableView<Utilisateur> liste_partenaire;
    @FXML
    private TableColumn<?, ?> id_partenaire;
    @FXML
    private TableColumn<?, ?> nom_partenaire;
    @FXML
    private TableColumn<?, ?> prenom_partenaire;
    @FXML
    private TableColumn<?, ?> email_partenaire;
    @FXML
    private TableColumn<?, ?> username_partenaire;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ServiceLogin serviceLogin = new ServiceLogin();
    private ObservableList<Utilisateur> masterData = FXCollections.observableArrayList();
    ObservableList<Utilisateur> ListPartenaire = FXCollections.observableArrayList();
    private XSSFWorkbook wb;
    private XSSFSheet sheet;
    private XSSFRow header;
    private ResultSet rs = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        AfficherPartenaire();
        Recherche();
    }

    @FXML
    private void modifier(MouseEvent event) {
    }

    @FXML
    private void ajouter(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/InscriptionPartenaire.fxml"));
            Dialog dialog = new Dialog();
            dialog.getDialogPane().setContent(root);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.show();
        } catch (IOException ex) {
            Logger.getLogger(GestionPartenaireController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void supprimer(ActionEvent event) {
        String x = PartenaireSelectionner();
        Alert a1 = new Alert(Alert.AlertType.WARNING);
        a1.setTitle("Supprimer partenaire");
        a1.setContentText("Vous voulez vraiment supprimer ce partenaire ?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            serviceLogin.supprimer(x);
            Alert a2 = new Alert(Alert.AlertType.INFORMATION);
            a2.setTitle("Supprimer partenaire");
            a2.setContentText("Partenaire supprimé avec succés!");
            a2.show();

            ListPartenaire.clear();
            AfficherPartenaire();

        } else {
            a1.close();
        }
    }

    @FXML
    private void reafficher_categorie(MouseEvent event) {
        ListPartenaire.clear();
        AfficherPartenaire();
        Recherche();
    }

    private void AfficherPartenaire() {

        String role = "a:1:{i:0;s:9:\"ROLE_PART\";}";

        ServiceLogin forumService = new ServiceLogin();
        ArrayList arrayList1 = (ArrayList) forumService.AfficherClien(role);

        ListPartenaire = FXCollections.observableArrayList(arrayList1);

        id_partenaire.setCellValueFactory(new PropertyValueFactory<>("id_Utilisateur"));
        nom_partenaire.setCellValueFactory(new PropertyValueFactory<>("nom_Utilisateur"));
        prenom_partenaire.setCellValueFactory(new PropertyValueFactory<>("prenom_Utilisateur"));
        username_partenaire.setCellValueFactory(new PropertyValueFactory<>("username_Utilisateur"));
        email_partenaire.setCellValueFactory(new PropertyValueFactory<>("email"));

        liste_partenaire.setItems(ListPartenaire);
    }

    private void Recherche() {
        FilteredList<Utilisateur> filteredData = new FilteredList<>(ListPartenaire, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(utilisateur -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (utilisateur.getNom_Utilisateur().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (utilisateur.getPrenom_Utilisateur().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        SortedList<Utilisateur> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(liste_partenaire.comparatorProperty());
        liste_partenaire.setItems(sortedData);
    }

    private String PartenaireSelectionner() {
        String selectedItem = liste_partenaire.getSelectionModel().getSelectedItem().getUsername_Utilisateur();
        int selectedIndex = liste_partenaire.getSelectionModel().getSelectedIndex();
        System.out.println(selectedItem);
        return selectedItem;
    }

    @FXML
    private void mod(ActionEvent event) {
        try {
            Utilisateur u = liste_partenaire.getSelectionModel().getSelectedItem();
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("/views/ModifierUnUtilisateur.fxml"));
            Parent p = Loader.load();
            ModifierUtilisateurController display = Loader.getController();
            display.setUtilisateur(u);
            Dialog dialog = new Dialog();
            dialog.getDialogPane().setContent(p);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.show();
        } catch (IOException ex) {
            Logger.getLogger(GestionClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void exporterPDF(ActionEvent event) throws SQLException, IOException {
        String role = "a:1:{i:0;s:9:\"ROLE_PART\";}";
        String query = "select * from `fos_user` where roles =" + "'" + role + "'";
        con = ConnectionUtil.conDB();
        String qry = "select * from fos_user where roles =" + "'" + role + "'";
        ResultSet rs = con.createStatement().executeQuery(qry);
        System.out.println("kk");
        int i = 1;
        wb = new XSSFWorkbook();
        System.out.println("n");
        sheet = wb.createSheet("Partenaire Details");
        System.out.println("d");
        header = sheet.createRow(0);
        System.out.println("f");
        header.createCell(0).setCellValue("Identifiant");
        header.createCell(1).setCellValue("Nom");
        header.createCell(2).setCellValue("Prénom");
        header.createCell(3).setCellValue("Nom d'utilisateur ");
        header.createCell(4).setCellValue("E-Mail");
        header.createCell(5).setCellValue("Status");
        System.out.println("4");
        while (rs.next()) {
            System.out.println("9");
            XSSFRow row = sheet.createRow(i);
            System.out.println("o");
            row.createCell(0).setCellValue(rs.getInt("id"));
            System.out.println("qs");
            row.createCell(1).setCellValue(rs.getString("nom"));
            row.createCell(2).setCellValue(rs.getString("prenom"));
            row.createCell(3).setCellValue(rs.getString("username"));
            row.createCell(4).setCellValue(rs.getString("email"));
  
            int enabled = rs.getInt("enabled");
            String state;
            if (enabled == 1) {
                state = "Activer";
            } else {
                state = "Désactiver";
            }
            row.createCell(5).setCellValue(state);
            i++;
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("choose title");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
            System.out.println("dd");
            
       
            
            FileOutputStream fileOut = new FileOutputStream(chooser.getSelectedFile() + "\\Partenaire.xlsx");
            System.out.println("qs");
            wb.write(fileOut);
            fileOut.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("La liste des partenaire en format Exel a été exporter .");
            alert.showAndWait();
            
            rs.close();
        } else {
             System.out.println("No Selection ");
        }

    }

    private String getNomCategorie(int id) throws SQLException {
        String categorie_nom;
        String req = "select nom from categorie where id =" + "'" + id + "'";
        Statement statement = con.createStatement();
        ResultSet resultSet = statement.executeQuery(req);
        while (resultSet.next()) {
            // System.out.println(resultSet.getString("nom"));
            categorie_nom = resultSet.getString("nom");
            return categorie_nom;
        }
        return null;

    }

}
