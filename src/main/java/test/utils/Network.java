package test.utils;

import java.util.Map;

public class Network {
	private Integer id;
	private String bssid;
	private Long time_sec;
	private Long time_usec;
	private Double lat;
	private Double lon;
	private Double spd;
	private Double heading;
	private Double alt;
	private Double signal_dbm;
	private Integer noise_dbm;
	private String essid;
	
	public Network(final Integer id, final String bssid, final Long time_sec, final Long time_usec,
			final Double lat, final Double lon, final Double spd, final Double heading, final Double alt,
			final Double signal_dbm, final int noise_dbm, final String essid) {
		super();
		this.id = id;
		this.bssid = bssid;
		this.time_sec = time_sec;
		this.time_usec = time_usec;
		this.lat = lat;
		this.lon = lon;
		this.spd = spd;
		this.heading = heading;
		this.alt = alt;
		this.signal_dbm = signal_dbm;
		this.noise_dbm = noise_dbm;
		this.essid = essid;
	}

	public Network(final Map<String, Object> map) {
		essid = (String) map.get("essid");
		lon = Double.valueOf(map.get("lon").toString());
		lat = Double.valueOf(map.get("lat").toString());
	}

	public Integer getId() {
		return id;
	}
	public void setId(final Integer id) {
		this.id = id;
	}
	public String getBssid() {
		return bssid;
	}
	public void setBssid(final String bssid) {
		this.bssid = bssid;
	}
	public Long getTime_sec() {
		return time_sec;
	}
	public void setTime_sec(final Long time_sec) {
		this.time_sec = time_sec;
	}
	public Long getTime_usec() {
		return time_usec;
	}
	public void setTime_usec(final Long time_usec) {
		this.time_usec = time_usec;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(final Double lat) {
		this.lat = lat;
	}
	public Double getLon() {
		return lon;
	}
	public void setLon(final Double lon) {
		this.lon = lon;
	}
	public Double getSpd() {
		return spd;
	}
	public void setSpd(final Double spd) {
		this.spd = spd;
	}
	public Double getHeading() {
		return heading;
	}
	public void setHeading(final Double heading) {
		this.heading = heading;
	}
	public Double getAlt() {
		return alt;
	}
	public void setAlt(final Double alt) {
		this.alt = alt;
	}
	public Double getSignal_dbm() {
		return signal_dbm;
	}
	public void setSignal_dbm(final Double signal_dbm) {
		this.signal_dbm = signal_dbm;
	}
	public Integer getNoise_dbm() {
		return noise_dbm;
	}
	public void setNoise_dbm(final Integer noise_dbm) {
		this.noise_dbm = noise_dbm;
	}
	public String getEssid() {
		return essid;
	}
	public void setEssid(final String essid) {
		this.essid = essid;
	}
}
