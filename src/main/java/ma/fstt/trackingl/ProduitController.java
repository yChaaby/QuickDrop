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
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import ma.fstt.model.Livreur;
import ma.fstt.model.LivreurDAO;
import ma.fstt.model.Produit;
import ma.fstt.model.ProduitDAO;

import java.awt.geom.Arc2D;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import static java.lang.Float.parseFloat;

public class ProduitController implements Initializable{

    @FXML
    private TableView<Produit> mytable;

    @FXML
    private Produit produitSelected;
    @FXML
    private HBox BtnHolder;

    @FXML
    private TextField nom ;

    @FXML
    private TextField prix ;

    @FXML
    private TextField desc ;

    @FXML
    private Label Prix;

    @FXML
    private Label Desc;

    @FXML
    private Label Nom;

   @FXML
   private Button ok;

    @FXML
    private TableColumn <Produit,String> col_nom ;

    @FXML
    private TableColumn <Produit ,Long> col_id ;

    @FXML
    private TableColumn <Produit ,String> col_desc ;

    @FXML
    private TableColumn <Produit ,Float> col_prix ;

    public void onLivreurButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("produit-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.stageP.getScene().getWidth(), HelloApplication.stageP.getScene().getHeight());
        HelloApplication.stageP.setScene(scene);
        HelloApplication.stageP.show();
        if(HelloApplication.stageP.isFullScreen()){

            HelloApplication.stageP.setFullScreen(true);
        }else {

        }
    }

    public void onBackButtonClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashbord.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.stageP.getScene().getWidth(), HelloApplication.stageP.getScene().getHeight());
        HelloApplication.stageP.setScene(scene);
        HelloApplication.stageP.show();
    }


    public void onSaveButtonClick(ActionEvent actionEvent) throws SQLException {


            if(Objects.equals(ok.getText(), "Save")){
                ProduitDAO ContollerP = new ProduitDAO();
                ContollerP.save(new Produit(0l,nom.getText(),parseFloat(prix.getText()),desc.getText()));
            } else if (Objects.equals(ok.getText(), "Modifier")) {
                ProduitDAO ContollerP = new ProduitDAO();
                ContollerP.update(new Produit(produitSelected.getId_Produit(),nom.getText(),parseFloat(prix.getText()),desc.getText()));

            }

        UpdateTable();
    }

    public static ObservableList<Produit> getDataLivreurs(){

        ProduitDAO produitDAO = null;

        ObservableList<Produit> listfx = FXCollections.observableArrayList();

        try {
            produitDAO = new ProduitDAO();
            listfx.addAll(produitDAO.getAll());

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listfx ;
    }

    public void UpdateTable(){

        col_id.setCellValueFactory(new PropertyValueFactory<Produit,Long>("id_Produit"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Produit,String>("nom"));
        col_prix.setCellValueFactory(new PropertyValueFactory<Produit,Float>("prix"));
        col_desc.setCellValueFactory(new PropertyValueFactory<Produit,String>("description"));

        mytable.setItems(this.getDataLivreurs());
        if(mytable.getColumns().size()==6){
            return;
        }else{

            addButtonToTable("Modifier");
            addButtonToTable("Supprimer");
        }
    }
    // ce morceau de code permet d'ajouter des boutton au ligne
    // et aussi gere ses event

    private void addButtonToTable(String smia) {
        TableColumn<Produit, Void> colBtn = new TableColumn(smia);

        Callback<TableColumn<Produit, Void>, TableCell<Produit, Void>> cellFactory = new Callback<TableColumn<Produit, Void>, TableCell<Produit, Void>>() {
            @Override
            public TableCell<Produit, Void> call(final TableColumn<Produit, Void> param) {
                final TableCell<Produit, Void> cell = new TableCell<Produit, Void>() {

                    private final Button btn = new Button(smia);

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Produit data = getTableView().getItems().get(getIndex());
                            produitSelected = data;
                            if(btn.getText()=="Supprimer"){
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setContentText("Are you sure you wanna delete this ?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if(result.get() == ButtonType.OK){
                                    try {
                                        ProduitDAO produitDAO = new ProduitDAO();
                                        produitDAO.delete(data);
                                        UpdateTable();
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                }

                            } else if (btn.getText()=="Modifier") {
                                nom.setText(data.getNom());
                                prix.setText(""+data.getPrix());
                                Nom.setText("Nom Produit ("+data.getId_Produit()+")");
                                Desc.setText("Description ("+data.getId_Produit()+")");
                                desc.setText(data.getDescription());
                                ok.setText("Modifier");
                                Button Cancel = new Button("Annuler");


                                Cancel.setOnAction((ActionEvent eventHanler) -> {
                                    nom.setText("");
                                    desc.setText("");
                                    prix.setText("");
                                    Nom.setText("Nom Produit:");
                                    Prix.setText("Prix  :");
                                    Desc.setText("Description :");
                                    BtnHolder.getChildren().remove(1);

                                });
                                if(BtnHolder.getChildren().size()<2){
                                    BtnHolder.getChildren().add(Cancel);
                                }

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UpdateTable();
    }

    public void Validation(MouseEvent mouseEvent) {
        if((prix.getText().matches("^(?!0\\d)(?!999999\\.9999)\\d{1,6}(?:\\.\\d{1,4})?$") )){

            prix.setStyle("-fx-background-color:  #00A082FF, #FFC244FF;\\n\" +\n" +
                    "                    \"\\t-fx-background-insets: 0, 0 0 1 0;\\n\" +\n" +
                    "                    \"\\t-fx-background-radius: 0;");
            System.out.println("validé prix");

        }else{
            prix.setStyle("-fx-background-color:  Red, #FFC244FF;\n" +
                    "\t-fx-background-insets: 0, 0 0 1 0;\n" +
                    "\t-fx-background-radius: 0;");
            ok.setDisable(true);

        }
        if((nom.getText().matches("^.+$") )){

            nom.setStyle("-fx-background-color:  #00A082FF, #FFC244FF;\\n\" +\n" +
                    "                    \"\\t-fx-background-insets: 0, 0 0 1 0;\\n\" +\n" +
                    "                    \"\\t-fx-background-radius: 0;");
            System.out.println("validé nom");

        }else{
            nom.setStyle("-fx-background-color:  Red, #FFC244FF;\n" +
                    "\t-fx-background-insets: 0, 0 0 1 0;\n" +
                    "\t-fx-background-radius: 0;");
            ok.setDisable(true);

        }
        if(prix.getText().matches("^(?!0\\d)(?!999999\\.9999)\\d{1,6}(?:\\.\\d{1,4})?$") && nom.getText().matches("^.+$")){

            ok.setDisable(false);

        }

    }
}



