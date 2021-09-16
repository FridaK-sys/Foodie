package ui;

import javafx.collections.FXCollections;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
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

        Recipe recipe2 = new Recipe("recipe2", 2);
        recipe2.addIngredient(new Ingredient("Fisk", 3, "dl"));

        mainBook.addRecipe(recipe1);
        mainBook.addRecipe(recipe2);
        // mainBook.addRecipe(recipe2);
        recipes.add(recipe1);
        recipes.add(recipe2);
        mainListView.getItems().addAll(recipes);
        // recipes.add(("e"));
    }

}
