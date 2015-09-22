package test.view;

import java.time.Duration;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import org.reactfx.util.FxTimer;
import org.reactfx.util.Timer;

import test.Main;
import test.utils.Gps;

public class WVController {
	String _url = Main.class.getResource("index.html").toExternalForm();

	@FXML
	private WebView _webView;
	
	@FXML
	private CheckBox _track;

	@FXML
	private CheckBox _clear;
	
	private WebEngine _engine;
	private JSObject _jsobj;
	private Gps _gps;

	private Timer _updTimer;

	@FXML
	private void initialize() {
		_engine = _webView.getEngine();
		_engine.load(_url);
		_engine.setJavaScriptEnabled(true);
		_jsobj = (JSObject) _webView.getEngine().executeScript("window");
		_updTimer = FxTimer.runPeriodically(Duration.ofMillis(5000), () -> addCoordOnMap());
		_updTimer.stop();
		_gps = new Gps();
		new Thread(_gps).start();
	}

	@FXML
	public void runScr() {
		System.out.println("Start updating.");
		_updTimer.restart();
	}

	@FXML
	private void stopScr(){
		System.out.println("Stop updating.");
		_updTimer.stop();
	}
	
	@FXML
	private void clearMap(){
		_jsobj.call("clearMap");
	}
	
	private void addCoordOnMap() {
		System.out.println("Try to add coords on map.");
		Double lon = _gps.getLon();
		Double lat = _gps.getLat();

		if (lon.isNaN() || lat.isNaN() || lon == 0.0 || lat == 0.0) {
			System.out.println("coord is not valid");
		} else {
			_jsobj.call("addPoint", lat, lon, _track.isSelected());
		}
	}
}
