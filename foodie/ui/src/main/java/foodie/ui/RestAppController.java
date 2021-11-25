package foodie.ui;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import foodie.ui.utils.CookbookAccess;
import foodie.ui.utils.RemoteCookbookAccess;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class RestAppController extends AbstractController {

  @FXML
  private AnchorPane mainListView;

  @FXML
  ListViewController mainListViewController;

  /**
   * Set up the URI.
   *
   * @return the new URI
   */

  private URI uriSetup() {
    URI newUri = null;
    try {
      newUri = new URI("http://localhost:8080/cookbook");
    } catch (URISyntaxException e) {
      System.out.println(e.getMessage());
    }
    return newUri;

  }

  /**
   * Initialize method.
   *
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    setUpStorage();
    initializeRecipesView();
    mainListViewController.setMaster(this);
  }

  /**
   * Makes the URI endpoint.
   */

  @Override
  protected void setUpStorage() {
    dataAccess = new RemoteCookbookAccess(uriSetup());
  }


  /**
   * Updates the mainListViewController.
   */

  @Override
  public void update() {
    mainListViewController.update();
  }


}
