package it.polimi.ingsw.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import it.polimi.ingsw.controller.*;

/**
 * Server's class using socket
 *
 * @author Foini Lorenzo
 */
public class Server {
    private static int numPlayers = 4; // Default value => Changed by first client to connect
    private static int countConnectedClients = 0; // Number of connected client to the game
    private static boolean gameEnded = false;
    private static final ArrayList<String> clientUsernames = new ArrayList<>();
    private static final ArrayList<String> availableColors = new ArrayList<>();
    private static ArrayList<ClientHandlerSocket> clients = new ArrayList<>();


    /**
     * Main method
     * It creates a new instance of controller, add available colors, accept connection for client
     * and use a client handler for threads
     *
     * @author Foini Lorenzo
     */
    public static void main(String[] args) {
        // Initialise Controller => 4 as default
        Controller controller = new Controller(numPlayers);

        // Add available colors as string
        availableColors.add("blue");
        availableColors.add("green");
        availableColors.add("red");
        availableColors.add("yellow");

        int portNum = 54321; // Get random port

        // Try to get clients' connections
        try (ServerSocket ss = new ServerSocket(portNum)) {
            System.out.println("Server is connected.\nWait for connections...");
            // While loop until all players enter the game
            while (countConnectedClients < numPlayers-1) {
                Socket clientSocket = ss.accept(); // New client connections

                ClientHandlerSocket clientThread = new ClientHandlerSocket(clientSocket); // Create a new client handler
                clientThread.start(); // Start thread
                /*while( PRIMO GIOCATORE NON HA INSERITO NUMERO DI GIOCATORI){

                } //ESCO DAL WHILE QUANDO IL PRIMO GIOCATORE HA INSERITO IL NUMERO DI GIOCATORI
                 */
                clients.add(clientThread); // Add thread to controller's list of clients
            }

            /*while(ULTIMO GIOCATORE DEVE AVER INSERITO TUTTI I SUOI DATI){

            }*/

            // THE GAME HAS NOW STARTED
            System.out.println("NON FUNZIONA");
        } catch (IOException e) {
            System.err.println("Game ended because an I/O error occurred: " + e.getMessage());
        }
    }

    /**
     * num player getter
     *
     * @return number of players in this game
     * @author Foini Lorenzo
     */
    public synchronized static int getNumPlayers() {
        return numPlayers;
    }

    /**
     * num player setter
     *
     * @param num number of players in this game
     * @author Foini Lorenzo
     */
    public synchronized static void setNumPlayers(int num) {
        numPlayers = num;
    }

    /**
     * count of connected clients getter
     *
     * @return number of clients connected to the server
     * @author Foini Lorenzo
     */
    public synchronized static int getCountConnectedClients() {
        return countConnectedClients;
    }

    /**
     * Method for incrementing by 1 the number of connected players
     * @author Foini Lorenzo
     */
    public synchronized static void incrementCountConnectedClients() {
        countConnectedClients++;
    }

    /**
     * game ended getter
     *
     * @return true if the game is ended, false otherwise
     * @author Foini Lorenzo
     */
    public synchronized static boolean getGameEnded() {
        return gameEnded;
    }

    /**
     * game ended setter
     *
     * @param isEnded true if the game is ended, false otherwise
     * @author Foini Lorenzo
     */
    public synchronized static void setGameEnded(boolean isEnded) {
        gameEnded = isEnded;
    }

    /**
     * available colors getter
     *
     * @return list of available colors
     * @author Foini Lorenzo
     */
    public static ArrayList<String> getAvailableColors() {
        return availableColors;
    }

    /**
     * Method for removing a given color from the list of available colors
     *
     * @param color which color to be removed from list
     * @author Foini Lorenzo
     */
    public static void removeAvailableColor(String color) {
        availableColors.remove(color);
    }

    /**
     * clients' username getter
     *
     * @return list of clients' username
     * @author Foini Lorenzo
     */
    public static ArrayList<String> getClientUsername() {
        return clientUsernames;
    }

    /**
     * Method for adding a given username to the list of available colors
     *
     * @param clientUsername which username to be added to list
     * @author Foini Lorenzo
     */
    public static void addClientUsername(String clientUsername) {
        clientUsernames.add(clientUsername);
    }
}