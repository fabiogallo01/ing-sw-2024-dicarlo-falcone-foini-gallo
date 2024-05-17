package it.polimi.ingsw.networking;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.model.cards.GamingCard;
import it.polimi.ingsw.model.cards.GoldCard;
import it.polimi.ingsw.model.cards.ObjectiveCard;
import it.polimi.ingsw.model.cards.StarterCard;
import it.polimi.ingsw.model.exception.EmptyDeckException;
import it.polimi.ingsw.model.exception.EmptyObjectiveDeckException;
import it.polimi.ingsw.model.game.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
                    int i=0;
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
                                gameController=controller;
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
            while(!gameController.getGameTable().isFull()) {
                Thread.onSpinWait();
            }
            out.println("Game starts now.");

            // Handle game-specific communication and actions here
            String input;
            while ((input = in.readLine()) != null) {
                // Process player input and communicate with the game controller
                //gameController.processMove(player, input);
                out.println("Move processed: " + input);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (EmptyObjectiveDeckException e) {
            throw new RuntimeException(e);
        } catch (EmptyDeckException e) {
            throw new RuntimeException(e);
        }
    }

    private void startGame() {

    }

    public String askUsername() throws IOException {
        out.println("Insert your username:"); // Display message
        String username = in.readLine(); // Get client input

        // Check if the username is available or already present
        ArrayList<String> alreadyUsedUsername = Server.getClientUsername();
        while (alreadyUsedUsername.contains(username)) {
            out.println("This username is taken. Please insert a new username:"); // INVALID
            username = in.readLine();
        }
        // Show in server new connection
        //System.out.println(username + " has connected to the game.");

        // Insert username in server's list of usernames
        Server.addClientUsername(username);

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

}


