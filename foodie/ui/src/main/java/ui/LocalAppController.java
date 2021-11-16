package ui;

import java.io.File;

import ui.utils.LocalCookbookAccess;

public class LocalAppController extends AbstractController {

  @Override
  protected void setUpStorage() {
    dataAccess = new LocalCookbookAccess(System.getProperty("user.dir") + File.separator + "cookbook.json");
  }

}
