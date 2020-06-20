package ui.manager;


import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import bean.Manager;
import ui.seller.SellerFrame;
import ui.widget.Measurable;
import utils.Constants;
import utils.LookUtils;
import utils.WidgetUtils;



public class ManagerFrame extends JFrame implements Measurable {

    private CustomerPanel customerPanel = new CustomerPanel();
    private ManagerPanel managerPanel = new ManagerPanel();
    private SellPointPanel sellPointPanel = new SellPointPanel();
    private TrainPanel trainPanel = new TrainPanel();
    private TrainSchedulePanel schedulePanel = new TrainSchedulePanel();
    private TrainOrderPanel orderPanel = new TrainOrderPanel();
    private static boolean windowsIsAlive = true;
    @SuppressWarnings("WeakerAccess")
    public ManagerFrame() {
        Constants.checkManagerType(Manager.TYPE_SUPERUSER);
        setTitle("Trainticket booking system");
        initComponents();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				setWindowsIsAlive(false);
			}



			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				int option= JOptionPane.showConfirmDialog(
						ManagerFrame.this, "Confirm exit system? ", "point out ",JOptionPane.YES_NO_OPTION);
			    if(option==JOptionPane.YES_OPTION)
			    {
			    	System.exit(0);
			    }
			    else
			    {
			    	return;
			    }
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
        	
        });
    }

    private void initComponents(){
        JTabbedPane tabbedPane1 = new JTabbedPane();
        tabbedPane1.setFont(new Font("TimesRoman", Font.PLAIN, 14));
        tabbedPane1.addTab("User management", customerPanel);
        tabbedPane1.addTab("Conductor management", managerPanel);
        tabbedPane1.addTab("Ticket office management", sellPointPanel);
        tabbedPane1.addTab("Train management", trainPanel);
        tabbedPane1.addTab("Train schedule management", schedulePanel);
        tabbedPane1.addTab("Order management", orderPanel);

        add(tabbedPane1);
    }


    public static void main(String[] args) {
        LookUtils.beautyEye();
        WidgetUtils.popup(ManagerFrame.class);
    }

    @Override
    public int width() {
        return 1024;
    }

    @Override
    public int height() {
        return 768;
    }

	public static boolean isWindowsIsAlive() {
		return windowsIsAlive;
	}
	private void setWindowsIsAlive(boolean b) {
		// TODO Auto-generated method stub
		
	}
}