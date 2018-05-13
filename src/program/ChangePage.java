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

/**
 * ChangePage class for managing scene.
 *
 * @author Supaluk Jaroensuk
 */
public class ChangePage {

    /**
     * Load UI when chick button on homepage.
     * @param UI is UI that want to change.
     * @param pane is pane of UI that was called.
     * @param appClass is class of homepage.
     */
    public static void loadUI(String UI, BorderPane pane, Class appClass){
        Parent root = null;
        try {
            root =FXMLLoader.load(appClass.getClass().getResource("/UI/" + UI + ".fxml"));
        } catch (IOException ex){
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        pane.setCenter(root);
    }


    /**
     * Change scene to other page.
     * @param fxmlPath is path of UI that want to change.
     * @param pane is pane of UI that was called.
     */
    public static void changeUI(String fxmlPath, Pane pane) {
        try {
            URL url = ChangePage.class.getClassLoader().getResource(fxmlPath);
            AnchorPane anotherPane = (AnchorPane) FXMLLoader.load(url);
            pane.getChildren().setAll(anotherPane);
        }catch (IOException | NullPointerException e){
            e.printStackTrace();
        }
    }
}
