package dao;

import bean.Station;
import javax.swing.table.TableModel;
import java.util.List;

public interface StationDAO {
    void insertStation(Station station);
    void deleteStation(Station station);
    void deleteStation(int station_id);
    void updateStation(Station station);

    List<Station> getAllStations();
}
