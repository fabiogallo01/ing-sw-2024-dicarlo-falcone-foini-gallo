package it.polimi.ingsw.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.game.Player;

/**
 * Server's class using socket
 *
 * @author Foini Lorenzo
 */
public class Server {
    private static int numPlayers = 4; // Default value => Changed by first client to connect
    private static int ready = 0;
    private static int countConnectedClients = 0; // Number of connected client to the game
    private static final ArrayList<String> clientUsernames = new ArrayList<>();
    private static final ArrayList<String> availableColors = new ArrayList<>();
    private static ArrayList<ClientHandlerSocket> clients = new ArrayList<>();
    private static volatile boolean firstClientConnected = false;
    private static volatile boolean connected = false;
    private static Controller controller = new Controller(numPlayers);// Initialise Controller => 4 as default

    /**
     * Main method
     * It creates a new instance of controller, add available colors, accept connection for client
     * and use a client handler for threads
     *
     * @author Foini Lorenzo
     */
    public static void main(String[] args) {
        // Add available colors as string
        availableColors.add("blue");
        availableColors.add("green");
        availableColors.add("red");
        availableColors.add("yellow");

        int portNum = 54321; // Get random port
        ServerSocket ss = null; // Initialise server socket

        // Try to get clients' connections
        try {
            ss = new ServerSocket(portNum);
            System.out.println("Server is connected.\nWait for connections...");
            // While loop until all players enter the game
            while (countConnectedClients < numPlayers) {
                Socket clientSocket = ss.accept(); // New client connections

                ClientHandlerSocket clientThread = new ClientHandlerSocket(clientSocket); // Create a new client handler
                clientThread.start(); // Start thread

                // Waits for the player to actually be connected, so the conditions inside the while works properly
                while (!connected) {
                    Thread.onSpinWait();
                }
                connected = false;

                // Waits for the first player to insert the number of players of the game, so it does not mess with the condition in the while
                while (!firstClientConnected) {
                    Thread.onSpinWait();
                }

                clients.add(clientThread); // Add thread to controller's list of clients
            }
            //waits for all the players to be ready
            while (ready < numPlayers) {
                Thread.onSpinWait();
            }

            // THE GAME HAS NOW STARTED
            System.out.println("\nGAME HAS STARTED\n");
            // Send to all the clients a message which says that the game has started and their numbers
            int clientNum = 1;
            for (ClientHandlerSocket clientThread : clients) {
                clientThread.sendStartGameMessage();
                clientThread.sendWaitTurnMessage();
                clientNum++;
            }

            // Start turn
            // For inactive players: display wait message
            // For the active player: ask turn information
            playGame();


        } catch (IOException | InterruptedException e) {
            System.err.println("Game ended because an I/O error occurred: " + e.getMessage());
        } finally {
            // Close connection
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public static Controller getController(){
        return controller;
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
        controller.getGameTable().setNumPlayers(num);
    }

    public static void setReady() {
        Server.ready++;
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
     * Connected clients setter
     *
     * @param connected boolean to assign
     * @author Gallo Fabio
     */
    public static void setConnected(boolean connected) {
        Server.connected = connected;
    }

    /**
     * Method for incrementing by 1 the number of connected players
     * @author Foini Lorenzo
     */
    public synchronized static void incrementCountConnectedClients() {
        countConnectedClients++;
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

    /**
     * First client connected setter
     *
     * @param firstClientConnected boolean to be set
     * @author Foini Lorenzo
     */
    public static void setFistClientConnected(boolean firstClientConnected) {
        Server.firstClientConnected = firstClientConnected;
    }

    /**
     * Method for play game: Iterate through players for handling turns
     * Turn: ask phase and draw phase
     *
     * @throws java.io.InterruptedIOException and IOException
     * @author Foini Lorenzo
     */
    public static void playGame() throws InterruptedException, IOException {
        // Start turn
        // For current player: ask turn information

        // Iterate until a player reaches 20 points
        while (!controller.getGameTable().isEnded()) {
            // Iterate through clients
            for (ClientHandlerSocket clientThread : clients) {
                // Send a message to server
                System.out.println("It's " + clientThread.getUsername() + "'s turn.");

                // Play card "phase"
                // Ask active player his play and send messages
                clientThread.sendSelectPlayMessage();
                clientThread.askPlay();
                clientThread.sendCorrectPlayMessage();

                // draw card "phase" and send messages
                clientThread.sendSelectDrawMessage();
                clientThread.askDraw();
                clientThread.sendCorrectDrawMessage();

                // Send turn messages
                clientThread.sendFinishTurnMessage();
                clientThread.sendWaitTurnMessage();
            }
            System.out.print("\n");
        }
        // Exit from while loop => At least a player has reach 20 points
        // Now play last turn: we can avoid to ask the client to draw a card after the play

        // Show a message to all clients which says that the final turn has started and a wait message
        for (ClientHandlerSocket clientHandlerSocket : clients){
            clientHandlerSocket.sendLastTurnMessage();
            clientHandlerSocket.sendWaitTurnMessage();
        }

        // Take last turn for all players
        for (ClientHandlerSocket clientThread : clients) {
            // Send a message to server
            System.out.println("It's " + clientThread.getUsername() + "'s turn.");

            // Play card "phase"
            // Ask active player his play and send messages
            clientThread.sendSelectPlayMessage();
            clientThread.askPlay();
            clientThread.sendCorrectPlayMessage();

            // Send turn messages
            clientThread.sendFinishTurnMessage();
            clientThread.sendWaitFinishGameMessage();
        }

        // Call controller's method for calculate final points
        controller.calculateFinalPoints();
        controller.finalScoreboard();

        System.out.println("\nTHE GAME IS ENDED\n");

        // Call controller's method for getting final leaderboard
        LinkedHashMap<Player, Integer> leaderboard = controller.getLeaderboard();
        System.out.println("\nLeaderboard:");
        for (Map.Entry<Player, Integer> entry : leaderboard.entrySet()) {
            System.out.println(entry.getKey().getUsername() + ": " + entry.getValue());
        }

        // Call to controller's method for getting winners
        ArrayList<Player> winners = controller.calculateWinners(leaderboard);

        // Send winners and scoreboard messages to clients
        for(ClientHandlerSocket clientThread : clients){
            clientThread.sendWinnersMessage(winners);
            clientThread.sendLeaderboardMessage(leaderboard);
        }
    }
}