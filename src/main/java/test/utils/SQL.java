package test.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SQL {

	private static final String url = "jdbc:mysql://localhost:3306/experiment";
	private static final String user = "root";
	private static final String password = "qwe123";
	private static Connection _connection;
	private static Statement _statement;
	private static ResultSet _resultSet;
	private static SQL _instance;
	public final static String _REQ = "select essid, bssid, avg(signal_dbm), lat, lon"
			+ "	from rawdata"
			+ " where(lat > (%1$f - %3$f)"
			+ " and   lat < (%1$f + %3$f / 2))"
			+ " and  (lon > (%2$f - %3$f)"
			+ " and   lon < (%2$f + %3$f / 2))"
			+ " group by bssid"
			+ " order by avg(signal_dbm)";

	private SQL() {
		try {
			_connection = DriverManager.getConnection(url, user, password);
			_statement = _connection.createStatement();
		} catch (SQLException e) {
			_connection = null;
			_statement = null;
			e.printStackTrace();
		}
	}

	public static SQL get() {
		if (_instance == null) {
			_instance = new SQL();
		}
		return _instance;
	}

	public void clear() {
		try {
			_resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			_connection.close();
			_statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public LinkedList<Map<String, Object>> request(final String r) {
		LinkedList<Map<String, Object>> res = new LinkedList<>();
		try {
			_resultSet = _statement.executeQuery(r);
			int cc = _resultSet.getMetaData().getColumnCount();
			while (_resultSet.next()) {
				Map<String, Object> columns = new HashMap<String, Object>();
				for (int i = 1; i <= cc; i++) {
					columns.put(_resultSet.getMetaData().getColumnLabel(i),
							_resultSet.getObject(i));
				}
				res.add(columns);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			clear();
		}
		return res;
	}

	public LinkedList<Map<String, Object>> requestNetwork(final Double lat,
			final Double lon, final Double area) {
		return request(String.format(_REQ, lat, lon, area));
	}
}
