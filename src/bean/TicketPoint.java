package bean;

import java.io.Serializable;

public class TicketPoint implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4855819587272464117L;
	private int point_id;
    private String username;
    private String address;
    private String open_time;

    public int getPointId() {
        return point_id;
    }

    public void setPointId(int point_id) {
        this.point_id = point_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenTime() {
        return open_time;
    }

    public void setOpenTime(String open_time) {
        this.open_time = open_time;
    }
}