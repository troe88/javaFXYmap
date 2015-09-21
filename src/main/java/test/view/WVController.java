package test.view;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.script.ScriptEngine;

import org.reactfx.util.FxTimer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import test.Main;

public class WVController {
	String _url = Main.class.getResource("index.html").toExternalForm();

	@FXML
	private WebView _webView;

	private WebEngine _engine;

	private JSObject _w;

	private Float lat = 37.64f;

	@FXML
	private void initialize() {
		_engine = _webView.getEngine();
		_engine.load(_url);
		_engine.setJavaScriptEnabled(true);
		System.out.println(_engine.isJavaScriptEnabled());
	}

	// 55.76, 37.64
	@FXML
	public void runScr() {
		System.out.println("run scr");
		_w = (JSObject) _webView.getEngine().executeScript("window");
//		_w.call("addPoint", "55.76", lat.toString());
		FxTimer.runPeriodically(Duration.ofMillis(1000), () -> doSomething());
	}

	private void doSomething() {
		System.out.println("qwe");
		_w.call("addPoint", "55.76", lat.toString());
		lat += 0.01f;	
	}
}
