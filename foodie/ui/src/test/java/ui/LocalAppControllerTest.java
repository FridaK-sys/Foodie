// package ui;

// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertNull;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.stage.Stage;
// import org.junit.jupiter.api.Test;
// import org.testfx.framework.junit5.ApplicationTest;
// import ui.utils.CookbookAccess;
// import java.net.URL;

// /**
//  * Test class for LocalAppController
//  */

// public class LocalAppControllerTest extends ApplicationTest{

//   AbstractController localController;

//   @Override
//   public void start(final Stage stage) throws Exception {
//     URL fxmlLocation = getClass().getResource("Main_test.fxml");
//     FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
//     Parent root = fxmlLoader.load();
//     Scene scene = new Scene(root);
//     this.localController = fxmlLoader.getController();
//     stage.setScene(scene);
//     stage.show();

    assertNull(localController.dataAccess, "dataAccess is not null");
    localController.setUpStorage();
    assertNotNull(localController.dataAccess,"dataAccess is null");
  }
  
// }
