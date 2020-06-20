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
/**
 * Student information management system _ Student basic information add class 
 * @author sun 
 * @time 2020-06-10 16:30  
 */
public class StuInfoAdd extends JFrame {
	/*
	 * The basic components
	 */
	JPanel contentp = new JPanel();
	JPanel toolp = new JPanel();

// student ID
	JLabel xhlbl = new JLabel("Student ID");
	JTextField xhtxt = new JTextField();

 //name
	JLabel xmlbl = new JLabel("Name");
	JTextField xmtxt = new JTextField();

//genfer
	JLabel xblbl = new JLabel("Gender");
	JComboBox xbcbb = new JComboBox();

 //national
	JLabel mzlbl = new JLabel("National");
	JTextField mztxt = new JTextField();

 //data of birth
	JLabel csrqlbl = new JLabel("Birth of date");
	JFormattedTextField csrqtxt = new JFormattedTextField(DateFormat.getDateInstance());

//Native place
	JLabel jglbl = new JLabel("Native place");
	JTextField jgtxt = new JTextField();

 //class
	JLabel bjlbl = new JLabel("Class");
	JComboBox bjcbb = new JComboBox();

 //semester
	JLabel xqlbl = new JLabel("Semester");
	JComboBox xqcbb = new JComboBox();

//upload
	JButton addbtn = new JButton("ADD");

 //cancle
	JButton cancelbtn = new JButton("CANCEL");

        /**
	 * Initializes the base component
	 * 
	 * @throws Exception
	 */	
	private void initComponent() throws Exception {
//data of birth
		csrqtxt.setColumns(14);

//gender
		xbcbb.setVerifyInputWhenFocusTarget(true);
		xbcbb.addItem("Male");
		xbcbb.addItem("Female");

//classs
		bjcbb.setVerifyInputWhenFocusTarget(true);
		bjcbb.addItem("1311");
		bjcbb.addItem("1312");

//semester


		xqcbb.setVerifyInputWhenFocusTarget(true);
		xqcbb.addItem("131401");
		xqcbb.addItem("131402");

		
//The content of the panel
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
 //Tool
		toolp.setLayout(new FlowLayout(FlowLayout.RIGHT)); 
		toolp.add(addbtn);
		toolp.add(cancelbtn);

 //Frame
		setLayout(new BorderLayout());
		this.getContentPane().add(contentp, BorderLayout.CENTER);
		this.getContentPane().add(toolp, BorderLayout.SOUTH);

	}
/**
	 * The sontructor
	 */
	
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
