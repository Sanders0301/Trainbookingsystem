package dao;

import bean.TrainSchedule;
import java.util.Date;
import java.util.List;

public interface TrainScheduleDAO {
    void insertTrainSchedule(TrainSchedule trainSchedule);
    void deleteTrainSchedule(TrainSchedule trainSchedule);
    void deleteTrainSchedule(int train_schedule_id);
    void updateTrainSchedule(TrainSchedule trainSchedule);

    List<TrainSchedule> getTrainScheduleList(int limit, int skip);

    List<TrainSchedule> searchTrainSchedule(String depart_station, String arrive_station, Date depart_date, boolean isStudent);
}
