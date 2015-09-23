package test.utils;

import test.view.WVController;


public class Bridge {
	public Double _long;
	public Double _lat;
	private WVController _wvController;
	
	public void test(final Object lon, final Object lat) {
		_long = Double.valueOf(lon.toString());
		_lat = Double.valueOf(lat.toString());
		System.out.println("long: " + _long + " lat: " + _lat);
		_wvController.onClick(_long, _lat);
	}

	public void setWvController(final WVController wvController) {
		_wvController = wvController;
	}
}
