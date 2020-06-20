package bean;

import java.io.Serializable;
import java.util.List;

public class Train implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7940566248413481232L;
	private String train_id;
    private String train_type;
    private List<Passby> train_passby;
    private List<SeatGroup> seats;

    public String getTrainId() {
        return train_id;
    }

    public void setTrainId(String train_id) {
        this.train_id = train_id;
    }

    public String getTrainType() {
        return train_type;
    }

    public void setTrainType(String train_type) {
        this.train_type = train_type;
    }

    public List<Passby> getTrainPassby() {
        return train_passby;
    }

    public void setTrainPassby(List<Passby> train_passby) {
        this.train_passby = train_passby;
    }

    public String getTrainPassbyString() {
        StringBuilder passby = new StringBuilder("|");
        if(train_passby != null){
            int i = 0;
            for(Passby p : train_passby){
                passby.append(p.getDepartStation().getStationName()).append('|');
                if (i == train_passby.size() - 1) {
                    passby.append(p.getArriveStation().getStationName()).append('|');
                }
                i++;
            }
        }
        return passby.toString();
    }

    public List<SeatGroup> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatGroup> seats) {
        this.seats = seats;
    }

    public float calcMoneyBetween(int departStationOrder, int arriveStationOrder, String seatType, boolean isStudent) {
        if (departStationOrder == 0 || departStationOrder >= arriveStationOrder || arriveStationOrder - 1 > train_passby.size()) {
            return -1;
        }
        float distance = 0;
        for (int i = departStationOrder; i < arriveStationOrder; i++) {
            distance += train_passby.get(i - 1).getDistance();
        }
        float baseDistance = (int) (distance - (((int) distance) % 20));
        if (baseDistance < 20) {
            baseDistance = 20;
        }
        float discount = isStudent ? 0.5f : 1.0f;
        float ratio = 0.06861f;
        if ("Hard seat".equals(seatType) || "no more seat".equals(seatType)) {
            ratio *= discount;
        } else if ("Hard sleeper".equals(seatType)) {
            ratio *= 0.8f + discount;
        } else if ("soft sleeper".equals(seatType)) {
            ratio *= 2.5f + discount;
        }
        float money = baseDistance * ratio;
        float insurance = money * 0.02f;
        float basicCost = 2.5f;
        money = Math.round(money + insurance + basicCost);
        return money;
    }
}