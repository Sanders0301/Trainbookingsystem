package test;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;


public class SwingFlowLayoutDemo {
	private static JFrame mainFrame;

	public static void main(String[] args) {
		
		JFrame.setDefaultLookAndFeelDecorated(true);

		mainFrame = new JFrame("Swing FlowLayout Demo");
		mainFrame.setSize(400, 200);

		
		Container container = mainFrame.getContentPane();
		JTextArea textArea = new JTextArea();
		Dimension dim = new Dimension(380, 120);
		textArea.setPreferredSize(dim);
		container.add(textArea);

		
		JButton btnOk = new JButton("Login");
		btnOk.setPreferredSize(new Dimension(85, 23));
		container.add(btnOk);

		
		JButton btnCancel = new JButton("Cancle");
		btnCancel.setPreferredSize(new Dimension(85, 23));
		container.add(btnCancel);

		
		FlowLayout layout = new FlowLayout(FlowLayout.TRAILING);

		
		container.setLayout(layout);

		
		layout.setHgap(5);

		
		layout.setVgap(8);

		
		mainFrame.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});

		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		int height = dimension.height;
		int width = dimension.width;
		mainFrame.setLocation((width - 400) / 2, (height - 200) / 2);
		mainFrame.setVisible(true);
	}
}