/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package todofornewhopescharity;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

/**
 *
 * @author razi
 */
public class ToDoServer {

    private static final int PORT = 1234;
    private static ServerSocket servSock;
    private static Map<String, List<String>> todoMap = Collections.synchronizedMap(new HashMap<>());

    public static void main(String[] args) {
        System.out.println("Opening port...\n");
        try {
            servSock = new ServerSocket(PORT); // Step 1, creating a server socket
            System.out.println("Server is running...");// confirm server is running
        } catch (IOException e) {
            System.out.println("Unable to attach to port!"); // error if port is unavailable
            System.exit(1);
        }

        // Continuously accept and handle client connections in separate threads
        while (true) {
            try {
                Socket link = servSock.accept(); // Accept incoming client connections
                new ClientHandler(link).start(); // Start a new thread to handle each client
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Inner class to handle client connections in separate threads
    private static class ClientHandler extends Thread {

        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Message received from client: " + message);
                    if (message.trim().equalsIgnoreCase("STOP")) {
                        out.println("TERMINATE"); // Send TERMINATE message back to client
                        break; // Break the loop to close the connection
                    }

                    // Split message into action and description
                    String[] parts = message.split(";", 3);
                    String action = parts[0].trim().toLowerCase();
                    String date = parts.length > 1 ? parts[1].trim() : "";
                    String description = parts.length > 2 ? parts[2].trim() : "";

                    // Handle the action and generate response
                    String response;
                    try {
                        response = handleAction(action, date, description);
                    } catch (IncorrectActionException e) {
                        response = "Error: " + e.getMessage();
                    }

                    // Send response back to client
                    out.println(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Ensure the connection is closed
                try {
                    System.out.println("\n* Closing connection... *");
                    clientSocket.close();
                } catch (IOException e) {
                    System.out.println("Unable to disconnect!");
                    System.exit(1);
                }
            }
        }
    }

    // Handle actions outside the ClientHandler class
    private static String handleAction(String action, String date, String description) throws IncorrectActionException {
        synchronized (todoMap) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM", Locale.ENGLISH);
            try {
                switch (action.toLowerCase()) {
                    case "add":
                        Date taskDate = sdf.parse(date); // Ensure correct format for date
                        String formattedDateAdd = sdf.format(taskDate);
                        List<String> tasksAdd = todoMap.getOrDefault(formattedDateAdd, new ArrayList<>());
                        tasksAdd.add(description);
                        todoMap.put(formattedDateAdd, tasksAdd); // Add task to map
                        return "Task added for " + date + ": " + description;

                    case "list":
                        if (date.equalsIgnoreCase("all")) {
                            if (todoMap.isEmpty()) {
                                return "No tasks available.";
                            }
                            StringBuilder allTasks = new StringBuilder();
                            for (List<String> taskList : todoMap.values()) {
                                for (String task : taskList) {
                                    allTasks.append(task).append(", ");
                                }
                            }
                            return allTasks.length() > 0 ? allTasks.substring(0, allTasks.length() - 2) : "No tasks found.";
                        } else if (date.isEmpty()) {
                            return "Please specify a date or 'all' to list all tasks.";
                        } else {
                            Date listDate = sdf.parse(date); // Parse date
                            String formattedDateList = sdf.format(listDate);
                            List<String> tasksList = todoMap.get(formattedDateList);
                            if (tasksList == null || tasksList.isEmpty()) {
                                return "No tasks for " + date;
                            } else {
                                return String.join(", ", tasksList);  // Just return the tasks for that date
                            }
                        }

                    default:
                        throw new IncorrectActionException("Invalid action: " + action);
                }
            } catch (ParseException e) {
                throw new IncorrectActionException("Invalid date format: " + date);
            }
        }
    }

}
// Custom exception for incorrect actions
class IncorrectActionException extends Exception {

    public IncorrectActionException(String message) {
        super(message);
    }
}
