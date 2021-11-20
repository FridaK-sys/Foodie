package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.utils.LocalCookbookAccess;

public class AppTest extends AbstractAppTest {

    private AbstractController controller;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("Main_test.fxml"));
        final Parent root = loader.load();
        this.controller = loader.getController();
        setTestData();
        this.controller.setCookbookAccess(dataAccess);
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testController() {
        assertNotNull(this.controller, "Error loading controller");
        assertNotNull(this.controller.getCookbook(), "Error accessing data");
    }

}
