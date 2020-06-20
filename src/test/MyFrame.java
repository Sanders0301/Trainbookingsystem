package test;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
public class MyFrame extends javax.swing.JFrame {
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
        MyFrame f = new MyFrame();
    }

    JLabel label1;
    JLabel label2;
    JLabel label3;
    JTextField tf;
    JPasswordField psf;
    JRadioButton rb1;
    JRadioButton rb2;

    JButton bt1;
    JButton bt2;

    public MyFrame() {
       // this.setVisible(true);
       // this.setSize(250, 220);
       // this.setVisible(true);
        //this.setLocation(400, 200);

        label1 = new JLabel("Trainticket booking system");
        label2 = new JLabel("Account");
        label3 = new JLabel("Password");
        tf = new JTextField();
        psf = new JPasswordField();
        rb1 = new JRadioButton("Remember the password");
        rb2 = new JRadioButton("Auto Login");
        bt1 = new JButton("Login");
        
       /* GroupLayout layout = new GroupLayout(this.getContentPane());
       // this.getContentPane().setLayout(layout);
        
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup().addComponent(label2)
                .addComponent(label3));
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup().addComponent(label1)
                .addComponent(psf).addComponent(rb1).addComponent(rb2)
                .addComponent(tf).addComponent(bt1));
        hGroup.addGap(5);
        layout.setHorizontalGroup(hGroup);
        
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label1));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label2)
                .addComponent(tf));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label3)
                .addComponent(psf));
        vGroup.addGroup(layout.createParallelGroup().addComponent(rb1));

        vGroup.addGroup(layout.createParallelGroup().addComponent(rb2));
        vGroup.addGroup(layout.createParallelGroup(Alignment.TRAILING)
                .addComponent(bt1));
        vGroup.addGap(10);
        
        layout.setVerticalGroup(vGroup); */
    }
}