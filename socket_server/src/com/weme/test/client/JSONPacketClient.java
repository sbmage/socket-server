package com.weme.test.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.weme.test.json.JSONPacketReader;
import com.weme.test.json.JSONPacketWriter;
import com.weme.test.json.JSONReader;
import com.weme.test.json.JSONWriter;

/**
 * Push encoded JSON data packets through a file byte stream.
 * 
 * 
 * @author Ryan Beckett
 * @version 1.0
 * @since Dec 23, 2011
 */
public class JSONPacketClient {

    public static void main(String[] args) throws Exception {

        String encd = "UTF-8";
        String sendJson2 = "{\"id\": 1, \"method\": \"logout\"}}";

        // write JSON to file
//        JSONWriter wrtr = new JSONPacketWriter(new FileOutputStream(file, false), encd);
        
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        DataOutputStream oos = null;
        DataInputStream ois = null;
        
        for (int i = 0; i < 5; i++) {
            socket = new Socket(host.getHostName(), 9876);
            oos = new DataOutputStream(socket.getOutputStream());
            
            JSONWriter wrtr = new JSONPacketWriter(oos, encd);
            String sendJson = "{\"id\": "+i+", \"method\": \"login\", \"params\": {\"type\": \"token\", \"token\": \"XXXXXXXXXXXXXXXXXXXXXXXXXXXX\"}}";
            if(i==4)wrtr.write(sendJson2);
            else wrtr.write(sendJson);
            System.out.println("Successfully send JSON data to + " + host.getHostName() + ":");
            System.out.println(sendJson);

            ois = new DataInputStream(socket.getInputStream());
            // read JSON from file
//            JSONReader rdr = new JSONPacketReader(new FileInputStream(file), encd);
            JSONReader rdr = new JSONPacketReader(ois, encd);
            String retrievedJSON = rdr.read();
            System.out.println("Retrieved XML: " + retrievedJSON);

            wrtr.close();
            rdr.close();
            Thread.sleep(100);	
		}


    }
}
