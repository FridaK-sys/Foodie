package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import json.FileHandler;

public class NewRecipeController implements Initializable  { 

    private Recipe newRecipe;
    private FileHandler fileHandler = new FileHandler();
    private ObservableList<Ingredient> ingredients = FXCollections.observableArrayList();


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

    @FXML
    private Button backButton;

    @FXML
    private Label errorMessageLabel;

    public void addIngredientButton(ActionEvent ae){
        try {
            if(ingredientTitle.getText().isBlank()) {
                throw new IllegalArgumentException("Missing a title here...");
            }
			if (ingredientAmount.getText() != null && !ingredientAmount.getText().isEmpty()) {
				Ingredient newIngredient = new Ingredient(ingredientTitle.getText(),
						(Double.parseDouble(ingredientAmount.getText())),
						(ingredientUnit.getText()));
				ingredients.add(newIngredient);
			} 
            else {
				Ingredient newIngredient = new Ingredient(ingredientTitle.getText()); //legge til en konstruktør i ingredient slitk at vi kan legge til ingredienser uten mengde, eks "salt"
				ingredients.add(newIngredient);
			}
			ingredientAmount.setText(null);
			ingredientTitle.setText(null);
			ingredientUnit.setText(null);

		} catch (NumberFormatException e) {
			errorMessageLabel.setText("Invalid input: ingredient amount must be a number");
            e.printStackTrace();
		} catch (IllegalArgumentException e) {
			errorMessageLabel.setText(e.getMessage());
            e.printStackTrace();
		} catch (NullPointerException e) {
			errorMessageLabel.setText("The ingredient needs a title");
            e.printStackTrace();
		}
    }




    public void createRecipe(ActionEvent ae) throws IOException    {
        
        try {
            if(recipeTitle.getText().isBlank() || recipePortions.getText().isBlank() || recipePortions.getText() == null) {
                throw new IllegalArgumentException("Missing name or portion size");
            }
            Recipe newRecipe = new Recipe(recipeTitle.getText(), Integer.parseInt(recipePortions.getText()));

            if (!(recipeDescription.getText() == null)){
                newRecipe.setDescription(recipeDescription.getText());
            }
            this.newRecipe = newRecipe;
            if (ingredients.isEmpty()){
                throw new IllegalArgumentException("You are missing ingredients");
            }

            for (Ingredient i : ingredients){
                newRecipe.addIngredient(i);
            }
            newRecipe.setDescription(recipeDescription.getText());

            fileHandler.writeRecipeToFile("src/main/resources/ui/test.txt", newRecipe);
            backButton.fire();
        } catch (NullPointerException e)   {
            errorMessageLabel.setText("You have empty fields");
        } catch (NumberFormatException e)   {
            errorMessageLabel.setText("ingredient amount must be a number");
        } catch (IllegalArgumentException e)   {
            errorMessageLabel.setText(e.getMessage());
        } 
        
    }

    //mvn -pl ui javafx:run 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ingredientListView.setItems(ingredients);
        
    }

    public void changeSceneToListView(ActionEvent ea) throws IOException {
            URL fxmlLocation = getClass().getResource("Main.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            
            Parent root = fxmlLoader.load();
            Scene viewRecipesScene = new Scene(root);

            // MainController controller = fxmlLoader.getController();
            // controller.initData(newRecipe);

            Stage stage = (Stage) ((Node) ea.getSource()).getScene().getWindow();
            stage.setScene(viewRecipesScene);
            stage.show();
    }

    
    
}
