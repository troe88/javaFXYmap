package test.view;

import java.time.Duration;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import org.reactfx.util.FxTimer;

import test.Main;
import test.utils.Gps;

public class WVController {
	String _url = Main.class.getResource("index.html").toExternalForm();

	@FXML
	private WebView _webView;

	private WebEngine _engine;
	private JSObject _w;
	private Gps _gps;

	@FXML
	private void initialize() {
		_engine = _webView.getEngine();
		_engine.load(_url);
		_engine.setJavaScriptEnabled(true);
		System.out.println(_engine.isJavaScriptEnabled());
		_gps = new Gps();
		new Thread(_gps).start();
	}

	@FXML
	public void runScr() {
		System.out.println("run scr");
		_w = (JSObject) _webView.getEngine().executeScript("window");
		// _w.call("addPoint", "55.76", lat.toString());
		FxTimer.runPeriodically(Duration.ofMillis(5000), () -> doSomething());
	}

	private void doSomething() {
		System.out.println("qwe");
		Double lon = _gps.getLon();
		Double lat = _gps.getLat();

		if (lon.isNaN() || lat.isNaN() || lon == 0.0 || lat == 0.0) {
			System.out.println("coord is not valid");
		} else {
			_w.call("addPoint", lat, lon);
		}
	}
}
