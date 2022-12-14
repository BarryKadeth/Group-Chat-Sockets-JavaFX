package application;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread{
	
	Socket socket;
	public ServerThread(Socket s) {
		
		this.socket = s;
	}
	
	public void run() {
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
			System.out.print("A client request recieved at" + socket);
			out.println("Whenever the moon and stars are set, whenever the winds");
		//	socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.print("Error");
		}
	}

}
