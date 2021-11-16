package ui;

import java.net.URI;
import java.net.URISyntaxException;
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

}
