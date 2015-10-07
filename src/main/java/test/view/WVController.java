package test.view;

import java.time.Duration;
import java.util.LinkedList;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import org.reactfx.util.FxTimer;
import org.reactfx.util.Timer;

import test.Main;
import test.utils.Bridge;
import test.utils.Gps;
import test.utils.Network;
import test.utils.PingTest;
import test.utils.SQL;

public class WVController {
	private static final long _DEFAULT_UPD_SPEED = 1000L;
	String _url = Main.class.getResource("/index.html").toExternalForm();
	private ObservableList<Network> _networkData = FXCollections
			.observableArrayList();

	@FXML
	private ProgressIndicator _pi;

	@FXML
	private Button _start;

	@FXML
	private Button _stop;

	@FXML
	private TextField _area;

	@FXML
	private TextField _updSpeed;

	@FXML
	private WebView _webView;

	@FXML
	private CheckBox _track;

	@FXML
	private CheckBox _clear;

	@FXML
	private TableView<Network> _table;

	@FXML
	private TableColumn<Network, Double> _lat;

	@FXML
	private TableColumn<Network, Double> _lon;

	@FXML
	private TableColumn<Network, String> _essid;

	private WebEngine _engine;
	private JSObject _jsobj;
	private Gps _gps;
	private Timer _updTimer;
	private PingTest _pingTest;

	@FXML
	private void initialize() {
		_engine = _webView.getEngine();
		_engine.load(_url);
		_engine.setJavaScriptEnabled(true);
		_jsobj = (JSObject) _webView.getEngine().executeScript("window");

		initGPS();
		initBridge();
		initPing();

		_lat.setCellValueFactory(new PropertyValueFactory<Network, Double>(
				"lat"));
		_lon.setCellValueFactory(new PropertyValueFactory<Network, Double>(
				"lon"));
		_essid.setCellValueFactory(new PropertyValueFactory<Network, String>(
				"essid"));
		_table.setItems(_networkData);
		_stop.setDisable(true);
		_pi.setVisible(false);

	}

	private void initPing() {
		_pingTest = new PingTest();
		Thread thread = new Thread(_pingTest);
		thread.setDaemon(true);
		thread.start();
	}

	private void initBridge() {
		Bridge bridge = new Bridge();
		_jsobj.setMember("java", bridge);
		bridge.setWvController(this);
	}

	private void initTimer() {
		Long t = 0L;
		try {
			t = Long.valueOf(_updSpeed.getText());
		} catch (Exception e) {
			System.out.println("Update speed error, use default speed "
					+ _DEFAULT_UPD_SPEED);
			t = _DEFAULT_UPD_SPEED;
			e.printStackTrace();
		}
		_updTimer = FxTimer.runPeriodically(Duration.ofMillis(t),
				new Runnable() {
					@Override
					public void run() {
						addCoordOnMap();
					}
				});
	}

	private void initGPS() {
		_gps = new Gps();
		Thread thread = new Thread(_gps);
		thread.setDaemon(true);
		thread.start();
	}

	@FXML
	public void runScr() {
		System.out.println("Start updating.");
		initTimer();
		_start.setDisable(true);
		_stop.setDisable(false);
		_area.setEditable(false);
		_updSpeed.setEditable(false);
		_pi.setVisible(true);
	}

	@FXML
	private void stopScr() {
		System.out.println("Stop updating.");
		_updTimer.stop();
		_area.setEditable(true);
		_updSpeed.setEditable(true);
		_start.setDisable(false);
		_stop.setDisable(true);
		_pi.setVisible(false);
	}

	@FXML
	private void clearMap() {
		_jsobj.call("clearMap");
	}

	private void addCoordOnMap() {
		Double lon = _gps.getLon();
		Double lat = _gps.getLat();
		process(lon, lat);
	}

	public void onClick(final Double lon, final Double lat) {
		clearMap();
		process(lon, lat);
	}

	public void process(final Double lon, final Double lat) {
		System.out.println("process...");
		_networkData.clear();
		Double area = 0.0;
		try {
			area = Double.valueOf(_area.getText());
		} catch (Exception e) {
			System.err.println("Area error");
			e.printStackTrace();
		}

		if (lon.isNaN() || lat.isNaN() || lon == 0.0 || lat == 0.0) {
			System.out.println("coord is not valid");
		} else {
			LinkedList<Map<String, Object>> request = SQL.get().requestNetwork(
					lat, lon, area);
			for (Map<String, Object> map : request) {
				_networkData.add(new Network(map));
			}
			System.out.println("Network found: " + request.size());
			_jsobj.call("addPoint", lat, lon, area, _track.isSelected(),
					genColor());
		}
	}

	private String genColor() {
		String color = "#000000";
		if (_pingTest.getIsReacheble()) {
			color = "#0000FF";
		} else {
			color = "#FF0000";
		}
		return color;
	}
}
