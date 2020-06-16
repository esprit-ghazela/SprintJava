/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;
import java.io.IOException;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;
import models.Club;
import services.ServiceClub;



/**
 *
 * @author Ahmed laifi
 */
public class ClubController implements Initializable {
    
    @FXML
    private TextField enom;
      @FXML
    private TextField efondateur;
    @FXML
    private DatePicker edate;
  
    @FXML
    private TextField esolde;
      @FXML
    private Button Eajouter;
    @FXML
    private TableView<Club> table_event;
    @FXML
    private TableColumn<Club,String> cid;
    @FXML
    private TableColumn<Club,String> cnom;
    @FXML
    private TableColumn<Club,String> cfondateur;
    @FXML
    private TableColumn<Club,String>cdate;
    @FXML
    private TableColumn<Club,String>csolde;
    @FXML
    private Button supprimer;
    @FXML
    private Button modifier;
    private Club cc=null;
 
    @FXML
    private AnchorPane pag;
  @FXML
    private Button trier;
    @FXML
    private Button gererComp;
   
  
    
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
         afficher();  
         
          table_event.setOnMouseClicked(new EventHandler<MouseEvent>()
                
        {
            @Override
            public void handle(MouseEvent event) {
                cc = (Club)table_event.getSelectionModel().getSelectedItem();
                System.out.println(cc);
                enom.setText(cc.getNom());
                  efondateur.setText(cc.getFondateur());
                  
                LocalDate d1=cc.getDate_creation().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
       
                edate.setValue(d1);
 
                int cprix=cc.getSolde();
                String nb_PPP=String.valueOf(cprix);
                esolde.setText(nb_PPP);
                
            }
            
            
        }
                
                
                
        );
         
         
}
    
    
     @FXML
    private void ajouter(ActionEvent event) {
         if(enom.getText().isEmpty() || edate==null ||  efondateur.getText().isEmpty() )
        
        {

         JOptionPane.showMessageDialog(null, "verifer les champs");   
        }
        else{

        String nome = enom.getText();
          String fondateure = efondateur.getText();
        LocalDate dd =edate.getValue();
        Date datee = java.sql.Date.valueOf(dd);
        int soldee = Integer.parseInt(esolde.getText());
        
        ServiceClub sp = new ServiceClub();
        Club e = new Club(nome,fondateure, datee , soldee);
      
        
        
        sp.insert(e);
         JOptionPane.showMessageDialog(null, "ajout avec succes");
         enom.clear();

        edate.setValue(null);
    efondateur.clear();
       esolde.clear();
        afficher();
       
    }}
    
    
     private void afficher()
   { ServiceClub sp = new ServiceClub();
   List Clubs=sp.displayAll();
       ObservableList et=FXCollections.observableArrayList(Clubs);
       table_event.setItems(et);
       
       cid.setCellValueFactory(new PropertyValueFactory<>("id"));
       cnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
       cfondateur.setCellValueFactory(new PropertyValueFactory<>("fondateur"));
       cdate.setCellValueFactory(new PropertyValueFactory<>("date_creation"));
   
       csolde.setCellValueFactory(new PropertyValueFactory<>("solde"));

   
   }
    
     
      @FXML
    private void supprimer_event(ActionEvent event) {
        ServiceClub cs = new ServiceClub();
         Club cc = (Club)table_event.getSelectionModel().getSelectedItem();
        System.out.println(cc);
        if(cc== null){
            JOptionPane.showMessageDialog(null, "choisir club");
                   
        }else{
            cs.delete(cc.getId());
    
           afficher();
           
           JOptionPane.showMessageDialog(null, "club supprimer");
            enom.clear();
 
        edate.setValue(null);
       efondateur.clear();
        esolde.clear();
        cc=null;
    }
    }
     
    
     @FXML
    private void modifer_event(ActionEvent event) {
        ServiceClub cs = new ServiceClub();
        
        System.out.println(cc);
        if(cc== null){
            JOptionPane.showMessageDialog(null, "choisir club");
                   
        }
                else
                {
                  
        String nome = enom.getText();
        LocalDate dd =edate.getValue();
        Date datee = java.sql.Date.valueOf(dd);
        String fondateure = efondateur.getText();
        
        int soldee = Integer.parseInt(esolde.getText());
      
           cs.update(new Club(nome,fondateure, datee , soldee),cc.getId());
           
       afficher();
        JOptionPane.showMessageDialog(null, "club modifier");
       enom.clear();
      
        edate.setValue(null);
       efondateur.clear();
        esolde.clear();
        cc=null;

                }
        
    }
    
    
    @FXML
    private void trier(ActionEvent event) {
       ServiceClub sp = new ServiceClub();
       List events=sp.trieParsolde();
       ObservableList et=FXCollections.observableArrayList(events);
       table_event.setItems(et);
         
       cid.setCellValueFactory(new PropertyValueFactory<>("id"));
       cnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
       cfondateur.setCellValueFactory(new PropertyValueFactory<>("fondateur"));
       cdate.setCellValueFactory(new PropertyValueFactory<>("date_creation"));
   
       csolde.setCellValueFactory(new PropertyValueFactory<>("solde"));


    }
    
    @FXML
    private void gererCompetition(ActionEvent event) throws IOException {
       AnchorPane pane=FXMLLoader.load(getClass().getResource("GestionCompetition.fxml"));
        pag.getChildren().setAll(pane);
    }
    
    }
    
