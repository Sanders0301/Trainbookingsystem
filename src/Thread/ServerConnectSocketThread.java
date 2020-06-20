package Thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JOptionPane;

import bean.Customer;
import bean.Manager;
import bean.Seat;
import bean.TrainOrder;
import bean.TrainSchedule;
import dao.MySQLManager;
import dao.DBManager;
import ui.manager.ManagerFrame;
import ui.seller.SellerFrame;
import utils.WidgetUtils;

public class ServerConnectSocketThread implements Runnable{
    private static Socket socket = null;  
    private BufferedReader br = null;  
    private PrintWriter pw = null;  
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;
    
    public ServerConnectSocketThread(Socket s)
    {   
        this.socket=s;
    
    }
    
	public static Socket getSocket() {
		return socket;
	}

	@Override
	public void run() {


        try {
            
            ois = new ObjectInputStream(socket.getInputStream());
            
            oos = new ObjectOutputStream(socket.getOutputStream());
            String data = null;
			try {
				data = (String)ois.readObject();
			} catch (ClassNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
            if(data != null){
                System.out.println("The client submits information is:"+data);
                String[] Info = data.split(" ");
               // Manager manager = MySQLManager.getInstance().dao().login(Info[0], Info[1]);
	        Manager manager = DBManager.getInstance().dao().login(Info[0], Info[1]);
                if (manager != null) {
                	oos.writeObject(manager);
                	oos.flush();
                }
            }
//            Thread sat = new Thread(new ServerActionThread());
//            sat.start();
            try {
            	while(ManagerFrame.isWindowsIsAlive() == true && !socket.isClosed()){
        			Object objectData = null;
        			objectData = ois.readObject();
        			System.out.println(objectData);
        				if(objectData != null){
        					if(objectData.getClass() == TrainOrder.class){
        						TrainOrder order = (TrainOrder) objectData;
        						if(order.getOrderState() == TrainOrder.STATE_REFUNDED){
        						//	MySQLManager.getInstance().dao().updateTrainOrder(order);
								DBManager.getInstance().dao().updateTrainOrder(order);
        						}else if(order.getOrderState() == TrainOrder.STATE_RESERVED){
        							int res = 0;
        							//res = MySQLManager.getInstance().dao().insertTrainOrder(order);
								res = DBManager.getInstance().dao().insertTrainOrder(order);
        							oos.writeObject(res);
        							oos.flush();
        						}else if(order.getOrderState() == TrainOrder.STATE_PAYED){
        							// MySQLManager.getInstance().dao().updateTrainOrder(order);
								DBManager.getInstance().dao().updateTrainOrder(order);
        						}
        					}else if(objectData.getClass() == TrainSchedule.class){
        					//	List<Seat> seat = MySQLManager.getInstance().dao().getFreeSeats((TrainSchedule)objectData);
						List<Seat> seat = DBManager.getInstance().dao().getFreeSeats((TrainSchedule)objectData);
        						oos.writeObject(seat);
        						oos.flush();	
        					}else if(objectData.getClass() == String.class){
        						String s = (String) objectData;
        						String[] recieveString = s.split(" ");
        						System.out.println(recieveString[1]);
        						if(recieveString[0].equals("schedules")){
        							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        							try {
        								//List<TrainSchedule> schedules = MySQLManager.getInstance().dao()
									List<TrainSchedule> schedules = DBManager.getInstance().dao()
        								.searchTrainSchedule(recieveString[1], recieveString[2], 
        										format.parse(recieveString[3]),Boolean.getBoolean(recieveString[4]));
        								if(schedules.isEmpty()){
        									oos.writeObject(null);
        									oos.flush();
        								}else{
	        								oos.writeObject(schedules);
	        								oos.flush();
        								}
        							} catch (ParseException e) {
        								// TODO Auto-generated catch block
        								e.printStackTrace();
        							}
        						}else if(recieveString[0].equals("idNum")){
        							if(recieveString[1].equals("sex")){
        								int sex = 0;
        								//sex = MySQLManager.getInstance().dao().getSexFromIdNum(recieveString[2]);
									sex = DBManager.getInstance().dao().getSexFromIdNum(recieveString[2]);
        								if(sex == 0){
        									oos.writeObject(0);
        									oos.flush();
        								}else{
	        								oos.writeObject(sex);
	        								oos.flush();
        								}
        							}
        							else if(recieveString[1] .equals("customer") ){
        								Customer c = null;
        								//c = MySQLManager.getInstance().dao().getCustomerByIdNum(recieveString[2]);
									c = DBManager.getInstance().dao().getCustomerByIdNum(recieveString[2]);
        								System.out.println(c);
        								if(c == null){
        									oos.writeObject(null);
        									oos.flush();
        								}else{
        									oos.writeObject(c);
        									oos.flush();
        								}
        							}
        						}else if(recieveString[0].equals("seat")){
        							Seat seat = null;
        							//seat = MySQLManager.getInstance().dao().getSeat(recieveString[1], 
								seat = DBManager.getInstance().dao().getSeat(recieveString[1], 
        									Integer.parseInt(recieveString[2]), Integer.parseInt(recieveString[3]));
        							oos.writeObject(seat);
        							oos.flush();
        						}else if(recieveString[0].equals("station")){
        							//oos.writeObject(MySQLManager.getInstance().dao().getStationByName(recieveString[1]));
								oos.writeObject(DBManager.getInstance().dao().getStationByName(recieveString[1]));
        							oos.flush();
        						}
        					}else if(objectData.getClass() == Customer.class){
        						// MySQLManager.getInstance().dao().upsertCustomer((Customer)objectData);
							DBManager.getInstance().dao().upsertCustomer((Customer)objectData);
        					}else if(objectData.getClass() == Manager.class){
        						Manager currentManager = (Manager)objectData;
        						//MySQLManager.getInstance().dao().updateManager(currentManager);
							DBManager.getInstance().dao().updateManager(currentManager);
        					}
        				}
        		}
            //socket.shutdownInput();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
        	try {
        		oos.close();
        		ois.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
            
        }catch(Exception e){
        	e.printStackTrace();
        }
	}
}

