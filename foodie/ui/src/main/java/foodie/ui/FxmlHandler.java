package foodie.ui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.net.URL;

public class FxmlHandler {
	/**
	 * Either builds the scene from {@link FxmlInfo} or loads the built scene.<br>
	 * <br>
	 * Uses this class's ClassLoader to find the URL of the FXML file. If the URL is {@code null} then
	 * the FXML file could not be found.
	 * 
	 * @param fxmlInfo the FXML file info to load the scene with
	 * @return the built scene, or {@code null} if there was an error
	 */
	public Scene load(FxmlModel fxmlModel) {

		if (fxmlModel.hasScene()) {
			return fxmlModel.getScene();
		}

		URL url = getClass().getResource(fxmlModel.getResourceName());

		if (url == null) {
			System.out.println("error");
		}

		FXMLLoader loader = new FXMLLoader(url);
		Scene scene;

		try {
			Parent root = loader.load();
			scene = new Scene(root);
		} catch (IOException e) {
			e.printStackTrace();
			Platform.exit();
			return null;
		}

		// Write back the updated FxmlInfo to the scenes Map in Main
		fxmlModel.setScene(scene);
		SceneHandler.updateScenes(fxmlModel.getSceneName(), fxmlModel);

		FoodieController controller = loader.getController();
		fxmlModel.setController(controller);

		scene.getStylesheets().add(getClass().getResource("MainStyle.css").toString());
		
		if (controller != null) {
			controller.setStage(fxmlModel.getStage());
		}
		return scene;
	}

}
