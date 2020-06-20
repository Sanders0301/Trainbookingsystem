 package dao;

import bean.Customer;
import bean.Manager;
import javax.swing.table.TableModel;
import java.util.List;

public interface ManagerDAO {
    void insertManager(Manager manager);
    void deleteManager(Manager manager);
    void updateManager(Manager manager);
    Manager getManagerById(String manager_id);
    List<Manager> getAllManagers();
    List<Manager> searchManagers(String key);

    Manager login(String username, String password);
}
