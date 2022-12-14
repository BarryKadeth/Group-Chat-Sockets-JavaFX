package application;

import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.net.ServerSocket;

import java.net.Socket;

public class ServicedServer extends Service <String>{
	int port;
	ObservableList<Client> ipAddressList;
	private Server server;
	
	public ServicedServer (int port, ObservableList<Client> ipAddressList)
	{
		
		setOnSucceeded(event -> 
			{
				System.out.println("This is setOnSucceeded " + server);
				
			});
				
		this.port = port;
		this.ipAddressList = ipAddressList;
	}
	
	public void myStop() throws Exception
	{
		if(server != null)
		{
			System.out.println("This is Ok");
		//	server.myStop();
		}
		else
			System.out.println("This is not Ok");
		
	}

	@Override
	protected Task<String> createTask() {
		// TODO Auto-generated method stub
		
		return new Task <String>()
		{
			@Override
			protected String call() throws Exception {
				//Thread.sleep(10000);
				
				System.out.println("Creating task....");
			//	server = new Server(port, ipAddressList);
				
				System.out.println("Task Created .....");
				
				return "nothing";
			}
		};
        
	}

	public Server getServer() {
		return server;
	}
}
