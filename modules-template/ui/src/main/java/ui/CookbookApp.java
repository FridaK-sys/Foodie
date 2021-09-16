package ui;
 
import java.net.URL;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CookbookApp extends Application{

    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        launch(CookbookApp.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Main.fxml"));
        URL fxmlLocation = getClass().getResource("Main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        stage.setTitle("Cookbook<3");
        // Image icon = new Image("crochet-icon-12.jpeg");
        // stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.show();
    }
    
}
