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
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Host ID not found!");
            System.exit(1);
        }
        run();
    }

    private static void run() {
        Socket link = null;//Step 1.
        try {
            //establish connection
            link = new Socket(host, PORT);//Step 1.
            BufferedReader in = new BufferedReader(new InputStreamReader(link.getInputStream()));//Step 2.
            PrintWriter out = new PrintWriter(link.getOutputStream(), true); //Step 2.
            //Set up stream for keyboard entry...
            BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
            String message = null;
            String response = null;
            while (true) {
                // Read message from user
                System.out.println("Enter message to be sent to server: ");
                message = userEntry.readLine();
                out.println(message); // Send message to server
                response = in.readLine(); // Read response from server
                System.out.println("\n<SERVER RESPONSE> " + response);
                // Break loop if 'stop' message is sent
                if (message.trim().toLowerCase().equals("stop")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Ensure the connection is closed
            try {
                System.out.println("\n* TERMINATED connection... *");
                if (link != null && !link.isClosed()) {
                    link.close();
                }
            } catch (IOException e) {
                System.out.println("Unable to disconnect/close!");
                System.exit(1);
            }
        }
    }
}
