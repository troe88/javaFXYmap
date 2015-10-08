package test;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	private Stage primaryStage;
	private static Main _main;

	@Override
	public void start(final Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Network map");
		Main._main = this;

		initRootLayout();
	}

	private void initRootLayout() {
		primaryStage.setScene(new Scene(LayoutLoader.getRootLayout()));
		LayoutLoader.getRootLayout().setCenter(LayoutLoader.getWVLayout());
		primaryStage.show();
	}

	public static void main(final String[] args) throws IOException {
		 launch(args);
	}

	public static Main get_main() {
		return _main;
	}

}
