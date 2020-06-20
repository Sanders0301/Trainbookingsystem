package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import dao.MySQLManager;
import dao.DBManager;

public class TrainSchedule implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6605309205055557484L;
	private int sche_id;
    private Date depart_time;
    private Date presell_time;
    private float speed;
    private Train train;

    public int getScheId() {
        return sche_id;
    }

    public void setScheId(int sche_id) {
        this.sche_id = sche_id;
    }

    public Date getDepartTime() {
        return depart_time;
    }

    public void setDepartTime(Date depart_time) {
        this.depart_time = depart_time;
    }

    public Date getPresellTime() {
        return presell_time;
    }

    public void setPresellTime(Date presell_time) {
        this.presell_time = presell_time;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    private String getPassTime(long time) {
        time /= 1000 * 60;
        String res = (time % 60) + "mins";
        time /= 60;
        if (time > 0) {
            res = (time % 24) + "hour" + res;
            time /= 24;
        }
        if (time > 0) {
            res = time + "day" + res;
        }
        return res;
    }
    /**
     * Calculate additional travel information based on the starting and ending stations: such as the number and price of seats available, mileage and time
     * @param departStationName
     * @param arriveStationName
     * @param isStudent
     * @return Additional itinerary information
     */

    
    public Extra getExtra(String departStationName, String arriveStationName, boolean isStudent) {
        Extra e = new Extra();
        // passby extra
        List<Passby> passbyList = train.getTrainPassby();
        long departTime = 0, arriveTime = 0;
        long time = depart_time.getTime();
        for (Passby p : passbyList) {
            time += p.getStayTime() * 60 * 1000; // sec to ms
            if (p.getDepartStation().getStationName().startsWith(departStationName)) {
                e.departStationOrder = p.getStationOrder();
                e.departStationName = p.getDepartStation().getStationName();
                departTime = time;
            }
            time += p.getDistance() / speed * 60 * 60 * 1000; // hour to ms
            if (p.getArriveStation().getStationName().startsWith(arriveStationName)) {
                e.arriveStationOrder = p.getStationOrder() + 1;
                e.arriveStationName = p.getArriveStation().getStationName();
                arriveTime = time;
            }
        }
        e.departTime = new Date(departTime);
        e.arriveTime = new Date(arriveTime);
        e.trainId = train.getTrainId();
        e.passTime = getPassTime(arriveTime - departTime);
        // seat extra
	
        List<SeatAggregate> saList = DBManager.getInstance().dao()
                .getSeatAggregateInSchedule(this, e.departStationOrder, e.arriveStationOrder);
        for (SeatAggregate sa : saList) {
            if ("Hard sleeper".equals(sa.getSeatType())) {
                e.hardSeatNum = sa.getSeatNum();
                e.hardSeatMoney = train.calcMoneyBetween(e.departStationOrder, e.arriveStationOrder, sa.getSeatType(), isStudent);
            } else if ("Hard sleeper".equals(sa.getSeatType())) {
                e.hardBerthNum = sa.getSeatNum();
                e.hardBerthMoney = train.calcMoneyBetween(e.departStationOrder, e.arriveStationOrder, sa.getSeatType(), isStudent);
            } else if ("Soft sleeper".equals(sa.getSeatType())) {
                e.softBerthNum = sa.getSeatNum();
                e.softBerthMoney = train.calcMoneyBetween(e.departStationOrder, e.arriveStationOrder, sa.getSeatType(), isStudent);
            } else if ("no more seat".equals(sa.getSeatType())) {
                e.noSeatNum = sa.getSeatNum();
                e.noSeatMoney = train.calcMoneyBetween(e.departStationOrder, e.arriveStationOrder, sa.getSeatType(), isStudent);
            }
        }
        return e;
    }

    public class Extra {
        public Date departTime;
        public Date arriveTime;
        public String departStationName;
        public String arriveStationName;
        public int departStationOrder;
        public int arriveStationOrder;
        public String trainId;
        public String passTime;
        public int hardSeatNum, hardBerthNum, softBerthNum, noSeatNum;
        public float hardSeatMoney, hardBerthMoney, softBerthMoney, noSeatMoney;
    }
}
