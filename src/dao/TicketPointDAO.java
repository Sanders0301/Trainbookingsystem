package dao;

import bean.TicketPoint;
import javax.swing.table.TableModel;
import java.util.List;

public interface TicketPointDAO {
    void insertTicketPoint(TicketPoint ticketPoint);
    void deleteTicketPoint(TicketPoint ticketPoint);
    void deleteTicketPoint(int point_id);
    void updateTicketPoint(TicketPoint ticketPoint);

    TicketPoint getTicketPointById(int point_id);
    List<TicketPoint> getAllTicketPoints();
    List<TicketPoint> searchTicketPoints(String key);
}