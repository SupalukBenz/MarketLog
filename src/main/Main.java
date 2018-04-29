package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Main extends Application {

    private double x,y;

    @Override
    public void start(final Stage primaryStage) throws Exception {
        try {
            Parent root = (Parent) FXMLLoader.load(getClass().getResource(setFileName("/UI/HomePage.fxml")));
//            stageSet = primaryStage;
//            parent = root;

            Scene scene = new Scene(root);

            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    x = event.getSceneX();
                    y = event.getSceneY();
                }
            });

            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    primaryStage.setX(event.getSceneX() - x);
                    primaryStage.setY(event.getSceneY() - y);
                }
            });

            primaryStage.getIcons().add(new Image("/UI/photos/MarketLogIcon.png"));
            primaryStage.setTitle("Market Log");
            primaryStage.setScene(scene);
//            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.sizeToScene();
            primaryStage.show();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String setFileName(String fileName){
        return fileName;

    }

    public static void main(String[] args) {
        launch(args);
    }
}
