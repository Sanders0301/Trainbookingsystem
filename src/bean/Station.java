package bean;

import java.io.Serializable;

public class Station implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5908844098490542228L;
	private int station_id;
    private String station_name;

    public int getStationId() {
        return station_id;
    }

    public void setStationId(int station_id) {
        this.station_id = station_id;
    }

    public String getStationName() {
        return station_name;
    }

    public void setStationName(String station_name) {
        this.station_name = station_name;
    }
}