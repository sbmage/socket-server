package com.weme.test.server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9888;
     
    public static void main(String args[]) throws IOException, ClassNotFoundException{
        //create the socket server object
        server = new ServerSocket(port);
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
        	try {
        		  System.out.println("Waiting for client request");
                  //creating socket and waiting for client connection
                  Socket socket = server.accept();
                  //read from socket to ObjectInputStream object
//                  ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                  DataInputStream ois = new DataInputStream(socket.getInputStream());
                  //convert ObjectInputStream object to String
//                  String message = (String) ois.readObject();
                  byte[] in = new byte[100];
                  ois.read(in,0,in.length);
                  String response = new String(in,0,in.length);
                  response.trim();
                  System.out.println("Message Received: " + response);
                  //create ObjectOutputStream object
//                  ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                  DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
                  byte[] out = new byte[100];
                  String sendmgs = "kugistory.net";   // your sending message
                  out = sendmgs.getBytes();
                  oos.write(out);
                  oos.flush();
                  //write object to Socket
//                  oos.writeObject("Hi Client "+response);
                  //close resources
                  ois.close();
                  oos.close();
                  socket.close();
                  //terminate the server if client sends exit request
                  if(response.equalsIgnoreCase("exit")) break;
			} catch (Exception e) {
				e.printStackTrace();
			}
        }
        System.out.println("Shutting down Socket server!!");
        //close the ServerSocket object
        server.close();
    }
     
}