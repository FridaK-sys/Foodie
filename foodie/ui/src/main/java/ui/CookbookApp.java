package ui;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CookbookApp extends Application {

  public static void main(String[] args) throws Exception {
    launch(CookbookApp.class, args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    URL fxmlLocation = getClass().getResource("Main.fxml");
    FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
    Parent root = fxmlLoader.load();
    Scene scene = new Scene(root);
    scene.setUserData(fxmlLoader);

    stage.setTitle("Foodie<3");

    stage.setScene(scene);
    stage.show();
  }

}
