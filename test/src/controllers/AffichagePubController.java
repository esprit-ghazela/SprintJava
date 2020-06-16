/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
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
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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
import javafx.util.Duration;
import models.Publicite;
import models.PubliciteAimer;
import models.annonce;
import services.ServicePublicite;
import services.ServicePubliciteAimer;
import utils.DataSource;

/**
 * FXML Controller class
 *
 * @author yousra
 */
public class AffichagePubController implements Initializable {
    @FXML
    private ScrollPane scroll;
   
    private Connection con;
    @FXML
    private AnchorPane pubFront;
    @FXML
    private HBox imgslider;

    public AffichagePubController() {
        con = DataSource.getInstance().getCnx();

    }
    private Statement ste;
    private PreparedStatement pre;
    /**
     * Initializes the controller class.
     */
   
               
         List<String> type;
    String imgG="";
    int verif=0;

   
      private final double IMG_WIDTH = 1222;
    private final double IMG_HEIGHT = 300;
 
    private int NUM_OF_IMGS ;
    private final int SLIDE_FREQ = 4; // in secs
         
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            start1();
        } catch (Exception ex) {
            Logger.getLogger(AffichagePubController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    public void start1() throws Exception {
          
  
        
        ServicePubliciteAimer pa = new ServicePubliciteAimer();
        String req="select * from pub  ";
        List<VBox> list = new ArrayList<>();
        
        try {
            ste=con.createStatement();
            ResultSet rs = ste.executeQuery(req);
            while(rs.next()){
                
                
                java.util.Date d1 = new java.util.Date(rs.getDate(4).getTime());
               java.util.Date d2 = new java.util.Date(rs.getDate(5).getTime());
   Publicite p= new Publicite(rs.getInt(1),rs.getString(2),rs.getString(6),d1, rs.getString(3), d2,rs.getDouble(7));
                System.out.println(p);
              ImageView v=new ImageView(new Image(rs.getString(6)));
                 
        v.setFitHeight(300);
        v.setFitWidth(1222);
              Button bt1=new Button("like");
              if (pa.chercher_ajout(new PubliciteAimer(p.getId_publicite(),10, d2))) {
                   bt1.setDisable(true);
              }
               VBox v1=new VBox();
               v1.setAlignment(Pos.CENTER);
               v1.setSpacing(10);
               v1.getChildren().addAll(v,bt1);
               list.add(v1);
               NUM_OF_IMGS=list.size();
               bt1.setOnAction(new EventHandler<ActionEvent>() {
    @Override public void handle(ActionEvent e) {
        try {
            pa.ajouter(new PubliciteAimer(p.getId_publicite(),10,new Date()));
             bt1.setDisable(true);
        } catch (SQLException ex) {
            Logger.getLogger(AffichagePubController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
});
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServicePublicite.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        
       
        imgslider.getChildren().addAll(list);
        
     
        startAnimation(imgslider);
       
      }
    
    
    
    
    
    
    
    private void startAnimation(final HBox hbox) {
        //error occured on (ActionEvent t) line
        //slide action
        EventHandler<ActionEvent> slideAction = (ActionEvent t) -> {
            TranslateTransition trans = new TranslateTransition(Duration.seconds(1.5), hbox);
            trans.setByX(-IMG_WIDTH);
            trans.setInterpolator(Interpolator.EASE_BOTH);
            trans.play();
        };
        //eventHandler
        EventHandler<ActionEvent> resetAction = (ActionEvent t) -> {
            TranslateTransition trans = new TranslateTransition(Duration.seconds(1), hbox);
            trans.setByX((NUM_OF_IMGS - 1) * IMG_WIDTH);
            trans.setInterpolator(Interpolator.EASE_BOTH);
            trans.play();
        };
 
        List<KeyFrame> keyFrames = new ArrayList<>();
        for (int i = 1; i <= NUM_OF_IMGS; i++) {
            if (i == NUM_OF_IMGS) {
                keyFrames.add(new KeyFrame(Duration.seconds(i * SLIDE_FREQ), resetAction));
            } else {
                keyFrames.add(new KeyFrame(Duration.seconds(i * SLIDE_FREQ), slideAction));
            }
        }
//add timeLine
        Timeline anim = new Timeline(keyFrames.toArray(new KeyFrame[NUM_OF_IMGS]));
 
        anim.setCycleCount(Timeline.INDEFINITE);
        anim.playFromStart();
    } 
    
}
