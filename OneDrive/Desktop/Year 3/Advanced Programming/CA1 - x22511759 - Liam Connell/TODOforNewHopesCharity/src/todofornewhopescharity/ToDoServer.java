/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package todofornewhopescharity;

import java.io.*;
import java.net.*;

/**
 *
 * @author razi
 */
public class ToDoServer { 
  private static ServerSocket servSock;
  private static final int PORT = 1234;
  private static int clientConnections = 0;

  public static void main(String[] args) {
    System.out.println("Opening port...\n");
    try 
    {
        servSock = new ServerSocket(PORT);      //Step 1.
    }
    catch(IOException e) 
    {
         System.out.println("Unable to attach to port!");
         System.exit(1);
    }
    
    do 
    {
         run();
    }while (true);

  }
  
  private static void run()
  {
    Socket link = null;                        //Step 2.
    try 
    {
      link = servSock.accept();               //Step 2.
      clientConnections++;
      BufferedReader in = new BufferedReader( new InputStreamReader(link.getInputStream())); //Step 3.
      PrintWriter out = new PrintWriter(link.getOutputStream(),true); //Step 3.
      
      String message = in.readLine();         //Step 4.
      System.out.println("Message received from client: " + clientConnections + "  "+ message);
      out.println("Echo Message: " + message);     //Step 4.
     }
    catch(IOException e)
    {
        e.printStackTrace();
    }
    finally 
    {
       try {
	    System.out.println("\n* Closing connection... *");
            link.close();				    //Step 5.
	}
       catch(IOException e)
       {
            System.out.println("Unable to disconnect!");
	    System.exit(1);
       }
    }
  } // finish run method 
} // finish the class
