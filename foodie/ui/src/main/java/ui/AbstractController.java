package ui;

import core.Cookbook;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import ui.utils.CookbookInterface;

public abstract class AbstractController implements IFoodieController, Initializable {

  protected CookbookInterface dataAccess;

  @FXML
  ListViewController mainListViewController;

  protected abstract void setUpStorage();

  public void setCookbookAccess(CookbookInterface access) {
    this.dataAccess = access;
    initializeRecipesView();
  }

  public void initializeRecipesView() {
    mainListViewController.setCookbookAccess(dataAccess);
  }

  public Cookbook getCookbook() {
    return mainListViewController.getCookbook();
  }

}
