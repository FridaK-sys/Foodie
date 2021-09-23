package ui;

import java.net.URL;
import java.util.ResourceBundle;

import core.Ingredient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NewRecipeController implements Initializable   {


    @FXML
    private TextField recipeTitle;

    @FXML
    private TextField recipePortions;

    @FXML
    private TextField ingredientTitle;

    @FXML
    private TextField ingredientAmount;

    @FXML
    private TextField ingredientUnit;

    @FXML
    private ListView<Ingredient> ingredientListView;

    @FXML
    private TextArea recipeDescription;




    public void createRecipe(ActionEvent ae)    {
        if(recipeTitle.getText().isBlank() || recipePortions.getText().isBlank() || recipePortions.getText() == null) {
            throw new IllegalArgumentException("Mangler tittel og porsjoner");

        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        
    }

    
    
}
