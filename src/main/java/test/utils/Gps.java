package test.utils;

import java.util.concurrent.TimeUnit;

import de.taimos.gpsd4java.api.ObjectListener;
import de.taimos.gpsd4java.backend.GPSdEndpoint;
import de.taimos.gpsd4java.backend.ResultParser;
import de.taimos.gpsd4java.types.TPVObject;

public class Gps implements Runnable {
	private static final String _LOCALHOST = "localhost";
	private int _port = 2947;
	private Double _lon = 0.0;
	private Double _lat = 0.0;
	private GPSdEndpoint _ep;

	public Double getLon() {
		synchronized (_lon) {
			return _lon;
		}
	}

	public void setLon(final Double lon) {
		if (lon.isNaN())
			return;
		synchronized (_lon) {
			_lon = lon;
		}
	}

	public void setLat(final Double lat) {
		if (lat.isNaN())
			return;
		synchronized (_lat) {
			_lat = lat;
		}
	}

	public Double getLat() {
		synchronized (_lat) {
			return _lat;
		}
	}

	@Override
	public void run() {
		try {
			_ep = new GPSdEndpoint(_LOCALHOST, _port, new ResultParser());
			_ep.addListener(new ObjectListener() {
				@Override
				public void handleTPV(final TPVObject tpv) {
					setLat(tpv.getLatitude());
					setLon(tpv.getLongitude());
				}
			});
			_ep.start();
			_ep.watch(true, true);

			while (true) {
				TimeUnit.SECONDS.sleep(1);
				System.out.println("long: " + getLon() + " lat: " + getLat());
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			_ep.stop();
		}
	}

}
