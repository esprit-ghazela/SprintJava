/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.itextpdf.text.Rectangle;
import com.jfoenix.controls.JFXTextField;
import com.twilio.rest.api.v2010.account.usage.Record;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JFileChooser;
import models.Categorie;
import models.Produit;
import models.Utilisateur;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
public class GestionClientController implements Initializable {

    @FXML
    private BorderPane borderpane;
    @FXML
    private JFXTextField filterField;
    @FXML
    private TableView<Utilisateur> liste_client;
    @FXML
    private TableColumn<?, ?> id_client;
    @FXML
    private TableColumn<?, ?> nom_client;
    @FXML
    private TableColumn<?, ?> prenom_client;
    @FXML
    private TableColumn<?, ?> email_client;
    @FXML
    private TableColumn<?, ?> username_client;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ServiceLogin serviceLogin = new ServiceLogin();
    private ObservableList<Utilisateur> masterData = FXCollections.observableArrayList();
    ObservableList<Utilisateur> ListClient = FXCollections.observableArrayList();
    private XSSFWorkbook wb;
    private XSSFSheet sheet;
    private XSSFRow header;
    private ResultSet rs = null;
    
    
    @FXML
    private TableColumn status_client;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        AfficherClient();
        Recherche();

    }
    /*
     @FXML
     private void modifier(MouseEvent event) {
       
     System.out.print("ddd");
     FXMLLoader Loader = new FXMLLoader();
     Loader.setLocation(getClass().getResource("/views/ModifierUnUtilisateur.fxml"));
     Parent p = Loader.load();
     ModifierUtilisateurController display = Loader.getController();
     display.setUtilisateur(u);
     Dialog dialog = new Dialog();
     dialog.getDialogPane().setContent(p);
     dialog.initStyle(StageStyle.UNDECORATED);
     dialog.show();
        
     }*/

    @FXML
    private void ajouter(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/InscriptionClient.fxml"));
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
        String x = ClientSelectionner();
        Alert a1 = new Alert(Alert.AlertType.WARNING);
        a1.setTitle("Supprimer client");
        a1.setContentText("Vous voulez vraiment supprimer ce client ?");
        Optional<ButtonType> result = a1.showAndWait();
        if (result.get() == ButtonType.OK) {
            serviceLogin.supprimer(x);
            Alert a2 = new Alert(Alert.AlertType.INFORMATION);
            a2.setTitle("Supprimer client");
            a2.setContentText("Client supprimé avec succés!");
            a2.show();

            ListClient.clear();
            AfficherClient();

        } else {
            a1.close();
        }
    }

    @FXML
    private void reafficher_categorie(MouseEvent event) {
        ListClient.clear();
        AfficherClient();
        Recherche();
    }

    private void AfficherClient() {
        ServiceLogin forumService = new ServiceLogin();
        String role = "a:1:{i:0;s:11:\"ROLE_CLIENT\";}";
        ArrayList arrayList1 = (ArrayList) forumService.AfficherClien(role);

        ListClient = FXCollections.observableArrayList(arrayList1);

        id_client.setCellValueFactory(new PropertyValueFactory<>("id_Utilisateur"));

        nom_client.setCellValueFactory(new PropertyValueFactory<>("nom_Utilisateur"));

        prenom_client.setCellValueFactory(new PropertyValueFactory<>("prenom_Utilisateur"));
        username_client.setCellValueFactory(new PropertyValueFactory<>("username_Utilisateur"));
        email_client.setCellValueFactory(new PropertyValueFactory<>("email"));
        status_client.setCellValueFactory(new PropertyValueFactory<>("enabled"));

        liste_client.setItems(ListClient);

    }

    @FXML
    private void mod(ActionEvent event) {
        try {
            Utilisateur u = liste_client.getSelectionModel().getSelectedItem();
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
    private void modifier(MouseEvent event) {
    }

    //Define the button cell
    private class ButtonCell extends TableCell<Record, Boolean> {

        final Button cellButton = new Button("Delete");

        ButtonCell() {

            //Action when the button is pressed
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    Utilisateur currentPerson;
                    // currentPerson = (Utilisateur)ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                    System.out.print("hell");
                    //remove selected item from the table list
                    //data.remove(currentPerson);
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }
    }

    private void Recherche() {
        FilteredList<Utilisateur> filteredData = new FilteredList<>(ListClient, p -> true);
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
        sortedData.comparatorProperty().bind(liste_client.comparatorProperty());
        liste_client.setItems(sortedData);
    }

    private String ClientSelectionner() {
        String selectedItem = liste_client.getSelectionModel().getSelectedItem().getUsername_Utilisateur();
        int selectedIndex = liste_client.getSelectionModel().getSelectedIndex();
        System.out.println(selectedItem);
        return selectedItem;
    }

    private void addButtonToTable() {
        TableColumn<Utilisateur, Void> colBtn = new TableColumn("Button Column");

        Callback<TableColumn<Utilisateur, Void>, TableCell<Utilisateur, Void>> cellFactory = new Callback<TableColumn<Utilisateur, Void>, TableCell<Utilisateur, Void>>() {
            @Override
            public TableCell<Utilisateur, Void> call(final TableColumn<Utilisateur, Void> param) {
                final TableCell<Utilisateur, Void> cell = new TableCell<Utilisateur, Void>() {

                    private final Button btn = new Button("Action");

                    {
                        btn.setOnAction((ActionEvent event) -> {

                            String selectedItem = liste_client.getSelectionModel().getSelectedItem().getUsername_Utilisateur();
                            int selectedIndex = liste_client.getSelectionModel().getSelectedIndex();
                            System.out.println(selectedItem);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        liste_client.getColumns().add(colBtn);

    }

    @FXML
    private void exporterPDF(ActionEvent event) throws SQLException, IOException {
        String role = "a:1:{i:0;s:11:\"ROLE_CLIENT\";}";

        con = ConnectionUtil.conDB();
        String qry = "select * from fos_user where roles =" + "'" + role + "'";
        ResultSet rs = con.createStatement().executeQuery(qry);
        System.out.println("kk");
        int i = 1;
        wb = new XSSFWorkbook();
        System.out.println("n");
        sheet = wb.createSheet("Clients Details");
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

            FileOutputStream fileOut = new FileOutputStream(chooser.getSelectedFile() + "\\Clients.xlsx");
            System.out.println("qs");
            wb.write(fileOut);
            fileOut.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("La liste des clients en format Exel a été exporter .");
            alert.showAndWait();

            rs.close();
        } else {
            System.out.println("No Selection ");
        }

    }

}
/*

 // Group
 ToggleGroup group = new ToggleGroup();

 group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
 @Override
 public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
 // Has selection.
 if (group.getSelectedToggle() != null) {
 RadioButton button = (RadioButton) group.getSelectedToggle();
 System.out.println("Button: " + button.getText());
 labelInfo.setText("Client : " + button.getText());
 }
 }
 });

 // Radio 1: Male
 RadioButton button1 = new RadioButton("Activer");
 button1.setToggleGroup(group);
 button1.setSelected(true);

 // Radio 2: Female.
 RadioButton button2 = new RadioButton("Desactiver");
 button2.setToggleGroup(group);
 */
