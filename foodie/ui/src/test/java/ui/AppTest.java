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
import javafx.scene.control.Label;
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
        controller = SceneHandler.getScenes().get(SceneName.MAIN).getController();
        controller.setCookbookAccess(dataAccess);
        stage.setTitle("Multi-Scene Demo");
        stage.show();
    }

    @BeforeEach
    public void setupItems(){
        setTestData();
    }

    @Test
    public void testController() {
        assertNotNull(controller, "Error loading controller");
        assertNotNull(controller.getCookbook(), "Error accessing data");
    }

    @Test
    public void testDataAccess(){
        assertNotNull(this.controller.getAccess());
        assertEquals(this.controller.getAccess(), this.dataAccess);
    }

    @Test
    public void testSceneChange() {
        assertTrue(Window.getWindows().size() == 1);
        Stage firstStage = (Stage) Window.getWindows().get(0);
        Scene firstScene = firstStage.getScene();
        Predicate<ListViewCell> listCell = cell -> cell.lookup(".label") != null;

        clickOn(listViewCell(listCell, 1));

        assertTrue(Window.getWindows().size() == 1);
        Scene secondScene = (Scene) Window.getWindows().get(0).getScene();


        testSceneChange(firstScene, secondScene);

        clickOn("#editRecipe");

        Scene thirdScene = (Scene) Window.getWindows().get(0).getScene();

        testSceneChange(thirdScene, secondScene);

        clickOn("#backButton");

        Scene fourthScene = (Scene) Window.getWindows().get(0).getScene();

        testSceneChange(thirdScene, secondScene);
        assertEquals(fourthScene, secondScene);

        clickOn("#backButton");

        Scene fifthScene = (Scene) Window.getWindows().get(0).getScene();

        testSceneChange(fifthScene, fourthScene);
        assertEquals(fifthScene, firstScene);

        clickOn("#newButton");

        Scene sixthScene = (Scene) Window.getWindows().get(0).getScene();
        testSceneChange(sixthScene, fifthScene);
        assertEquals(thirdScene, sixthScene);

    }

    @Test
    public void testEditRecipe() {
        Predicate<ListViewCell> listCell = cell -> cell.lookup(".label") != null;

        clickOn(listViewCell(listCell, 1));


        Label tag = lookup("#labelTag").query();
        String recipeTag = dataAccess.getCookbook().getRecipes().get(1).getLabel();
        assertEquals(recipeTag, "breakfast");

        clickOn("#editRecipe");
        clickOn("#lunch");
        clickOn("#saveRecipeButton");

        Label tag2 = lookup("#labelTag").query();
        String recipeTag2 = dataAccess.getCookbook().getRecipes().get(1).getLabel();
        assertEquals(recipeTag2, "lunch", "not saved to file");
        assertEquals(tag2.getText(), "lunch", "label not updated");

    }

    private void testSceneChange(Scene s1, Scene s2){
        assertNotEquals(s1, s2);
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
