package it.polimi.ingsw.networking;

import java.io.*;
import java.net.Socket;

/**
 * Class representing client in a client-server architecture
 * It uses socket for establish connection and show/get server's/client's messages
 * args[0] contains UI choice: TUI or GUI (GUI as default if args is empty)
 *
 * @author Foini Lorenzo
 */
public class Client {
    private static Socket socket = null; // Initialise socket
    private static BufferedReader in;
    private static PrintWriter out;
    private static BufferedReader userInput;
    private static boolean useTUI = false; // GUI as default

    /**
     * Main method
     * It is necessary for establish connection to the server (if possible) and setup parameters
     *
     * @author Foini Lorenzo
     */
    public static void main(String[] args) {
        // Initialise server IP and port
        String hostName = "127.0.0.1";
        int port = 54321;

        try {
            socket = new Socket(hostName, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Input => Server messages to client
            out = new PrintWriter(socket.getOutputStream(), true); // Output => Client messages to server
            userInput = new BufferedReader(new InputStreamReader(System.in)); // Read client keyboard

            // Check if user plays with TUI or GUI
            try {
                String userChoice = args[0].toUpperCase();
                out.println(userChoice);
                if (userChoice.equals("TUI")) {
                    useTUI = true;
                    startTUI();
                } else {
                    startGUI();
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                out.println("GUI");
                startGUI(); // Start GUI as default without command line parameter
            }

        } catch (IOException e){
            System.err.println("Game ended because an I/O error occurred: " + e.getMessage());
        } finally {
            // Close connection
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private static void startTUI() throws IOException {
        /*
              The following code it is used for handles communication between the client and the server.
              In particular, the while loop takes care of continuously reading responses from the server via in.readLine(),
              until the server closes the connection or a null message is received.
              For each message received from server, client prints it out to console via System.out.println(serverResponse).
              Iterate until there is a server message.
            */
        String serverMessage;
        while ((serverMessage = in.readLine()) != null) {
            System.out.println(serverMessage); // Display server message

            // If the message requires an answer from the user, then catch that answer
            if (serverMessage.startsWith("Specify the number of players (insert 2, 3 or 4):") ||
                    serverMessage.startsWith("Insert your username:") ||
                    serverMessage.startsWith("Username already in use. Please insert a new username:") ||
                    serverMessage.startsWith("Insert your color:") ||
                    serverMessage.startsWith("Invalid color. Please select a color from the previous list:") ||
                    serverMessage.startsWith("On which side you want to play the starter card (insert front or back):") ||
                    serverMessage.startsWith("Insert front or back:") ||
                    serverMessage.startsWith("Select your secret objective card (insert 1 or 2):") ||
                    serverMessage.startsWith("Which card you want to play (insert 1, 2 or 3):") ||
                    serverMessage.startsWith("Please insert 1, 2 or 3:") ||
                    serverMessage.startsWith("On which side you want to play this card(insert front or back):") ||
                    serverMessage.startsWith("Insert integer row value:") ||
                    serverMessage.startsWith("Insert integer column value:") ||
                    serverMessage.startsWith("Insert 1, 2 or 3:") ||
                    serverMessage.startsWith("Insert the card's number:")
            ) {
                    /*
                      Get user's input via userInput.readLine() and pass it to the server via out.println()
                    */
                String userInputMessage = userInput.readLine(); // Get client input in the keyboard
                out.println(userInputMessage); // Pass it to the handler
            }
        }
    }

    private static void startGUI() throws IOException{
        // TODO
    }

    private boolean getUseTui(){
        return useTUI;
    }
}