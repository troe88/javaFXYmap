package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class LayoutLoader {

	private static List<String> _layoutLocations = new ArrayList<String>(
			Arrays.asList("/RootLayout.fxml", "/WebViewLayout.fxml"));
	private static List<Object> _loadLayouts = new ArrayList<>();

	static {
		for (String location : _layoutLocations) {
			FXMLLoader _loader = new FXMLLoader();
			_loader.setLocation(Main.class.getResource(location));
			try {
				_loadLayouts.add(_loader.load());
			} catch (IOException e) {
				System.err.println("Load " + location + " error.");
				e.printStackTrace();
			}
		}
	}

	public static BorderPane getRootLayout() {
		return (BorderPane) _loadLayouts.get(0);
	}

	public static AnchorPane getWVLayout() {
		return (AnchorPane) _loadLayouts.get(1);
	}
}