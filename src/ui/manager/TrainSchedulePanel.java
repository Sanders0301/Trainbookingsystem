package ui.manager;


import javax.swing.*;

import bean.Train;
import bean.TrainSchedule;
import dao.MySQLManager;
import dao.DBManager;
import ui.base.ListTableModel;
import ui.base.ModelPanel;
import ui.widget.XDialog;
import utils.Constants;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TrainSchedulePanel extends ModelPanel {

    private List<TrainSchedule> schedules;

    private void fetchAll() {
       // schedules = MySQLManager.getInstance().dao().getTrainScheduleList(100, 0);
        schedules = DBManager.getInstance().dao().getTrainScheduleList(100, 0);
    }

    @Override
    public ListTableModel getTableModel() {
        if (schedules == null) fetchAll();
        return new ListTableModel<TrainSchedule>(Constants.ColumnName.SCHEDULE, schedules){
            @Override
            public Object getValueAt(int row, int column) {
                TrainSchedule schedule = schedules.get(row);
                switch (column){
                    case 0:
                        return schedule.getScheId();
                    case 1:
                        return schedule.getDepartTime();
                    case 2:
                        return schedule.getPresellTime();
                    case 3:
                        return schedule.getSpeed();
                    case 4:
                        return schedule.getTrain().getTrainId();
                }
                return null;
            }
        };
    }

    @Override
    public void onSearch(String key) {
        EventQueue.invokeLater(() -> {
            if (key.equals("")) {
                fetchAll();
            } else {
              //  schedules = MySQLManager.getInstance().dao().searchTrainSchedule(key);
	        schedules = DBManager.getInstance().dao().searchTrainSchedule(key);
            }
            refresh();
        });
    }

    @Override
    public void onInsert() {
        new XDialog() {
            @Override
            protected void initComponents() {
                
                String[] columns = Constants.ColumnName.SCHEDULE;
                addField(columns[1], "");
                addField(columns[2], "");
                addField(columns[3], "");
                addField(columns[4], "");
            }

            @Override
            protected void onOK() {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    TrainSchedule schedule = new TrainSchedule();
                    schedule.setDepartTime(format.parse(field(0)));
                    schedule.setPresellTime(format.parse(field(1)));
                    schedule.setSpeed(Float.parseFloat((field(2))));
                    Train train = new Train();
                    train.setTrainId(field(3));
                    schedule.setTrain(train);
                    EventQueue.invokeLater(() -> {
                      //  MySQLManager.getInstance().dao().insertTrainSchedule(schedule);
		        DBManager.getInstance().dao().insertTrainSchedule(schedule);
                        fetchAll();
                        refresh();
                    });
                    super.onOK();
                } catch (ParseException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Date format error!Please follow Year-Month-Day Hour:Mins:Seconds input");
                }
            }
        }.popup("Add ticket office");
    }

    @Override
    public void onDelete(int[] selectedRows) {
        if (selectedRows.length <= 0) return;
        EventQueue.invokeLater(() -> {
            for (int row : selectedRows) {
                TrainSchedule schedule = schedules.get(row);
               // MySQLManager.getInstance().dao().deleteTrainSchedule(schedule);
		 DBManager.getInstance().dao().deleteTrainSchedule(schedule);
            }
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                schedules.remove(i);
            }
            refresh();
        });
    }

    @Override
    public void onUpdate(int selectedRow) {
        if (selectedRow < 0) return;
        TrainSchedule schedule = schedules.get(selectedRow);
        new XDialog() {
            @Override
            protected void initComponents() {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                
                String[] columns = Constants.ColumnName.SCHEDULE;
                addLabel(columns[0], String.valueOf(schedule.getScheId()));
                addField(columns[1], format.format(schedule.getDepartTime()));
                addField(columns[2], format.format(schedule.getPresellTime()));
                addFloatField(columns[3], schedule.getSpeed());
                addField(columns[4], schedule.getTrain().getTrainId());
            }

            @Override
            protected void onOK() {
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    schedule.setDepartTime(format.parse(field(1)));
                    schedule.setPresellTime(format.parse(field(2)));
                    schedule.setSpeed(fieldFloat(3));
                    Train train = new Train();
                    train.setTrainId(field(4));
                    schedule.setTrain(train);
                    EventQueue.invokeLater(() -> {
                      //  MySQLManager.getInstance().dao().updateTrainSchedule(schedule);
		       DBManager.getInstance().dao().updateTrainSchedule(schedule);
                        refresh();
                        super.onOK();
                    });
                } catch (ParseException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Date format error!�Please follow Year-Month-Day Hour:Mins:Seconds input");
                }
            }
        }.popup("Update ticket office");
    }
}