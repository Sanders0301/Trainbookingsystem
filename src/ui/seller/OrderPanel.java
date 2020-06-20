package ui.seller;


import javax.swing.*;
import javax.swing.plaf.BorderUIResource;

import bean.Customer;
import bean.Seat;
import bean.Station;
import bean.TicketPoint;
import bean.Train;
import bean.TrainOrder;
import bean.TrainSchedule;
import dao.MySQLManager;
import dao.DBManager;
import ui.widget.Measurable;
import ui.widget.XDialog;
import ui.widget.XPanel;
import ui.widget.XTextField;
import utils.Constants;
import utils.StringUtils;
import utils.WidgetUtils;

import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import org.jdesktop.swingx.JXImagePanel;
import org.jdesktop.swingx.JXImageView;

import Controller.TrainTicketClient;
import Controller.TrainTicketServer;
import Thread.ServerConnectSocketThread;

@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal"})
public class OrderPanel extends JPanel {
    private JTextField departStationText;
    private JTextField arriveStationText;
    private JTextField departDateText;
    private JCheckBox stuBox;
    private JButton queryBtn;
    private List<ScheduleItem> scheduleItems = new ArrayList<>(); // view

    private JScrollPane schedulesScroll;
    private XPanel schedulesPanel;
    private List<TrainSchedule> schedules = new ArrayList<>(); // model

    private int leftMargin = 12;
    private int topMargin = 12;
    private int labelWidth = 100;
    private int labelHeight = 30;
    private int fieldWidth = 200;
    private int fieldHeight = 30;
    private int padding = 10;
    private int btnWidth = 100, btnHeight = 30;

    private int panelBaseHeight = 200;

    public OrderPanel() {
        initComponents();
        departStationText.setText("Auckland");
        arriveStationText.setText("Christchurch");
        departDateText.setText("2019-12-20");
    }

    private void initComponents() {
        setLayout(null);
        add(departStationText = new XTextField("Starting station"));
        add(arriveStationText = new XTextField("Terminus"));
        add(departDateText = new XTextField("Date of departure yyyy-mm-dd"));
        add(stuBox = new JCheckBox("Student Adult"));
        add(queryBtn = new JButton("Search"));
        add(schedulesScroll = new JScrollPane(schedulesPanel = new XPanel()));
        queryBtn.addActionListener(e -> onQueryClicked());

        initLayout();
    }

    private void initLayout() {
        int x = leftMargin, y = topMargin;
        x += padding;
        y += padding;
        departStationText.setBounds(x, y, fieldWidth, fieldHeight);
        x += padding + fieldWidth + padding;
        arriveStationText.setBounds(x, y, fieldWidth, fieldHeight);
        x += padding + fieldWidth + padding;
        departDateText.setBounds(x, y, fieldWidth, fieldHeight);
        x += padding + fieldWidth + padding;
        stuBox.setBounds(x, y, btnWidth, btnHeight);
        x += padding + btnWidth + padding;
        queryBtn.setBounds(x, y, btnWidth, btnHeight);
        y += fieldHeight + padding;
        panelBaseHeight = y;
    }

    private void relayout() {
        int x = leftMargin + padding, y = panelBaseHeight + padding;
        for (ScheduleItem item : scheduleItems) {
            add(item);
            item.setBounds(x, y, item.itemWidth, item.itemHeight);
            y += padding + item.itemHeight + padding;
        }
        validate();
        updateUI();
    }

    public void onQueryClicked() {
        String departStation = departStationText.getText().trim();
        String arriveStation = arriveStationText.getText().trim();
        Date departDate;
        boolean isStudent = stuBox.isSelected();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            departDate = format.parse(departDateText.getText().trim());
        } catch (ParseException e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "Incorrect date format!�Please follow Year-Month-Day input");
            return;
        }
        if (StringUtils.empty(departStation)) {
            JOptionPane.showMessageDialog(null, "Please enter starting station");
        } else if (StringUtils.empty(arriveStation)) {
            JOptionPane.showMessageDialog(null, "Please enter Terminus");
        } else {
            onQuery(departStation, arriveStation, departDate, isStudent);
        }
    }

    @SuppressWarnings("unchecked")
	private void onQuery(String departStation, String arriveStation, Date departDate, boolean isStudent) {
        EventQueue.invokeLater(() -> {
        	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        	String dateString = format.format(departDate);
        	String s = new String("schedules"+" "+departStation+" "+arriveStation+" "+dateString+" "+isStudent);
        	System.out.println(s);
            Socket socket = TrainTicketClient.getSocket();
            try {
				ObjectOutputStream oos = TrainTicketClient.getOos();
				oos.writeObject(s);
				oos.flush();
				ObjectInputStream ois  =TrainTicketClient.getOis();
				schedules = (List<TrainSchedule>) ois.readObject();
				if(schedules == null){
					JOptionPane.showMessageDialog(null,"There is no schedule for the train");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            refresh(departStation, arriveStation, isStudent);
        });
    }

    private void refresh(String departStation, String arriveStation, boolean isStudent) {
        for (ScheduleItem item : scheduleItems) {
            remove(item);
        }
        scheduleItems.clear();
        if (null != schedules) {
        	for (TrainSchedule schedule : schedules) {
                scheduleItems.add(new ScheduleItem(schedule, departStation, arriveStation, isStudent));
            }
		}
        relayout();
    }

    private class SchedulesPanel extends XPanel {
        @Override
        protected void initComponents() {

        }
    }

    /**
     * Display train start time, start station, time, train number name��������
     */
    private class TrainInfoItem extends JPanel implements Measurable {
        private TrainSchedule schedule;
        private Train train;
        private TrainSchedule.Extra extra;

        private JLabel departTime, departStation, arriveTime, arriveStation;
        private JLabel trainId, passTime;
        private JXImageView passby;
        int itemWidth = 900, itemHeight = 200;

        public TrainInfoItem(TrainSchedule schedule, TrainSchedule.Extra extra) {
            this.schedule = schedule;
            this.train = schedule.getTrain();
            this.extra = extra;
            initComponents();
        }

        private void initValues() {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm");
            departTime.setText(format.format(extra.departTime));
            arriveTime.setText(format.format(extra.arriveTime));
            departStation.setText(extra.departStationName);
            arriveStation.setText(extra.arriveStationName);
            trainId.setText(extra.trainId);
            passTime.setText(extra.passTime);
            try {
                passby.setImage(new File("D:/TrainTicketsManager/res/train_passby.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void initComponents() {
            initialize();
            initAlignment();
            initLayout();
            initValues();
        }

        private void initialize() {
            add(departTime = new JLabel());
            add(arriveTime = new JLabel());
            add(departStation = new JLabel());
            add(arriveStation = new JLabel());
            add(trainId = new JLabel());
            add(passTime = new JLabel());
            add(passby = new JXImageView());
        }

        private void initAlignment() {
            Font boldFont = new Font("TimesRoman", Font.BOLD, 24);
            Font bigFont = new Font("TimesRoman", Font.PLAIN, 24);
            Font midFont = new Font("TimesRoman", Font.PLAIN, 16);
            Font smallFont = new Font("TimesRoman", Font.PLAIN, 14);

            WidgetUtils.format(departTime, boldFont, SwingConstants.CENTER, SwingConstants.BOTTOM);
            WidgetUtils.format(departStation, bigFont, SwingConstants.CENTER, SwingConstants.TOP);
            WidgetUtils.format(arriveTime, boldFont, SwingConstants.CENTER, SwingConstants.BOTTOM);
            WidgetUtils.format(arriveStation, bigFont, SwingConstants.CENTER, SwingConstants.TOP);

            WidgetUtils.format(trainId, smallFont, SwingConstants.CENTER, SwingConstants.BOTTOM);
            WidgetUtils.format(passTime, smallFont, SwingConstants.CENTER, SwingConstants.TOP);
        }

        private void initLayout() {
            setLayout(null);
            int x = 0, y = 0;
            x += padding;
            y += padding;
            departTime.setBounds(x, y, 100, 50);
            y += padding + 50 + padding;
            departStation.setBounds(x, y, 100, 50);
            x += 100 + 50;
            y = padding + 20;
            trainId.setBounds(x, y, 160, 30);
            y += 30 + padding;
            passby.setBounds(x, y, 160, 14);
            y += 14 + padding;
            passTime.setBounds(x, y, 160, 30);
            x += 160 + 50;
            y = padding;
            arriveTime.setBounds(x, y, 100, 50);
            y += padding + 50 + padding;
            arriveStation.setBounds(x, y, 100, 50);
            x += 100 + 50;
            y += padding + 40;
            itemWidth = x;
            itemHeight = y;
        }

        @Override
        public int width() {
            return itemWidth;
        }

        @Override
        public int height() {
            return itemHeight;
        }
    }

    /**
     * Show train information and reservation information
     */
    private class ScheduleItem extends JPanel implements Measurable {
        private TrainSchedule schedule;
        private Train train;
        private TrainSchedule.Extra extra;
        private String departStationNameInQuery, arriveStationNameInQuery;
        private boolean isStudent;

        private TrainInfoItem trainInfoItem;
        private JLabel hardSeat, hardBerth, softBerth, noSeat;
        private JButton orderHardSeat, orderHardBerth, orderSoftBerth, orderNoSeat;
        int itemWidth = 900, itemHeight = 200;

        public ScheduleItem(TrainSchedule schedule, String departStation, String arriveStation, boolean isStudent) {
            this.schedule = schedule;
            this.train = schedule.getTrain();
            this.departStationNameInQuery = departStation;
            this.arriveStationNameInQuery = arriveStation;
            this.isStudent = isStudent;
            this.extra = schedule.getExtra(departStation, arriveStation, isStudent);
            this.trainInfoItem = new TrainInfoItem(schedule, extra);
            initComponents();
        }

        private void initValues() {
            SimpleDateFormat format = new SimpleDateFormat("hh:mm");
            hardSeat.setText("Hard seat  " + extra.hardSeatNum + "ticket  " + extra.hardSeatMoney + "NZD");
            hardBerth.setText("Hard sleeper  " + extra.hardBerthNum + "ticket  " + extra.hardBerthMoney + "NZD");
            softBerth.setText("Soft sleeper  " + extra.softBerthNum + "ticket  " + extra.softBerthMoney + "NZD");
            noSeat.setText("No more seat  " + extra.noSeatNum + "ticket  " + extra.noSeatMoney + "NZD");
            orderHardSeat.setEnabled(extra.hardSeatNum > 0);
            orderHardBerth.setEnabled(extra.hardBerthNum > 0);
            orderSoftBerth.setEnabled(extra.softBerthNum > 0);
            orderNoSeat.setEnabled(extra.noSeatNum > 0);
        }

        private void initComponents() {
            initialize();
            initAlignment();
            initLayout();
            initValues();
        }

        private void initialize() {
            add(trainInfoItem);
            add(hardSeat = new JLabel());
            add(hardBerth = new JLabel());
            add(softBerth = new JLabel());
            add(noSeat = new JLabel());
            add(orderHardSeat = new JButton("Reserve"));
            add(orderHardBerth = new JButton("Reserve"));
            add(orderSoftBerth = new JButton("Reserve"));
            add(orderNoSeat = new JButton("Reserve"));
            orderHardSeat.addActionListener(e -> new OrderDialog("Hard seat").popup("Reserve Hard seat ticket"));
            orderHardBerth.addActionListener(e -> new OrderDialog("Hard sleeper").popup("Reserve Hard sleeper ticket"));
            orderSoftBerth.addActionListener(e -> new OrderDialog("Soft sleeper").popup("Reserve Soft sleeper ticket"));
            orderNoSeat.addActionListener(e -> new OrderDialog("No more seat").popup("Reserve No more seat ticket"));
            setBorder(new BorderUIResource.LineBorderUIResource(new Color(200, 200, 200)));
        }

        private void initAlignment() {
            Font boldFont = new Font("TimesRoman", Font.BOLD, 24);
            Font bigFont = new Font("TimesRoman", Font.PLAIN, 24);
            Font midFont = new Font("TimesRoman", Font.PLAIN, 16);
            Font smallFont = new Font("TimesRoman", Font.PLAIN, 14);

            WidgetUtils.format(hardSeat, midFont, SwingConstants.CENTER, SwingConstants.CENTER);
            WidgetUtils.format(hardBerth, midFont, SwingConstants.CENTER, SwingConstants.CENTER);
            WidgetUtils.format(softBerth, midFont, SwingConstants.CENTER, SwingConstants.CENTER);
            WidgetUtils.format(noSeat, midFont, SwingConstants.CENTER, SwingConstants.CENTER);

            WidgetUtils.format(orderHardSeat, midFont, SwingConstants.CENTER, SwingConstants.CENTER);
            WidgetUtils.format(orderHardBerth, midFont, SwingConstants.CENTER, SwingConstants.CENTER);
            WidgetUtils.format(orderSoftBerth, midFont, SwingConstants.CENTER, SwingConstants.CENTER);
            WidgetUtils.format(orderNoSeat, midFont, SwingConstants.CENTER, SwingConstants.CENTER);
        }

        private void initLayout() {
            setLayout(null);
            int x = leftMargin, y = topMargin;
            x += padding;
            y += padding;
            trainInfoItem.setBounds(x, y, trainInfoItem.itemWidth, trainInfoItem.itemHeight);
            x += trainInfoItem.itemWidth + 50;
            y = leftMargin + padding;
            hardSeat.setBounds(x, y, 200, 30);
            y += 30 + padding;
            hardBerth.setBounds(x, y, 200, 30);
            y += 30 + padding;
            softBerth.setBounds(x, y, 200, 30);
            y += 30 + padding;
            noSeat.setBounds(x, y, 200, 30);
            x += padding + 200 + padding;
            y = leftMargin + padding;
            orderHardSeat.setBounds(x, y, 80, 30);
            y += 30 + 10;
            orderHardBerth.setBounds(x, y, 80, 30);
            y += 30 + 10;
            orderSoftBerth.setBounds(x, y, 80, 30);
            y += 30 + 10;
            orderNoSeat.setBounds(x, y, 80, 30);
            x += 80 + padding + leftMargin;
            y += 30 + 10 + topMargin;
            itemWidth = x;
            itemHeight = y;
        }

        @Override
        public int width() {
            return itemWidth;
        }

        @Override
        public int height() {
            return itemHeight;
        }

        @SuppressWarnings("unchecked")
        private class OrderDialog extends XDialog {
            private JLabel dateInfo, seatInfo;
            private TrainInfoItem trainInfoItem;
            private JTextField nameField, telField;
            private JComboBox carriageBox, seatNumBox;
            private String seatType;
            private float seatMoney;
            private List<Seat> freeSeats;

            public OrderDialog(String seatType) {
                this.seatType = seatType;
                if ("Hard seat".equals(seatType)) {
                    seatMoney = extra.hardSeatMoney;
                } else if ("Hard sleeper".equals(seatType)) {
                    seatMoney = extra.hardBerthMoney;
                } else if ("Soft sleeper".equals(seatType)) {
                    seatMoney = extra.softBerthMoney;
                } else if ("No more seat".equals(seatType)) {
                    seatMoney = extra.noSeatMoney;
                }
                init();
            }

            @Override
            protected void onOK() {
                String name = field(2);
                String idNum = field(3);
                String tel = field(4);
                Customer buyer = new Customer();
                buyer.setName(name);
                buyer.setIdNum(idNum);
                buyer.setCustomerType(isStudent ? 1 : 0);
                Socket socket = ServerConnectSocketThread.getSocket();
                ObjectOutputStream oos = null;
                ObjectInputStream ois = null;
                TrainOrder order = new TrainOrder();
                TicketPoint point = new TicketPoint();
				try {
					oos = TrainTicketClient.getOos();
					String s = new String("idNum"+" "+"sex"+" "+idNum);
					oos.writeObject(s);
					oos.flush();
					ois = TrainTicketClient.getOis();
					int sex = (int)ois.readObject();
					buyer.setSex(sex);
					
					buyer.setTel(tel);
					oos.writeObject(buyer);
					oos.flush();
					int carriage = Integer.parseInt((String) Objects.requireNonNull(carriageBox.getSelectedItem()));
	                int seatNum = (int) seatNumBox.getSelectedItem();
	                s = new String("seat"+" "+train.getTrainId()+" "+carriage+" "+seatNum);
	                oos.writeObject(s);
	                oos.flush();
	                Seat seat = (Seat)ois.readObject();
	                
	                point.setPointId(Constants.currentManager.getPointId());
	                order.setTicketPoint(point);
	                order.setBuyer(buyer);
	                order.setTrainSchedule(schedule);
	                order.setSeat(seat);
	                order.setTrain(train);
	                s = new String("station"+" "+extra.departStationName);
	                oos.writeObject(s);
	                oos.flush();
	                Station departStation = (Station)ois.readObject();
	                order.setDepartStation(departStation);
	                s = new String("station"+" "+extra.arriveStationName);
	                oos.writeObject(s);
	                oos.flush();
	                order.setArriveStation((Station)ois.readObject());
	                order.setDepartStationRrder(extra.departStationOrder);
	                order.setArriveStationOrder(extra.arriveStationOrder);
	                order.setStudentTicket(isStudent);
	                order.setMoney(seatMoney);
	                order.setOrderState(TrainOrder.STATE_RESERVED);
	                oos.writeObject(order);
	                oos.flush();
	                int res = (int)ois.readObject();
	                if(res == 0){
	                    JOptionPane.showMessageDialog(null, "Reserve failure��The user has already reserved the trip");
	                }
	                else{
	                    PayDialog pay = new PayDialog();
	                    pay.popup("Waiting for user payment...");
	                    if (pay.result) {
	                        order.setOrderState(TrainOrder.STATE_PAYED);
	                        //Socket, transfer to server for Oder table data update.
	                        oos.writeObject(order);
	                        oos.flush();
	                        //MySQLManager.getInstance().dao().updateTrainOrder(order);
				DBManager.getInstance().dao().updateTrainOrder(order);
	                    }
	                    JOptionPane.showMessageDialog(null, "Payment successful");
	                    dispose();
	                }
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (ClassNotFoundException e1){
					e1.printStackTrace();
				}
            }

            private String[] getCarriages() {
                List<String> carriageList = new ArrayList<>();
                for (Seat s : freeSeats) {
                    String num = String.valueOf(s.getCarriageNum());
                    if (s.getSeatType().equals(seatType) && !carriageList.contains(num)) {
                        carriageList.add(num);
                    }
                }
                String[] carriages = new String[carriageList.size()];
                carriageList.toArray(carriages);
                return carriages;
            }

            public List<Integer> getFreeSeatIn(int carriage) {
                List<Integer> seats = new ArrayList<>();
                for (Seat s : freeSeats) {
                    if (s.getCarriageNum() == carriage) {
                        seats.add(s.getSeatNum());
                    }
                }
                return seats;
            }

            private void init() {
                SimpleDateFormat format = new SimpleDateFormat("MM/dd  HH:mm");
                String departDate = format.format(extra.departTime);
                Socket socket = TrainTicketClient.getSocket();
                try {
					ObjectOutputStream oos = TrainTicketClient.getOos();
					oos.writeObject(schedule);
					oos.flush();
					ObjectInputStream ois = TrainTicketClient.getOis();
					freeSeats = (List<Seat>)ois.readObject();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e2){
					e2.printStackTrace();
				}

                addItem(dateInfo = new JLabel(train.getTrainId() + "Train  " + departDate + "    " + seatType + "  " + seatMoney + "NZD"), 500, 40);
                addItem(trainInfoItem = new TrainInfoItem(schedule, extra));
                nameField = addField("Passenger Name", "");
                addField("Passport number", "").addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        // auto fill other blank
                        String idNum = field(3);
                        if (!StringUtils.empty(idNum)) {
                        	try {
								ObjectOutputStream oos = TrainTicketClient.getOos();
								ObjectInputStream ois = TrainTicketClient.getOis();
								String s = new String("idNum" + " " +"customer"+" "+idNum);
								oos.writeObject(s);
								oos.flush();
								Customer customer = (Customer)ois.readObject();
			                    if (customer != null) {
			                    	nameField.setText(customer.getName());
			                    	telField.setText(customer.getTel());
			                    }
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (ClassNotFoundException e2){
								e2.printStackTrace();
							}
                        }
                    }
                });
                telField = addField("Phone number", "");
                carriageBox = addComboBox("Train ID", getCarriages());
                (seatNumBox = addComboBox("Seat number")).setEnabled(false);

                WidgetUtils.format(dateInfo, new Font("TimesRoman", Font.PLAIN, 16), SwingConstants.CENTER, SwingConstants.CENTER);
                carriageBox.addItemListener(e -> {
                    if (ItemEvent.SELECTED == e.getStateChange()) {
                        int carriage = Integer.parseInt((String) e.getItem());
                        selectCarriage(carriage);
                    }
                });
                buttonOK.setText("PAY");
                // init carriage and seat
                selectCarriage(Integer.parseInt((String) carriageBox.getItemAt(0)));
            }

            private void selectCarriage(int carriage) {
                seatNumBox.setEnabled(true);
                seatNumBox.removeAllItems();
                for (Integer seat : getFreeSeatIn(carriage)) {
                    seatNumBox.addItem(seat);
                }
            }
        }
    }

    private class PayDialog extends XDialog {
        JXImagePanel imagePanel;
        boolean result = false;

        @Override
        protected void initComponents() {
            try {
                //imagePanel = new JXImagePanel(new URL("res/qrcode.png"));
		imagePanel = new JXImagePanel(new URL("images/qrcode.png"));
                imagePanel.setPreferredSize(new Dimension(500, 500));
                imagePanel.setStyle(JXImagePanel.Style.SCALED);
                addItem(imagePanel, 500, 500);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onOK() {
            result = true;
            super.onOK();
        }
    }
}