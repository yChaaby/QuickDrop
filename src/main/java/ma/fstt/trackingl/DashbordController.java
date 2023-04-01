package ma.fstt.trackingl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import ma.fstt.model.CommandeDAO;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class DashbordController implements Initializable {
    @FXML
    HBox DiagramsHolder;

    public void onLivreurButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("livreur-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.stageP.getScene().getWidth(), HelloApplication.stageP.getScene().getHeight());
        HelloApplication.stageP.setScene(scene);
        HelloApplication.stageP.show();
        if(HelloApplication.stageP.isFullScreen()){

            HelloApplication.stageP.setFullScreen(true);
        }else {

        }
    }

    public void onLivreurProduitClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("produit-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.stageP.getScene().getWidth(), HelloApplication.stageP.getScene().getHeight());
        HelloApplication.stageP.setScene(scene);
        HelloApplication.stageP.show();

    }

    public void onQuitClick(ActionEvent actionEvent) {
        System.exit(1);
    }

    public void onCommandeClick(ActionEvent actionEvent) throws IOException {
        System.out.println("in cmd");
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("commande-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.stageP.getScene().getWidth(), HelloApplication.stageP.getScene().getHeight());
        HelloApplication.stageP.setScene(scene);
        HelloApplication.stageP.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {
        try {
            CommandeDAO commandeDAO = new CommandeDAO();
            ResultSet RS = commandeDAO.DataParLivreur();

            CategoryAxis xaxis= new CategoryAxis();
            NumberAxis yaxis = new NumberAxis(0,10,1);
            xaxis.setLabel("Livreur");
            yaxis.setLabel("Commande associé");
            BarChart<String,Integer> bar = new BarChart(xaxis,yaxis);
            bar.setTitle("Commande par Livreur");
            XYChart.Series<String,Integer> series = new XYChart.Series<>();
            while(RS.next()){
                series.getData().add(new XYChart.Data(RS.getString(1),RS.getInt(2)));
            }
            bar.setPrefHeight(300);
            bar.setPrefWidth(420);
            bar.getData().add(series);
            DiagramsHolder.getChildren().add(bar);
            RS=commandeDAO.DataParClient();
            PieChart piechart = new PieChart();
            piechart.setData(getChartData(RS));
            piechart.setLegendSide(Side.LEFT);
            piechart.setTitle("Client");
            piechart.setClockwise(true);
            DiagramsHolder.getChildren().add(piechart);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private ObservableList<PieChart.Data> getChartData(ResultSet RS) throws SQLException {
        ObservableList<PieChart.Data> list = FXCollections.observableArrayList();
        while(RS.next()){
            list.add(new PieChart.Data(RS.getString(1), RS.getInt(2)));
            System.out.println("test");
        }

        return list;
    }
}

