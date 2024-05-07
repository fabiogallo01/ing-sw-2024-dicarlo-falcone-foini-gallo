package it.polimi.ingsw.networking;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.game.Player;

import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * Class which handle multiple threads representing clients connections to server
 * It is also used for handling players' turns
 * It extends Thread and override method run
 *
 * @author Foini Lorenzo
 */
public class ClientHandlerSocket extends Thread{
    private final Socket clientSocket;
    private BufferedReader in; // Input message from client to server
    private PrintWriter out; // Output message from server to client
    private String username;

    /**
     * ViewClientHandler constructor, it assigns/creates all class's parameters
     *
     * @param client representing client's socket
     * @author Foini Lorenzo
     */
    public ClientHandlerSocket(Socket client) {
        clientSocket = client;
    }

    /**
     * Override of method run
     * It asks the client to insert username, color, side of starter card and secret objective card
     * Catch IOException if an I/O error occurred
     * Catch empty deck exceptions
     *
     * @author Foini Lorenzo
     */
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); // Input => Client messages
            out = new PrintWriter(clientSocket.getOutputStream(), true); // Output => Server messages

            Server.incrementCountConnectedClients(); // Increment server's counter of connected clients
            Server.setConnected(true);
            // If first client => Ask for number of players
            // Such number must be 2, 3 or 4
            if(Server.getCountConnectedClients() == 1) {
                askNumberPlayers();
            }

            // Ask client's username and insert such username in list of players' username
            username = askUsername();

            // Ask client's color and remove such color from list of available colors
            String color = AskColor();

            // Show to the client his starter card
            StarterCard starterCard;
            try {
                starterCard = (StarterCard) Server.getController().getGameTable().getStarterDeck().drawTopCard();
            } catch (EmptyDeckException e) {
                throw new RuntimeException(e);
            }
            Server.getController().getView().displayStarterCard(starterCard,out);
            // Ask client on which side he wants to play the starter card
            boolean sideStarterCard = askStarterCardSide();
            // Assign such side to starter card
            starterCard.setSide(sideStarterCard);

            // Create player hand and display it
            ArrayList<GamingCard> hand = new ArrayList<>();
            hand.add((GamingCard) Server.getController().getGameTable().getResourceDeck().drawTopCard());
            hand.add((GamingCard) Server.getController().getGameTable().getResourceDeck().drawTopCard());
            hand.add((GoldCard) Server.getController().getGameTable().getGoldDeck().drawTopCard());
            out.println("\nThis is your hand:\n");
            Server.getController().getView().displayResourceCard(hand.get(0), out); // Call to View's method
            Server.getController().getView().displayResourceCard(hand.get(1), out); // Call to View's method
            Server.getController().getView().displayGoldCard((GoldCard)hand.get(2), out); // Call to View's method

            // Show the two common objective cards
            out.println("\nThis are the two common objectives:\n");
            ObjectiveCard[] commonObjective = Server.getController().getGameTable().getCommonObjectives();
            Server.getController().getView().displayObjectiveCard(commonObjective[0], out);
            Server.getController().getView().displayObjectiveCard(commonObjective[1], out);

            // Ask client to select his secret objective cards from two different objective cards
            out.println("Now you have to choose which secret objective card you want to use.");
            out.println("You can choose one card from the following two objective cards\n");

            ObjectiveCard secretObjectiveCard = askSecretObjectiveCard();

            // Call to controller's method for create a new player
            // It also inserts the new player in the game table
            Server.getController().createNewPlayer(username, color, starterCard, hand, secretObjectiveCard);

            // Display message which says that the player has been added to the game and wait for start
            out.println("\nYou have been added to the game.\nPlease wait for other players.");

            // Set server ready
            Server.setReady();

        } catch (IOException e) {
            System.err.println("Game ended because an I/O error occurred: " + e.getMessage());
        } catch(EmptyDeckException | EmptyObjectiveDeckException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Send start game message to client
     *
     * @author Foini Lorenzo
     */
    public void sendStartGameMessage() {
        out.println("\nThanks for waiting, now all the players are connected. The game will starts in a few seconds");
    }

    /**
     * Send wait turn message to client
     *
     * @author Foini Lorenzo
     */
    public void sendWaitTurnMessage(){
        out.println("\nPlease wait for your turn.\n");
    }

    /**
     * Send finish turn message to client
     *
     * @author Foini Lorenzo
     */
    public void sendFinishTurnMessage(){
        out.println("You have correctly play your turn.");
    }

    /**
     * Send last turn message to client
     *
     * @author Foini Lorenzo
     */
    public void sendLastTurnMessage(){
        out.println("A player has reached 20 points, so the last turn will now start.");
    }

    /**
     * Send select play message to client
     *
     * @author Foini Lorenzo
     */
    public void sendSelectPlayMessage(){
        out.println("It's your turn.\nNow you have to play a card from your hand.\n");
    }

    /**
     * Send correct play message to client
     *
     * @author Foini Lorenzo
     */
    public void sendCorrectPlayMessage(){
        out.println("You have correctly play the card in your game area.");
        out.println("That card has been removed from your hand.");
    }

    /**
     * Send select draw message to client
     *
     * @author Foini Lorenzo
     */
    public void sendSelectDrawMessage(){
        out.println("\nNow you have to select from where you want to draw your next card.");
    }

    /**
     * Send correct draw message to client
     *
     * @author Foini Lorenzo
     */
    public void sendCorrectDrawMessage(){
        out.println("\nYou have correctly draw a new card and added it in your hands.");
    }

    public void sendWaitFinishGameMessage(){
        out.println("\nWait for others players' last turns.\nThe game will end soon");
    }

    /**
     * Client's username getter
     *
     * @author Foini Lorenzo
     */
    public String getUsername(){
        return username;
    }

    /**
     * Method to ask number of players
     *
     * @author Foini Lorenzo
     */
    public synchronized void askNumberPlayers() throws IOException {
        String stringNum;
        do{
            out.println("Specify the number of players (insert 2, 3 or 4):"); // Display message
            stringNum = in.readLine(); // Get client input
        }while(!stringNum.equals("2") && !stringNum.equals("3") && !stringNum.equals("4")); // Must be 2, 3 or 4

        int num = Integer.parseInt(stringNum); // Parse to int
        // Assign such number to server's param and gameTable's param
        Server.setNumPlayers(num);
        Server.setFistClientConnected(true);
    }

    /**
     * Method to ask client's username
     *
     * @author Foini Lorenzo
     */
    public synchronized String askUsername() throws IOException {
        out.println("Insert your username:"); // Display message
        String username = in.readLine(); // Get client input

        // Check if the username is available or already present
        ArrayList<String> alreadyUsedUsername = Server.getClientUsername();
        while(alreadyUsedUsername.contains(username)){
            out.println("Username already in use. Please insert a new username:"); // INVALID
            username = in.readLine();
        }
        // Show in server new connection
        System.out.println(username + " has connected to the game.");

        // Insert username in server's list of usernames
        Server.addClientUsername(username);

        return username;
    }

    /**
     * Method to ask number client's color
     *
     * @author Foini Lorenzo
     */
    public synchronized String AskColor() throws IOException {
        // Display available colors
        ArrayList<String> availableColors = Server.getAvailableColors();
        out.println("Now you have to choose a color from this list:");
        for(String color : availableColors){
            out.println(color);
        }
        out.println("Insert your color:"); // Display message
        String selectedColor = in.readLine().toLowerCase(); // Get client's input

        // Check color selection
        while(!availableColors.contains(selectedColor)){
            out.println("Invalid color. Please select a color from the previous list:"); // INVALID
            selectedColor = in.readLine().toLowerCase();
        }

        // Remove such color from available list
        Server.removeAvailableColor(selectedColor);

        return selectedColor;
    }

    /**
     * Method to ask the side of starter card
     *
     * @author Foini Lorenzo
     */
    public synchronized boolean askStarterCardSide() throws IOException {
        // Display message
        out.println("On which side you want to play the starter card (insert front or back):");
        String stringSide = in.readLine().toLowerCase(); // Get client's input

        // Check input
        while(!stringSide.equals("front") && !stringSide.equals("back")){
            out.println("Insert front or back:"); // INVALID
            stringSide = in.readLine().toLowerCase();
        }

        return stringSide.equals("front"); // true => front, false => back
    }

    /**
     * Method to ask which secret card to use in the game for this client
     *
     * @author Foini Lorenzo
     */
    public synchronized ObjectiveCard askSecretObjectiveCard() throws EmptyObjectiveDeckException, IOException{
        // Draw two objective cards from objective deck
        ObjectiveCard card1 = Server.getController().getGameTable().getObjectiveDeck().drawTopCard();
        ObjectiveCard card2 = Server.getController().getGameTable().getObjectiveDeck().drawTopCard();

        // Display the two cards
        Server.getController().getView().displayObjectiveCard(card1, out);
        Server.getController().getView().displayObjectiveCard(card2, out);

        // Ask choice to client (1 => First card, 2 => Second card)
        String stringChoice;
        do {
            out.println("Select your secret objective card (insert 1 or 2):");
            stringChoice = in.readLine();
        }while(!stringChoice.equals("1") && !stringChoice.equals("2"));

        if(stringChoice.equals("1")) return card1;
        return card2;
    }

    /**
     * Method for asking client which card he wants to play, his side and where to play such card
     *
     * @author Foini Lorenzo
     */
    public void askPlay() throws IOException {
        // Display player's area and his hand
        out.println("This is your game area:");
        try {
            Server.getController().getView().displayArea(Server.getController().getGameTable().getPlayerByUsername(username).getPlayerArea().getCards(), out);
        } catch (NoPlayerWithSuchUsernameException e) {
            throw new RuntimeException(e);
        }

        // Call to View's function for display hand
        try{
            // Get the player's hand and display it
            ArrayList<GamingCard> handToPrint = Server.getController().getGameTable().getPlayerByUsername(username).getHand();
            Server.getController().getView().displayHand(handToPrint, out);
        } catch(NoPlayerWithSuchUsernameException e){
            out.println(e.getMessage());
        }


        // Ask player which card he wants to play from his hand
        out.println("Which card you want to play (insert 1, 2 or 3):");
        String stringPositionCardHand = in.readLine();

        // Check user input
        while(!stringPositionCardHand.equals("1") && !stringPositionCardHand.equals("2") && !stringPositionCardHand.equals("3")){
            out.println("Please insert 1, 2 or 3:");
            stringPositionCardHand = in.readLine();
        }
        int cardPositionHand = Integer.parseInt(stringPositionCardHand); // Parse to int

        // Ask player which side to play such card
        out.println("On which side you want to play this card(insert front or back):");
        String stringSide = in.readLine().toLowerCase(); // Get client's input

        // Check user input
        while(!stringSide.equals("front") && !stringSide.equals("back")){
            out.println("Insert front or back:"); // INVALID
            stringSide = in.readLine().toLowerCase();
        }
        boolean sideCardToPlay = stringSide.equals("front");

        // Ask where he wants to play that card in his game area
        out.println("Given your game area, now choose the position in the area where you want to play such card.");
        out.println("Insert integer row value:");
        int row = Integer.parseInt(in.readLine());
        out.println("Insert integer column value:");
        int column = Integer.parseInt(in.readLine());
        // Throws exception after if position is not valid

        int[] positionArea = new int[2];
        positionArea[0] = row;
        positionArea[1] = column;

        // Use controller's method for play this card
        try{
            // Play card
            Server.getController().getGameTable().getPlayerByUsername(username).playCard(cardPositionHand, positionArea, sideCardToPlay);

            // Show messages
            out.println("\nThe card has been played correctly.");
            out.println("This is your score after this play: " + Server.getController().getGameTable().getPlayerByUsername(username).getScore() + " pts.");
        }catch(NoPlayerWithSuchUsernameException | InvalidPlayCardIndexException | InvalidPositionAreaException | InvalidPlayException e){
            // An error has occur
            out.println("\nInvalid play." + e.getMessage());
            out.println("Now you will be asked to insert again all the information.\n");

            // Recall this function for ask again all the information
            askPlay();
        }
    }

    /**
     * Method for asking client from where he wants to draw his next card
     *
     * @author Foini Lorenzo
     */
    public void askDraw() throws IOException {
        // To display the back of the top card of resource deck
        ArrayList<Card> resourceDeck = Server.getController().getGameTable().getResourceDeck().getDeck();
        GamingCard topResourceCard = (GamingCard) resourceDeck.getLast();
        Server.getController().getView().displayTopResource(topResourceCard, out);
        // To display the back of the top card of gold deck
        ArrayList<Card> goldDeck = Server.getController().getGameTable().getGoldDeck().getDeck();
        GoldCard topGoldCard = (GoldCard) goldDeck.getLast();
        Server.getController().getView().displayTopGold(topGoldCard, out);
        // To display the 4 visible drawable cards
        ArrayList<GamingCard> visibleCards = Server.getController().getGameTable().getVisibleCard();
        Server.getController().getView().displayVisibleTableCard(visibleCards, out);

        // Ask user's choice
        out.println("You can draw from:\n- Resource deck (insert 1).\n- Gold deck (insert 2).\n- One of the four cards present in the table (insert 3).");
        out.println("Insert 1, 2 or 3:");
        String stringChoice = in.readLine();

        // Check user input
        while(!stringChoice.equals("1") && !stringChoice.equals("2") && !stringChoice.equals("3")){
            out.println("Please insert 1, 2 or 3:");
            stringChoice = in.readLine();
        }
        int choice = Integer.parseInt(stringChoice); // Parse to int

        // Switch case based on choice
        switch(choice){
            case 1:{
                // Draw card from resource deck
                try{
                    // Draw card
                    GamingCard cardToDraw = Server.getController().getGameTable().drawResourceCardDeck();
                    // Add card in player's hand
                    Server.getController().getGameTable().getPlayerByUsername(username).addCardHand(cardToDraw);

                } catch(EmptyDeckException | NoPlayerWithSuchUsernameException | HandAlreadyFullException e){
                    out.println(e.getMessage());
                    out.println("Select a different type of draw.");

                    // Recall this function for asking again
                    askDraw();
                }
                break;
            }
            case 2:{
                // Draw card from gold deck
                try{
                    // Draw card
                    GoldCard cardToDraw = Server.getController().getGameTable().drawGoldCardDeck();
                    // Add card in player's hand
                    Server.getController().getGameTable().getPlayerByUsername(username).addCardHand(cardToDraw);
                } catch(EmptyDeckException | NoPlayerWithSuchUsernameException | HandAlreadyFullException e){
                    out.println(e.getMessage());
                    out.println("Select a different type of draw.");

                    // Recall this function for asking again
                    askDraw();
                }
                break;
            }
            case 3:{
                int size = Server.getController().getGameTable().getVisibleCard().size();
                if(size == 0){
                    out.println("There are no cards in table.\nSelect a different type of draw.");
                    // Recall this function for asking again
                    askDraw();
                }else if(size == 1){
                    out.println("There is only 1 card in the table, so draw this card.");
                    try{
                        // Call to method and add card to hand
                        GamingCard cardToDraw = Server.getController().getGameTable().drawCardFromTable(0);
                        Server.getController().getGameTable().getPlayerByUsername(username).addCardHand(cardToDraw);
                    } catch(InvalidDrawFromTableException | NoPlayerWithSuchUsernameException | HandAlreadyFullException e){
                        out.println(e.getMessage());
                        // Recall this function for asking again
                        askDraw();
                    }
                }else {
                    out.println("Insert the card's number:");
                    int selectedPosition = Integer.parseInt(in.readLine());
                    try{
                        // Call to method and add card to hand
                        GamingCard cardToDraw = Server.getController().getGameTable().drawCardFromTable(selectedPosition-1);
                        Server.getController().getGameTable().getPlayerByUsername(username).addCardHand(cardToDraw);
                    }catch(InvalidDrawFromTableException | NoPlayerWithSuchUsernameException | HandAlreadyFullException e){
                        out.println(e.getMessage());
                        // Recall this function for asking again
                        askDraw();
                    }
                }
                break;
            }
        }
    }

    public void sendEndGameMessage(HashMap<Player, Integer> leaderboard) {
        out.println("\nThe game has ended.\n");

        List<Map.Entry<Player, Integer>> list = new LinkedList<>(leaderboard.entrySet());
        Map.Entry<Player, Integer> first = list.removeFirst();
        out.println("THE WINNER IS ... " + first.getKey().getUsername());
        out.println("\nFinal scoreboard:");

        out.println("1st: " + first.getKey().getUsername() + " " + first.getKey().getScore());
        first = list.removeFirst();
        out.println("2nd: " + first.getKey().getUsername() + " " + first.getKey().getScore());

        if(!list.isEmpty()){
            first = list.removeFirst();
            out.println("3rd: " + first.getKey().getUsername() + " " + first.getKey().getScore());

            if(!list.isEmpty()) {
                first = list.removeFirst();
                out.println("4th: " + first.getKey().getUsername() + " " + first.getKey().getScore());
            }
        }
    }
}