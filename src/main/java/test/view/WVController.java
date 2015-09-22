package test.view;

import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import test.utils.SQL;

public class WVController {
	private static final double _AREA = 0.00300;

	String _url = Main.class.getResource("index.html").toExternalForm();
	private ObservableList<Network> networkData = FXCollections
			.observableArrayList();

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

	@FXML
	private void initialize() {
		_engine = _webView.getEngine();
		_engine.load(_url);
		_engine.setJavaScriptEnabled(true);
		_jsobj = (JSObject) _webView.getEngine().executeScript("window");
		Bridge bridge = new Bridge();
		_jsobj.setMember("java", bridge);
		bridge.setWvController(this);
		_updTimer = FxTimer.runPeriodically(Duration.ofMillis(2000),
				() -> addCoordOnMap());
		_updTimer.stop();
		_gps = new Gps();
		new Thread(_gps).start();

		_lat.setCellValueFactory(new PropertyValueFactory<Network, Double>(
				"lat"));
		_lon.setCellValueFactory(new PropertyValueFactory<Network, Double>(
				"lon"));
		_essid.setCellValueFactory(new PropertyValueFactory<Network, String>(
				"essid"));
		_table.setItems(networkData);
	}

	@FXML
	public void runScr() {
		System.out.println("Start updating.");
		_updTimer.restart();
	}

	@FXML
	private void stopScr() {
		System.out.println("Stop updating.");
		_updTimer.stop();
	}

	@FXML
	private void clearMap() {
		_jsobj.call("clearMap");
	}

	private void addCoordOnMap() {
		System.out.println("Try to add coords on map.");
		Double lon = _gps.getLon();
		Double lat = _gps.getLat();

		process(lon, lat);
	}

	public void onClick(final Double lon, final Double lat) {
		clearMap();
		process(lon, lat);
	}

	public void process(final Double lon, final Double lat) {
		networkData.clear();

		LinkedList<Map<String, Object>> request = SQL.get().request(
				String.format(SQL._REQ, lat, lon, _AREA));
		for (Map<String, Object> map : request) {
			networkData.add(new Network(map));
		}

		System.out.println(Arrays.toString(request.toArray()));

		if (lon.isNaN() || lat.isNaN() || lon == 0.0 || lat == 0.0) {
			System.out.println("coord is not valid");
		} else {
			_jsobj.call("addPoint", lat, lon, _AREA, _track.isSelected());
		}
	}
}
