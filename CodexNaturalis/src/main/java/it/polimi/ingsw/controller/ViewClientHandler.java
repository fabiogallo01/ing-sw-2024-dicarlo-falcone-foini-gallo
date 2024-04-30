package it.polimi.ingsw.controller;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.StarterCard;
import it.polimi.ingsw.model.exception.EmptyDeckException;
import it.polimi.ingsw.model.exception.EmptyObjectiveDeckException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

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

                        // Initialize new GameTable
                        ControllerServer.initialiseGameTable(numPlayers);
                    }
                    // Add user to set of already present username
                    ControllerServer.addUser(username);
                } else {
                    out.println("Username already in use. Please choose a different username.");
                }
            }while(!exit);

            // Ask the player which color he wants
            ArrayList<String> availableColors = ControllerServer.getColors();
            String playerColor = askColor(availableColors);
            ControllerServer.addPlayersColors(playerColor);
            // Remove color from list of available colors and call server's set colors
            availableColors.remove(playerColor);
            ControllerServer.setColors(availableColors);

            //ask the player what side of the starter card he wants to play
            try {
                StarterCard starterCard = (StarterCard) ControllerServer.getGameTable().getStarterDeck().drawTopCard();
                String side = askSideStarterCard(starterCard);
            } catch (EmptyDeckException e) {
                throw new RuntimeException(e);
            }
            //TODO dare al controller la side che il giocatore ha deciso di giocare

            // Ask the player which objective card he wants to use between the two given
            try {
                ObjectiveCard secretObjective = askSecretObjective();
                // Add secret cards in controller list
                ControllerServer.addPlayersSecretCards(secretObjective);
            } catch (EmptyObjectiveDeckException e) {
                throw new RuntimeException(e);
            }
            while(!ControllerServer.isFinished()){
                //TO DO
                // while(isYourTurn){
                //
                // }
            }

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


    /**
     * Method to ask the client which side of the starter
     * card he wants to play
     *
     * @author giacomofalcone
     */
    public String askSideStarterCard(StarterCard starterCard) throws IOException {
        //TODO
        out.println("Your starter card: " + starterCard.toString());
        //out.println("Your starter card: " + );
        /*
        public StarterCard(boolean side, Corner[] frontCorners, Corner[] backCorners, Kingdom[] frontKingdoms) {
            super(side, frontCorners, backCorners); // Using the constructor with backCorners
            this.frontKingdoms = frontKingdoms;
        }*/
        out.println("Now choose which side of your starter card you want to play:\nFront / Back ?");
        String side = in.readLine().toLowerCase();

        // Check correct insert of the side
        while(side != "front" && side != "back") {
            out.println(out.println("Invalid side:\nFront / Back ?"););
            side = in.readLine().toLowerCase();
        }
        return side;
    }


    /**
     * Method for ask the client his color
     *
     * @author Lorenzo Foini
     */
    public String askColor(ArrayList<String> colors) throws IOException {
        // Show list of possible color and check its choice
        out.println("Now choose your color from this list:");
        for (String availableColor : colors) {
            out.println(availableColor);
        }
        String playerColor = in.readLine();

        // Check availability of the color
        while(!colors.contains(playerColor)) {
            out.println("Color not available, please select another one from the previous list.\nChoose color:");
            playerColor = in.readLine();
        }
        return playerColor;
    }

    /**
     * Method for ask the client to choose from two objective cards
     *
     * @author Lorenzo Foini
     */
    public ObjectiveCard askSecretObjective() throws EmptyObjectiveDeckException, IOException {
        ArrayList<ObjectiveCard> secretObjectiveCards = new ArrayList<>();

        // Generate two objective cards
        secretObjectiveCards.add(ControllerServer.getGameTable().getObjectiveDeck().drawTopCard());
        secretObjectiveCards.add(ControllerServer.getGameTable().getObjectiveDeck().drawTopCard());

        out.println("Now you have to choose a secret objective.\nYou can choose from this two cards:");
        // Show cards' params
        for (ObjectiveCard card : secretObjectiveCards){
            out.println("Card: " + card.toString());
        }

        // Get player choice
        out.println("Now insert your choose: 1 for card one, 2 for card two");
        int selection = Integer.parseInt(in.readLine());
        while(selection < 1 || selection > 2){
            out.println("Invalid selection, insert only 1 or 2.\nNow retry:");
            selection = Integer.parseInt(in.readLine());
        }

        // Return selected secret objective card
        return secretObjectiveCards.get(selection-1);
    }
}