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
        
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = null;
        
        int count = 0;
        System.out.println("***Server is start now！ please waiting for connection***");
        while(ManagerFrame.isWindowsIsAlive() == true){
            socket = serverSocket.accept();
            count++;	
            System.out.println("Number of times the server has been connected��"+count);
            InetAddress address = socket.getInetAddress();
            System.out.println("Current client IP is��"+address.getHostAddress());
            
            Thread thread = new Thread(new ServerConnectSocketThread(socket));
            thread.start();
        }            
        //serverSocket.close();
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
