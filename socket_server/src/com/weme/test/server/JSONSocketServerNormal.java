package com.weme.test.server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.json.JSONObject;

public class JSONSocketServerNormal {
    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9876;
     
    public static void main(String args[]) throws IOException, ClassNotFoundException{
        String encd = "UTF-8";
        
        boolean flag = false;
        
        //create the socket server object
        server = new ServerSocket(port);
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
        	try {
	            System.out.println("Waiting for client request");
	            //creating socket and waiting for client connection
	            Socket socket = server.accept();
	            //read from socket to ObjectInputStream object
	            DataInputStream ois = new DataInputStream(socket.getInputStream());
                byte[] in = new byte[100];
                ois.read(in,0,in.length);
                String response = new String(in,0,in.length);
                response.trim();
                System.out.println("Message Received: " + response);
                
                JSONObject message = null;
                String id = "";
				if (response != null){
					message = new JSONObject(response);
					id = message.get("id")+"";
				}
				
                DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
                byte[] out = new byte[100];
	            String sendJson = "{\"id\":" + id + ", \"result\": {\"uid\": \"#####\"}}";
                out = sendJson.getBytes();
                oos.write(out);
                oos.flush();

	            //close resources
                ois.close();
                oos.close();
                socket.close();
                if(response.equalsIgnoreCase("exit")) break;

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        }
        System.out.println("Shutting down Socket server!!");
        //close the ServerSocket object
        server.close();
    }
    
}
