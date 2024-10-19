/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package todofornewhopescharity;

import java.io.*;
import java.net.*;

/**
 *
 * @author liamf
 */
public class ToDoClient {
 private static InetAddress host;
    private static final int PORT = 1234;

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
    
   private static void run() {
    Socket link = null;				//Step 1.
    try 
    {
	link = new Socket(host,PORT);		//Step 1.
        //link = new Socket( "192.168.0.59", PORT);
	BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));//Step 2.
	PrintWriter out = new PrintWriter(link.getOutputStream(),true);	 //Step 2.

	//Set up stream for keyboard entry...
	BufferedReader userEntry =new BufferedReader(new InputStreamReader(System.in));
	String message = null;
        String response= null;
	
        System.out.println("Enter message to be sent to server: ");
        message =  userEntry.readLine();
        out.println(message); 		//Step 3.
        response = in.readLine();		//Step 3.
        System.out.println("\nSERVER RESPONSE> " + response);
    } 
    catch(IOException e)
    {
	e.printStackTrace();
    } 
    finally 
    {
        try 
        {
            System.out.println("\n* Closing connection... *");
            link.close();				//Step 4.
	}catch(IOException e)
        {
            System.out.println("Unable to disconnect/close!");
            System.exit(1);
	}
    }
 } // finish run method
}  //finish the class

