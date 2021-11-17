package ui;

import javafx.fxml.Initializable;
import ui.utils.CookbookInterface;

/**
 * Interface for RestApp- and LocalAppController
 */
public interface AbstractController extends FoodieController, Initializable {

  public CookbookInterface dataAccess;

  public void setUpStorage();

}
