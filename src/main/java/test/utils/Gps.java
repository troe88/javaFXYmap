package test.utils;

import java.util.concurrent.TimeUnit;

import de.taimos.gpsd4java.api.ObjectListener;
import de.taimos.gpsd4java.backend.GPSdEndpoint;
import de.taimos.gpsd4java.backend.ResultParser;
import de.taimos.gpsd4java.types.TPVObject;

public class Gps implements Runnable {
	private Double _lon = 0.0;
	private Double _lat = 0.0;
	private GPSdEndpoint _ep;

	public Double getLon() {
		synchronized (_lon) {
			return _lon;
		}
	}

	public void setLon(final Double lon) {
		if(lon.isNaN())
			return;
		synchronized (_lon) {
			_lon = lon;
		}
	}

	public void setLat(final Double lat) {
		if(lat.isNaN())
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
			String host = "localhost";
			int port = 2947;
			_ep = new GPSdEndpoint(host, port, new ResultParser());

			_ep.addListener(new ObjectListener() {
				@Override
				public void handleTPV(final TPVObject tpv) {
					System.out.println("111");
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
