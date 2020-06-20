package test;

import java.awt.BorderLayout;
import java.awt.Dimension; 
import java.awt.FlowLayout;
import java.awt.GridLayout; 
import java.text.DateFormat;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StuInfoAdd extends JFrame {
	
	JPanel contentp = new JPanel();
	JPanel toolp = new JPanel();

 
	JLabel xhlbl = new JLabel("Student ID");
	JTextField xhtxt = new JTextField();

 
	JLabel xmlbl = new JLabel("Name");
	JTextField xmtxt = new JTextField();


	JLabel xblbl = new JLabel("Gender");
	JComboBox xbcbb = new JComboBox();

 
	JLabel mzlbl = new JLabel("National");
	JTextField mztxt = new JTextField();

 
	JLabel csrqlbl = new JLabel("Birth of date");
	JFormattedTextField csrqtxt = new JFormattedTextField(DateFormat.getDateInstance());


	JLabel jglbl = new JLabel("Native place");
	JTextField jgtxt = new JTextField();

 
	JLabel bjlbl = new JLabel("Class");
	JComboBox bjcbb = new JComboBox();

 
	JLabel xqlbl = new JLabel("Semester");
	JComboBox xqcbb = new JComboBox();


	JButton addbtn = new JButton("ADD");

 
	JButton cancelbtn = new JButton("CANCEL");

	
	private void initComponent() throws Exception {

		csrqtxt.setColumns(14);


		xbcbb.setVerifyInputWhenFocusTarget(true);
		xbcbb.addItem("Male");
		xbcbb.addItem("Female");


		bjcbb.setVerifyInputWhenFocusTarget(true);
		bjcbb.addItem("1311");
		bjcbb.addItem("1312");


		xqcbb.setVerifyInputWhenFocusTarget(true);
		xqcbb.addItem("131401");
		xqcbb.addItem("131402");

		

		GridLayout gridlayout = new GridLayout();
		gridlayout.setRows(4);
		gridlayout.setColumns(4);
		gridlayout.setVgap(20);
		contentp.setLayout(gridlayout);
		xhlbl.setHorizontalAlignment(JLabel.CENTER);
		contentp.add(xhlbl);
		contentp.add(xhtxt);
		xmlbl.setHorizontalAlignment(JLabel.CENTER);
		contentp.add(xmlbl);
		contentp.add(xmtxt);
		xblbl.setHorizontalAlignment(JLabel.CENTER);
		contentp.add(xblbl);
		contentp.add(xbcbb);
		mzlbl.setHorizontalAlignment(JLabel.CENTER);
		contentp.add(mzlbl);
		contentp.add(mztxt);
		csrqlbl.setHorizontalAlignment(JLabel.CENTER);
		contentp.add(csrqlbl);
		contentp.add(csrqtxt);
		jglbl.setHorizontalAlignment(JLabel.CENTER);
		contentp.add(jglbl);
		contentp.add(jgtxt);
		bjlbl.setHorizontalAlignment(JLabel.CENTER);
		contentp.add(bjlbl);
		contentp.add(bjcbb);
		xqlbl.setHorizontalAlignment(JLabel.CENTER);
		contentp.add(xqlbl);
		contentp.add(xqcbb);
 
		toolp.setLayout(new FlowLayout(FlowLayout.RIGHT)); 
		toolp.add(addbtn);
		toolp.add(cancelbtn);

 
		setLayout(new BorderLayout());
		this.getContentPane().add(contentp, BorderLayout.CENTER);
		this.getContentPane().add(toolp, BorderLayout.SOUTH);

	}

	
	public StuInfoAdd() {
		try {

			initComponent();

			

			//this.setSize(450, 230); 
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
			/*Dimension frameSize = this.getSize(); 
			if (frameSize.height > screenSize.height) {
				frameSize.height = screenSize.height;
			}
			if (frameSize.width > screenSize.width) {
				frameSize.width = screenSize.width;
			} */
			//this.setTitle("Add basic information of students");
			//this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
			//this.setResizable(false);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println("=>Add basic information of students;");
		StuInfoAdd stuinfo = new StuInfoAdd();
		stuinfo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		stuinfo.setVisible(true);
	}
}
