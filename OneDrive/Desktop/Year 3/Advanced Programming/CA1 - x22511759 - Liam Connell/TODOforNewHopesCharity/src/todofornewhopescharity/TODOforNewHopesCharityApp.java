/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package todofornewhopescharity;

import java.util.Scanner;

/**
 *
 * @author liamf
 */
public class TODOforNewHopesCharityApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type 'server' to start server or 'client' to start the client:");
        //user inputs what they want to start, Server must be started first, then however many clients the user needs
        String choice = scanner.nextLine().trim().toLowerCase();

        if (choice.equals("server")) { //starting the server
            ToDoServer.main(args);  
        } else if (choice.equals("client")) { //starting the client
            ToDoClient.main(args);  
        } else {
            System.out.println("Invalid option. Please run the program again and choose 'server' or 'client'.");
        }
    }

}
