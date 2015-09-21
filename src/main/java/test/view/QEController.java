package test.view;

import java.util.List;

import javax.xml.ws.WebEndpoint;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import test.Main;
import test.model.HTMLBuilder;
import test.model.QuadricEquation;

public class QEController {

	@FXML
	private TextField _aField;

	@FXML
	private TextField _bField;

	@FXML
	private TextField _cField;

	@FXML
	private WebView _result;

	private WebEngine _engine;

	private QuadricEquation _qe;

	public QEController() {
	}

	@FXML
	private void initialize() {
		_qe = Main._qe;
		_aField.setText(_qe._A.toString());
		_bField.setText(_qe._B.toString());
		_cField.setText(_qe._C.toString());
		_engine = _result.getEngine();
		_qe.calculate();

		setResult();
	}

	private void setResult() {
		_engine.loadContent(HTMLBuilder.getFormattedQE(_qe._A, _qe._B, _qe._C, _qe._D, _qe._X1, _qe._X2));

	}

	@FXML
	private void calculateEvent() {
		try {
			_qe.setData(setA(), setB(), setC());
		} catch (NullPointerException e) {
			System.out.println("error");
			return;
		}
		_qe.calculate();
		setResult();
		_aField.requestFocus();
	}

	private Double getDoubleFromString(TextField field) throws NullPointerException {
		Double res = null;
		try {
			res = Double.parseDouble(field.getText());
		} catch (Exception e) {
			field.setStyle("-fx-text-fill: red;");
			throw new NullPointerException();
		}

		return res;
	}

	private double setA() {
		return getDoubleFromString(_aField);
	}

	private double setB() {
		return getDoubleFromString(_bField);
	}

	private double setC() {
		return getDoubleFromString(_cField);
	}

	@FXML
	private void setDefaultStyleEvent(Event a) {
		TextField t = (TextField) a.getSource();
		t.setStyle("-fx-text-fill: black;");
	}

	@FXML
	private void cleanEvent() {
		_qe.setData(0.0, 0.0, 0.0);
		_aField.clear();
		_bField.clear();
		_cField.clear();
		_result.getEngine().loadContent("<b>clear</b>");
		_aField.requestFocus();
	}
}
