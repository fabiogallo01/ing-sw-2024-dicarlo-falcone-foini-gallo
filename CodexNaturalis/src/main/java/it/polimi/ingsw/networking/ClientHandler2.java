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
 * @author Foini Lorenzo
 * @author Gallo Fabio
 */
public class ClientHandler2 extends Thread {
    private Socket socket;
    private List<Controller> controllers;
    private BufferedReader in;
    private PrintWriter out;
    private Controller gameController;
    private boolean joined = false;
    private String username;
    private boolean gui; //true => GUI, false => TUI
    Gson gson = new Gson(); // Gson for serialization

    /**
     * ViewClientHandler constructor, it assigns/creates all class's parameters
     *
     * @param socket      representing the client's socket
     * @param controllers representing all the controllers(matches) playing on the server
     * @author Foini Lorenzo
     * @author Gallo Fabio
     */
    public ClientHandler2(Socket socket, List<Controller> controllers) {
        this.socket = socket;
        this.controllers = controllers;
    }

    /**
     * Override of method run
     * It asks the client to either join or create a game, insert the username and all the players parameters, then plays the game.
     * Catches IOException if an I/O error occurred
     * Catches game exceptions
     *
     * @author Foini Lorenzo
     */

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            gui = "GUI".equalsIgnoreCase(in.readLine());

            // Ask client his username
            askUsername();

            // Ask client if he wants to create or join a game
            // It also call to method initializePlayer for asking other information
            createJoinGame();

            if (gameController.getReady() == gameController.getGameTable().getNumPlayers() - 1) {//when the last player joins, it starts the game
                gameController.setReady();
                gameController.startGame();//it sets the first player turn
            } else {
                gameController.setReady();
                while (gameController.getGameTable().getNumPlayers() != gameController.getReady()) {//it waits for all the players to be ready to play
                    Thread.onSpinWait();
                }
            }

            // TODO: Modify the following lines for handling turns with TUI and GUI (now it works only with TUI)

            out.println("Game starts now.");

            // Send gameTable to client is he is playing with GUI
            if(gui){
                GameTable gameTable = gameController.getGameTable();
                Gson gson = new Gson();
                String gameTableJson = gson.toJson(gameTable);
                out.println(gameTableJson);
            }

            Player player = gameController.getGameTable().getPlayerByUsername(username);

            while (!player.isTurn()) {//waits for your turn
                Thread.onSpinWait();
            }

            while (!gameController.getGameTable().isLastTurn()) {//it checks if it is the last turn
                playTurn();//plays the whole turn

                if (gameController.getGameTable().isEnded() && username.equals(gameController.getGameTable().getPlayers().getLast().getUsername())) {//the last player of the turn checks if the next turn is going to be the last
                    gameController.getGameTable().setLastTurn();
                }
                gameController.nextTurn();//sets the turn of this player to false, the turn of the next player to true
                while (!player.isTurn()) { // Client wait for his turn
                    Thread.onSpinWait();
                }
            }
            sendLastTurnMessage();

            playLastTurn();
            gameController.nextTurn();

            if (username.equals(gameController.getGameTable().getPlayers().getLast().getUsername())) {//the last player signals that he finished
                gameController.getGameTable().setFinished();
            }
            while (!gameController.getGameTable().isFinished()) {//it makes the other players wait for the last player to finish
                Thread.onSpinWait();
            }
            while (!player.isTurn()) {//it prints the final scoreboard and messages one player at a time, so they don't conflict
                Thread.onSpinWait();
            }
            gameController.calculateFinalPoints();
            gameController.finalScoreboard();

            LinkedHashMap<Player, Integer> leaderboard = gameController.getLeaderboard();

            ArrayList<Player> winners = gameController.calculateWinners(leaderboard);

            sendWinnersMessage(winners, username);
            sendLeaderboardMessage(leaderboard);
            gameController.nextTurn();//it lets the other players print the final messages

        } catch (IOException e) {
            e.printStackTrace();
        } catch (EmptyObjectiveDeckException | EmptyDeckException | NoPlayerWithSuchUsernameException |
                 InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Plays the turn of a player, by playing a card and drawing one
     *
     * @author Foini Lorenzo
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
     * Plays the last turn of a player, just by playing a card
     *
     * @author Foini Lorenzo
     * @author Gallo Fabio
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
        while (Server2.getClientsUsername().contains(username) || username.isEmpty()) {
            if(username.isEmpty()) {
                out.println("Invalid username. Please insert a valid username:");
            }else {
                out.println("Username already in use. Please insert a new username:"); // INVALID
            }
            username = in.readLine();
        }

        // Insert username in server's list of usernames
        Server2.addClientUsername(username);
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
                out.println(Server2.countGameNotFull());
            }
            String choice = in.readLine();

            if ("create".equalsIgnoreCase(choice)) {//if it creates a match, it asks how many players should play
                out.println("Enter number of players (insert 2/3/4):");
                String numPlayers = in.readLine();
                while (!numPlayers.equals("2") && !numPlayers.equals("3") && !numPlayers.equals("4")) {
                    out.println("Please insert 2/3/4:");
                    numPlayers = in.readLine();
                }
                int numberOfPlayers = Integer.parseInt(numPlayers);

                gameController = new Controller(numberOfPlayers);//it creates the gameTable inside the controller
                joined = initializePlayer(username);//inserts all the player info
                gameController.getGameTable().setJoined(joined);
                Server2.addController(gameController);//it adds the match to the matches that can be now joined

                out.println("Game created. Waiting for players...");
            } else if ("join".equalsIgnoreCase(choice)) {
                ArrayList<String> gameNotFull = new ArrayList<>(); // List of games that can be joined
                gameNotFull.add("0"); // Exit command
                int i = 1;
                // If client is playing with GUI, then the handler sends some prefixed messages
                if(gui) {
                    out.println("Sending games and players");
                }
                for (Controller controller : new ArrayList<>(controllers)) {// it checks the existent games that the player can join
                    if (!controller.getGameTable().isFull()) {//it prints the username of the players that are in the game
                        out.println("Game " + i);
                        for (Player p : controller.getGameTable().getPlayers()) {
                            out.println("-" + p.getUsername());
                        }
                        gameNotFull.add(String.valueOf(i));
                    }
                    i++;
                }
                if(gui) {
                    out.println("End sending games and players");
                }
                if(gameNotFull.size() > 1){
                    out.println("Which game you want to join (insert 0 to exit):");
                    String ans = in.readLine();
                    while (!gameNotFull.contains(ans)) {
                        out.println("Please insert a valid number of game (insert 0 to exit):");
                        ans = in.readLine();
                    }

                    if (!ans.equals("0")) {
                        gameController = controllers.get(Integer.parseInt(ans)-1);
                        gameController.getGameTable().setJoined(true);
                        joined = initializePlayer(username);
                        if (joined) {
                            out.println("Joined a game. Waiting for players...");
                        } else {
                            gameController.getGameTable().setJoined(false);
                        }
                    }
                }else {
                    out.println("No game to join.");
                }
            }
        }
    }

    /**
     * Method to ask client which color he wants to use
     *
     * @return the selected color
     * @throws IOException if connection lost
     * @author Foini Lorenzo
     */
    public String askColor() throws IOException {
        String selectedColor;
        ArrayList<String> availableColors = gameController.getAvailableColors();
        out.println("Choose a color from this list:");
        for (String color : availableColors) {
            out.println(color);
        }

        // If client is playing with GUI, then the handler send a prefixed message
        if(gui){
            out.println("End color");
        }

        out.println("Insert your color:"); // Display message
        selectedColor = in.readLine().toLowerCase(); // Get client's input

        // Check color selection in real time => Use gameController.getAvailableColors()
        while (!gameController.getAvailableColors().contains(selectedColor)) {
            out.println("Invalid color. Please select a color from the previous list:"); // INVALID
            selectedColor = in.readLine().toLowerCase();
        }

        // Remove such color from available list
        gameController.removeAvailableColor(selectedColor);

        return selectedColor;
    }

    /**
     * Method to ask client which color he wants to use
     *
     * @return the selected color
     * @throws IOException if connection lost
     * @author Foini Lorenzo
     */
    public boolean askStarterCard(StarterCard starterCard) throws IOException {
        // Check client UI
        // If GUI => Send to client the starter card's ID
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
     * Method to ask secret card he wants to use
     * It show to client his starter card, his hand, two common objective and the two secret objective
     *
     * @param starterCard: client's starter card
     * @param hand: client's hand
     * @param commonObjective: game common objective
     * @param secretCard1: first secret objective card
     * @param secretCard2: second secret objective card
     * @return the selected secret objective card
     * @throws IOException if connection lost
     * @author Foini Lorenzo
     */
    public ObjectiveCard askSecreteObjective(StarterCard starterCard, ArrayList<GamingCard> hand, ObjectiveCard[] commonObjective, ObjectiveCard secretCard1, ObjectiveCard secretCard2) throws IOException{
        // Check client UI
        // If GUI => Send to client the starter card's ID
        // If TUI => Call to viewTUI method for displaying such started card
        if(!gui) {
            // Show client's hand
            out.println("\nThis is your hand:\n");
            gameController.getViewTui().displayResourceCard(hand.get(0), out); // Call to ViewTUI's method
            gameController.getViewTui().displayResourceCard(hand.get(1), out); // Call to ViewTUI's method
            gameController.getViewTui().displayGoldCard((GoldCard) hand.get(2), out); // Call to ViewTUI's method

            // Show the two common objective cards
            out.println("\nThis are the two common objectives:\n");
            gameController.getViewTui().displayObjectiveCard(commonObjective[0], out); // Call to ViewTUI's method
            gameController.getViewTui().displayObjectiveCard(commonObjective[1], out); // Call to ViewTUI's method

            // Ask client to select his secret objective cards from two different objective cards
            out.println("Now you have to choose which secret objective card you want to use.");
            out.println("You can choose one card from the following two objective cards\n");
            // Display the two secret objective cards
            gameController.getViewTui().displayObjectiveCard(secretCard1, out);
            gameController.getViewTui().displayObjectiveCard(secretCard2, out);
        }

        // Ask choice to client (1 => First card, 2 => Second card)
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

        if (stringChoice.equals("1")) return secretCard1;
        return secretCard2;
    }

    /**
     * Method to initialize all the player parameters
     *
     * @param username players' username
     * @author Foini Lorenzo
     * @author Gallo Fabio
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
        return true;
    }

    /**
     * Method to ask the client which card he wants to play, its side and where to play the card
     *
     * @author Foini Lorenzo
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

        // Send gameTable to client is he is playing with GUI
        if(gui){
            GameTable gameTable = gameController.getGameTable();
            Gson gson = new Gson();
            String gameTableJson = gson.toJson(gameTable);
            out.println(gameTableJson);
        }

        String stringPositionCardHand = in.readLine();

        // Check user input
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
            gameController.getGameTable().getPlayerByUsername(username).playCard(cardPositionHand, positionArea, sideCardToPlay);

            // Show messages
            out.println("\nThe card has been played correctly.");
            out.println("This is your score after this play: " + gameController.getGameTable().getPlayerByUsername(username).getScore() + " pts.");
        } catch (NoPlayerWithSuchUsernameException | InvalidPlayCardIndexException | InvalidPositionAreaException |
                 InvalidPlayException e) {
            // An error has occur
            out.println("\nInvalid play." + e.getMessage());
            out.println("Now you will be asked to insert again all the information.\n");

            // Recall this function for ask again all the information
            askPlay(username);
        }
    }

    /**
     * Method to ask the client from where he wants to draw his next card
     *
     * @author Foini Lorenzo
     */
    public void askDraw(String username) throws IOException {
        // Display the back of the top card of resource deck
        ArrayList<Card> resourceDeck = gameController.getGameTable().getResourceDeck().getDeck();
        GamingCard topResourceCard = (GamingCard) resourceDeck.getLast();
        gameController.getViewTui().displayTopResource(topResourceCard, out);

        // Display the back of the top card of gold deck
        ArrayList<Card> goldDeck = gameController.getGameTable().getGoldDeck().getDeck();
        GoldCard topGoldCard = (GoldCard) goldDeck.getLast();
        gameController.getViewTui().displayTopGold(topGoldCard, out);

        // Display the 4 visible drawable cards
        ArrayList<GamingCard> visibleCards = gameController.getGameTable().getVisibleCard();
        gameController.getViewTui().displayVisibleTableCard(visibleCards, out);

        // Ask user's choice
        out.println("You can draw from:\n- Resource deck (insert 1).\n- Gold deck (insert 2).\n- One of the four cards present in the table (insert 3).");
        out.println("Insert 1/2/3:");
        String stringChoice = in.readLine();

        // Check user input
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
                    // Add card in player's hand
                    gameController.getGameTable().getPlayerByUsername(username).addCardHand(cardToDraw);

                } catch (EmptyDeckException | NoPlayerWithSuchUsernameException | HandAlreadyFullException e) {
                    out.println(e.getMessage());
                    out.println("Select a different type of draw.");

                    // Recall this function for asking again
                    askDraw(username);
                }
                break;
            }
            case 2: {
                // Draw card from gold deck
                try {
                    // Draw card
                    GoldCard cardToDraw = gameController.getGameTable().drawGoldCardDeck();
                    // Add card in player's hand
                    gameController.getGameTable().getPlayerByUsername(username).addCardHand(cardToDraw);
                } catch (EmptyDeckException | NoPlayerWithSuchUsernameException | HandAlreadyFullException e) {
                    out.println(e.getMessage());
                    out.println("Select a different type of draw.");

                    // Recall this function for asking again
                    askDraw(username);
                }
                break;
            }
            case 3: {
                int size = gameController.getGameTable().getVisibleCard().size();
                if (size == 0) {
                    out.println("There are no cards in table.\nSelect a different type of draw.");
                    // Recall this function for asking again
                    askDraw(username);
                } else if (size == 1) {
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
                } else {
                    out.println("Insert the card's number (insert 1/2/3/4):");
                    int selectedPosition = Integer.parseInt(in.readLine());
                    try {
                        // Call to method and add card to hand
                        GamingCard cardToDraw = gameController.getGameTable().drawCardFromTable(selectedPosition - 1);
                        gameController.getGameTable().getPlayerByUsername(username).addCardHand(cardToDraw);
                    } catch (InvalidDrawFromTableException | NoPlayerWithSuchUsernameException |
                             HandAlreadyFullException e) {
                        out.println(e.getMessage());
                        // Recall this function for asking again
                        askDraw(username);
                    }
                }
                break;
            }
        }
    }

    public void sendSelectPlayMessage() {
        out.println("It's your turn.\nNow you have to play a card from your hand.\n");
    }

    public void sendCorrectPlayMessage() {
        out.println("You have correctly play the card in your game area.");
        out.println("That card has been removed from your hand.");
    }

    public void sendSelectDrawMessage() {
        out.println("\nNow you have to select from where you want to draw your next card.");
    }

    public void sendCorrectDrawMessage() {
        out.println("\nYou have correctly draw a new card and added it in your hands.");
    }

    public void sendFinishTurnMessage() {
        out.println("You have correctly play your turn.");
    }

    public void sendWaitTurnMessage() {
        out.println("\nPlease wait for your turn.\n");
    }

    public void sendLastTurnMessage() {
        out.println("A player has reached 20 points, so the last turn will now start.");
    }

    public void sendWaitFinishGameMessage() {
        out.println("\nWait for others players' last turns.\nThe game will end soon");
    }

    public void sendWinnersMessage(ArrayList<Player> winners, String username) {
        boolean hasWon = false; // true: player has won, false: player has lost.

        out.println("\nGAME OVER.\n");
        if (winners.size() == 1) {
            out.print("THE WINNER IS ... ");
        } else {
            out.print("THE WINNERS ARE ... ");
        }

        for (Player winner : winners) {
            out.print(winner.getUsername() + " ");
            if (winner.getUsername().equals(username)) {
                hasWon = true;
            }
        }

        // Send message to show if the player has won or lost
        if (hasWon) {
            out.println("\nCONGRATULATIONS, YOU WON!!!");
        } else {
            out.println("\nSORRY, YOU LOST.");
        }
    }

    public void sendLeaderboardMessage(LinkedHashMap<Player, Integer> leaderboard) {
        // Send final leaderboard message
        out.println("\nFinal scoreboard:");

        // If a player has a different score respect to the last one, then "update" his final position.
        int current_index = 1;
        int lastScore = -1; // Keep track of last score
        String lastPosition = ""; // Keep track of last position

        for (Map.Entry<Player, Integer> entry : leaderboard.entrySet()) {
            int current_score = entry.getValue(); // Get player's score
            if (current_score != lastScore) {
                String position = current_index + getSuffix(current_index); // "Update" final position
                out.println(position + entry.getKey().getUsername() + ", score: " + current_score);
                lastPosition = position;
                lastScore = current_score;
            } else {
                out.println(lastPosition + entry.getKey().getUsername() + ", score: " + current_score);
            }
            current_index++;
        }

        // Send end game message
        out.println("\nTHANKS FOR PLAYING, the connection will now be reset.\n");
    }

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
}