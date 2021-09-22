package ui;

import javafx.collections.FXCollections;

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
import core.Recipe;
import core.Cookbook;
import core.Ingredient;
import javafx.fxml.Initializable;

public class MainController implements Initializable {

    private Cookbook mainBook = new Cookbook();
    private ObservableList<Recipe> recipes = FXCollections.observableArrayList();

    @FXML
    private ListView<Recipe> mainListView;

    public void initialize(URL url, ResourceBundle rb) {

        Recipe recipe1 = new Recipe("recipe1", 2);
        recipe1.addIngredient(new Ingredient("Fisk", 3, "dl"));
        recipe1.setDescription("testin");

        Recipe recipe2 = new Recipe("recipe2", 2);
        recipe2.addIngredient(new Ingredient("Fisk", 3, "dl"));
        // recipe2.setDescription("wii");

        mainBook.addRecipe(recipe1);
        mainBook.addRecipe(recipe2);
        // mainBook.addRecipe(recipe2);
        recipes.add(recipe1);
        recipes.add(recipe2);
        mainListView.getItems().addAll(recipes);
        // recipes.add(("e"));
    }

    public void changeSceneToViewRecipe(ActionEvent ae) throws IOException {
        // if (!mainListView.getSelectionModel().isEmpty()) {
            URL fxmlLocation = getClass().getResource("ViewRecipe.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            
            // FXMLLoader loader = new FXMLLoader();
            // loader.setLocation(getClass().getClassLoader().getResource("ViewRecipe.fxml"));
            Parent root = fxmlLoader.load();

            Scene viewRecipesScene = new Scene(root);

            ViewRecipeController controller = fxmlLoader.getController();
            controller.initData(mainListView.getSelectionModel().getSelectedItem());
        

            Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
            stage.setScene(viewRecipesScene);
            stage.show();
        // }
    }

}

