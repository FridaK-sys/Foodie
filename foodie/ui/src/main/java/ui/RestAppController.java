package ui;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import ui.utils.CookbookAccess;
import ui.utils.RemoteCookbookAccess;

public class RestAppController extends AbstractController {

  @FXML
  private Pane mainListView;

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
  }

  /**
   * Makes the URI endpoint.
   */

  @Override
  protected void setUpStorage() {
    dataAccess = new RemoteCookbookAccess(uriSetup());
  }

  public void setCookbookAccess(CookbookAccess access) {
    this.dataAccess = access;
    initializeRecipesView();
  }

  /**
   * Updates the mainListViewController.
   */

  @Override
  public void update() {
    mainListViewController.update();
  }

}
