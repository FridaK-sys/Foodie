package ui;

import javafx.fxml.Initializable;
import ui.utils.CookbookInterface;

/**
 * Abstract class for RestApp- and LocalAppController
 */
public abstract class AbstractController implements FoodieController, Initializable {

  protected CookbookInterface dataAccess;

  protected abstract void setUpStorage();

}
