/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package todofornewhopescharity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 *
 * @author liamf
 */
public class ToDoClient {
        private static InetAddress host;
    private static final int PORT = 1234;
    private static DatagramSocket dgramSocket;
    private static DatagramPacket inPacket, outPacket;
    private static byte[] buffer;

    public static void main(String[] args) {
    try 
    {
        host = InetAddress.getLocalHost();
    } 
    catch(UnknownHostException e) 
    {
        System.out.println("Host ID not found!");
        System.exit(1);
    }
    run();
}

private static void run() 
{
    try 
    {
        dgramSocket = new DatagramSocket();		//Step 1.
	//Set up stream for keyboard entry...
	BufferedReader userEntry = new BufferedReader(
                                        new InputStreamReader(System.in));
	String message=null;
        String response=null;
	do 
        {
            System.out.println("Enter message: ");
            message = userEntry.readLine();
            if (!message.equals("***CLOSE***")) 
            {
		outPacket = new DatagramPacket(message.getBytes(),message.length(), host, PORT);    //Step 2.
		dgramSocket.send(outPacket);	//Step 3.
		
                buffer = new byte[256];     //Step 4.
		inPacket = new DatagramPacket(buffer, buffer.length); 	//Step 5.
		dgramSocket.receive(inPacket);	//Step 6.
		response = new String(inPacket.getData(), 0, inPacket.getLength());	//Step 7.
		System.out.println("\nSERVER> " + response);
            }
	}while (!message.equals("***CLOSE***"));
        
    } 
    catch(IOException e) 
    {
        e.printStackTrace();
    } 
    finally 
    {
        System.out.println("\n Closing connection... ");
        dgramSocket.close();	//Step 8.
     }
}
}

