package it.polimi.ingsw.networking;

import java.io.*;
import java.net.Socket;

/**
 * Class representing client in a client-server architecture
 * It uses socket for establish connection and show/get server's/client's messagea
 * Use TUI
 *
 * @author Lorenzo Foini
 */
public class Client {

    /**
     * Main method
     * It is necessary for establish connection to the server (if possible) and setup parameters
     *
     * @author Lorenzo Foini
     */
    public static void main(String[] args) {
        // Initialise server IP and port
        String hostName = "127.0.0.1";
        int port = 54321;

        try {
            Socket socket = new Socket(hostName, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Input => Server messages to client
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true); // Output => Client messages to server
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in)); // Read client keyboard

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
                if (serverMessage.startsWith("Specify the number of players (2, 3 or 4):")) {
                    /*
                      Asks the user to enter the number of players via userInput.readLine()
                      Sends the response to the server via out.println(numPlayersInput)
                    */
                    String numPlayersInput = userInput.readLine(); // Get client input in the keyboard
                    out.println(numPlayersInput); // Pass it to the handler
                } else if (serverMessage.startsWith("Insert your username:") || serverMessage.startsWith("Username already in use. Please insert a new username:")) {
                    /*
                      Asks the user to enter username via userInput.readLine()
                      Sends the response to the server via out.println(usernameInput)
                    */
                    String usernameUsername = userInput.readLine();
                    out.println(usernameUsername);
                } else if (serverMessage.startsWith("On which side you want to play the starter card (insert front or back):") || serverMessage.startsWith("Insert front or back")) {
                    /*
                      Asks the user to enter the side of starter card via userInput.readLine()
                      Sends the response to the server via out.println(usernameInput)
                    */
                    String sideStarterCardsInput = userInput.readLine();
                    out.println(sideStarterCardsInput);
                } else if (serverMessage.startsWith("Insert your color:") || serverMessage.startsWith("Invalid color. Please select a color from the previous list:")) {
                    /*
                      Asks the user to enter username via userInput.readLine()
                      Sends the response to the server via out.println(usernameInput)
                    */
                    String usernameColor = userInput.readLine();
                    out.println(usernameColor);
                } else if (serverMessage.startsWith("Select your secret objective card (insert 1 or 2)")) {
                    /*
                      Asks the user his secret objective card via userInput.readLine()
                      Sends the response to the server via out.println(usernameInput)
                    */
                    String selectedObjectiveCard = userInput.readLine();
                    out.println(selectedObjectiveCard);
                }
            }
        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
        }
    }
}