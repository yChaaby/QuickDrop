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
import ma.fstt.model.Livreur;
import ma.fstt.model.LivreurDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class LivreurController implements Initializable {

    @FXML
    private Livreur livreurSelected;
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
    private TableView<Livreur> mytable ;

    @FXML
    private TableColumn<Livreur ,Long> col_id ;


    @FXML
    private TableColumn <Livreur ,String> col_nom ;

    @FXML
    private TableColumn <Livreur ,String> col_tele ;

    @FXML
    protected void onSaveButtonClick() {

        // accees a la bdd

        try {
            LivreurDAO livreurDAO = new LivreurDAO();
            if(Objects.equals(ok.getText(), "Save")){
                Livreur liv = new Livreur(0l , nom.getText() , tele.getText());
                livreurDAO.save(liv);
            } else if (ok.getText()=="Modifier") {
                livreurDAO.update(new Livreur(livreurSelected.getId_livreur() , nom.getText() , tele.getText()));
                nom.setText("");
                tele.setText("");
                Nom.setText("Nom :");
                Tele.setText("Telephone :");
                BtnHolder.getChildren().remove(1);
            }

            UpdateTable();




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public void UpdateTable(){

        col_id.setCellValueFactory(new PropertyValueFactory<Livreur,Long>("id_livreur"));
        col_nom.setCellValueFactory(new PropertyValueFactory<Livreur,String>("nom"));
        col_tele.setCellValueFactory(new PropertyValueFactory<Livreur,String>("telephone"));

        mytable.setItems(this.getDataLivreurs());
        if(mytable.getColumns().size()==5){
            return;
        }else{
            addButtonToTable("Modifier");
            addButtonToTable("Supprimer");
        }
    }
    // ce morceau de code permet d'ajouter des boutton au ligne
    // et aussi gere ses event
    private void addButtonToTable(String smia) {
        TableColumn<Livreur, Void> colBtn = new TableColumn(smia);

        Callback<TableColumn<Livreur, Void>, TableCell<Livreur, Void>> cellFactory = new Callback<TableColumn<Livreur, Void>, TableCell<Livreur, Void>>() {
            @Override
            public TableCell<Livreur, Void> call(final TableColumn<Livreur, Void> param) {
                final TableCell<Livreur, Void> cell = new TableCell<Livreur, Void>() {

                    private Button btn = new Button(smia);

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Livreur data = getTableView().getItems().get(getIndex());
                            livreurSelected=data;
                            if(btn.getText()=="Supprimer"){
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("Confirmation");
                                alert.setContentText("vous êtes sûr ");
                                Optional<ButtonType> result = alert.showAndWait();
                                if(result.get() == ButtonType.OK){
                                    try {
                                        LivreurDAO livreurDAO = new LivreurDAO();
                                        livreurDAO.delete(data);
                                        UpdateTable();
                                    } catch (SQLException e) {
                                        throw new RuntimeException(e);
                                    }
                                }

                            } else if (btn.getText()=="Modifier") {
                                nom.setText(data.getNom());
                                tele.setText(data.getTelephone());
                                Nom.setText("Nom ("+data.getId_livreur()+")");
                                Tele.setText("Telephone ("+data.getId_livreur()+")");
                                ok.setText("Modifier");
                                Button Cancel = new Button("Annuler");


                                Cancel.setOnAction((ActionEvent eventHanler) -> {
                                    nom.setText("");
                                    tele.setText("");
                                    Nom.setText("Nom :");
                                    Tele.setText("Telephone :");
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

    public static ObservableList<Livreur> getDataLivreurs(){

        LivreurDAO livreurDAO = null;

        ObservableList<Livreur> listfx = FXCollections.observableArrayList();

        try {
            livreurDAO = new LivreurDAO();
            listfx.addAll(livreurDAO.getAll());

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listfx ;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpdateTable();
    }

    public void onBackButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashbord.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.stageP.getScene().getWidth(), HelloApplication.stageP.getScene().getHeight());

        HelloApplication.stageP.setScene(scene);

        if(HelloApplication.stageP.isFullScreen()){
            HelloApplication.stageP.setFullScreen(true);
        }else {

        }

    }
    public void Validation
            (MouseEvent mouseEvent) {
        if((tele.getText().matches("^\\d{10}$") )){

            tele.setStyle("-fx-background-color:  #00A082FF, #FFC244FF;\\n\" +\n" +
                    "                    \"\\t-fx-background-insets: 0, 0 0 1 0;\\n\" +\n" +
                    "                    \"\\t-fx-background-radius: 0;");
            System.out.println("validé prix");

        }else{
            tele.setStyle("-fx-background-color:  Red, #FFC244FF;\n" +
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
        if(tele.getText().matches("^\\d{10}$") && nom.getText().matches("^.+$")){

            ok.setDisable(false);

        }

    }


}