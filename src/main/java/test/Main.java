package test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import test.model.QuadricEquation;

public class Main extends Application {

	private Stage primaryStage;
	private static Main _main;
	public static QuadricEquation _qe;

	@Override
	public void start(final Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Network map");
		Main._main = this;

		_qe = new QuadricEquation(1.0, 3.0, -4.0);
		
		initRootLayout();
	}

	private void initRootLayout() {
		primaryStage.setScene(new Scene(LayoutLoader.getRootLayout()));
		LayoutLoader.getRootLayout().setCenter(LayoutLoader.getWVLayout());
		primaryStage.show();
	}

	public static void main(final String[] args) {
		launch(args);
	}

	public static Main get_main() {
		return _main;
	}

}
