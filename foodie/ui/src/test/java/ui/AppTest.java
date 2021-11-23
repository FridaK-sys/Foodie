package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import ui.utils.CookbookAccess;
import ui.utils.LocalCookbookAccess;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import java.util.function.Predicate;
import core.Recipe;

public class AppTest extends AbstractAppTest {

    private AbstractController controller;
    private CookbookAccess dataAccess = new LocalCookbookAccess("/foodie-test.json");

    @Override
    public void start(final Stage stage) throws Exception {
        SceneHandler.initializeTest(stage);

        stage.setScene(SceneHandler.getScenes().get(SceneName.MAIN).getScene());
        controller = (AbstractController) SceneHandler.getScenes().get(SceneName.MAIN).getController();
        controller.setCookbookAccess(dataAccess);
        stage.setTitle("Multi-Scene Demo");
        stage.show();
    }

    @BeforeEach
    public void setAccess(){
        
    }

    @Test
    public void testController() {
        assertNotNull(controller, "Error loading controller");
        assertNotNull(controller.getCookbook(), "Error accessing data");
    }

    @Test
    public void testDataAccess(){
        // Platform.runLater(new Runnable() {

        //     @Override
        //     public void run() {
        //         controller.setCookbookAccess(new LocalCookbookAccess("/foodie-test.json"));

        //     }

        // });
        assertNotNull(this.controller.getAccess());
        assertEquals(this.controller.getAccess(), this.dataAccess);
    }

    @Test
    public void testRecipeListView() {
        assertTrue(Window.getWindows().size() == 1);
        Stage firstStage = (Stage) Window.getWindows().get(0);
        Scene firstScene = firstStage.getScene();
        Predicate<ListViewCell> listCell = cell -> cell.lookup(".label") != null;

        clickOn(listViewCell(listCell, 1));
        clickOn("#recipeTitle");

        assertTrue(Window.getWindows().size() == 1);
        Stage secondStage = (Stage) Window.getWindows().get(0);
        Scene secondScene = secondStage.getScene();

        assertEquals(firstStage, secondStage);
        assertNotEquals(firstScene, secondScene);
    }

    @Test
    public void testEditRecipe() {
        Predicate<ListViewCell> listCell = cell -> cell.lookup(".label") != null;

        clickOn(listViewCell(listCell, 1));
        clickOn("#recipeTitle");
        clickOn("#")
    }

    private Node waitForNode(Predicate<Node> nodeTest, int num) {
        WaitForAsyncUtils.waitForFxEvents();
        Node[] nodes = new Node[1];
        try {
            WaitForAsyncUtils.waitFor(200, TimeUnit.MILLISECONDS, () -> {
                while (true) {
                    if ((nodes[0] = findNode(nodeTest, num)) != null) {
                        return true;
                    }
                    Thread.sleep(100);
                }
            });
        } catch (TimeoutException e) {
            fail("No appropriate node available");
        }
        return nodes[0];
    }

    private Node findNode(Predicate<Node> nodeTest, int num) {
        int count = 0;
        for (Node node : lookup(nodeTest).queryAll()) {
            if (nodeTest.test(node) && count++ == num) {
                return node;
            }
        }
        return null;
    }

    private ListViewCell listViewCell(Predicate<ListViewCell> test, int num) {
        return (ListViewCell) waitForNode(node -> node instanceof ListViewCell listCell && test.test(listCell), num);
    }

}
