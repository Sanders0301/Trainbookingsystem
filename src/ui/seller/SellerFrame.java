package ui.seller;

import javax.swing.*;
import Controller.TrainTicketClient;
import bean.Manager;
import ui.widget.Measurable;
import utils.Constants;
import utils.LookUtils;
import utils.WidgetUtils;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

public class SellerFrame extends JFrame implements Measurable {
	private static final long serialVersionUID = 1L;
	private OrderPanel orderPanel = new OrderPanel();
	private HistoryPanel historyPanel = new HistoryPanel();
	private ModifyPasswordPanel modifyPasswordPanel = new ModifyPasswordPanel();

	public SellerFrame() {
		Constants.checkManagerType(Manager.TYPE_SELLER);
		setTitle("Trainticket booking system");
		initComponents();
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				try {
					TrainTicketClient.getSocket().close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				int option = JOptionPane.showConfirmDialog(SellerFrame.this, "Comfirm exit system? ", "Point out ",
						JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					System.exit(0);
				} else {
					return;
				}
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private void initComponents() {
		JTabbedPane tabbedPane1 = new JTabbedPane();
		tabbedPane1.setFont(new Font("TimesRoman", Font.PLAIN, 14));
		tabbedPane1.addTab("Reserve list", orderPanel);
		tabbedPane1.addTab("Reserve record", historyPanel);
		tabbedPane1.addTab("Change password", modifyPasswordPanel);

		add(tabbedPane1);
	}

	@Override
	public int width() {
		return 1024;
	}

	@Override
	public int height() {
		return 768;
	}

	public static void main(String[] args) {
		 
		LookUtils.beautyEye();
		WidgetUtils.popup(SellerFrame.class);
	}
}
