package test.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SQL {

	private static final String url = "jdbc:mysql://localhost:3306/experiment";
	private static final String user = "root";
	private static final String password = "qwe123";
	private static Connection con;
	private static Statement stmt;
	private static ResultSet rs;
	private static SQL _sql;
	public final static String _REQ = "select essid, bssid, avg(signal_dbm), lat, lon"
			+ "	from rawdata"
			+ " where(lat > (%1$f - %3$f)"
			+ " and   lat < (%1$f + %3$f / 2))"
			+ " and  (lon > (%2$f - %3$f)"
			+ " and   lon < (%2$f + %3$f / 2))"
			+ " group by bssid"
			+ " order by avg(signal_dbm)";

	// lat %2$f
	// long %3$f
	private SQL() {
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static SQL get() {
		if (_sql == null) {
			_sql = new SQL();
		}
		return _sql;
	}

	public void clear() {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			con.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public LinkedList<Map<String, Object>> request(final String r) {
		LinkedList<Map<String, Object>> res = new LinkedList<>();
		try {
			rs = stmt.executeQuery(r);
			int cc = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				Map<String, Object> columns = new HashMap<String, Object>();
				for (int i = 1; i <= cc; i++) {
					columns.put(rs.getMetaData().getColumnLabel(i),
							rs.getObject(i));
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

	public static void main(final String[] args) {
		System.out.println(Arrays.toString(SQL.get()
				.request(String.format(_REQ, 60.006960804, 30.378349636, 0.001000))
				.toArray()));
		SQL.get().close();
	}
}
