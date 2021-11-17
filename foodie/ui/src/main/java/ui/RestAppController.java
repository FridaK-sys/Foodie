package ui;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import ui.utils.RemoteCookbookAccess;

public class RestAppController extends AbstractController {

  private URI uriSetup() {
    URI newUri = null;
    try {
      newUri = new URI("http://localhost:8080/cookbook");
    } catch (URISyntaxException e) {
      System.out.println(e.getMessage());
    }
    return newUri;

  }

  @Override
  protected void setUpStorage() {
    dataAccess = new RemoteCookbookAccess(uriSetup());
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub

  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // TODO Auto-generated method stub

  }

}
