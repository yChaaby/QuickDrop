package ma.fstt.trackingl;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.util.Stack;

public class HelloApplication extends Application {
    static Stage stageP;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("dashbord.fxml"));


        Scene scene = new Scene(fxmlLoader.load(),763,530);

        stage.setTitle("QuickDrop");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED); // close , minimize pannel disable !
        stage.setScene(scene);
        stage.show();
        String currentPath = new java.io.File(".").getCanonicalPath();
        System.out.println("Current dir:" + currentPath);
        //Image icon = new Image(getClass().getResourceAsStream("QuickDrop.png"));
        //stage.getIcons().add(icon);
        stageP=stage;


    }

    public static void main(String[] args) {
        launch();

    }
}
