package ui.manager;


import javax.swing.*;

import bean.Customer;
import bean.Passby;
import bean.Seat;
import bean.SeatGroup;
import bean.TicketPoint;
import bean.Train;
import bean.TrainOrder;
import bean.TrainSchedule;
import dao.DBManager;
import dao.MySQLManager;
import ui.base.ListTableModel;
import ui.base.ModelPanel;
import ui.widget.XDialog;
import utils.Constants;
import utils.StringUtils;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

public class TrainOrderPanel extends ModelPanel {

    protected List<TrainOrder> orders;

    private void fetchAll() {
	    
        // orders = MySQLManager.getInstance().dao().getTrainOrderList(100, 0);
	orders = DBManager.getInstance().dao().getTrainOrderList(100, 0);
    }

    @Override
    public ListTableModel getTableModel() {
        if (orders == null) fetchAll();
        //"Order ID"," Customer name ", "train number "," departure station ", "Arrival station "," Amount ", "Order Status"
        return new ListTableModel<TrainOrder>(Constants.ColumnName.ORDER, orders){
            @Override
            public Object getValueAt(int row, int column) {
                TrainOrder order = orders.get(row);
                switch (column){
                    case 0:
                        return order.getOrderId();
                    case 1:
                        return order.getBuyer().getName();
                    case 2:
                        return order.getTrain().getTrainId();
                    case 3:
                        return order.getDepartStation().getStationName();
                    case 4:
                        return order.getArriveStation().getStationName();
                    case 5:
                        return order.getMoney();
                    case 6:
                        return order.getOrderStateString();
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
               // orders = MySQLManager.getInstance().dao().searchTrainOrders(key);
	        orders = DBManager.getInstance().dao().searchTrainOrders(key);
            }
            refresh();
        });
    }


    private JComboBox carriageBox, seatNumBox, seatTypeBox;
    private JComboBox departBox, arriveBox, stuBox;
    private JLabel moneyField;
    private Train train = new Train();

    @SuppressWarnings("unchecked")
    private boolean checkTrainId(String train_id) {
        if (StringUtils.empty(train_id)) {
            return false;
        }
        
        if (train != null && train_id.equals(train.getTrainId())) {
            return true;
        }
	//The state of the selection box is cleared when checked, so do not double-check
       // train = MySQLManager.getInstance().dao().getTrainById(train_id);
        train = DBManager.getInstance().dao().getTrainById(train_id);
        carriageBox.removeAllItems();
        departBox.removeAllItems();
        arriveBox.removeAllItems();
        if (train == null) {
            JOptionPane.showMessageDialog(null, train_id + "Is wrong");
            carriageBox.setEnabled(false);
            seatNumBox.setEnabled(false);
            departBox.setEnabled(false);
            arriveBox.setEnabled(false);
            return false;
        }
        carriageBox.setEnabled(true);
        seatNumBox.setEnabled(true);
        departBox.setEnabled(true);
        arriveBox.setEnabled(true);
        // load carriage box
        for (SeatGroup sg : train.getSeats()) {
            carriageBox.addItem(sg.getCarriageNum());
        }
        // set to default carriage and trigger listener to fill seatComboBox
        selectCarriage(1, 1);
        // load station
        for (Passby p : train.getTrainPassby()) {
            departBox.addItem(p.getDepartStation().getStationName());
            arriveBox.addItem(p.getArriveStation().getStationName());
        }
        return true;
    }

    private void reCalcMoney() {
        moneyField.setText(String.valueOf(
                train.calcMoneyBetween(
                        departBox.getSelectedIndex() + 1,
                        arriveBox.getSelectedIndex() + 2,
                        (String) seatTypeBox.getSelectedItem(),
                        stuBox.getSelectedIndex() == 1)
        ));
    }

    private void addListener() {
        carriageBox.addItemListener(e -> {
            if (ItemEvent.SELECTED == e.getStateChange()) {
                seatNumBox.removeAllItems();
                int carriageNum = (int) e.getItem();
                selectCarriage(carriageNum, 1);
            }
        });
        ItemListener reCalcListener = e -> {
            if (ItemEvent.SELECTED == e.getStateChange()) {
                reCalcMoney();
            }
        };
        departBox.addItemListener(reCalcListener);
        arriveBox.addItemListener(reCalcListener);
        stuBox.addItemListener(reCalcListener);
    }

    @SuppressWarnings("unchecked")
    private void selectCarriage(int carriageNum, int seatNum) {
        carriageBox.setSelectedItem(carriageNum);
        // fill seat combo box
        seatNumBox.removeAllItems();
        for (SeatGroup sg : train.getSeats()) {
            if (sg.getCarriageNum() == carriageNum) {
                // set seat num in this carriage
                int seatCnt = sg.getSeatNum();
                for (int i = 1; i <= seatCnt; i++) {
                    seatNumBox.addItem(i);
                }
                // set seat type in this carriage
                seatTypeBox.setSelectedItem(sg.getSeatType());
                break;
            }
        }
        seatNumBox.setSelectedItem(seatNum);
        reCalcMoney();
    }

    @Override
    public void onInsert() {
        new XDialog() {

            @Override
            protected void initComponents() {
                //Ticket booth ID "/ /", "user ID", "train trip ID", "service", "car", "seat number", "seat type", "originating station", "station", "student", "amount", "order status"
                String[] columns = Constants.ColumnName.ORDER_INSERT;
                addNumberField(columns[0], null); 
                addField(columns[1], ""); 
                addNumberField(columns[2], null); 
                addField(columns[3], "").addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        checkTrainId(field(3));
                    }
                }); 
                (carriageBox = addComboBox(columns[4])).setEnabled(false); 
                (seatNumBox = addComboBox(columns[5])).setEnabled(false); 
                (seatTypeBox = addComboBox(columns[6], "Hard seat", "Hard sleeper", "Soft sleeper", "no more seat")).setEnabled(false); // ��λ����
                (departBox = addComboBox(columns[7])).setEnabled(false); 
                (arriveBox = addComboBox(columns[8])).setEnabled(false); 
                stuBox = addComboBox(columns[9], "Adult ticket", "Student ticket"); 
                moneyField = addLabel(columns[10], String.valueOf(0.0f)); 
                addLabel(columns[11], "Reserve"); 
                addListener();
                train = null; // reset train avoid use old dialog data
            }

            @Override
            protected void onOK() {
                EventQueue.invokeLater(() -> {
                    TrainOrder order = new TrainOrder();
                   // TicketPoint point = MySQLManager.getInstance().dao().getTicketPointById(fieldInt(0));
		    TicketPoint point = DBManager.getInstance().dao().getTicketPointById(fieldInt(0));
                    if (point == null) {
                        JOptionPane.showMessageDialog(null, "The ticket counter does not exist");
                        return;
                    }
                    order.setTicketPoint(point);
                  //  Customer buyer = MySQLManager.getInstance().dao().getCustomerByIdNum(field(1));
		    Customer buyer = DBManager.getInstance().dao().getCustomerByIdNum(field(1));
                    if (buyer == null) {
                        JOptionPane.showMessageDialog(null, "The user does not exist");
                        return;
                    }
                    order.setBuyer(buyer);
                    checkTrainId(field(3));
                    order.setTrain(train);
                  //  TrainSchedule schedule = MySQLManager.getInstance().dao().getTrainScheduleById(fieldInt(2));
		    TrainSchedule schedule = DBManager.getInstance().dao().getTrainScheduleById(fieldInt(2));
                    if (schedule == null) {
                        JOptionPane.showMessageDialog(null, "The train journey does not exist");
                        return;
                    }
                    order.setTrainSchedule(schedule);
                   // Seat seat = MySQLManager.getInstance().dao().getSeat(train.getTrainId(), (int) optionValue(4), (int) optionValue(5));
		    Seat seat = DBManager.getInstance().dao().getSeat(train.getTrainId(), (int) optionValue(4), (int) optionValue(5));
                    seat.setTrain(train);
                    order.setSeat(seat);
                    if (fieldFloat(10) < 0) {
                        JOptionPane.showMessageDialog(null, "��");
                        return;
                    }
                   // order.setDepartStation(MySQLManager.getInstance().dao().getStationByName((String) optionValue(7)));
                  //  order.setArriveStation(MySQLManager.getInstance().dao().getStationByName((String) optionValue(8)));
		   order.setDepartStation(DBManager.getInstance().dao().getStationByName((String) optionValue(7)));
                    order.setArriveStation(DBManager.getInstance().dao().getStationByName((String) optionValue(8)));
                    order.setDepartStationRrder(option(7) + 1);
                    order.setArriveStationOrder(option(8) + 2);
                    order.setStudentTicket(option(9) == 1);
                    order.setMoney(Float.parseFloat(field(10)));
                    order.setOrderState(TrainOrder.STATE_RESERVED);
                   //  MySQLManager.getInstance().dao().insertTrainOrder(order);
		     DBManager.getInstance().dao().insertTrainOrder(order);
                    fetchAll();
                    refresh();
                    super.onOK();
                });
            }
        }.popup("Update Order");
    }

    @Override
    public void onDelete(int[] selectedRows) {
        if (selectedRows.length <= 0) return;
        EventQueue.invokeLater(() -> {
            for (int row : selectedRows) {
                TrainOrder order = orders.get(row);
              //  MySQLManager.getInstance().dao().deleteTrainOrder(order);
	        DBManager.getInstance().dao().deleteTrainOrder(order);
            }
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                orders.remove(i);
            }
            refresh();
        });
    }

    @Override
    public void onUpdate(int selectedRow) {
        if (selectedRow < 0) return;
        TrainOrder order = orders.get(selectedRow);
        new XDialog() {
            @Override
            protected void initComponents() {
                //"Ticket office ID", "user ID", "train trip ID", "service", "car", "seat number", "seat type", "originating station", "station", "student", "amount", "order status"
                String[] columns = Constants.ColumnName.ORDER_INSERT;
//                addLabel("Order ID", String.valueOf(order.getOrderId())); 

                addNumberField(columns[0], order.getTicketPoint().getPointId()); //Ticket office ID
                addField(columns[1], order.getBuyer().getIdNum()); 
                addNumberField(columns[2], order.getTrainSchedule().getScheId()); 
                addField(columns[3], order.getTrain().getTrainId()).addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        checkTrainId(field(3));
                    }
                }); 

                (carriageBox = addComboBox(columns[4])).setEnabled(false); 
                (seatNumBox = addComboBox(columns[5])).setEnabled(false); 
                (seatTypeBox = addComboBox(columns[6], "Hard seat", "Hard sleeper", "Soft sleeper", "no more seat")).setEnabled(false); 
                (departBox = addComboBox(columns[7])).setEnabled(false); 
                (arriveBox = addComboBox(columns[8])).setEnabled(false); 
                stuBox = addComboBox(columns[9], "Adult ticket", "Student ticket"); 
                moneyField = addLabel(columns[10], String.valueOf(order.getMoney())); 
                addLabel(columns[11], order.getOrderStateString()); 

                addListener();
                train = null; // reset train avoid use old dialog data
                checkTrainId(order.getTrain().getTrainId());
                departBox.setSelectedItem(order.getDepartStation().getStationName());
                arriveBox.setSelectedItem(order.getArriveStation().getStationName());
                stuBox.setSelectedIndex(order.isStudentTicket() ? 1 : 0);
              //  order.setSeat(MySQLManager.getInstance().dao().getSeatById(order.getSeat().getSeatId()));
	        order.setSeat(DBManager.getInstance().dao().getSeatById(order.getSeat().getSeatId()));
                selectCarriage(order.getSeat().getCarriageNum(), order.getSeat().getSeatNum());
            }

            @Override
            protected void onOK() {
                EventQueue.invokeLater(() -> {
                   // TicketPoint point = MySQLManager.getInstance().dao().getTicketPointById(fieldInt(0));
		   TicketPoint point = DBManager.getInstance().dao().getTicketPointById(fieldInt(0));
                    if (point == null) {
                        JOptionPane.showMessageDialog(null, "The ticket counter does not exist");
                        return;
                    }
                    order.setTicketPoint(point);
                  // Customer buyer = MySQLManager.getInstance().dao().getCustomerByIdNum(field(1));
		   Customer buyer = DBManager.getInstance().dao().getCustomerByIdNum(field(1));
                    if (buyer == null) {
                        JOptionPane.showMessageDialog(null, "The user does not exist");
                        return;
                    }
                    order.setBuyer(buyer);
                    checkTrainId(field(3));
                    order.setTrain(train);
                  //  TrainSchedule schedule = MySQLManager.getInstance().dao().getTrainScheduleById(fieldInt(2));
		    TrainSchedule schedule = DBManager.getInstance().dao().getTrainScheduleById(fieldInt(2));
                    if (schedule == null) {
                        JOptionPane.showMessageDialog(null, "The train journey does not exist");
                        return;
                    }
                    order.setTrainSchedule(schedule);
                   // Seat seat = MySQLManager.getInstance().dao().getSeat(train.getTrainId(), (int) optionValue(4), (int) optionValue(5));
		    Seat seat = DBManager.getInstance().dao().getSeat(train.getTrainId(), (int) optionValue(4), (int) optionValue(5));
                    seat.setTrain(train);
                    order.setSeat(seat);
                    if (fieldFloat(10) < 0) {
                        JOptionPane.showMessageDialog(null, "��");
                        return;
                    }
                   // order.setDepartStation(MySQLManager.getInstance().dao().getStationByName((String) optionValue(7)));
                  //  order.setArriveStation(MySQLManager.getInstance().dao().getStationByName((String) optionValue(8)));
		   order.setDepartStation(DBManager.getInstance().dao().getStationByName((String) optionValue(7)));
                    order.setArriveStation(DBManager.getInstance().dao().getStationByName((String) optionValue(8)));
                    order.setDepartStationRrder(option(7) + 1);
                    order.setArriveStationOrder(option(8) + 2);
                    order.setStudentTicket(option(9) == 1);
                    order.setMoney(Float.parseFloat(field(10)));
                    order.setOrderState(TrainOrder.STATE_RESERVED);
                  //  MySQLManager.getInstance().dao().updateTrainOrder(order);
		    DBManager.getInstance().dao().updateTrainOrder(order);
                    refresh();
                    super.onOK();
                });
            }
        }.popup("Update date");
    }
}