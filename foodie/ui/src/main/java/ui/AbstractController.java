package ui;

import javafx.fxml.Initializable;

import ui.utils.CookbookInterface;

public abstract class AbstractController implements IFoodieController, Initializable {

  protected CookbookInterface dataAccess;


  protected abstract void setUpStorage();

}
