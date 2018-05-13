package main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * Main class of market log program for running and showing UI of program.
 *
 * @author Supaluk Jaroensuk
 */
public class Main extends Application {

    /**
     * x-axis and y-axis, as a double.
     */
    private double x,y;

    /**
     * The application initialization method
     * @param primaryStage is constructed by the platform
     * @throws Exception prevent null error.
     */
    @Override
    public void start(final Stage primaryStage) throws Exception {
        try {
            Parent root = (Parent) FXMLLoader.load(getClass().getResource("/UI/HomePage.fxml"));

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
            primaryStage.sizeToScene();
            primaryStage.show();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Launch a standalone application
     * @param args is the command line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args);
    }
}
