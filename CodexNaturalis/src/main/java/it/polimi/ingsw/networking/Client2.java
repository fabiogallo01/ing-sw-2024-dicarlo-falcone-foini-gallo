package it.polimi.ingsw.networking;

import it.polimi.ingsw.model.exception.NoPlayerWithSuchUsernameException;
import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.view.gui.DrawCardFrame;
import it.polimi.ingsw.view.gui.ViewGUI;
import it.polimi.ingsw.view.gui.WaitStartGameFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

import com.google.gson.Gson;

/**
 * Class representing client in a client-server architecture
 * It uses socket to establish connection and show/get server's/client's messages
 *
 *
 * @author Foini Lorenzo
 * @author Fabio Gallo
 */
public class Client2 {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    /**
     * Main method
     * It is necessary to establish connection to the server (if possible) and play the game
     *
     * @param args in the position [0] it contains the UI choice: TUI or GUI (GUI as default if args is empty)
     * @author Foini Lorenzo
     * @author Fabio Gallo
     */
    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            try {
                if (args[0].equalsIgnoreCase("TUI")) {
                    out.println("TUI");
                    startTUI(out, in); // Start communication with TUI
                } else {
                    out.println("GUI");
                    startGUI(out, in); // Start communication with GUI
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                out.println("GUI");
                startGUI(out, in); // Start GUI as default without command line parameter
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * This method is called when the client chooses to use TUI
     *
     * @author Foini Lorenzo
     */
    private static void startTUI(PrintWriter out, BufferedReader in) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Connected to the game server");//TUI

        String response;
        while ((response = in.readLine()) != null) {
            System.out.println(response);//TUI
            //TODO sostituire tutti gli or con una condizione sola, creando una classe di un genere di messaggi
            if (response.equals("Do you want to create a new game or join a game? (insert create/join):") ||
                    response.equals("Which game you want to join (insert 0 to exit):") ||
                    response.equals("Please insert a valid number of game (insert 0 to exit):") ||
                    response.equals("Enter number of players (insert 2/3/4):") ||
                    response.equals("Please insert 2/3/4:") ||
                    response.equals("Insert your username:") ||
                    response.equals("Invalid username. Please insert a valid username:") ||
                    response.equals("Username already in use. Please insert a new username:") ||
                    response.equals("Insert your color:") ||
                    response.equals("Invalid color. Please select a color from the previous list:") ||
                    response.equals("On which side you want to play the starter card (insert front/back):") ||
                    response.equals("Insert front/back:") ||
                    response.equals("Select your secret objective card (insert 1/2):") ||
                    response.equals("Which card you want to play (insert 1/2/3):") ||
                    response.equals("Please insert 1/2/3:") ||
                    response.equals("On which side you want to play this card (insert front/back):") ||
                    response.equals("Insert integer row value (from 0 to 80):") ||
                    response.equals("Insert integer column value (from 0 to 80):") ||
                    response.equals("Insert 1/2/3:") ||
                    response.equals("Insert the card's number (insert 1/2/3/4):")
            ) {
                String input = scanner.nextLine();
                out.println(input);
            }
        }
    }

    /**
     * This method is called when the client chooses to use GUI
     *
     * @author Foini Lorenzo
     * @author Fabio Gallo
     */
    private static void startGUI(PrintWriter out, BufferedReader in) throws IOException {
        Gson gson = new Gson(); // Gson for serialization
        GameTable gameTable = null; // Instance of gameTable
        Player player; // Instance of player, it represents this client
        ViewGUI viewGui = new ViewGUI(); // Instance of GUI
        String response; // Messages from server
        String username = null; // Client's username
        Map<String, List<String>> joinGamesAndPlayers = new LinkedHashMap<>(); // Map of games that can be joined and their client's username
        ArrayList<String> availableColors = new ArrayList<>(); // List of available colors
        WaitStartGameFrame waitStartGame = null; // JFrame for waiting start of the game
        String drawVisibleCardIndex = "";

        // Now client reads messages from server and display a window
        while ((response = in.readLine()) != null) {
            if(response.equals("Insert your username:")){
                // TODO: Add boolean parameter false, see code of ViewGui for better understanding
                username = viewGui.displayUsername();
                out.println(username);
            } else if(response.equals("Username already in use. Please insert a new username:") ||
                      response.equals("Invalid username. Please insert a valid username:")){
                // TODO: Add boolean parameter false, see code of ViewGui for better understanding
                username = viewGui.displayUsername();
                out.println(username);
            } else if (response.equals("Do you want to create a new game or join a game? (insert create/join):")){
                // Get number of games that can be joined
                int countGameNotFull = Integer.parseInt(in.readLine());
                out.println(viewGui.displayCreateJoinGame(countGameNotFull));
            } else if (response.equals("Enter number of players (insert 2/3/4):")){
                out.println(viewGui.displayNumberPlayer());
            } else if(response.equals("Sending games and players")){
                // Now get list of games and their players by using a while loop
                response = in.readLine();
                String currentGame = "";

                while(!response.equals("End sending games and players")){
                    if(response.startsWith("Game")){ // Get a new game
                        currentGame = response;
                        joinGamesAndPlayers.put(currentGame, new ArrayList<>());
                    } else { // Get player for such game
                        joinGamesAndPlayers.get(currentGame).add(response);
                    }
                    // Get next message from server
                    response = in.readLine();
                }
            } else if(response.equals("Which game you want to join (insert 0 to exit):")){
                out.println(viewGui.displayJoinGameIndex(joinGamesAndPlayers));
            } else if(response.equals("Choose a color from this list:")){
                // Now use a while loop for getting list of colors and add them in availableColors
                response = in.readLine();

                while(!response.equals("End color")){
                    availableColors.add(response);
                    // Get next message from server
                    response = in.readLine();
                }
            }
            else if(response.equals("Insert your color:")){
                // TODO: Add boolean parameter false, see code of ViewGui for better understanding
                out.println(viewGui.displayColor(availableColors));
            } else if(response.equals("Invalid color. Please select a color from the previous list:")){
                // TODO: Add boolean parameter false, see code of ViewGui for better understanding
                out.println(viewGui.displayColor(availableColors));
            } else if(response.equals("On which side you want to play the starter card (insert front/back):")){
                // Get ID of the starter card from server
                int starterCardID = Integer.parseInt(in.readLine());

                // Call to viewGui method
                out.println(viewGui.displayStarterCard(starterCardID));
            } else if(response.equals("Select your secret objective card (insert 1/2):")){
                // TODO: Add parameters for getting ids of starter card, hand and common objectives

                // Get starter card side
                String starterCardSide = in.readLine();

                // Get IDs of: starter card, hand's cards, common objectives, secret objective cards from server
                int starterCardID = Integer.parseInt(in.readLine());

                int[] handCardIDs = new int[3];
                handCardIDs[0] = Integer.parseInt(in.readLine());
                handCardIDs[1] = Integer.parseInt(in.readLine());
                handCardIDs[2] = Integer.parseInt(in.readLine());

                int[] commonObjectiveCardIDs = new int[2];
                commonObjectiveCardIDs[0] = Integer.parseInt(in.readLine());
                commonObjectiveCardIDs[1] = Integer.parseInt(in.readLine());

                int[] secretObjectiveCardIDs = new int[2];
                secretObjectiveCardIDs[0] = Integer.parseInt(in.readLine());
                secretObjectiveCardIDs[1] = Integer.parseInt(in.readLine());

                // Call to viewGui method
                out.println(viewGui.displayObjectiveCards(starterCardSide, starterCardID, handCardIDs, commonObjectiveCardIDs, secretObjectiveCardIDs));
            } else if(response.equals("Game created. Waiting for players...")){
                viewGui.displayWaitStartGame(true);
                // END OF LOGIN PART
            } else if(response.equals("Joined a game. Waiting for players...")){
                waitStartGame = viewGui.displayWaitStartGame(false);
                // END OF LOGIN PART
            } else if(response.equals("Game starts now.")){
                // TODO: Create new frame for wait your turn
            } else if(response.equals("Which card you want to play (insert 1/2/3):")){
                // Close wait start game frame
                if (waitStartGame != null) {
                    waitStartGame.dispose();
                }

                // Get Json of GameTable and deserialize it
                String gameTableJson = in.readLine();
                gameTable = gson.fromJson(gameTableJson, GameTable.class);

                // Get instance of player
                try {
                    player = gameTable.getPlayerByUsername(username);
                } catch (NoPlayerWithSuchUsernameException e) {
                    throw new RuntimeException(e);
                }

                // Call to viewGui method
                viewGui.playgame(player, gameTable);
            } else if(response.equals("Insert 1/2/3:")){
                // Call to function for displaying draw choices
                int index = viewGui.displayDrawChoice(gameTable);
                switch(index){
                    case 1:
                    case 2:{
                        out.println(index);
                        break;
                    }
                    default:{
                        // Client draws from visible cards in the table
                        out.println(3);

                        // Index can be 3/4/5/6, but handler except 1/2/3/4 when client draws from visible cards
                        // => Remove two
                        drawVisibleCardIndex = String.valueOf(index-2);
                    }
                }
            } else if(response.equals("Insert the card's number (insert 1/2/3/4):")){
                // Use string drawVisibleCardIndex created before
                out.println(drawVisibleCardIndex);
            }

            //TODO implement all the rest
        }
    }
}