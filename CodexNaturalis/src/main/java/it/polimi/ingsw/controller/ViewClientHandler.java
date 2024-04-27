package it.polimi.ingsw.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class which handle multiple threads representing clients connections to server
 * It extends Thread and override method run
 *
 * @author Lorenzo Foini
 */
public class ViewClientHandler extends Thread {
    private final Socket clientSocket;
    private final BufferedReader in;
    private final PrintWriter out;

    /**
     * ViewClientHandler constructor, it assigns/creates all class's parameters
     *
     * @param socket representing a socket
     * @throws IOException if an I/O error occurred
     * @author Lorenzo Foini
     */
    public ViewClientHandler(Socket socket) throws IOException {
        this.clientSocket = socket;
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    /**
     * Override of method run
     * It asks the client to insert username, color and secret objective card
     * Catch IOException if an I/O error occurred
     *
     * @author Lorenzo Foini
     */
    @Override
    public void run() {
        try {
            // Ask for username and check its availability
            boolean exit = false;
            do{
                out.println("Insert your username:");
                String username = in.readLine();
                if (ControllerServer.isUsernameAvailable(username)) { // Available
                    exit = true;
                    out.println("Username accepted.");
                    if (ControllerServer.getNumPlayers() == 0) {
                        // Ask for number of game's players if the client is the first one to connect
                        int numPlayers;
                        do{
                            out.println("Specify the number of players:");
                            numPlayers = Integer.parseInt(in.readLine());
                        }while(numPlayers < 2 || numPlayers > 4);
                        // Set controller's param numPlayer
                        ControllerServer.setNumPlayers(numPlayers);
                    }
                    // Add user to set of already present username
                    ControllerServer.addUser(username);
                } else {
                    out.println("Username already in use. Please choose a different username.");
                }
            }while(!exit);

            // Ask the player which color he wants
            // Not ask to first player => Assign black as default
            // For other players show list of possible color and check its choice
            // TODO

            // Ask the player which objective card he wants to use between the two given
            // TODO

        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
        } finally {
            // Close socket
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("An I/O error occurred: " + e.getMessage());
            }
        }
    }
}