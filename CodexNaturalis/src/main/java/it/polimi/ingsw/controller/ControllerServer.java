package it.polimi.ingsw.controller;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.game.*;

/**
 * Class representing controller, but it also works as a server in a client-server architecture
 * It uses socket for establish connection and handle flow of the game
 * Use a client handler for handle multiple threads
 *
 * @author Lorenzo Foini
 */
public class ControllerServer {
    // Assign/create all parameters
    private static final int port = 10000;
    private static int numPlayers = 0;
    private static final Set<String> playersUsernames = new HashSet<>();
    public static ArrayList<ViewClientHandler> clients= new ArrayList<>();
    private static GameTable gameTable;

    /**
     * Main method
     * It is necessary for set up the server and accept connections
     * It also initialises and start threads
     * Catch IOException and exceptions throw by gameTable
     *
     * @author Lorenzo Foini
     */
    public static void main(String[] args) {
        // Run server using new server socket
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running...");
            // While the number of connection to the server is less than the number of game's players
            // then the server wait for new connection
            while (numPlayers == 0 || playersUsernames.size() < numPlayers) {
                // Accept new connection
                Socket clientSocket = serverSocket.accept();
                // Using thread for handle connection
                ViewClientHandler client = new ViewClientHandler(clientSocket);
                client.start(); // Start thread
                if(playersUsernames.size() == 1) {
                    // Initialise gameTable => Moved to ViewClientHandler???
                    gameTable = new GameTable(numPlayers);
                }

                // ADD ViewClientHandler to the list of already connected clients
                clients.add(client);

                // Add player's username to the list of players in gameTable
                // Need to initialise all player's attribute
                // TODO: gameTable.addPlayer(new Player(all params));
            }

            // Call method for handle players' turn and flow of the game
            playGame();
        } catch (IOException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
        } catch (EmptyObjectiveDeckException | EmptyDeckException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method for checking if the client selected username is available or not
     * It is necessary to use synchronized because of threads
     *
     * @param username client's username
     * @return boolean representing the availability
     * @author Lorenzo Foini
     */
    public static synchronized boolean isUsernameAvailable(String username) {
        return !playersUsernames.contains(username);
    }

    /**
     * Method for add username to the set of usernames
     * It is necessary to use synchronized because of threads
     *
     * @param username client's username to be added
     * @author Lorenzo Foini
     */
    public static synchronized void addUser(String username) {
        playersUsernames.add(username);
    }

    /**
     * numPlayers getter
     * It is necessary to use synchronized because of threads
     *
     * @return number of players
     * @author Lorenzo Foini
     */
    public static synchronized int getNumPlayers() {
        return numPlayers;
    }

    /**
     * numPlayers setter
     * It is necessary to use synchronized because of threads
     *
     * @param num number of players to be set
     * @author Lorenzo Foini
     */
    public static synchronized void setNumPlayers(int num) {
        numPlayers = num;
    }

    /**
     * gameTable getter
     * It is necessary to use synchronized because of threads
     *
     * @return gameTable instance
     * @author Lorenzo Foini
     */
    public static synchronized GameTable getGameTable() {
        return gameTable;
    }

    /**
     * gameTable setter
     * It is necessary to use synchronized because of threads
     *
     * @param newGameTable representing gameTable to be set
     * @author Lorenzo Foini
     */
    public static synchronized void setGameTable(GameTable newGameTable) {
        gameTable = newGameTable;
    }

    /**
     * Method for handle players' turn and flow of the game
     *
     * @author Lorenzo Foini
     */
    public static void playGame(){
        // Use param clients for handle turn and play
        // TODO
    }
}