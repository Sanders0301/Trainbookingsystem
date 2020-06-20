package Controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import Thread.ServerConnectSocketThread;
import ui.manager.ManagerFrame;

public class TrainTicketServer implements Runnable{
	public void StartServer(int port){
    try {
        //Create a server-side Socket with the ServerSocket binding to listen on
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = null;
        //Record the number of clients that have connected to the server
        int count = 0;
        System.out.println("***Server is start now！ please waiting for connection***");
        while(ManagerFrame.isWindowsIsAlive() == true){//The loop listens for new client connections
	    //Call the Accept () method to listen and wait for the client to connect to get the Socket instance
            socket = serverSocket.accept();
            count++;	//Record number of connections
            System.out.println("Number of times the server has been connected��"+count);
            InetAddress address = socket.getInetAddress();
            System.out.println("Current client IP is��"+address.getHostAddress());
            //Create a new thread
            Thread thread = new Thread(new ServerConnectSocketThread(socket));
            thread.start();
        }            
        //serverSocket.close();Always listen in a loop without closing the connection
    } catch (IOException e) {
        e.printStackTrace();
    }
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
                System.out.println("***Server is started at port 5002***");
		this.StartServer(5002);
	}
}
