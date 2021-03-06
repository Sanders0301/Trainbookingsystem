package Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import bean.Manager;
import ui.manager.ManagerFrame;
import ui.seller.SellerFrame;
import utils.Constants;
import utils.WidgetUtils;

public class TrainTicketClient {
	private static Socket socket;
	private static ObjectOutputStream oos;
	private static ObjectInputStream ois;
	public static Socket getSocket() {
		return socket;
	}
	public void connect(String IP, int port, String userName, String password){
            try {
                System.out.println("--------------client connects to server");
		//Create client-side Socket, specifying the server address and port
                socket = new Socket(IP, port);
		//After establishing the connection, the output stream is obtained and the information is sent to the server
                setOos(new ObjectOutputStream(socket.getOutputStream()));
		//The output stream is wrapped as a print stream
		//Send information to the server side
                oos.writeObject(userName +" "+ password);//Write to memory buffer
                oos.flush();//Flush the cache and output information to the server side
                // socket.shutdownOutput();
                // oos.close();
		//Gets the input stream and receives the server-side response
                setOis( new ObjectInputStream(socket.getInputStream()));
                try {
				Manager manager = (Manager) ois.readObject();
				Constants.currentManager = manager;
				if(manager != null){
	            	JOptionPane.showMessageDialog(null, "Login success!");
	            	WidgetUtils.popup(SellerFrame.class);
	            }
	            else{
	            	JOptionPane.showMessageDialog(null, "Connection failed ,Please login again.");
	            }
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            

        //ois.close();
        //Close other resources
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	public static ObjectInputStream getOis() {
		return ois;
	}
	public static void setOis(ObjectInputStream ois) {
		TrainTicketClient.ois = ois;
	}
	public static ObjectOutputStream getOos() {
		return oos;
	}
	public static void setOos(ObjectOutputStream oos) {
		TrainTicketClient.oos = oos;
	}
}
