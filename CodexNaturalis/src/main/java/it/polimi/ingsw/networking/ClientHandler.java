package it.polimi.ingsw.networking;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.game.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

/**
 * Class which handles a thread representing a client connections to the server
 * It is also used to handle players' turns
 * It extends Thread and override method run
 *
 * @author Foini Lorenzo, Gallo Fabio
 */
public class ClientHandler extends Thread {
    private final Socket socket;
    private final List<Controller> controllers; // List of all controllers in the server
    private BufferedReader in;  // Receive message from client
    private PrintWriter out;  // Send message to client
    private Controller gameController; // Controller of the game in which there is the client
    private boolean joined = false;
    private String username;
    private boolean gui; //true => GUI, false => TUI
    Gson gson = new Gson(); // Gson for serialization

    /**
     * ClientHandler constructor, it assigns socket and controllers
     *
     * @param socket representing the client's socket
     * @param controllers representing all the controllers(matches) playing on the server
     * @author Gallo Fabio
     */
    public ClientHandler(Socket socket, List<Controller> controllers) {
        this.socket = socket;
        this.controllers = controllers;
    }

    /**
     * Override of method run
     * It asks the client to either join or create a game, insert the username and all the players parameters, then plays the game.
     * Catches IOException if an I/O error occurred
     * Catches game exceptions if thrown
     *
     * @author Foini Lorenzo, Gallo Fabio
     */

    @Override
    public void run() {
        try {
            // Create new input buffered reader and output print writer
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Get client selected UI
            gui = "GUI".equalsIgnoreCase(in.readLine());

            // Ask client his username by calling method askUsername()
            askUsername();

            // Ask client if he wants to create or join a game
            // It also call to method initializePlayer for asking other information
            createJoinGame();

            // Check if this client his the last one for starting the game
            if (gameController.getReady() == gameController.getGameTable().getNumPlayers() - 1) {//when the last player joins, it starts the game
                gameController.setReady(); // Increment gameController parameter ready by 1
                gameController.startGame(); // Set the first player turn and start of the game
            } else {
                gameController.setReady(); // Increment gameController parameter ready by 1

                //Now wait for all the players to be ready to play
                while (gameController.getGameTable().getNumPlayers() != gameController.getReady()) {
                    // If the game has crashed during the login-phase then exit
                    if(gameController.isCrashed()){
                        out.println("Game crashed.");
                        gameController.setDisconnectedPlayers(); // All the player disconnect
                        return;
                    }
                    Thread.onSpinWait(); // Wait
                }
            }

            // Send message to client
            out.println("Game starts now.");

            // Send gameTable to client if he is playing with GUI by calling to method updateGui()
            if(gui){
                updateGui();
            }

            // Get client's player's reference
            Player player = gameController.getGameTable().getPlayerByUsername(username);

            // Wait for your turn
            while (!player.isTurn()) {
                // If the game has crashed during the login-phase then exit
                if(gameController.isCrashed()){
                    out.println("Game crashed.");
                    gameController.setDisconnectedPlayers(); // All the player disconnect
                    return;
                }
                Thread.onSpinWait(); // Wait
            }

            // Checks if it is the last turn
            while (!gameController.getGameTable().isLastTurn()) {
                // Client plays his turn
                playTurn();

                // For the last player of the turn checks if the next turn is going to be the last
                if (gameController.getGameTable().isEnded() && username.equals(gameController.getGameTable().getPlayers().getLast().getUsername())) {
                    gameController.getGameTable().setLastTurn();
                }

                // Sets the turn of this player to false, the turn of the next player to true
                gameController.nextTurn();

                // Client wait for his turn
                while (!player.isTurn()) {
                    // If the game has crashed during the login-phase then exit
                    if(gameController.isCrashed()){
                        out.println("Game crashed.");
                        gameController.setDisconnectedPlayers(); // All the player disconnect
                        return;
                    }
                    Thread.onSpinWait(); // Wait
                }
            }

            // Send message to the client which says that the last turn has started
            sendLastTurnMessage();

            // Play last turn
            // Call to a different method since we are not going to draw a card
            playLastTurn();

            // Sets the turn of this player to false, the turn of the next player to true
            gameController.nextTurn();

            // Check if the client is the last player to finish
            if (username.equals(gameController.getGameTable().getPlayers().getLast().getUsername())) {
                // Set gameTable parameter finished to true
                gameController.getGameTable().setFinished();
            }

            // While loop until the game is not finished
            // It makes the other players wait for the last player to finish
            while (!gameController.getGameTable().isFinished()) {
                // If the game has crashed during the login-phase then exit
                if(gameController.isCrashed()){
                    out.println("Game crashed.");
                    gameController.setDisconnectedPlayers(); // All the player disconnect
                    return;
                }
                Thread.onSpinWait(); // Wait
            }

            // Now prints the final scoreboard and messages one player at a time, so they don't conflict
            while (!player.isTurn()) {
                // If the game has crashed during the login-phase then exit
                if(gameController.isCrashed()){
                    out.println("Game crashed.");
                    gameController.setDisconnectedPlayers(); // All the player disconnect
                    return;
                }
                Thread.onSpinWait(); // Wait
            }

            // Calculate final points for all the players
            gameController.calculateFinalPoints();

            // Calculate final scoreboard
            gameController.finalScoreboard();

            // Get leaderboard of the game
            LinkedHashMap<Player, Integer> leaderboard = gameController.getLeaderboard();

            // Calculate winners of the game
            ArrayList<Player> winners = gameController.calculateWinners(leaderboard);

            // Send messages which says that the client has won/lost
            sendWinnersMessage(winners, username);
            // Send final scoreboard
            sendLeaderboardMessage(leaderboard);

            // Let the other players print the final messages
            gameController.nextTurn();

            // Disconnect all the player because the game is ended
            gameController.setDisconnectedPlayers();
        } catch (IOException e) {
            // Show error message which says which client crashed
            System.err.println("Error: "+ username + " crashed.");

            // Need to set gameTable parameter crashed to true
            if(gameController!=null) {
                gameController.setCrashed();
                gameController.setDisconnectedPlayers();
            }
        } catch (EmptyObjectiveDeckException | EmptyDeckException | NoPlayerWithSuchUsernameException |
                 InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method which allow the client to play his turn (play a card and draw a new one)
     *
     * @throws IOException if an IO error occur
     * @author Foini Lorenzo, Gallo Fabio
     */
    private synchronized void playTurn() throws IOException {

        // Play card "phase"
        // Ask active player his play and send messages
        sendSelectPlayMessage();
        askPlay(username);
        sendCorrectPlayMessage();

        // draw card "phase" and send messages
        sendSelectDrawMessage();
        askDraw(username);
        sendCorrectDrawMessage();

        // Send turn messages
        sendFinishTurnMessage();
        sendWaitTurnMessage();
    }

    /**
     * Method which allow the client to play his last turn (play a card)
     *
     * @throws IOException if an IO error occur
     * @throws InterruptedException if happen
     * @author Foini Lorenzo, Gallo Fabio
     */
    private synchronized void playLastTurn() throws IOException, InterruptedException {

        // Play card "phase"
        // Ask active player his play and send messages
        sendSelectPlayMessage();
        askPlay(username);
        sendCorrectPlayMessage();

        // Send turn messages
        sendFinishTurnMessage();
        sendWaitFinishGameMessage();
    }

    /**
     * Method to ask client's username
     * It assigns to the parameter username the client's username
     *
     * @throws IOException if connection lost
     * @author Foini Lorenzo
     */
    public void askUsername() throws IOException {
        out.println("Insert your username:"); // Display message
        username = in.readLine(); // Get client input

        // Check if username is valid: already present or empty
        while (Server.getClientsUsername().contains(username) || username.isEmpty()) {
            if(username.isEmpty()) {
                out.println("Invalid username. Please insert a valid username:"); // EMPTY username
            }else {
                out.println("Username already in use. Please insert a new username:"); // INVALID username
            }

            // If client is playing with GUI, then send the previous username
            // It will be used for displaying a new type of window
            if(gui){
                out.println(username); // Previous invalid username
            }

            // Get client username from input
            username = in.readLine();
        }

        // Insert username in server's list of usernames
        Server.addClientUsername(username);
    }

    /**
     * Method to ask client if he wants to create or join a game
     *
     * @throws IOException if connection lost
     * @throws EmptyObjectiveDeckException if the objective deck is empty
     * @throws EmptyDeckException if deck of resource card or gaming card is empty
     * @author Foini Lorenzo
     */
    public void createJoinGame() throws IOException, EmptyObjectiveDeckException, EmptyDeckException {
        //it allows the client to create or join a match
        while (!joined) {
            // Ask player choice
            out.println("Do you want to create a new game or join a game? (insert create/join):");
            // If the client is playing with GUI, then send him the number of game that can be joined
            if(gui){
                out.println(Server.countGameNotFull());
            }
            String choice = in.readLine();

            if ("create".equalsIgnoreCase(choice)) { //if it creates a match, it asks how many players should play
                out.println("Enter number of players (insert 2/3/4):");
                String numPlayers = in.readLine();
                // Iterate until client select a valid number
                while (!numPlayers.equals("2") && !numPlayers.equals("3") && !numPlayers.equals("4")) {
                    out.println("Please insert 2/3/4:");
                    numPlayers = in.readLine();
                }
                int numberOfPlayers = Integer.parseInt(numPlayers);

                // Create the gameTable inside the controller
                gameController = new Controller(numberOfPlayers);

                // Call to method initializePlayer for getting all the other info
                joined = initializePlayer(username);

                // Increment gameTable parameter joined by 1
                gameController.getGameTable().setJoined(joined);

                // Add this match to the matches that can be now joined
                Server.addController(gameController);

                out.println("Game created. Waiting for players...");
            } else if ("join".equalsIgnoreCase(choice)) { // Client wants to join a game
                ArrayList<String> gameNotFull = new ArrayList<>(); // List of games that can be joined
                gameNotFull.add("0"); // Exit command => Back to create/join question

                int i = 1;
                // If client is playing with GUI, then the handler sends some prefixed messages for identification
                if(gui) {
                    out.println("Sending games and players");
                }
                // Check the existent games that the player can join
                for (Controller controller : new ArrayList<>(controllers)) {
                    // Check if the game is not already full of players
                    if (!controller.getGameTable().isFull()) {
                        out.println("Game " + i);
                        // Print the username of the players that are in the game
                        for (Player p : controller.getGameTable().getPlayers()) {
                            out.println("-" + p.getUsername());
                        }
                        // Add index i to the game that can be joined
                        gameNotFull.add(String.valueOf(i));
                    }
                    i++;
                }
                // Send end message if client plays with GUI for identification
                if(gui) {
                    out.println("End sending games and players");
                }

                // Check number of game that can be joined (1 => Exit)
                if(gameNotFull.size() > 1){
                    out.println("Which game you want to join (insert 0 to exit):");
                    String ans = in.readLine(); // Client choice
                    // Check client choice
                    while (!gameNotFull.contains(ans)) {
                        out.println("Please insert a valid number of game (insert 0 to exit):");
                        ans = in.readLine();
                    }

                    // Client join a game
                    if (!ans.equals("0")) {
                        // Get controller of the joined game
                        gameController = controllers.get(Integer.parseInt(ans)-1); // Start with 1, not 0
                        // Increment gameTable parameter joined by 1
                        gameController.getGameTable().setJoined(true);
                        joined = initializePlayer(username);
                        if (joined) { // Client has insert all of its info correctly
                            out.println("Joined a game. Waiting for players...");
                        } else { // Decrement number of player joined because of a fail/disconnection
                            gameController.getGameTable().setJoined(false);
                        }
                    }
                }else {
                    // There aren't games that can be joined
                    out.println("No game to join.");
                }
            }
        }
    }

    /**
     * Method to ask client which color he wants to use
     *
     * @return the selected color as a string
     * @throws IOException if connection lost
     * @author Foini Lorenzo, Gallo Fabio
     */
    public String askColor() throws IOException {
        String selectedColor;
        // Get list of available colors and display them
        ArrayList<String> availableColors = gameController.getAvailableColors();
        out.println("Choose a color from this list:");
        for (String color : availableColors) {
            out.println(color);
        }

        // If client is playing with GUI, then the handler send a prefixed message for identification
        if(gui){
            out.println("End color");
        }

        out.println("Insert your color:"); // Display message
        selectedColor = in.readLine().toLowerCase(); // Get client's input

        // Check color selection in real time => Use gameController.getAvailableColors()
        while (!gameController.getAvailableColors().contains(selectedColor)) {
            out.println("Invalid color. Please select a color from the previous list:"); // INVALID
            // If clint is playing with GUI, then send the previous selected color
            if(gui){
                out.println(selectedColor); // Client's previous selected color
            }
            selectedColor = in.readLine().toLowerCase();
        }

        // Remove selected color from gameController's available list of colors
        gameController.removeAvailableColor(selectedColor);

        return selectedColor;
    }

    /**
     * Method to ask client which side of the starter card he wants to play
     *
     * @return the selected side as a boolean
     *         true => Front
     *         false => Back
     * @throws IOException if connection lost
     * @author Foini Lorenzo, Gallo Fabio
     */
    public boolean askStarterCard(StarterCard starterCard) throws IOException {
        // Check client UI
        // If TUI => Call to viewTUI method for displaying such started card
        if(!gui){
            gameController.getViewTui().displayStarterCard(starterCard, out);
        }

        // Ask client on which side he wants to play the starter card
        out.println("On which side you want to play the starter card (insert front/back):");
        // Send ID of teh starter card if the client is playing with GUI
        if(gui){
            out.println(starterCard.getID());
        }
        String stringSide = in.readLine().toLowerCase(); // Get client's input

        // Check input
        while (!stringSide.equals("front") && !stringSide.equals("back")) {
            out.println("Insert front/back:"); // INVALID
            stringSide = in.readLine().toLowerCase();
        }

        return stringSide.equals("front");// true => front, false => back
    }

    /**
     * Method to ask which secret card the client wants to use
     * It show to client his starter card, his hand, two common objective and the two secret objective
     *
     * @param starterCard: client's starter card
     * @param hand: client's hand
     * @param commonObjective: game common objective
     * @param secretCard1: first secret objective card
     * @param secretCard2: second secret objective card
     * @return the selected secret objective card
     * @throws IOException if connection lost
     * @author Foini Lorenzo, Gallo Fabio
     */
    public ObjectiveCard askSecreteObjective(StarterCard starterCard, ArrayList<GamingCard> hand, ObjectiveCard[] commonObjective, ObjectiveCard secretCard1, ObjectiveCard secretCard2) throws IOException{
        // Check client UI
        // If TUI => Call to viewTUI method for displaying such started card
        if(!gui) {
            // Show client's hand
            out.println("\nThis is your hand:\n");
            gameController.getViewTui().displayResourceCard(hand.get(0), out); // Call to ViewTUI method
            gameController.getViewTui().displayResourceCard(hand.get(1), out); // Call to ViewTUI method
            gameController.getViewTui().displayGoldCard((GoldCard) hand.get(2), out); // Call to ViewTUI method

            // Show the two common objective cards
            out.println("\nThis are the two common objectives:\n");
            gameController.getViewTui().displayObjectiveCard(commonObjective[0], out); // Call to ViewTUI method
            gameController.getViewTui().displayObjectiveCard(commonObjective[1], out); // Call to ViewTUI method

            // Ask client to select his secret objective cards from two different objective cards
            out.println("Now you have to choose which secret objective card you want to use.");
            out.println("You can choose one card from the following two objective cards\n");

            // Display the two secret objective cards
            gameController.getViewTui().displayObjectiveCard(secretCard1, out);
            gameController.getViewTui().displayObjectiveCard(secretCard2, out);
        }

        // Ask choice to client (1 => First objective card, 2 => Second objective card)
        String stringChoice;
        do {
            out.println("Select your secret objective card (insert 1/2):");

            // Send IDs of: starter card (and his side), hand's cards, common objectives, secret objectives
            if(gui){
                if(starterCard.getSide()) out.println("front");
                else out.println("back");
                out.println(starterCard.getID());
                out.println(hand.get(0).getID());
                out.println(hand.get(1).getID());
                out.println(hand.get(2).getID());
                out.println(commonObjective[0].getID());
                out.println(commonObjective[1].getID());
                out.println(secretCard1.getID());
                out.println(secretCard2.getID());
            }

            stringChoice = in.readLine();
        } while (!stringChoice.equals("1") && !stringChoice.equals("2"));

        // Return the selected card given the index
        if (stringChoice.equals("1")) return secretCard1;
        return secretCard2;
    }

    /**
     * Method to initialize all the player parameters
     *
     * @param username players' username
     * @throws IOException if connection lost
     * @throws EmptyDeckException if a deck is empty
     * @throws EmptyObjectiveDeckException if the objective deck is empty (never happen)
     * @author Foini Lorenzo, Gallo Fabio
     */
    public boolean initializePlayer(String username) throws IOException, EmptyDeckException, EmptyObjectiveDeckException {
        // Ask client his color
        String selectedColor = askColor();

        // Create the starter card
        StarterCard starterCard;
        try {
            starterCard = (StarterCard) gameController.getGameTable().getStarterDeck().drawTopCard();
        } catch (EmptyDeckException e) {
            throw new RuntimeException(e);
        }

        // Show to the client his starter card and ask for its placement
        // Assign such side to starter card
        boolean sideStarterCard = askStarterCard(starterCard);
        starterCard.setSide(sideStarterCard);

        // Create client's hand
        ArrayList<GamingCard> hand = new ArrayList<>();
        hand.add((GamingCard) gameController.getGameTable().getResourceDeck().drawTopCard());
        hand.add((GamingCard) gameController.getGameTable().getResourceDeck().drawTopCard());
        hand.add((GoldCard) gameController.getGameTable().getGoldDeck().drawTopCard());

        // Get game common objective
        ObjectiveCard[] commonObjective = gameController.getGameTable().getCommonObjectives();

        // Draw two objective cards from objective deck
        ObjectiveCard secretCard1 = gameController.getGameTable().getObjectiveDeck().drawTopCard();
        ObjectiveCard secretCard2 = gameController.getGameTable().getObjectiveDeck().drawTopCard();

        // Show to client his starter card, his hand, the common objective and ask for his secret objective
        ObjectiveCard secretObjectiveCard = askSecreteObjective(starterCard, hand, commonObjective, secretCard1, secretCard2);

        // Call to controller method for create new player with client's information
        gameController.createNewPlayer(username, selectedColor, starterCard, hand, secretObjectiveCard);

        // Correct initialization of player's parameters
        return true;
    }

    /**
     * Method to ask the client which card he wants to play, its side and where to play the card
     *
     * @param username of the client
     * @throws IOException if connection is lost
     * @author Foini Lorenzo, Gallo Fabio
     */
    public void askPlay(String username) throws IOException {
        // Display player's area and his hand
        out.println("This is your game area:");
        try {
            gameController.getViewTui().displayArea(gameController.getGameTable().getPlayerByUsername(username).getPlayerArea().getCards(), out);
        } catch (NoPlayerWithSuchUsernameException e) {
            throw new RuntimeException(e);
        }

        // Call to View's function for display hand
        try {
            // Get the player's hand and display it
            ArrayList<GamingCard> handToPrint = gameController.getGameTable().getPlayerByUsername(username).getHand();
            gameController.getViewTui().displayHand(handToPrint, out);
        } catch (NoPlayerWithSuchUsernameException e) {
            out.println(e.getMessage());
        }

        // Ask player which card he wants to play from his hand
        out.println("Which card you want to play (insert 1/2/3):");

        // Send gameTable to client if he's playing with GUI
        if(gui){
            updateGui();
        }

        // Get client's selected card to be played
        String stringPositionCardHand = in.readLine();

        // Check client's input
        while (!stringPositionCardHand.equals("1") && !stringPositionCardHand.equals("2") && !stringPositionCardHand.equals("3")) {
            out.println("Please insert 1/2/3:");
            stringPositionCardHand = in.readLine();
        }
        int cardPositionHand = Integer.parseInt(stringPositionCardHand); // Parse to int

        // Ask player which side to play such card
        out.println("On which side you want to play this card (insert front/back):");
        String stringSide = in.readLine().toLowerCase(); // Get client's input

        // Check user input
        while (!stringSide.equals("front") && !stringSide.equals("back")) {
            out.println("Insert front/back:"); // INVALID
            stringSide = in.readLine().toLowerCase();
        }
        boolean sideCardToPlay = stringSide.equals("front");

        // Ask where he wants to play that card in his game area
        out.println("Given your game area, now choose the position in the area where you want to play such card.");
        out.println("Insert integer row value (from 0 to 80):");
        int row = Integer.parseInt(in.readLine());
        out.println("Insert integer column value (from 0 to 80):");
        int column = Integer.parseInt(in.readLine());
        // Throws exception later if position is not valid

        int[] positionArea = new int[2];
        positionArea[0] = row;
        positionArea[1] = column;

        // Use controller's method for play this card
        try {
            // Play card
            Player player = gameController.getGameTable().getPlayerByUsername(username);
            player.playCard(cardPositionHand, positionArea, sideCardToPlay);

            // Assign score to the player after the play of the card
            gameController.getGameTable().assignScore(player, player.getScore());

            // Show messages for correct play and his points
            out.println("\nThe card has been played correctly.");
            out.println("This is your score after this play: " + gameController.getGameTable().getPlayerByUsername(username).getScore() + " pts.");
        } catch (NoPlayerWithSuchUsernameException | InvalidPlayCardIndexException | InvalidPositionAreaException |
                 InvalidPlayException e) {
            // An error has occur: invalid play
            out.println("\nInvalid play.\n" + e.getMessage());
            out.println("Now you will be asked to insert again all the information.\n");

            // Recall this function for ask again all the information about the play
            askPlay(username);
        }
    }

    /**
     * Method to ask the client from where he wants to draw his next card
     *
     * @throws IOException if connection is lost
     * @author Foini Lorenzo, Gallo Fabio
     */
    public void askDraw(String username) throws IOException {
        // Display the back of the top card of resource deck, if present
        ArrayList<Card> resourceDeck = gameController.getGameTable().getResourceDeck().getDeck();
        if(!resourceDeck.isEmpty()){
            GamingCard topResourceCard = (GamingCard) resourceDeck.getLast();
            gameController.getViewTui().displayTopResource(topResourceCard, out);
        }

        // Display the back of the top card of gold deck, if present
        ArrayList<Card> goldDeck = gameController.getGameTable().getGoldDeck().getDeck();
        if(!resourceDeck.isEmpty()){
            GoldCard topGoldCard = (GoldCard) goldDeck.getLast();
            gameController.getViewTui().displayTopGold(topGoldCard, out);
        }

        // Display the 4 visible drawable cards, if presents
        ArrayList<GamingCard> visibleCards = gameController.getGameTable().getVisibleCard();
        if(!visibleCards.isEmpty()){
            gameController.getViewTui().displayVisibleTableCard(visibleCards, out);
        }

        // Ask client's choice
        out.println("You can draw from:\n- Resource deck (insert 1).\n- Gold deck (insert 2).\n- One of the four cards present in the table (insert 3).");
        out.println("Insert 1/2/3:");

        // Send update message to GUI
        if(gui){
            updateGui();
        }

        // Get client's input
        String stringChoice = in.readLine();

        // Check client's input
        while (!stringChoice.equals("1") && !stringChoice.equals("2") && !stringChoice.equals("3")) {
            out.println("Please insert 1/2/3:");
            stringChoice = in.readLine();
        }
        int choice = Integer.parseInt(stringChoice); // Parse to int

        // Switch case based on choice
        switch (choice) {
            case 1: {
                // Draw card from resource deck
                try {
                    // Draw card
                    GamingCard cardToDraw = gameController.getGameTable().drawResourceCardDeck();
                    // Add card in player's hand if possible
                    gameController.getGameTable().getPlayerByUsername(username).addCardHand(cardToDraw);

                } catch (EmptyDeckException | NoPlayerWithSuchUsernameException | HandAlreadyFullException e) {
                    // Print exception message
                    out.println(e.getMessage());
                    out.println("Select a different type of draw.");

                    // Recall this function for asking again the draw
                    askDraw(username);
                }
                break;
            }
            case 2: {
                // Draw card from gold deck
                try {
                    // Draw card
                    GoldCard cardToDraw = gameController.getGameTable().drawGoldCardDeck();
                    // Add card in player's hand if possible
                    gameController.getGameTable().getPlayerByUsername(username).addCardHand(cardToDraw);
                } catch (EmptyDeckException | NoPlayerWithSuchUsernameException | HandAlreadyFullException e) {
                    // Print exception message
                    out.println(e.getMessage());
                    out.println("Select a different type of draw.");

                    // Recall this function for asking again the draw
                    askDraw(username);
                }
                break;
            }
            case 3: {
                int size = gameController.getGameTable().getVisibleCard().size();
                // Check size of the visible cards in the table
                if (size == 0) { // No cards
                    out.println("There are no cards in table.\nSelect a different type of draw.");
                    // Recall this function for asking again the draw
                    askDraw(username);

                } else if (size == 1) { // Only one card, so draw it
                    out.println("There is only 1 card in the table, so draw this card.");
                    try {
                        // Call to method and add card to hand
                        GamingCard cardToDraw = gameController.getGameTable().drawCardFromTable(0);
                        gameController.getGameTable().getPlayerByUsername(username).addCardHand(cardToDraw);
                    } catch (InvalidDrawFromTableException | NoPlayerWithSuchUsernameException |
                             HandAlreadyFullException e) {
                        out.println(e.getMessage());
                        // Recall this function for asking again
                        askDraw(username);
                    }
                } else { // Ask client which card he wants to draw
                    out.println("Insert the card's number (insert 1/2/3/4):");
                    int selectedPosition = Integer.parseInt(in.readLine());
                    try {
                        // Call to method and add card to hand
                        GamingCard cardToDraw = gameController.getGameTable().drawCardFromTable(selectedPosition - 1);
                        gameController.getGameTable().getPlayerByUsername(username).addCardHand(cardToDraw);
                    } catch (InvalidDrawFromTableException | NoPlayerWithSuchUsernameException |
                             HandAlreadyFullException e) {
                        // Print exception message
                        out.println(e.getMessage());
                        // Recall this function for asking again the draw
                        askDraw(username);
                    }
                }
                break;
            }
        }
    }

    /**
     * Method for sending to the client a message
     * It says that is client's turn
     *
     * @author Gallo Fabio
     */
    public void sendSelectPlayMessage() {
        out.println("It's your turn.\nNow you have to play a card from your hand.\n");
    }

    /**
     * Method for sending to the client a message
     * It says that the client has correctly played a card
     *
     * @author Gallo Fabio
     */
    public void sendCorrectPlayMessage() {
        out.println("You have correctly play the card in your game area.");
        out.println("That card has been removed from your hand.");
    }

    /**
     * Method for sending to the client a message
     * It says that the clint has to choose from where he wants to draw
     *
     * @author Gallo Fabio
     */
    public void sendSelectDrawMessage() {
        out.println("\nNow you have to select from where you want to draw your next card.");
    }

    /**
     * Method for sending to the client a message
     * It says that the client has correctly drawn a card
     *
     * @author Gallo Fabio
     */
    public void sendCorrectDrawMessage() {
        out.println("\nYou have correctly draw a new card and added it in your hands.");
    }

    /**
     * Method for sending to the client a message
     * It says that client's turn is finished
     *
     * @author Gallo Fabio
     */
    public void sendFinishTurnMessage() {
        out.println("You have correctly play your turn.");
        if(gui) updateGui();
    }

    /**
     * Method for sending to the client a wait message
     *
     * @author Gallo Fabio
     */
    public void sendWaitTurnMessage() {
        out.println("\nPlease wait for your turn.\n");
    }

    /**
     * Method for sending to the client a message
     * It says that the last turn will now start
     *
     * @author Gallo Fabio
     */
    public void sendLastTurnMessage() {
        out.println("A player has reached 20 points, so the last turn will now start.");
    }

    /**
     * Method for sending to the client a wait message
     *
     * @author Gallo Fabio
     */
    public void sendWaitFinishGameMessage() {
        out.println("\nWait for others players' last turns.\nThe game will end soon");
    }

    /**
     * Method for sending to the client a message
     * It says who is/are the winner(s) and if the client has won/lost
     *
     * @author Gallo Fabio
     */
    public void sendWinnersMessage(ArrayList<Player> winners, String username) {
        boolean hasWon = false; // true: player has won, false: player has lost.

        out.println("\nGAME OVER.\n");
        if (winners.size() == 1) {
            out.print("THE WINNER IS ... ");
        } else {
            out.print("THE WINNERS ARE ... ");
        }

        for(int i = 0; i < winners.size(); i++){
            String winnerUsername = winners.get(i).getUsername();
            if(i == winners.size()-1) out.println(winnerUsername+"\n");
            else out.print(winnerUsername + ", ");
            if(winnerUsername.equals(username)) hasWon = true;
        }

        // Send message to show if the player has won or lost
        if (hasWon) {
            out.println("CONGRATULATIONS, YOU WON!!!\n");
        } else {
            out.println("SORRY, YOU LOST.\n");
        }
    }

    /**
     * Method for sending to the client a message
     * It sends the final leaderboard of the game
     *
     * @author Gallo Fabio
     */
    public void sendLeaderboardMessage(LinkedHashMap<Player, Integer> leaderboard) {
        // Send final leaderboard message
        out.println("Final scoreboard:");

        // If a player has a different score respect to the last one, then "update" his final position.
        int currentIndex = 1;
        int lastScore = -1; // Keep track of last score
        int lastNumObjectivesSatisfied = -1; // Keep track of last number of objectives satisfied
        String lastPosition = ""; // Keep track of last position

        for (Map.Entry<Player, Integer> entry : leaderboard.entrySet()) {
            int currentScore = entry.getValue(); // Get player's score
            int currentNumObjectiveSatisfied = entry.getKey().getNumObjectivesSatisfied(); // Get player's number of objectives satisfied
            if (currentScore != lastScore || currentNumObjectiveSatisfied != lastNumObjectivesSatisfied) {
                String position = currentIndex + getSuffix(currentIndex); // "Update" final position
                out.println(position + entry.getKey().getUsername() + ", score: " + currentScore + ", number of objectives satisfied: " + currentNumObjectiveSatisfied);
                lastPosition = position; // Assign current position to previous position
                lastScore = currentScore; // Assign current score to previous score
                lastNumObjectivesSatisfied = currentNumObjectiveSatisfied; // Assign previous number of objectives satisfied to the previous one
            } else {
                out.println(lastPosition + entry.getKey().getUsername() + ", score: " + currentScore + ", number of objectives satisfied: " + currentNumObjectiveSatisfied);
            }
            currentIndex++;
        }

        // Send end game message
        out.println("\nTHANKS FOR PLAYING, the connection will now be reset.\n");
    }

    /**
     * Method for get index of the leaderboard
     *
     * @param index of the position
     * @author Gallo Fabio
     */
    public String getSuffix(int index) {
        if (index == 1) {
            return "st: ";
        } else if (index == 2) {
            return "nd: ";
        } else if (index == 3) {
            return "rd: ";
        }
        return "th: ";
    }

    /**
     * Method for sending to the client a JSON version of the gameTable
     *
     * @author Foini Lorenzo
     */
    private void updateGui(){
        GameTable gameTable = gameController.getGameTable();
        String gameTableJson = gson.toJson(gameTable);
        out.println(gameTableJson);

        sendCounterResources();
    }

    /**
     * Method for sending to the client the counters of the resources in his area
     *
     * @author Foini Lorenzo
     */
    private void sendCounterResources(){
        try {
            // Get client's player's reference
            PlayerArea playerArea = gameController.getGameTable().getPlayerByUsername(username).getPlayerArea();
            // Count all the resources and send them to the client
            out.println(playerArea.countKingdoms(Kingdom.ANIMALKINGDOM));
            out.println(playerArea.countKingdoms(Kingdom.FUNGIKINGDOM));
            out.println(playerArea.countKingdoms(Kingdom.INSECTKINGDOM));
            out.println(playerArea.countKingdoms(Kingdom.PLANTKINGDOM));
        } catch(NoPlayerWithSuchUsernameException e) {
            System.out.println(e.getMessage());
        }
    }
}