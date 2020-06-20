package ui.manager;


import java.awt.*;
import java.util.List;

import bean.Manager;
import dao.MySQLManager;
import dao.DBManager;
import ui.base.ListTableModel;
import ui.base.ModelPanel;
import ui.widget.XDialog;
import utils.Constants;

public class ManagerPanel extends ModelPanel {
    private List<Manager> managers;

    private void fetchAll(){
      //  managers = MySQLManager.getInstance().dao().getAllManagers();
        managers = DBManager.getInstance().dao().getAllManagers();
    }

    @Override
    public ListTableModel getTableModel() {
        if(managers == null) fetchAll();
        return new ListTableModel<Manager>(Constants.ColumnName.MANAGER, managers) {
            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Manager manager = managers.get(rowIndex);
                switch (columnIndex){
                    case 0:
                        return manager.getManagerId();
                    case 1:
                        return manager.getPointId();
                    case 2:
                        return manager.getUsername();
                    case 3:
                        return manager.getPassword();
                    case 4:
                        return manager.getName();
                    case 5:
                        return manager.getSexString();
                    case 6:
                        return manager.getManagerTypeString();
                }
                return null;
            }
        };
    }

    @Override
    public float[] getColumnWeight(int columnCount) {
        return new float[]{ 1, 1, 2, 2, 2, 1, 1 };
    }

    @Override
    public void onSearch(String key) {
        EventQueue.invokeLater(() -> {
            if (key.equals("")) {
                fetchAll();
            } else {
               // managers = MySQLManager.getInstance().dao().searchManagers(key);
	        managers = DBManager.getInstance().dao().searchManagers(key);
            }
            refresh();
        });
    }

    @Override
    public void onInsert() {
        new XDialog() {
            @Override
            protected void initComponents() {
                
                String[] columns = Constants.ColumnName.MANAGER;
                addNumberField(columns[1], 0);
                addField(columns[2], "");
                addField(columns[3], "");
                addField(columns[4], "");
                addComboBox(columns[5], "Female", "Male");
                addComboBox(columns[6], "admin", "Conductor");
            }
            @Override
            protected void onOK() {
                Manager manager = new Manager();
                manager.setPointId(fieldInt(0));
                manager.setUsername(field(1));
                manager.setPassword(field(2));
                manager.setName(field(3));
                manager.setSex(option(4));
                manager.setManagerType(option(5) + 1);
                EventQueue.invokeLater(() -> {
                  //  MySQLManager.getInstance().dao().insertManager(manager);
		    DBManager.getInstance().dao().insertManager(manager);
                    fetchAll();
                    refresh();
                    super.onOK();
                });
            }
        }.popup("Add Admin");
    }

    @Override
    public void onDelete(int[] selectedRows) {
        if(selectedRows.length <= 0) return;
        EventQueue.invokeLater(() -> {
            for (int row : selectedRows) {
                Manager manager = managers.get(row);
               // MySQLManager.getInstance().dao().deleteManager(manager);
	        DBManager.getInstance().dao().deleteManager(manager);
            }
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                managers.remove(i);
            }
            refresh();
        });
    }

    @Override
    public void onUpdate(int selectedRow) {
        if(selectedRow < 0) return;
        Manager manager = managers.get(selectedRow);
        new XDialog() {
            @Override
            protected void initComponents() {
               
                String[] columns = Constants.ColumnName.MANAGER;
                addLabel(columns[0], String.valueOf(manager.getManagerId()));
                addNumberField(columns[1], manager.getPointId());
                addField(columns[2], manager.getUsername());
                addField(columns[3], manager.getPassword());
                addField(columns[4], manager.getName());
                addComboBox(columns[5], "Female", "Male").setSelectedIndex(manager.getSex());
                addComboBox(columns[6], "Admin", "Conductor").setSelectedIndex(manager.getManagerType() - 1);
            }

            @Override
            protected void onOK() {
                manager.setPointId(fieldInt(1));
                manager.setUsername(field(2));
                manager.setPassword(field(3));
                manager.setName(field(4));
                manager.setSex(option(5));
                manager.setManagerType(option(6) + 1);
                EventQueue.invokeLater(() -> {
                  //  MySQLManager.getInstance().dao().updateManager(manager);
		    DBManager.getInstance().dao().updateManager(manager);
                    refresh();
                    super.onOK();
                });
            }
        }.popup("Update admin");
    }
}