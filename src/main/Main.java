package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.server.ExportException;

public class Main extends Application {

    private static Stage stageSet;

    /**
     * Set stage for edit form other class
     * @return stage for edit
     */
    public static Stage getStage() { return stageSet; }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            stageSet = primaryStage;
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("/UI/LoginUI.fxml"));

            Scene scene = new Scene(root);
            primaryStage.setTitle("Market Log");
            primaryStage.setScene(scene);
            primaryStage.sizeToScene();
            primaryStage.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
