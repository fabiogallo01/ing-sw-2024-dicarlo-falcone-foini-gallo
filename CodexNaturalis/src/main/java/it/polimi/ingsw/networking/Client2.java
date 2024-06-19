package it.polimi.ingsw.networking;

import it.polimi.ingsw.model.exception.NoPlayerWithSuchUsernameException;
import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.view.gui.*;

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
    private static String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;
    private static boolean tui=false;

    /**
     * Main method
     * It is necessary to establish connection to the server (if possible) and play the game
     *
     * @param args in the position [0] it contains the UI choice: TUI or GUI (GUI as default if args is empty)
     * @author Foini Lorenzo
     * @author Fabio Gallo
     */
    public static void main(String[] args) {
        if(args.length==1) {
            if(args[0].equalsIgnoreCase("TUI")) {
                tui=true;
            }else if (!args[0].equalsIgnoreCase("GUI")) {
                SERVER_ADDRESS = args[0];
            }
        }

        if (args.length == 2) {
            SERVER_ADDRESS = args[0];
            tui = (args[1].equalsIgnoreCase("TUI"));
        }


        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            try {
                if (tui) {
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
            // TODO: Change all of the or clauses in a single condition by creating a new class for type of message
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
            if(response.equals("Game crashed.")){
                break;
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
        GameTable gameTable; // Instance of gameTable
        Player player; // Instance of player, it represents this client
        ArrayList<Integer> counterResources;
        ViewGUI viewGui = new ViewGUI(); // Instance of GUI
        String response; // Messages from server
        String username = null; // Client's username
        Map<String, List<String>> joinGamesAndPlayers = new LinkedHashMap<>(); // Map of games that can be joined and their client's username
        ArrayList<String> availableColors = new ArrayList<>(); // List of available colors
        WaitStartGameFrame waitStartGame = null; // JFrame for waiting start of the game
        WaitEndGameFrame waitEndGame = null; // JFrame for waiting end of the game
        GameFrame gameFrame = null; // JFrame with main in game view
        String drawVisibleCardIndex = "";
        String invalidPlay = "";
        String mistakePlay = "";
        String winnerMessage = "";
        boolean hasWon = false;

        // Now client reads messages from server and display a window
        while ((response = in.readLine()) != null) {
            if(response.equals("Insert your username:")){
                username = viewGui.displayUsername();
                out.println(username);
            } else if(response.equals("Username already in use. Please insert a new username:") ||
                      response.equals("Invalid username. Please insert a valid username:")){
                // Get client's previous selected username
                String previousUsername = in.readLine();
                username = viewGui.displayRepeatedUsername(previousUsername);
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
                out.println(viewGui.displayColor(availableColors));
            } else if(response.equals("Invalid color. Please select a color from the previous list:")){
                // Get client's previous selected username
                String previousColor = in.readLine();
                out.println(viewGui.displayInvalidColor(availableColors, previousColor));
            } else if(response.equals("On which side you want to play the starter card (insert front/back):")){
                // Get ID of the starter card from server
                int starterCardID = Integer.parseInt(in.readLine());

                // Call to viewGui method
                out.println(viewGui.displayStarterCard(starterCardID));
            } else if(response.equals("Select your secret objective card (insert 1/2):")){
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
                out.println(viewGui.displayObjectiveCards(starterCardSide, starterCardID, handCardIDs,
                            commonObjectiveCardIDs, secretObjectiveCardIDs));
            } else if(response.equals("Game created. Waiting for players...")){
                waitStartGame = viewGui.displayWaitStartGame(true);
            } else if(response.equals("Joined a game. Waiting for players...")){
                waitStartGame = viewGui.displayWaitStartGame(false);

                // END OF LOGIN PART

            } else if(response.equals("Game starts now.")){
                // START OF GAME PHASE: PLAY CARDS AND DRAWS

                // Close wait start game frame
                if (waitStartGame != null) {
                    waitStartGame.dispose();
                }

                // Get gui and player instances
                gameTable = getGui(in,gson);
                player = getPlayer(gameTable, username);
                counterResources = getCounterResources(in);

                // Call to viewGui method
                gameFrame = viewGui.playGame(out, player, gameTable, counterResources, invalidPlay, mistakePlay);
            } else if(response.equals("Which card you want to play (insert 1/2/3):")){
                // Get gui and player instances
                gameTable = getGui(in,gson);
                player = getPlayer(gameTable, username);
                counterResources = getCounterResources(in);

                // Call to gameFrame method for update the frame
                if (gameFrame != null) {
                    gameFrame.updateGameFrame(player, gameTable, counterResources, invalidPlay, mistakePlay);
                }
            } else if(response.startsWith("Invalid play.")){
                // Get mistake
                invalidPlay = in.readLine();
                mistakePlay = in.readLine();
            } else if(response.equals("Insert 1/2/3:")){
                // Update GUI
                // Get gui and player instances
                gameTable = getGui(in,gson);
                player = getPlayer(gameTable, username);
                counterResources = getCounterResources(in);

                // Call to gameFrame method for update the frame
                if (gameFrame != null) {
                    gameFrame.updateGameFrame(player, gameTable, counterResources, invalidPlay, mistakePlay);
                }

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
            } else if(response.equals("You have correctly play your turn.")){
                // Get gui and player instances
                gameTable = getGui(in,gson);
                player = getPlayer(gameTable, username);
                player.setTurn(false);
                counterResources = getCounterResources(in);

                // Call to gameFrame method for update the frame
                if (gameFrame != null) {
                    gameFrame.updateGameFrame(player, gameTable, counterResources, invalidPlay, mistakePlay);
                }
            } else if(response.equals("Wait for others players' last turns.")){
                waitEndGame = viewGui.displayWaitEndGame();

                // END OF GAME PHASE

            } else if(response.contains("THE WINNER")){
                // Close wait end game frame
                if (waitEndGame != null) {
                    waitEndGame.dispose();
                }

                // START OF POST-GAME PHASE
                winnerMessage = response;
            } else if(response.equals("CONGRATULATIONS, YOU WON!!!")){
                hasWon = true;
            } else if(response.equals("SORRY, YOU LOST.")){
                hasWon = false;
            } else if(response.equals("Final scoreboard:")){
                ArrayList<String> finalScoreboard = new ArrayList<>();
                // Get messages from server with the final scoreboard as a string
                // Add each message in the list
                while(response.equals("Final scoreboard:") || response.contains("1st") ||
                      response.contains("2nd") || response.contains("3rd") || response.contains("4th")){
                    finalScoreboard.add(response);
                    response = in.readLine();
                }
                viewGui.displayPostGame(winnerMessage, hasWon, finalScoreboard);
            }else if(response.equals("Game crashed.")){
                if (gameFrame != null) {
                    gameFrame.dispose();
                }
                viewGui.displayGameCrashed();
                break;
            }
        }
    }

    private static GameTable getGui(BufferedReader in, Gson gson) throws IOException {
        // Get Json of GameTable and deserialize it
        String gameTableJson = in.readLine();
        return gson.fromJson(gameTableJson, GameTable.class);
    }

    private static Player getPlayer(GameTable gameTable, String username){
        // Get instance of player
        Player player;
        try {
            player = gameTable.getPlayerByUsername(username);
        } catch (NoPlayerWithSuchUsernameException e) {
            throw new RuntimeException(e);
        }

        return player;
    }

    private static ArrayList<Integer> getCounterResources(BufferedReader in) throws IOException{
        ArrayList<Integer> counterResources = new ArrayList<>();
        counterResources.add(Integer.parseInt(in.readLine())); // Animal kingdom counter
        counterResources.add(Integer.parseInt(in.readLine())); // Fungi kingdom counter
        counterResources.add(Integer.parseInt(in.readLine())); // Insect kingdom counter
        counterResources.add(Integer.parseInt(in.readLine())); // Plant kingdom counter

        return counterResources;
    }
}