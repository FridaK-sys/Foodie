package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ui.utils.LocalCookbookAccess;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import java.util.function.Predicate;
import core.Recipe;

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

    @Test
    public void testRecipeListView() {
        // ListView<Recipe> recipessListView = lookup("#mainListView").query();
        // recipessListView.get
        Predicate<ListViewCell> listCell = cell -> cell.lookup(".label") != null;
        clickOn(listViewCell(listCell, 1));;

        // checkRecipesListViewItems(recipe1, recipe2, recipe3);
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
