package program;

import controller.HomePageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangePage {

    public static void loadUI(String UI, BorderPane pane, Class appClass){
        Parent root = null;
        try {
            root =FXMLLoader.load(appClass.getClass().getResource("/UI/" + UI + ".fxml"));
        } catch (IOException ex){
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        pane.setCenter(root);
    }


    public static void changeUI(String fxmlPath, Pane pane) {
        try {
            URL url = ChangePage.class.getClassLoader().getResource(fxmlPath);
            AnchorPane anotherPane = FXMLLoader.load(url);
            pane.getChildren().setAll(anotherPane);
        }catch (IOException | NullPointerException e){
            e.printStackTrace();
        }
    }
}
