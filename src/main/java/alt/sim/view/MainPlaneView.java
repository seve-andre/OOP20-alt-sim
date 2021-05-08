package application;

import javafx.application.Application;
import javafx.stage.Stage;
import test.ImageResized;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * View class for show the Plane.
 */
public class MainPlaneView extends Application {
	/** width value of the Screen. */
	public static int SCREEN_WIDTH = 1280;
	/** height value of the Screen. */
	public static int SCREEN_HEIGHT = 768;
	
	@Override
	public void start(Stage stage) {
	
		try {
			Pane pane_root = new Pane();
			ImageResized planeImageResized = new ImageResized();
			
			//Calculating the Proportion --> (Image:Screen)
			planeImageResized.resizeImageSprite();
			
			// View Plane demonstrating:
			pane_root.resize(SCREEN_WIDTH, SCREEN_HEIGHT);
			pane_root.getChildren().add(planeImageResized.getImageSprite());
			
			Scene scene = new Scene(pane_root, SCREEN_WIDTH, SCREEN_HEIGHT);
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * Launch a standalone application calling the JavaFx mainThread.
	 * @param args the command line arguments. 
	*/
	public static void main(String[] args) {
		launch(args);
	}
}
