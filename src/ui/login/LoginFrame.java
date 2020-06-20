package ui.login;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import Controller.TrainTicketClient;
import Controller.TrainTicketServer;
import bean.Customer;
import bean.Manager;
import dao.MySQLManager;
import dao.DBManager;
import ui.manager.ManagerFrame;
import ui.manager.RegisterFrame;
import ui.manager.RegisterPanel;
import ui.widget.Measurable;
import ui.widget.XDialog;
import ui.widget.XPanel;
import utils.Constants;
import utils.LookUtils;
import utils.StringUtils;
import utils.WidgetUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

public class LoginFrame extends JFrame implements Measurable {

	private static final long serialVersionUID = 1L;
	private JTextField usernameField, passwordField;
	
	private JComboBox managerTypeCB = new JComboBox();

	public LoginFrame() {
		setTitle("Login");
		initComponents();
	}

	private void initComponents() {
		
		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel head = new JLabel("TrainTicketBookingSystem");
		WidgetUtils.format(head, new Font("TimesRoman", Font.PLAIN, 32), SwingConstants.CENTER, SwingConstants.CENTER);
		XPanel contentPane = new XPanel();
		contentPane.addItem(head, 300, 60);
		
		JLabel tip = new JLabel("name:admin, pwd:123456");
		contentPane.addItem(tip, 160,60);
		
		usernameField = contentPane.addHint("UserName");
		passwordField = contentPane.addPasswordHint("Password");
		managerTypeCB.addItem("Admin");
		managerTypeCB.addItem("User");
//		managerTypeCB.addItem("User");

		JButton btnLogin = new JButton("Login");
		contentPane.addItem(managerTypeCB);
		btnLogin.addActionListener(this::onLoginClicked);
		contentPane.addItem(btnLogin, 80, 20);

		JButton btnRegister = new JButton("Register");
		btnRegister.addActionListener(this::onRegisterClicked);
		contentPane.addItem(btnRegister, 80, 20);

		add(contentPane);
	}

	private void clearPassword() {
		passwordField.setText("");
	}

	
	private void onLoginClicked(ActionEvent e) {
		String username = usernameField.getText();
		String password = passwordField.getText();
		System.out.println(username + " " + password);
		String error = "Unknow error";
		if (StringUtils.empty(username)) {
			JOptionPane.showMessageDialog(null, "Please input user name");
		} else if (StringUtils.empty("password")) {
			JOptionPane.showMessageDialog(null, "Please enter password");
		} else {
                    System.out.println("---------------login------------------");
			EventQueue.invokeLater(() -> {
				if (managerTypeCB.getSelectedItem() == "Admin") {
					//Manager manager = MySQLManager.getInstance().dao().login(username, password);
                                        System.out.println("---------------start to login------------------");
					Manager manager = DBManager.getInstance().dao().login(username, password);
					Constants.currentManager = manager;
					if (manager == null) {
						JOptionPane.showMessageDialog(null, "Wrong password");
					} else if (Manager.TYPE_SUPERUSER == manager.getManagerType()) {
						JOptionPane.showMessageDialog(null, "Login as admin");
						WidgetUtils.popup(ManagerFrame.class);
						dispose();
						
						Thread thread = new Thread(new TrainTicketServer());
						thread.start();
					} else if (Manager.TYPE_SELLER == manager.getManagerType()) {
						
						JOptionPane.showMessageDialog(null, "Wrong user type");
					}
				} else {
					TrainTicketClient ttc = new TrainTicketClient();
					System.out.println("---------------connect to server------------------");
					ttc.connect("127.0.0.1", 5002, username, password);
					dispose();
				}
			});
		}
	}
	
	private void onRegisterClicked(ActionEvent e) {
		userReg();
	}
	
	
	private void userReg() {
		new XDialog() {
            @Override
            protected void initComponents() {
                
                String[] columns = Constants.ColumnName.CUSTOMER;
                addField(columns[0], "");
                addField(columns[1], "");
                addComboBox(columns[2], "female", "male");
                addField(columns[3], "");
                addComboBox(columns[4], "Adult", "Student");
            }

            @Override
            protected void onOK() {
                Customer customer = new Customer();
                customer.setIdNum(field(0));
                customer.setName(field(1));
                customer.setSex(option(2));
                customer.setTel(field(3));
                customer.setCustomerType(option(4) + 1);
                customer.setReg_time(new Date());
                EventQueue.invokeLater(() -> {
                   // MySQLManager.getInstance().dao().insertCustomer(customer);
		    DBManager.getInstance().dao().insertCustomer(customer);
                    JOptionPane.showMessageDialog(null, "Register done. Login now");
                    super.onOK();
                });
            }
        }.popup("Register");
	}

	@Override
	public int width() {
		return 420;
	}

	@Override
	public int height() {
		return 540;
	}

	public static void main(String[] args) {
		LookUtils.beautyEye();
		WidgetUtils.popup(LoginFrame.class, EXIT_ON_CLOSE);
	}
}