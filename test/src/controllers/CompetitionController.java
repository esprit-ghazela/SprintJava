/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import models.Competition;
import services.ServiceCompetition;
import utils.DataSource;

/**
 * FXML Controller class
 *
 * @author Ahmed laifi
 */
public class CompetitionController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private TextField enomc;
    @FXML
    private TextField eregion;
    @FXML
    private DatePicker edebut;
    @FXML
    private DatePicker efinal;

    @FXML
    private TextField eprime;

    @FXML
    private TableView<Competition> table_eventComp;
    @FXML
    private TableColumn<Competition, String> idComp;
    @FXML
    private TableColumn<Competition, String> regionComp;
    @FXML
    private TableColumn<Competition, String> debutComp;
    @FXML
    private TableColumn<Competition, String> finalComp;
    @FXML
    private TableColumn<Competition, String> primeComp;
    @FXML
    private TableColumn<Competition, String> nomComp;
    @FXML
    private Button supprimerComp;
    @FXML
    private Button EajouterComp;
    @FXML
    private Button modifierComp;

    private Competition cp = null;

    @FXML
    private AnchorPane pagComp;

    @FXML
    private TextField search;
    @FXML
    private Button gererC;
    @FXML
    private BarChart<String, Integer> chart;
    @FXML
    private Button stat;

    private Connection cnx;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        afficher();

        table_eventComp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                cp = (Competition) table_eventComp.getSelectionModel().getSelectedItem();
                System.out.println(cp);
                enomc.setText(cp.getNomcomp());
                eregion.setText(cp.getRegion());
                LocalDate d1 = cp.getDebut().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                edebut.setValue(d1);
                LocalDate d2 = cp.getDfinal().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                efinal.setValue(d2);
                int cprix = cp.getPrime();
                String nb_PPP = String.valueOf(cprix);
                eprime.setText(nb_PPP);

            }
        }
        );
    }

    @FXML
    private void ajouterComp(ActionEvent event) {

        if (eregion.getText().isEmpty() || enomc.getText().isEmpty() || edebut == null || efinal == null || eprime.getText().isEmpty()) {

            JOptionPane.showMessageDialog(null, "verifer les champs");
        } else if (edebut.getValue().equals(efinal.getValue()) || edebut.getValue().isAfter(efinal.getValue())) {
            JOptionPane.showMessageDialog(null, "Attention la Date debut doit etre supérieur a la  date fin");
        } else {
            String nome = enomc.getText();
            String regionee = eregion.getText();
            LocalDate dd = edebut.getValue();
            Date datee = java.sql.Date.valueOf(dd);
            LocalDate df = efinal.getValue();
            Date datee1 = java.sql.Date.valueOf(df);
            int soldee = Integer.parseInt(eprime.getText());
            ServiceCompetition sp = new ServiceCompetition();
            Competition c = new Competition(regionee, datee, datee1, soldee, nome);

            sp.insert(c);
            JOptionPane.showMessageDialog(null, "ajout avec succes");
            enomc.clear();
            eregion.clear();
            edebut.setValue(null);
            efinal.setValue(null);
            eprime.clear();
            afficher();

        }
    }

    private void afficher() {
        ServiceCompetition sp = new ServiceCompetition();
        List Competitions = sp.displayAll();
        ObservableList et = FXCollections.observableArrayList(Competitions);
        table_eventComp.setItems(et);

        idComp.setCellValueFactory(new PropertyValueFactory<>("id"));
        regionComp.setCellValueFactory(new PropertyValueFactory<>("region"));
        debutComp.setCellValueFactory(new PropertyValueFactory<>("debut"));
        finalComp.setCellValueFactory(new PropertyValueFactory<>("final"));
        primeComp.setCellValueFactory(new PropertyValueFactory<>("prime"));

        nomComp.setCellValueFactory(new PropertyValueFactory<>("nomcomp"));

    }

    @FXML
    private void supprimerComp_event(ActionEvent event) {
        ServiceCompetition cs = new ServiceCompetition();
        Competition cp = (Competition) table_eventComp.getSelectionModel().getSelectedItem();
        System.out.println(cp);
        if (cp == null) {
            JOptionPane.showMessageDialog(null, "choisir Competition");

        } else {
            cs.delete(cp.getId());

            afficher();

            JOptionPane.showMessageDialog(null, "Competition supprimer");
            enomc.clear();
            eregion.clear();
            edebut.setValue(null);
            efinal.setValue(null);
            eprime.clear();
            cp = null;
        }
    }

    @FXML
    private void modiferComp_event(ActionEvent event) {
        ServiceCompetition cs = new ServiceCompetition();

        System.out.println(cp);
        if (cp == null) {
            JOptionPane.showMessageDialog(null, "choisir Competition");

        } else {
            String regionee = eregion.getText();
            String nome = enomc.getText();
            LocalDate dd = edebut.getValue();
            Date datee = java.sql.Date.valueOf(dd);
            LocalDate df = efinal.getValue();
            Date datee1 = java.sql.Date.valueOf(df);
            int soldee = Integer.parseInt(eprime.getText());

            cs.update(new Competition(regionee, datee, datee1, soldee, nome), cp.getId());

            afficher();
            JOptionPane.showMessageDialog(null, "Competition modifier");
            enomc.clear();
            eregion.clear();
            edebut.setValue(null);
            efinal.setValue(null);
            eprime.clear();
            cp = null;

        }

    }

    @FXML
    private void recherche(ActionEvent event) {
        ServiceCompetition cc = new ServiceCompetition();
        ArrayList AL = (ArrayList) cc.displayAll();
        ObservableList OReservation = FXCollections.observableArrayList(AL);
        FilteredList<Competition> filteredData = new FilteredList<>(OReservation, p -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(myObject.getRegion()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;

                }
                return false;
            });
        });
        SortedList<Competition> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table_eventComp.comparatorProperty());
        table_eventComp.setItems(sortedData);
    }

    @FXML
    private void gererClub(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("views/GestionClub.fxml"));
        pagComp.getChildren().setAll(pane);
    }

    @FXML
    private void loadChart() {
        try {
            String query = "select region,prime FROM Competition ORDER BY   prime";
            XYChart.Series<String, Integer> series = new XYChart.Series<>();
            cnx = DataSource.getInstance().getCnx();
            ResultSet rss = null;
            try {
                rss = cnx.createStatement().executeQuery(query);
            } catch (SQLException ex) {
                Logger.getLogger(CompetitionController.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (rss.next()) {
                try {
                    series.getData().add(new XYChart.Data<>(rss.getString(1), rss.getInt(2)));
                } catch (SQLException ex) {
                    Logger.getLogger(CompetitionController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            chart.getData().add(series);
        } catch (SQLException ex) {
            Logger.getLogger(CompetitionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
