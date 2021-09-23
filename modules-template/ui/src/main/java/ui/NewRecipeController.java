package ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import core.Cookbook;
import core.Ingredient;
import core.Recipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewRecipeController implements Initializable   {

    private Recipe newRecipe;
    private FileHandler fileHandler = new FileHandler();


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




    public void createRecipe(ActionEvent ae) throws IOException    {
        if(recipeTitle.getText().isBlank() || recipePortions.getText().isBlank() || recipePortions.getText() == null) {
            throw new IllegalArgumentException("Mangler tittel og porsjoner");
        }
        try {
            Recipe newRecipe = new Recipe(recipeTitle.getText(), Integer.parseInt(recipePortions.getText()));
            // Recipe newRecipe = new Recipe("test", 2);

            if (!(recipeDescription.getText() == null)){
                newRecipe.setDescription(recipeDescription.getText());
            }
            this.newRecipe = newRecipe;
        } catch (NumberFormatException e)   {
            throw new NumberFormatException("Ingrediensvolum må være et tall");
        }
        Cookbook testBook = new Cookbook();
        testBook.addRecipe(newRecipe);
        fileHandler.writeRecipeToFile("modules-template/ui/src/main/resources/ui/test.txt", newRecipe);
        backButton.fire();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        
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
