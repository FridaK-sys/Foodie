package ui;
 
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CookbookApp extends Application{

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Main.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        stage.setTitle("Cookbook<3");
        // Image icon = new Image("crochet-icon-12.jpeg");
        // stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.show();
    }
    
}
