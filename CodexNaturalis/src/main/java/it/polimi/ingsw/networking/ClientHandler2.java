package it.polimi.ingsw.networking;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.game.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ClientHandler2 implements Runnable {
    private Socket socket;
    private List<Controller> controllers;
    private BufferedReader in;
    private PrintWriter out;
    private Controller gameController;
    private boolean joined = false;

    public ClientHandler2(Socket socket, List<Controller> controllers) {
        this.socket = socket;
        this.controllers = controllers;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String username = askUsername();
            while (!joined) {
                out.println("Enter your choice (create/join):");
                String choice = in.readLine();


                if ("create".equalsIgnoreCase(choice)) {
                    out.println("Enter number of players (2-4):");
                    int numberOfPlayers = Integer.parseInt(in.readLine());

                    gameController = new Controller(numberOfPlayers);
                    initializePlayer(username);

                    Server2.addController(gameController);

                    joined = true;
                    out.println("Game created. Waiting for players...");
                } else if ("join".equalsIgnoreCase(choice)) {
                    int i = 0;
                    controllers = Server2.getControllers();
                    for (Controller controller : controllers) {
                        i++;
                        if (!controller.getGameTable().isFull()) {
                            out.println("Game " + i + ":");
                            for (Player p : controller.getGameTable().getPlayers()) {
                                out.println("-" + p.getUsername());
                            }
                            out.println("Do you want to join this game? (Y/N)");
                            String ans = in.readLine();
                            if ("Y".equalsIgnoreCase(ans)) {
                                gameController = controller;
                                initializePlayer(username);
                                joined = true;
                                out.println("Joined a game. Waiting for players...");
                                break;
                            }
                        }
                    }
                    if (!joined) {
                        out.println("No available games to join. Please try again.");
                    }

                }
            }
            if(gameController.getReady()==gameController.getGameTable().getNumPlayers()-1) {
                gameController.setReady();
                gameController.startGame();
            }else {
                gameController.setReady();
                while (gameController.getGameTable().getNumPlayers() != gameController.getReady()) {
                    Thread.onSpinWait();
                }
            }
            out.println("Game starts now.");

            Player player = gameController.getGameTable().getPlayerByUsername(username);

            while(!player.isTurn()){
                Thread.onSpinWait();
            }

            while(!gameController.getGameTable().isLastTurn()){//TODO fix the condition so that the last turn is handled properly, probably add a global flag or something like that
                playTurn(username);

                if(gameController.getGameTable().isEnded() && username.equals(gameController.getGameTable().getPlayers().getLast().getUsername())){
                    gameController.getGameTable().setLastTurn();
                }
                gameController.nextTurn();
                while(!player.isTurn()){
                    Thread.onSpinWait();
                }
            }
            sendLastTurnMessage();

            playLastTurn(username);
            gameController.nextTurn();

            if(username.equals(gameController.getGameTable().getPlayers().getLast().getUsername())){
                gameController.getGameTable().setFinished();
            }
            while(!gameController.getGameTable().isFinished()){
                Thread.onSpinWait();
            }

            gameController.calculateFinalPoints();
            gameController.finalScoreboard();

            LinkedHashMap<Player, Integer> leaderboard = gameController.getLeaderboard();

            ArrayList<Player> winners = gameController.calculateWinners(leaderboard);

            sendWinnersMessage(winners, username);
            sendLeaderboardMessage(leaderboard);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (EmptyObjectiveDeckException | EmptyDeckException | NoPlayerWithSuchUsernameException |
                 InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private synchronized void playTurn(String username) throws IOException {

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

    private synchronized void playLastTurn(String username) throws IOException, InterruptedException {

        // Play card "phase"
        // Ask active player his play and send messages
        sendSelectPlayMessage();
        askPlay(username);
        sendCorrectPlayMessage();

        // Send turn messages
        sendFinishTurnMessage();
        sendWaitFinishGameMessage();
    }


    public String askUsername() throws IOException {
        out.println("Insert your username:"); // Display message
        String username = in.readLine(); // Get client input

        // Check if the username is available or already present
        ArrayList<String> alreadyUsedUsername = Server2.getClientUsername();
        while (alreadyUsedUsername.contains(username)) {
            out.println("This username is taken. Please insert a new username:"); // INVALID
            username = in.readLine();
        }
        // Show in server new connection
        //System.out.println(username + " has connected to the game.");

        // Insert username in server's list of usernames
        Server2.addClientUsername(username);

        return username;
    }

    public void initializePlayer(String username) throws IOException, EmptyDeckException, EmptyObjectiveDeckException {
        //COLOR PICKING
        out.println("Choose a color:");
        for (String color : gameController.getAvailableColors()) {
            out.println("-" + color);
        }
        out.println("Insert your color:"); // Display message
        String selectedColor = in.readLine().toLowerCase(); // Get client's input

        // Check color selection
        while (!gameController.getAvailableColors().contains(selectedColor)) {
            out.println("Invalid color. Please select a color from the list:"); // INVALID
            for (String color : gameController.getAvailableColors()) {
                out.println("-" + color);
            }
            selectedColor = in.readLine().toLowerCase();
        }

        // Remove such color from available list
        gameController.removeAvailableColor(selectedColor);


        //STARTER CARD PLACEMENT
        StarterCard starterCard;
        try {
            starterCard = (StarterCard) gameController.getGameTable().getStarterDeck().drawTopCard();
        } catch (EmptyDeckException e) {
            throw new RuntimeException(e);
        }
        gameController.getViewTui().displayStarterCard(starterCard, out);
        // Ask client on which side he wants to play the starter card
        // Display message
        out.println("On which side you want to play the starter card (insert front or back):");
        String stringSide = in.readLine().toLowerCase(); // Get client's input

        // Check input
        while (!stringSide.equals("front") && !stringSide.equals("back")) {
            out.println("Insert front or back:"); // INVALID
            stringSide = in.readLine().toLowerCase();
        }

        boolean sideStarterCard = stringSide.equals("front");// true => front, false => back
        // Assign such side to starter card
        starterCard.setSide(sideStarterCard);


        //HAND CREATION
        ArrayList<GamingCard> hand = new ArrayList<>();
        hand.add((GamingCard) gameController.getGameTable().getResourceDeck().drawTopCard());
        hand.add((GamingCard) gameController.getGameTable().getResourceDeck().drawTopCard());
        hand.add((GoldCard) gameController.getGameTable().getGoldDeck().drawTopCard());
        out.println("\nThis is your hand:\n");
        gameController.getViewTui().displayResourceCard(hand.get(0), out); // Call to View's method
        gameController.getViewTui().displayResourceCard(hand.get(1), out); // Call to View's method
        gameController.getViewTui().displayGoldCard((GoldCard) hand.get(2), out); // Call to View's method


        // Show the two common objective cards
        out.println("\nThis are the two common objectives:\n");
        ObjectiveCard[] commonObjective = gameController.getGameTable().getCommonObjectives();
        gameController.getViewTui().displayObjectiveCard(commonObjective[0], out);
        gameController.getViewTui().displayObjectiveCard(commonObjective[1], out);


        // Ask client to select his secret objective cards from two different objective cards
        out.println("Now you have to choose which secret objective card you want to use.");
        out.println("You can choose one card from the following two objective cards\n");


        // Draw two objective cards from objective deck
        ObjectiveCard card1 = gameController.getGameTable().getObjectiveDeck().drawTopCard();
        ObjectiveCard card2 = gameController.getGameTable().getObjectiveDeck().drawTopCard();

        // Display the two cards
        gameController.getViewTui().displayObjectiveCard(card1, out);
        gameController.getViewTui().displayObjectiveCard(card2, out);

        // Ask choice to client (1 => First card, 2 => Second card)
        String stringChoice;
        do {
            out.println("Select your secret objective card (insert 1 or 2):");
            stringChoice = in.readLine();
        } while (!stringChoice.equals("1") && !stringChoice.equals("2"));

        ObjectiveCard secretObjectiveCard;

        if (stringChoice.equals("1")) secretObjectiveCard = card1;
        else secretObjectiveCard = card2;

        gameController.createNewPlayer(username, selectedColor, starterCard, hand, secretObjectiveCard);
    }


    public void askPlay(String username) throws IOException {
        // Display player's area and his hand
        out.println("This is your game area:");
        try {
            gameController.getViewTui().displayArea(gameController.getGameTable().getPlayerByUsername(username).getPlayerArea().getCards(), out);
        } catch (NoPlayerWithSuchUsernameException e) {
            throw new RuntimeException(e);
        }

        // Call to View's function for display hand
        try{
            // Get the player's hand and display it
            ArrayList<GamingCard> handToPrint = gameController.getGameTable().getPlayerByUsername(username).getHand();
            gameController.getViewTui().displayHand(handToPrint, out);
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
            gameController.getGameTable().getPlayerByUsername(username).playCard(cardPositionHand, positionArea, sideCardToPlay);

            // Show messages
            out.println("\nThe card has been played correctly.");
            out.println("This is your score after this play: " + gameController.getGameTable().getPlayerByUsername(username).getScore() + " pts.");
        }catch(NoPlayerWithSuchUsernameException | InvalidPlayCardIndexException | InvalidPositionAreaException | InvalidPlayException e){
            // An error has occur
            out.println("\nInvalid play." + e.getMessage());
            out.println("Now you will be asked to insert again all the information.\n");

            // Recall this function for ask again all the information
            askPlay(username);
        }
    }
    public void askDraw(String username) throws IOException {
        // To display the back of the top card of resource deck
        ArrayList<Card> resourceDeck = gameController.getGameTable().getResourceDeck().getDeck();
        GamingCard topResourceCard = (GamingCard) resourceDeck.getLast();
        gameController.getViewTui().displayTopResource(topResourceCard, out);
        // To display the back of the top card of gold deck
        ArrayList<Card> goldDeck = gameController.getGameTable().getGoldDeck().getDeck();
        GoldCard topGoldCard = (GoldCard) goldDeck.getLast();
        gameController.getViewTui().displayTopGold(topGoldCard, out);
        // To display the 4 visible drawable cards
        ArrayList<GamingCard> visibleCards = gameController.getGameTable().getVisibleCard();
        gameController.getViewTui().displayVisibleTableCard(visibleCards, out);

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
                    GamingCard cardToDraw = gameController.getGameTable().drawResourceCardDeck();
                    // Add card in player's hand
                    gameController.getGameTable().getPlayerByUsername(username).addCardHand(cardToDraw);

                } catch(EmptyDeckException | NoPlayerWithSuchUsernameException | HandAlreadyFullException e){
                    out.println(e.getMessage());
                    out.println("Select a different type of draw.");

                    // Recall this function for asking again
                    askDraw(username);
                }
                break;
            }
            case 2:{
                // Draw card from gold deck
                try{
                    // Draw card
                    GoldCard cardToDraw = gameController.getGameTable().drawGoldCardDeck();
                    // Add card in player's hand
                    gameController.getGameTable().getPlayerByUsername(username).addCardHand(cardToDraw);
                } catch(EmptyDeckException | NoPlayerWithSuchUsernameException | HandAlreadyFullException e){
                    out.println(e.getMessage());
                    out.println("Select a different type of draw.");

                    // Recall this function for asking again
                    askDraw(username);
                }
                break;
            }
            case 3:{
                int size = gameController.getGameTable().getVisibleCard().size();
                if(size == 0){
                    out.println("There are no cards in table.\nSelect a different type of draw.");
                    // Recall this function for asking again
                    askDraw(username);
                }else if(size == 1){
                    out.println("There is only 1 card in the table, so draw this card.");
                    try{
                        // Call to method and add card to hand
                        GamingCard cardToDraw = gameController.getGameTable().drawCardFromTable(0);
                        gameController.getGameTable().getPlayerByUsername(username).addCardHand(cardToDraw);
                    } catch(InvalidDrawFromTableException | NoPlayerWithSuchUsernameException | HandAlreadyFullException e){
                        out.println(e.getMessage());
                        // Recall this function for asking again
                        askDraw(username);
                    }
                }else {
                    out.println("Insert the card's number:");
                    int selectedPosition = Integer.parseInt(in.readLine());
                    try{
                        // Call to method and add card to hand
                        GamingCard cardToDraw = gameController.getGameTable().drawCardFromTable(selectedPosition-1);
                        gameController.getGameTable().getPlayerByUsername(username).addCardHand(cardToDraw);
                    }catch(InvalidDrawFromTableException | NoPlayerWithSuchUsernameException | HandAlreadyFullException e){
                        out.println(e.getMessage());
                        // Recall this function for asking again
                        askDraw(username);
                    }
                }
                break;
            }
        }
    }

    public void sendSelectPlayMessage(){
        out.println("It's your turn.\nNow you have to play a card from your hand.\n");
    }
    public void sendCorrectPlayMessage(){
        out.println("You have correctly play the card in your game area.");
        out.println("That card has been removed from your hand.");
    }
    public void sendSelectDrawMessage(){
        out.println("\nNow you have to select from where you want to draw your next card.");
    }
    public void sendCorrectDrawMessage(){
        out.println("\nYou have correctly draw a new card and added it in your hands.");
    }
    public void sendFinishTurnMessage(){
        out.println("You have correctly play your turn.");
    }
    public void sendWaitTurnMessage(){
        out.println("\nPlease wait for your turn.\n");
    }
    public void sendLastTurnMessage(){
        out.println("A player has reached 20 points, so the last turn will now start.");
    }
    public void sendWaitFinishGameMessage(){
        out.println("\nWait for others players' last turns.\nThe game will end soon");
    }
    public void sendWinnersMessage(ArrayList<Player> winners, String username){
        boolean hasWon = false; // true: player has won, false: player has lost.

        out.println("\nGAME OVER.\n");
        if(winners.size() == 1){
            out.print("THE WINNER IS ... ");
        }else{
            out.print("THE WINNERS ARE ... ");
        }

        for(Player winner : winners){
            out.print(winner.getUsername() + " ");
            if(winner.getUsername().equals(username)){
                hasWon = true;
            }
        }

        // Send message to show if the player has won or lost
        if(hasWon){
            out.println("\nCONGRATULATIONS, YOU WIN!!!");
        }else{
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
            }else{
                out.println(lastPosition + entry.getKey().getUsername() + ", score: " + current_score);
            }
            current_index++;
        }

        // Send end game message
        out.println("\nTHANKS FOR PLAYING, the connection will now be reset.\n");
    }
    public String getSuffix(int index){
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


