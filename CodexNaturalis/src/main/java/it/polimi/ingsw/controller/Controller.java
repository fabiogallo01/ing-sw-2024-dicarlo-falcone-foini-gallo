package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.view.tui.ViewTUI;

import java.util.*;

/**
 * Controller's class
 * It contains gameTable and a reference to ViewTUI class
 * It also contains parameters for correct handling of the game
 *
 * @author Foini Lorenzo, Gallo Fabio
 */
public class Controller {
    private final GameTable gameTable;
    private final ViewTUI viewTUI;
    private final ArrayList<String> availableColors = new ArrayList<>();
    private int ready = 0; // Counter representing the number of players in gameTable
                           // It is increased when a client/player join the game
    private boolean calculateFinalPoints = true; // If false => Need to calculate the final points
                                                 // If true => Final points are already calculated
    private boolean crashed = false; // If false => The game has not crashed
                                    // If true => The game has crashed, so all the clients disconnect
    private int disconnectedPlayers = 0;

    public int getDisconnectedPlayers() {
        return disconnectedPlayers;
    }

    public void setDisconnectedPlayers() {
        this.disconnectedPlayers++;
    }

    /**
     * isCrashed getter
     *
     * @return boolean for crashed. true => Game crashed, false => Game not crashed
     * @author Gallo Fabio
     */
    public boolean isCrashed() {
        return crashed;
    }

    /**
     * isCrashed setter to true => The game has crashed and all the clients disconnect
     *
     * @author Gallo Fabio
     */
    public void setCrashed() {
        crashed = true;
    }

    /**
     * ready getter
     *
     * @return value of ready
     * @author Gallo Fabio
     */
    public int getReady() {
        return ready;
    }

    /**
     * ready setter => It increments ready value by one
     * It is called when a client/player joins to the game
     *
     * @author Gallo Fabio
     */
    public void setReady() {
        this.ready++;
    }

    /**
     * Controller constructor, it creates a new instance of GameTable and ViewTUI
     * It also add the available colors
     * It catches EmptyDeckException or EmptyObjectiveDeckException if the deck are empty
     * => Never been caught
     *
     * @param numPlayers representing the number of players in game Table
     * @author Foini Lorenzo, Gallo Fabio
     */
    public Controller(int numPlayers) {
        try {
            // Create new instance of GameTable (with the number of players) and viewTUI
            gameTable = new GameTable(numPlayers);
            viewTUI = new ViewTUI();

            // Add colors in availableColors list
            availableColors.add("blue");
            availableColors.add("green");
            availableColors.add("red");
            availableColors.add("yellow");
        } catch (EmptyDeckException | EmptyObjectiveDeckException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * gameTable getter
     *
     * @return game table
     * @author Foini Lorenzo
     */
    public GameTable getGameTable(){
        return gameTable;
    }

    /**
     * viewTUI getter
     *
     * @return ViewTUI
     * @author Foini Lorenzo
     */
    public ViewTUI getViewTui(){
        return viewTUI;
    }

    /**
     * availableColors getter
     *
     * @return list of available colors
     * @author Gallo Fabio
     */
    public ArrayList<String> getAvailableColors() {
        return availableColors;
    }

    /**
     * Method for removing a given color from the list of available colors
     * It is guaranteed that such color is in the list
     *
     * @param color which color to be removed from list
     * @author Gallo Fabio
     */
    public void removeAvailableColor(String color) {
        availableColors.remove(color);
    }

    /**
     * Method for create a new player given its parameters and add him in gameTable's arrayList of Players
     *
     * @param username player's username
     * @param color player's color
     * @param starterCard player's starter card
     * @param hand player's hand
     * @param secretObjectiveCard player's secret objective card
     * @author Foini Lorenzo
     */
    public void createNewPlayer(String username, String color, StarterCard starterCard, ArrayList<GamingCard> hand, ObjectiveCard secretObjectiveCard){
        // Create and initialise new player's area
        boolean[][] area = new boolean[81][81];
        for (boolean[] booleans : area) {
            Arrays.fill(booleans, true); // Set true to all cell in the current row
        }
        area[40][40] = false; // Add starter card => Cell (40,40) contains a card, so set such cell to false

        // Create the arrayList of played cards in player's area
        ArrayList<Card> playedCards = new ArrayList<>();

        // Set starter card's position: (40,40)
        int[] starterCardPosition = new int[2];
        starterCardPosition[0] = 40;
        starterCardPosition[1] = 40;
        starterCard.setInGamePosition(starterCardPosition);
        playedCards.add(starterCard); //Adding the played starter card from ViewClientHandler with the side already set

        // Create new instance of PlayerArea for this new player
        PlayerArea playerArea = new PlayerArea(area, playedCards);

        // Assign color as an enum value
        Color colorEnum = Color.valueOf(color.toUpperCase());

        // Create new player and add it into gameTable
        Player player = new Player(username, 0, playerArea, colorEnum, secretObjectiveCard, starterCard, hand);
        gameTable.addPlayer(player);
    }

    /**
     * Method to calculate the total final points for all the players
     * It calculates objective points and sum them to their actual points
     * It is called one time for every player, but it counts the final points only for the first one
     * => For doing so it uses the boolean value of calculateFinalPoint parameter, which is true
     * => After the first time, the boolean is set to false
     *
     * @author Foini Lorenzo
     */
    public void calculateFinalPoints(){
        // Calculate the final points if the respective parameter is true, otherwise not calculate final points
        if(calculateFinalPoints){
            // Get list of gameTable's players
            ArrayList<Player> players = gameTable.getPlayers();
            for(Player player: players){
                // Get player's score
                int points = player.getScore();

                // Calculate final points and sum up to previous points
                points += player.calculateObjectivePoints(gameTable.getCommonObjectives());
                try {
                    // Assign to the player his final points
                    player.setScore(points);
                } catch (NegativeScoreException e) {
                    throw new RuntimeException(e);
                }
            }

            // Set calculateFinalPoints to false
            calculateFinalPoints = false;
        }
    }

    /**
     * Method to set final points in gameTable's scoreboard
     *
     * @author Foini Lorenzo
     */
    public void finalScoreboard(){
        // Iterate through players and assign their score in the scoreboard
        for(Player player : gameTable.getPlayers()) {
            gameTable.assignScore(player, player.getScore());
        }
    }

    /**
     * Method to order gameTable's scoreboard, from the player with max points to the one with less
     * It two players have equals points, then check the number of objectives satisfied
     *
     * @return the ordered scoreboard as a LinkedHashMap
     * @author Foini Lorenzo
     */
    public LinkedHashMap<Player, Integer> getLeaderboard(){
        // Call to method createLeaderboard
        HashMap<Player,Integer> scoreboard = createLeaderboard();

        // Create a list with map entries of teh previous scoreboard
        List<Map.Entry<Player, Integer>> orderedList = new LinkedList<>(scoreboard.entrySet());

        // Sort the list based on score first, number of objectives satisfied second
        orderedList.sort((entry1, entry2) -> {
            // Compare by score first
            int scoreComparison = entry2.getValue().compareTo(entry1.getValue());
            if (scoreComparison != 0) { // Score are different, so return the bigger one
                return scoreComparison;
            }
            // If scores are equal, compare by number of objectives satisfied
            return Integer.compare(entry2.getKey().getNumObjectivesSatisfied(), entry1.getKey().getNumObjectivesSatisfied());
        });

        // Create a new ordered LinkedHashMap
        LinkedHashMap<Player, Integer> orderedHash = new LinkedHashMap<>();

        // Add ordered couple player-score from orderedList into orderedHash
        for (Map.Entry<Player, Integer> entry : orderedList) {
            orderedHash.put(entry.getKey(), entry.getValue());
        }

        // Return the ordered HashMap of the scoreboard
        return orderedHash;
    }

    /**
     * Method to order create scoreboard by using gameTable's players and theis scores
     * It is necessary to create new scoreboard because gameTable's scoreboard is static
     * => It causes error when there are multiple matches ongoing
     *
     * @return the new scoreboard as a HashMap
     * @author Foini Lorenzo
     */
    private HashMap<Player,Integer> createLeaderboard(){
        HashMap<Player,Integer> scoreboard = new HashMap<>();
        for(Player player : gameTable.getPlayers()) {
            scoreboard.put(player, player.getScore());
        }
        return scoreboard;
    }

    /**
     * Method to calculate who are the winners based on their scores and number of objectives satisfied
     *
     * @param leaderboard ordered from first to last
     * @return list of winners as Player
     * @author Foini Lorenzo, Gallo Fabio
     */
    public ArrayList<Player> calculateWinners(LinkedHashMap<Player, Integer> leaderboard){
        // Set max score and max number of objectives satisfied to -1
        int maxScore = -1;
        int maxNumObjectiveSatisfied = -1;

        // Iterate through players for checking their scores
        for (Player player : leaderboard.keySet()) {
            // Get player's parameters
            int playerScore = player.getScore();
            int playerNumObjectiveSatisfied = player.getNumObjectivesSatisfied();

            // Check values of the two parameters with the previous maximum ones
            if(playerScore > maxScore) { // New max has been found
                // Set max score and max number of objectives satisfied
                maxScore = playerScore;
                maxNumObjectiveSatisfied = playerNumObjectiveSatisfied;
            } else if(playerScore == maxScore && playerNumObjectiveSatisfied > maxNumObjectiveSatisfied){ // New max has been found
                // Set max number of objectives satisfied
                maxNumObjectiveSatisfied = playerNumObjectiveSatisfied;
            }
        }

        // Get list of winners as Player
        ArrayList<Player> winners = new ArrayList<>();
        for (Player player : leaderboard.keySet()) {
            // If the current player has score the maximum points and has the maximum number of objectives satisfied
            // => Add it to the list of winners
            if (player.getScore() == maxScore && player.getNumObjectivesSatisfied() == maxNumObjectiveSatisfied) {
                winners.add(player);
            }
        }
        return winners;
    }

    /**
     * Method for starting the game
     * => It set first player's turn to true, so the game can start
     *
     * @author Gallo Fabio
     */
    public void startGame() {
        gameTable.getPlayers().getFirst().setTurn(true);
    }

    /**
     * Method for setting to true the turn of next player
     *
     * @author Foini Lorenzo, Gallo Fabio
     */
    public void nextTurn(){
        int i=0;
        for(Player player : gameTable.getPlayers()) {
            if(player.isTurn()) {
                // Turn is finished, so set turn to false
                player.setTurn(false);

                // Set next player's turn to true
                // Need to check if the previous player is the last in gameTable list for restarting
                if(i==gameTable.getNumPlayers()-1){
                    gameTable.getPlayers().getFirst().setTurn(true);
                    break;
                }
                gameTable.getPlayers().get(i+1).setTurn(true);
                break;
            }
            i++;
        }
    }
}