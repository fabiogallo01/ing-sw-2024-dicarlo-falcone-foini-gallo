package it.polimi.ingsw.controller;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import it.polimi.ingsw.model.cards.*;
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
    private static final ArrayList<String> playersUsernames = new ArrayList<>();
    public static ArrayList<ViewClientHandler> clients= new ArrayList<>();
    private static GameTable gameTable;
    private static ArrayList<String> colors = new ArrayList<>();
    public static ArrayList<String> playersColors = new ArrayList<>();
    public static ArrayList<ObjectiveCard> playersSecretCards = new ArrayList<>();
    public static ArrayList<StarterCard> starterCards= new ArrayList<>();


    /**
     * Main method
     * It is necessary for set up the server and accept connections
     * It also initialises and start threads
     * Catch IOException
     *
     * @author Lorenzo Foini
     */
    public static void main(String[] args) {
        // Run server using new server socket
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is running...");

            // Initialise colors
            colors.add("Blue");
            colors.add("Green");
            colors.add("Red");
            colors.add("Yellow");

            // While the number of connection to the server is less than the number of game's players
            // then the server wait for new connection
            while (numPlayers == 0 || playersUsernames.size() < numPlayers) {
                // Accept new connection
                Socket clientSocket = serverSocket.accept();
                // Using thread for handle connection
                ViewClientHandler client = new ViewClientHandler(clientSocket);
                client.start(); // Start thread

                // ADD ViewClientHandler to the list of already connected clients
                clients.add(client);

                // Add player's username to the list of players in gameTable
                // Need to initialise all player's attribute
                Player newPlayer = createNewPlayer(clients.size()-1);
                gameTable.addPlayer(newPlayer);
            }

            // Call method for handle players' turn and flow of the game
            playGame();
        } catch (IOException | EmptyDeckException e) {
            System.err.println("An I/O error occurred: " + e.getMessage());
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
     * gameTable initialization
     * It is necessary to use synchronized because of threads
     *
     * @param numPlayers representing number of players
     * @author Lorenzo Foini
     */
    public static synchronized void initialiseGameTable(int numPlayers) {
        try {
            gameTable = new GameTable(numPlayers);
        } catch (EmptyDeckException | EmptyObjectiveDeckException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Colors getter
     * It is necessary to use synchronized because of threads
     *
     * @return players' color
     * @author Lorenzo Foini
     */
    public static synchronized ArrayList<String> getColors() {
        return colors;
    }

    /**
     * Colors setter
     * It is necessary to use synchronized because of threads
     *
     * @param availableColors new list of available colors
     * @author Lorenzo Foini
     */
    public static synchronized void setColors(ArrayList<String> availableColors) {
        colors = availableColors;
    }

    /**
     * players' colors getter
     * It is necessary to use synchronized because of threads
     *
     * @return players' color
     * @author Lorenzo Foini
     */
    public static synchronized ArrayList<String> getPlayersColors() {
        return playersColors;
    }

    /**
     * Add color to the list of players' colors
     * It is necessary to use synchronized because of threads
     *
     * @param color new player's color
     * @author Lorenzo Foini
     */
    public static synchronized void addPlayersColors(String color) {
        playersColors.add(color);
    }

    /**
     * players' secret cards getter
     * It is necessary to use synchronized because of threads
     *
     * @return players' secret card
     * @author Lorenzo Foini
     */
    public static synchronized ArrayList<ObjectiveCard> getPlayersSecretCards() {
        return playersSecretCards;
    }

    /**
     * Add secret card to the list of players' secret cards
     * It is necessary to use synchronized because of threads
     *
     * @param card new player's secret card
     * @author Lorenzo Foini
     */
    public static synchronized void addPlayersSecretCards(ObjectiveCard card) {
        playersSecretCards.add(card);
    }

    public static synchronized StarterCard drawStarterCard() {
        try {
            return (StarterCard) gameTable.getStarterDeck().drawTopCard();
        } catch (EmptyDeckException e) {
            throw new RuntimeException(e);
        }
    }



    /**
     * Method for generate three random cards and add iit in player's hand
     *
     * @return player's hand
     * @author Lorenzo Foini
     */
    public static ArrayList<GamingCard> fillHand() throws EmptyDeckException {
        ArrayList<GamingCard> newHand = new ArrayList<>();

        newHand.add((GamingCard) gameTable.getResourceDeck().drawTopCard());
        newHand.add((GamingCard) gameTable.getResourceDeck().drawTopCard());
        newHand.add((GamingCard) gameTable.getGoldDeck().drawTopCard());

        return newHand;
    }

    /**
     * Method for create a new player and initialise his params
     *
     * @param index representing player's index in list of players
     * @author Lorenzo Foini
     */
    public static Player createNewPlayer(int index) throws EmptyDeckException {
        String username = playersUsernames.get(index);
        int score = 0;
        Color color = Color.valueOf(playersColors.get(index).toUpperCase());
        ObjectiveCard secretObjective = playersSecretCards.get(index);
        StarterCard starterCard = (StarterCard) gameTable.getStarterDeck().drawTopCard();
        ArrayList<GamingCard> hand = new ArrayList<>(fillHand());

        boolean[][] area = new boolean[81][81];
        for (boolean[] booleans : area) {
            Arrays.fill(booleans, true);
        }
        area[40][40] = false;
        ArrayList<Card> playedCards = new ArrayList<>();
        playedCards.add(starterCard);
        PlayerArea playerArea = new PlayerArea(area, playedCards);

        return new Player(username, score, playerArea, color, secretObjective, starterCard, hand);
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