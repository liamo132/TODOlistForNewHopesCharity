/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package todofornewhopescharity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * @author liamf
 */
public class ToDoServer {
    //start server or client
private static final int PORT = 1234;
    private static DatagramSocket dgramSocket;
    private static DatagramPacket inPacket, outPacket;
    private static byte[] buffer;

    public static void main(String[] args) 
    {
	System.out.println("Opening port...\n");
	try 
        {
            dgramSocket = new DatagramSocket(PORT);//Step 1.
	} 
        catch(SocketException e) 
        {
            System.out.println("Unable to attach to port!");
	    System.exit(1);
	}
	run();
    }

    private static void run()
    {
	try 
        {
            String messageIn, messageOut;
            int numMessages = 0;

            do 
            {
                buffer = new byte[256]; 		//Step 2.
                inPacket = new DatagramPacket(buffer, buffer.length); //Step 3.
                dgramSocket.receive(inPacket);	//Step 4.

                InetAddress clientAddress = inPacket.getAddress();	//Step 5.
                int clientPort = inPacket.getPort();		//Step 5.

                messageIn = new String(inPacket.getData(), 0, inPacket.getLength());	//Step 6.

                System.out.println("Message received.");
                numMessages++;
                messageOut = ("Message " + numMessages+ ": " + messageIn);
                
                outPacket = new DatagramPacket(messageOut.getBytes(),
                                         messageOut.length(),
                                         clientAddress,	
                                         clientPort);		//Step 7.
                dgramSocket.send(outPacket);	//Step 8.
            }while (true);
        } 
        catch(IOException e)
        {
            e.printStackTrace();
	} 
        finally 
        {		//If exception thrown, close connection.
            System.out.println("\n Closing connection... ");
            dgramSocket.close();				//Step 9.
	}
    }
}