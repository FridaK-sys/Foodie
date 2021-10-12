package ui;

import javafx.collections.FXCollections;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import json.FileHandler;
import core.Recipe;
import core.Cookbook;
import core.Ingredient;
import javafx.fxml.Initializable;

public class MainController implements Initializable{ 

    private Cookbook mainBook = new Cookbook();
    private ObservableList<Recipe> recipes = FXCollections.observableArrayList();
    private FileHandler fileHandler = new FileHandler();

    @FXML
    private ListView<Recipe> mainListView;

    public void initialize(URL url, ResourceBundle rb) {
        try {
			fileHandler.readRecipesFromFile("src/main/resources/ui/test.txt", mainBook);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

        recipes.setAll(mainBook.getRecipes());
        mainListView.setItems(recipes);
    }

    public void changeSceneToViewRecipe(ActionEvent ae) throws IOException {
            URL fxmlLocation = getClass().getResource("ViewRecipe.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            
            Parent root = fxmlLoader.load();

            Scene viewRecipesScene = new Scene(root);

            ViewRecipeController controller = fxmlLoader.getController();
            controller.initData(mainListView.getSelectionModel().getSelectedItem());
        

            Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
            stage.setScene(viewRecipesScene);
            stage.show();
    }

    public void changeSceneToNewRecipe(ActionEvent ae) throws IOException {
            URL fxmlLocation = getClass().getResource("NewRecipe.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            
            Parent root = fxmlLoader.load();

            Scene viewRecipesScene = new Scene(root);

            Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
            stage.setScene(viewRecipesScene);
            stage.show();
    }

    public void initData(Recipe recipe) {
        Recipe recipe3 = recipe;
        recipes.add(recipe3);
    }

    public Cookbook getCookbook(){
        return mainBook;
    }


}

