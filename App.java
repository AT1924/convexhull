package convexhull;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import support.convexhull.PaneOrganizer;
import support.convexhull.Constants;

/**
 * This class is the main class for Convex Hull. You don't need to touch this
 * file at all!
 */

public class App extends Application {

	@Override
	public void start(Stage stage) {
		/*
		* This is the image path for the background. If you are working from home, 
		* please change this to a folder (not file!) on your own computer.
		*/
//		String imagePath = "/course/cs016/lib/convexhull-images/";
//		String imagePath = "/Users/Zorro/Pictures/";
//		String imagePath = "/Users/Akhil/Desktop/CS_PICS";
		PaneOrganizer paneOrganizer = new PaneOrganizer(new MyHullFinder(imagePath));
		Scene scene = new Scene(paneOrganizer.getRoot());
		stage.setScene(scene);
		stage.setTitle("convexhull");
		stage.show();
	}

	/**
	 * WARNING: Program mainline. Do not modify this method!
	 */
	public static void main(String[] argv) {
		// launch is a method inherited from Application
		launch(argv);
	}
}
