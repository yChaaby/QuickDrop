package ma.fstt.trackingl;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import ma.fstt.model.Commande;
import ma.fstt.model.CommandeDAO;
import ma.fstt.model.Livreur;
import ma.fstt.model.LivreurDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class CommandeController implements  Initializable{

    @FXML
    private Commande livreurSelected;

    @FXML
    private HBox BtnHolder;

    @FXML
    private Label Nom;

    @FXML
    private Label Tele;

    @FXML
    private TextField nom ;

    @FXML
    private Button ok;

    @FXML
    private TextField tele ;

    @FXML
    private TableView<Commande> mytable ;

    @FXML
    private TableColumn<Commande ,Long> col_id ;

    @FXML
    private TableColumn <Commande ,String> col_client ;

    @FXML
    private TableColumn <Commande ,String> col_livreur ;

    @FXML
    private TableColumn <Commande ,String> col_produit ;

    @FXML
    private TableColumn <Commande ,Float> col_prix ;

    @FXML
    private TableColumn <Commande , LocalDate> col_date ;

    @FXML
    private TableColumn <Commande , Float> col_distance ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UpdateTable();
    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashbord.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.stageP.getScene().getWidth(), HelloApplication.stageP.getScene().getHeight());

        HelloApplication.stageP.setScene(scene);
    }


    public void UpdateTable(){

        col_id.setCellValueFactory(new PropertyValueFactory<Commande,Long>("id_commande"));
        col_client.setCellValueFactory(new PropertyValueFactory<Commande,String>("client"));
        col_livreur.setCellValueFactory(new PropertyValueFactory<Commande,String>("nomLivreur"));
        col_produit.setCellValueFactory(new PropertyValueFactory<Commande,String>("lesProduits"));
        col_prix.setCellValueFactory(new PropertyValueFactory<Commande,Float>("prixTotal"));
        col_distance.setCellValueFactory(new PropertyValueFactory<Commande,Float>("distance"));
        col_date.setCellValueFactory(new PropertyValueFactory<Commande,LocalDate>("dateLivraison"));

        mytable.setItems(this.getDataLivreurs());
        if(mytable.getColumns().size()==8){
            return;
        }else{
            addButtonToTable("Supprimer");
        }
    }
    private void addButtonToTable(String smia) {
        TableColumn<Commande, Void> colBtn = new TableColumn(smia);

        Callback<TableColumn<Commande, Void>, TableCell<Commande, Void>> cellFactory = new Callback<TableColumn<Commande, Void>, TableCell<Commande, Void>>() {
            @Override
            public TableCell<Commande, Void> call(final TableColumn<Commande, Void> param) {
                final TableCell<Commande, Void> cell = new TableCell<Commande, Void>() {

                    private final Button btn = new Button(smia);

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Commande data = getTableView().getItems().get(getIndex());
                            System.out.println(data);
                            try {
                                CommandeDAO commandeDAO = new CommandeDAO();
                                commandeDAO.delete(data);
                                UpdateTable();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }

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
        mytable.getColumns().add(colBtn);

    }

    private ObservableList<Commande> getDataLivreurs() {
        CommandeDAO commandeDAO = null;

        ObservableList<Commande> listfx = FXCollections.observableArrayList();

        try {
            commandeDAO = new CommandeDAO();
            listfx.addAll(commandeDAO.getAll());

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listfx ;
    }

    public void onAddButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("ajouterCommande-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.stageP.getScene().getWidth(), HelloApplication.stageP.getScene().getHeight());
        HelloApplication.stageP.setScene(scene);
    }


    public void Validation(MouseEvent mouseEvent) {

    }

    public void onSaveButtonClick(ActionEvent actionEvent) {

    }


}
