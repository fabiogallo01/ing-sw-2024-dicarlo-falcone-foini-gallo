package it.polimi.ingsw.view;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class representing view, but it also works as a client in a client-server architecture
 * It uses socket for establish connection and handle flow of the game
 * Use TUI
 *
 * @author Lorenzo Foini
 */
public class ViewClient {
    /**
     * Main method
     * It is necessary for establish connection to the server (if possible) and setup parameters
     *
     * @author Lorenzo Foini
     */
    public static void main(String[] args) {
        // Assign server's IP and port
        String serverAddress = "127.0.0.1";
        int serverPort = 10000;

        // Try connection
        try (Socket socket = new Socket(serverAddress, serverPort);
            // Initialise input and output stream (using libraries)
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            /*
              The following code it is used for handles communication between the client and the server.
              In particular, the while loop takes care of continuously reading responses from the server via in.readLine(),
              until the server closes the connection or a null message is received.
              For each message received from server, client prints it out to console via System.out.println(serverResponse).
            */
            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println(serverResponse);
                if (serverResponse.startsWith("Specify the number of players:")) {
                    /*
                      Asks the user to enter the number of players via userInput.readLine()
                      and sends the response to the server via out.println(numPlayersInput)
                    */
                    String numPlayersInput = userInput.readLine();
                    out.println(numPlayersInput);
                } else if (serverResponse.startsWith("Insert your username:")) {
                    /*
                      Asks the user to enter username via userInput.readLine()
                      and sends the response to the server via out.println(usernameInput)
                    */
                    String usernameInput = userInput.readLine();
                    out.println(usernameInput);
                }
                // TODO: Ask user other information: color and secret objective
            }
        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
        }
    }

    // TODO: Methods for TUI
}