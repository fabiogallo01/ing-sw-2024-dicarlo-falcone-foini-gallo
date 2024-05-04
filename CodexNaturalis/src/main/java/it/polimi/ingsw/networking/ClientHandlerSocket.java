package it.polimi.ingsw.networking;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.exception.EmptyDeckException;
import it.polimi.ingsw.model.exception.EmptyObjectiveDeckException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Class which handle multiple threads representing clients connections to server
 * It extends Thread and override method run
 *
 * @author Foini Lorenzo
 */
public class ClientHandlerSocket extends Thread{
    private final Socket clientSocket;
    private BufferedReader in; // Input message from client to server
    private PrintWriter out; // Output message from server to client
    private boolean isYourTurn = false; // Boolean value representing if the current turn is client's turn

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
            String username = askUsername();

            // Ask client's color and remove such color from list of available colors
            String color = AskColor();

            // Show to the client his starter card
            StarterCard starterCard;
            try {
                starterCard = (StarterCard) Controller.getGameTable().getStarterDeck().drawTopCard();
            } catch (EmptyDeckException e) {
                throw new RuntimeException(e);
            }
            displayStarterCard(starterCard);
            // Ask client on which side he wants to play the starter card
            boolean sideStarterCard = askStarterCardSide();
            // Assign such side to starter card
            starterCard.setSide(sideStarterCard);

            // Create player hand and display it
            ArrayList<GamingCard> hand = new ArrayList<>();
            hand.add((GamingCard)Controller.getGameTable().getResourceDeck().drawTopCard());
            hand.add((GamingCard)Controller.getGameTable().getResourceDeck().drawTopCard());
            hand.add((GoldCard)Controller.getGameTable().getGoldDeck().drawTopCard());
            displayResourceCard(hand.get(0));
            displayResourceCard(hand.get(1));
            displayGoldCard((GoldCard)hand.get(2));

            // Ask client to select his secret objective cards from two different objective cards
            ObjectiveCard secretObjectiveCard = askSecretObjectiveCard();

            // Call to controller's method for create a new player
            // It also inserts the new player in the game table
            Controller.createNewPlayer(username, color, starterCard, hand, secretObjectiveCard);

            // Display message which says that the player has been added to the game and wait for start
            out.println("You have been added to the game.\nPlease wait for other players");

            Server.setReady();
            /*
            while(!Server.getGameEnded()){
                if(Server.getCountConnectedClients() == Server.getNumPlayers()) {
                    out.println("The game has now started.\nYou are player number " + Server.getCountConnectedClients() + " to play");
                }
            }*/

        } catch (IOException e) {
            System.err.println("Game ended because an I/O error occurred " + e.getMessage());
        } catch(EmptyDeckException | EmptyObjectiveDeckException e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to ask number of players
     *
     * @author Foini Lorenzo
     */
    public synchronized void askNumberPlayers() throws IOException {
        int num;
        do{
            out.println("Specify the number of players (2, 3 or 4):"); // Display message
            num = Integer.parseInt(in.readLine()); // Get client input
        }while(num < 2 || num > 4); // Must be 2, 3 or 4

        // Assign such number to server's param
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
     * Method to display a given starter card
     *
     * @param starterCard given starter card to be displayed
     * @author Foini Lorenzo
     */
    public synchronized void displayStarterCard(StarterCard starterCard){
        // TODO: Display starter card's params
        out.println("This is your starter card: " + starterCard.toString()); // To modify
    }

    /**
     * Method to ask the side of starter card
     *
     * @author Foini Lorenzo
     */
    public synchronized boolean askStarterCardSide() throws IOException {
        out.println("On which side you want to play the starter card (insert front or back):");
        String stringSide = in.readLine().toLowerCase(); // Get client's input
        while(!stringSide.equals("front") && !stringSide.equals("back")){
            out.println("Insert front or back"); // INVALID
            stringSide = in.readLine().toLowerCase();
        }

        return stringSide.equals("front"); // true => front, false => back
    }

    /**
     * Method to display a given resource card
     *
     * @param resourceCard given resource card to be displayed
     * @author Foini Lorenzo
     */
    public void displayResourceCard(GamingCard resourceCard){
        // TODO: Display resource card's params
        out.println("Resource card: " + resourceCard.toString()); // To modify
    }

    /**
     * Method to display a given gold card
     *
     * @param goldCard given gold card to be displayed
     * @author Foini Lorenzo
     */
    public void displayGoldCard(GoldCard goldCard){
        // TODO: Display gold card's params
        out.println("Gold card: " + goldCard.toString()); // To modify
    }

    /**
     * Method to display a given objective card
     *
     * @param objectiveCard given objective card to be displayed
     * @author Foini Lorenzo
     */
    public void displayObjectiveCard(ObjectiveCard objectiveCard){
        // TODO: Display objective card's params
        out.println("Objective card: " + objectiveCard.toString()); // To modify
    }

    /**
     * Method to ask which secret card to use in the game for this client
     *
     * @author Foini Lorenzo
     */
    public synchronized ObjectiveCard askSecretObjectiveCard() throws EmptyObjectiveDeckException, IOException{
        // Draw two objective cards from objective deck
        ObjectiveCard card1 = Controller.getGameTable().getObjectiveDeck().drawTopCard();
        ObjectiveCard card2 = Controller.getGameTable().getObjectiveDeck().drawTopCard();

        // Display the two cards
        displayObjectiveCard(card1);
        displayObjectiveCard(card2);

        // Ask choice to client (1 => First card, 2 => Second card)
        out.println("Select your secret objective card (insert 1 or 2)");
        int choice = Integer.parseInt(in.readLine());
        while(choice != 1 && choice != 2){
            out.println("Select your secret objective card (insert 1 or 2)"); // INVALID
            choice = Integer.parseInt(in.readLine());
        }

        if(choice == 1) return card1;
        return card2;
    }

    public void setTurn(){
        isYourTurn= true;
    }

}