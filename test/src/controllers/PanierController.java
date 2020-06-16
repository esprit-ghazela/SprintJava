/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import static controllers.ProduitsController.listPanier;
import static controllers.VeloTnController.recupererUtilisateurConnecte;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.DefaultStringConverter;
import models.Categorie;
import models.Panier;
import models.Produit;
import services.ProduitService;
import services.ServiceLogin;
import services.ServicePanier;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class PanierController implements Initializable {

    @FXML
    private BorderPane borderpane;
    List<Panier> listPanierAff = new ArrayList<Panier>();

    List<Panier> listPanier = ProduitsController.listPanier;
    int qta;
    /*
     int qta

  
     private List<Panier> recupererPanier;
     //private static List<Panier> articles2;
     ServicePanier sp = new ServicePanier();
     */
    private ProduitsController controller1;
    /**
     * Initializes the controller class.
     */

    ObservableList<String> listQuntite = listQuntite = FXCollections.observableArrayList();
    ObservableList<Panier> ListPanier = FXCollections.observableArrayList();
    Panier recupererPanier;
    private TableView<Panier> liste_produit;
    private TableColumn<?, ?> id_produit;
    private TableColumn<?, ?> nom_produit;
    private TableColumn<?, ?> image_produit;
    private TableColumn<?, ?> prix_produit;
    private TableColumn<Panier, String> quantiter_produit;

    ObservableList<Integer> modeList;
    @FXML
    private FlowPane flowPane;

    int qn = 0;
    double p, total;
    @FXML
    private Label totale_achat;

    String testtt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        afficherProduit();

    }

    private ObservableList<String> getAllQuantite(int q) {
        if (q != 0) {
            for (int k = 0; k < q + 1; k++) {
                String qq = String.valueOf(k);
                listQuntite.add(qq);
            }

            return listQuntite;
        }
        listQuntite.clear();
        return null;
    }

    private void afficherProduit() {
        System.out.println("Panire : Size " + listPanier.size());
        for (int i = 0; i < listPanier.size(); i++) {
            System.out.println("Panire : Size " + listPanier.size());
            System.out.println("Identifient : " + listPanier.get(i).getId());
            System.out.println("Nom : " + listPanier.get(i).getNom());
            System.out.println("Reference : " + listPanier.get(i).getReference());
            System.out.println("Image : " + listPanier.get(i).getImage());
            System.out.println("Prix : " + listPanier.get(i).getPrix());
            System.out.println("Quntiter : " + listPanier.get(i).getQuantite());
            System.out.println("Quntiter panier : " + listPanier.get(i).getQt_panier());
            System.out.println("----------------------------------------------------------------");

        }

        ArrayList<Separator> as = new ArrayList<>();
        ArrayList<HBox> vbx = new ArrayList<>();

        if (listPanier.size() == 0) {
            Label donner = new Label("Vous n'avez aucun article dans votre panier");
            flowPane.getChildren().add(donner);
        } else {
            for (int i = 0; i < listPanier.size(); i++) {

                try {
                    //separator vertical entre les produt
                    Separator h = new Separator(Orientation.HORIZONTAL);
                    h.setPrefWidth(17);
                    h.setPrefHeight(44);
                    // h.setVisible(false);
                    as.add(h);
                    //creation de vbox pour contenir ele produit
                    HBox VBoxProduit = new HBox();
                    VBoxProduit.setSpacing(5);
                    VBoxProduit.setAlignment(Pos.CENTER);
                    VBoxProduit.setPrefHeight(100);
                    VBoxProduit.setPrefWidth(600);

                    //attribution des element
                    FileInputStream input = new FileInputStream("./src/images/" + listPanier.get(i).getImage());
                    Image image = new Image(input);
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(125);
                    imageView.setFitHeight(75);
                    int id = listPanier.get(i).getId();
                    Label nom = new Label(listPanier.get(i).getNom());
                    Label prix = new Label("Prix de l' : " + Double.toString(listPanier.get(i).getPrix()) + " DT");
                    Double totalUniter = listPanier.get(i).getId() * listPanier.get(i).getPrix();

                    Label totale = new Label("Totale : " + totalUniter + " DT");
                    // Panier listp = new Panier(list.get(i).getId_produit(), list.get(i).getNom_produit(), list.get(i).getImage_produit(), list.get(i).getQuantite_produit(), list.get(i).getPrix_produit());
                    int qt = listPanier.get(i).getQuantite();
                    p = listPanier.get(i).getPrix();
                    ComboBox comboBox = new ComboBox();
                    for (int j = 1; j < qt + 1; j++) {
                        comboBox.getItems().add(j);
                    }

                    comboBox.setVisibleRowCount(5);
                    //comboBox.setValue(1);
                    comboBox.setPromptText("Quantite");
//int qn;
                    comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

                        @Override
                        public void changed(ObservableValue options, Object oldValue, Object newValue) {
                            String o = newValue.toString();
                            qn = Integer.parseInt(o);
                            System.out.println(qn);
                            testtt = o;

                            // Label total_u = new Label("Totale"+);
                            total = p * qn;
                            System.out.println(total);
                            totale.setText("Totale : " + total + " DT");

                            modifierQuantiter(id, qn);
                            //"Totale : "+Double.toString(list.get(i).getPrix_produit())+" DT"
                            Double totalPanier = getTotal();
                            totale_achat.setText("" + totalPanier + " Dt");
                        }

                        private void modifierQuantiter(int id, int qn) {
                            for (int i = 0; i < listPanier.size(); i++) {
                                if (listPanier.get(i).getId() == id) {
                                    listPanier.get(i).setQt_panier(qn);
                                }
                            }
                        }

                    });

                    Panier newPanier = new Panier();

                    newPanier.setId(listPanier.get(i).getId());
                    newPanier.setNom(listPanier.get(i).getNom());
                    newPanier.setReference(listPanier.get(i).getReference());
                    newPanier.setImage(listPanier.get(i).getImage());
                    newPanier.setPrix(listPanier.get(i).getPrix());
                    newPanier.setQuantite(listPanier.get(i).getQuantite());
                    newPanier.setQt_panier(1);

                    Button btnSupp = new Button("Supprimer");
                    btnSupp.setStyle("-fx-background-color : red");
                    btnSupp.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            listPanier.remove(newPanier);
                            flowPane.getChildren().clear();
                            afficherProduit();

                        }
                    });

                    Double totalPanier = getTotal();
                    totale_achat.setText("" + totalPanier + " Dt");

                    VBoxProduit.getChildren().add(imageView);
                    VBoxProduit.getChildren().add(nom);
                    VBoxProduit.getChildren().add(prix);
                    VBoxProduit.getChildren().add(comboBox);
                    // VBoxProduit.getChildren().add(btnMod);
                    VBoxProduit.getChildren().add(totale);
                    VBoxProduit.getChildren().add(btnSupp);

                    //ajout des vbox du produit a vbox 
                    vbx.add(VBoxProduit);
                    flowPane.getChildren().add(vbx.get(i));
                    flowPane.getChildren().add(as.get(i));

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(ProduitsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    private void ModifQuantiter(int qn, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void ModifQuantiter(String o, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public double getTotal() {
        double total = 0;
        for (int i = 0; i < listPanier.size(); i++) {
            total = total + listPanier.get(i).getQt_panier() * listPanier.get(i).getPrix();
        }
        return total;
    }

    @FXML
    private void passerCom(ActionEvent event) {
         try {
            BorderPane pane = FXMLLoader.load(getClass().getResource("/views/Commande.fxml"));
            borderpane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    }




/*







 ServicePanier sp = new ServicePanier();
 List<Panier> list = null;
 String n = recupererUtilisateurConnecte.getNom_Utilisateur();
 list = sp.getArticle(n);

 ServicePanier panierService = new ServicePanier();
 //ArrayList<Panier> listPanier = (ArrayList<Panier>) panierService.getPanier();

 ArrayList<Separator> as = new ArrayList<>();
 ArrayList<HBox> vbx = new ArrayList<>();

 if (list.size() == 0) {
 Label donner = new Label("Vous n'avez aucun article dans votre panier");
 flowPane.getChildren().add(donner);
 } else {
 for (int i = 0; i < list.size(); i++) {

 try {
 //separator vertical entre les produt
 Separator h = new Separator(Orientation.HORIZONTAL);
 h.setPrefWidth(17);
 h.setPrefHeight(44);
 // h.setVisible(false);
 as.add(h);
 //creation de vbox pour contenir ele produit
 HBox VBoxProduit = new HBox();
 VBoxProduit.setSpacing(5);
 VBoxProduit.setAlignment(Pos.CENTER);
 VBoxProduit.setPrefHeight(100);
 VBoxProduit.setPrefWidth(600);

 //attribution des element
 FileInputStream input = new FileInputStream("./src/images/" + list.get(i).getImage_produit());
 Image image = new Image(input);
 ImageView imageView = new ImageView(image);
 imageView.setFitWidth(125);
 imageView.setFitHeight(75);
 int id = list.get(i).getId_produit();
 Label nom = new Label(list.get(i).getNom_produit());
 Label prix = new Label("Prix de l' : " + Double.toString(list.get(i).getPrix_produit()) + " DT");
 Double totalUniter = panierService.TotalUniter(id);

 Label totale = new Label("Totale : " + totalUniter + " DT");
 // Panier listp = new Panier(list.get(i).getId_produit(), list.get(i).getNom_produit(), list.get(i).getImage_produit(), list.get(i).getQuantite_produit(), list.get(i).getPrix_produit());
 int qt = list.get(i).getQt_total_produit();
 p = list.get(i).getPrix_produit();
 ComboBox comboBox = new ComboBox();
 for (int j = 1; j < qt + 1; j++) {
 comboBox.getItems().add(j);
 }

 comboBox.setVisibleRowCount(5);
 //comboBox.setValue(1);
 comboBox.setPromptText("Quantite");

 comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

 public void changed(ObservableValue options, Object oldValue, Object newValue) {
 String o = newValue.toString();
 q = Integer.parseInt(o);
 System.out.println(q);

 // Label total_u = new Label("Totale"+);
 total = p * q;
 System.out.println(total);
 totale.setText("Totale : " + total + " DT");
 panierService.modifierQuantiter(id, q);
 //"Totale : "+Double.toString(list.get(i).getPrix_produit())+" DT"

 Double totalPanier = panierService.TotalPanier(n);
 totale_achat.setText("" + totalPanier + " Dt");
 }

 });

 Button btnSupp = new Button("Supprimer");
 btnSupp.setStyle("-fx-background-color : red");
 btnSupp.setOnAction(new EventHandler<ActionEvent>() {

 @Override
 public void handle(ActionEvent event) {
 panierService.supprimerArticle(id);
 flowPane.getChildren().clear();
 afficherProduit();

 }
 });

 Double totalPanier = panierService.TotalPanier(n);
 totale_achat.setText("" + totalPanier + " Dt");

 VBoxProduit.getChildren().add(imageView);
 VBoxProduit.getChildren().add(nom);
 VBoxProduit.getChildren().add(prix);
 VBoxProduit.getChildren().add(comboBox);
 // VBoxProduit.getChildren().add(btnMod);
 VBoxProduit.getChildren().add(totale);
 VBoxProduit.getChildren().add(btnSupp);

 //ajout des vbox du produit a vbox 
 vbx.add(VBoxProduit);
 flowPane.getChildren().add(vbx.get(i));
 flowPane.getChildren().add(as.get(i));

 } catch (FileNotFoundException ex) {
 Logger.getLogger(ProduitsController.class.getName()).log(Level.SEVERE, null, ex);
 }
 }
 }

 }












 */
/*

 // TODO














 System.out.println("fuucjj$");
 List<Panier> articles2 = ServicePanier.getArticle2();
 // HashMap<String, String> hashmapPanier = new HashMap<>();
       

 articles2 = sp.getArticle2();
 int c = articles2.size();
 //System.out.println(c);
 for (int i = 0; i < c; i++) {
 System.out.println(articles2.get(i).getId_produit());

 Panier panier = new Panier();
 panier.setId_produit(articles2.get(i).getId_produit());
 panier.setNom_produit(articles2.get(i).getNom_produit());
 panier.setImage_produit(articles2.get(i).getImage_produit());
 panier.setPrix_produit(articles2.get(i).getPrix_produit());
 panier.setQuantite_produit(articles2.get(i).getQuantite_produit());

 ListPanier.add(panier);

 id_produit.setCellValueFactory(new PropertyValueFactory<>("id_produit"));

 nom_produit.setCellValueFactory(new PropertyValueFactory<>("nom_produit"));
 image_produit.setCellValueFactory(new PropertyValueFactory<>("image_produit"));
 prix_produit.setCellValueFactory(new PropertyValueFactory<>("prix_produit"));
 quantiter_produit.setCellValueFactory(new PropertyValueFactory<>("quantite_produit"));

 liste_produit.setItems(ListPanier); 
 }

 */
