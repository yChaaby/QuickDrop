package ma.fstt.trackingl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ma.fstt.model.Commande;
import ma.fstt.model.CommandeDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;


public class CommandePanel implements Initializable {

    @FXML private VBox produitsHolder;

    @FXML TextField nom_produit;
    @FXML TextField qte_produit;
    @FXML TextField nom_livreur;
    @FXML TextField distance;
    @FXML TextField client;
    @FXML
    DatePicker date;

    static int counter;


    public void Validation(MouseEvent mouseEvent) {

    }
    public int nbrProd(){

        return produitsHolder.getChildren().size();
    }

    public void onSaveButtonClick(ActionEvent actionEvent) throws SQLException, IOException {
        ZoneId defaultZoneId = ZoneId.systemDefault();
        Commande cmd= new Commande(0l,client.getText(),nom_livreur.getText(),nom_produit.getText(),0,parseFloat(distance.getText()), date.getValue(),parseInt(qte_produit.getText()),0);
        CommandeDAO commandeDAO = new CommandeDAO();
        int panier = commandeDAO.setPanier(cmd);
        for (int i=1;i<nbrProd();i++){
            HBox tempH = new HBox();
            tempH= (HBox) produitsHolder.getChildren().get(i);
            TextField QteP = (TextField) tempH.getChildren().get(1);
            System.out.println(QteP.getText());
            TextField nomP = (TextField) tempH.getChildren().get(0);
            System.out.println(nomP.getText());
            Commande TempC = cmd;
            cmd.setLesProduits(nomP.getText());
            cmd.setQte(parseInt(QteP.getText()));
            cmd.setPanier(panier);
            commandeDAO.save(cmd);
        }
        onBackButtonClick();


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
    }

    public void onBackButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("commande-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.stageP.getScene().getWidth(), HelloApplication.stageP.getScene().getHeight());
        HelloApplication.stageP.setScene(scene);
    }

    public void onAddProdButtonClick(ActionEvent actionEvent) {
        TextField id = new TextField();
        TextField qte = new TextField();
        HBox fieldProd = new HBox();
        fieldProd.setSpacing(30);
        id.setPrefHeight(nom_produit.getPrefHeight());
        qte.setPrefHeight(qte_produit.getPrefHeight());
        id.setPrefWidth(nom_produit.getPrefWidth());
        qte.setPrefWidth(qte_produit.getPrefWidth());
        counter++;
        id.setId(id.getId()+counter);
        qte.setId(qte_produit.getId()+counter);
        fieldProd.getChildren().addAll(id,qte);
        produitsHolder.getChildren().add(fieldProd);


    }

    public void onRestClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ajouterCommande-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.stageP.getScene().getWidth(), HelloApplication.stageP.getScene().getHeight());
        HelloApplication.stageP.setScene(scene);
    }
}
