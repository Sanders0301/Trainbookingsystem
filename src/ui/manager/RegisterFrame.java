package ui.manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import bean.Manager;
import ui.widget.Measurable;
import utils.Constants;
import utils.LookUtils;
import utils.WidgetUtils;


public class RegisterFrame extends JFrame implements Measurable {
	private static final long serialVersionUID = 1L;
	private RegisterPanel regPanel = new RegisterPanel();
    private static boolean windowsIsAlive = true;
    
    public RegisterFrame() {
//        Constants.checkManagerType(Manager.TYPE_SUPERUSER);
        setTitle("Traintickets booking system--User register");
        initComponents();
//        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
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
						RegisterFrame.this, "Comfirm exit system? ", "point out ",JOptionPane.YES_NO_OPTION);
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
//        JTabbedPane tabbedPane1 = new JTabbedPane();
//        tabbedPane1.setFont(new Font("TimesRoman", Font.PLAIN, 14));
//        tabbedPane1.addTab("User management", regPanel);
//        add(tabbedPane1);
    	add(regPanel);
    }


    public static void main(String[] args) {
        LookUtils.beautyEye();
        WidgetUtils.popup(RegisterFrame.class);
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