package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.view.tui.ViewTUI;

import java.util.*;

/**
 * Controller's class
 * It contains gameTable and View parameters
 *
 * @author Foini Lorenzo
 */
public class Controller {
    private final GameTable gameTable;
    private final ViewTUI viewTui;
    private final ArrayList<String> availableColors = new ArrayList<>();
    private int ready = 0;
    private boolean calculateFinalPoints = true;


    public int getReady() {
        return ready;
    }

    public void setReady() {
        this.ready++;
    }

    /**
     * Controller constructor, it creates a new instance of GameTable
     *
     * @param numPlayers representing the number of players in game Table
     * @author Foini Lorenzo
     */
    public Controller(int numPlayers) {
        try {
            gameTable = new GameTable(numPlayers);
            viewTui = new ViewTUI();
            availableColors.add("blue");
            availableColors.add("green");
            availableColors.add("red");
            availableColors.add("yellow");
        } catch (EmptyDeckException | EmptyObjectiveDeckException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Game table getter
     *
     * @return game table
     * @author Foini Lorenzo
     */
    public GameTable getGameTable(){
        return gameTable;
    }

    /**
     * ViewTUI getter
     *
     * @return ViewTui
     * @author Foini Lorenzo
     */
    public ViewTUI getViewTui(){
        return viewTui;
    }

    /**
     * available colors getter
     *
     * @return list of available colors
     * @author Foini Lorenzo
     */
    public ArrayList<String> getAvailableColors() {
        return availableColors;
    }

    /**
     * Method for removing a given color from the list of available colors
     *
     * @param color which color to be removed from list
     * @author Foini Lorenzo
     */
    public void removeAvailableColor(String color) {
        availableColors.remove(color);
    }

    /**
     * Method for create a new player given its parameters and add him in GameTable's arrayList of Players
     *
     * @param username player's username
     * @param color player's color
     * @param starterCard player's starter card
     * @param hand player's hand
     * @param secretObjectiveCard player's secret objective card
     * @author Foini Lorenzo
     */
    public void createNewPlayer(String username, String color, StarterCard starterCard, ArrayList<GamingCard> hand, ObjectiveCard secretObjectiveCard){
        // Create and initialise new player area
        boolean[][] area = new boolean[81][81];
        for (boolean[] booleans : area) {
            Arrays.fill(booleans, true);
        }
        area[40][40] = false; // Add starter card
        ArrayList<Card> playedCards = new ArrayList<>();
        // Set starter card position
        int[] starterCardPosition = new int[2];
        starterCardPosition[0] = 40;
        starterCardPosition[1] = 40;
        starterCard.setInGamePosition(starterCardPosition);
        playedCards.add(starterCard); //Adding the played starter card from ViewClientHandler with the side already set

        PlayerArea playerArea = new PlayerArea(area, playedCards);

        // Assign color as an enum value
        Color colorEnum = Color.valueOf(color.toUpperCase());

        // Create new player and add it into gameTable and scoreboard
        Player player = new Player(username, 0, playerArea, colorEnum, secretObjectiveCard, starterCard, hand);
        gameTable.addPlayer(player);
    }

    /**
     * Method to calculate the total final points for all the players
     *
     * @author Fabio Gallo
     */
    public void calculateFinalPoints(){
        // Calculate the final points if the respective parameter is true, otherwise not calculate final points
        if(calculateFinalPoints){
            //calculate objective points and sum them to their actual points
            ArrayList<Player> players = gameTable.getPlayers();
            Player player;
            for(int i=0; i<gameTable.getNumPlayers(); i++) {
                // Get player
                player = players.get(i);
                // Get score
                int points = player.getScore();
                points += player.calculateObjectivePoints(gameTable.getCommonObjectives());
                try {
                    player.setScore(points);
                } catch (NegativeScoreException e) {
                    throw new RuntimeException(e);
                }
            }
            calculateFinalPoints = false;
        }
    }

    /**
     * Method to set final points in the scoreboard
     *
     * @author Lorenzo Foini
     */
    public void finalScoreboard(){
        for(Player player : gameTable.getPlayers()) {
            gameTable.assignScore(player, player.getScore());
        }
    }

    /**
     * Method to order the scoreboard, from the player with max points to the one with less
     *
     * @return the ordered scoreboard
     * @author Lorenzo Foini, Fabio Gallo
     */
    public LinkedHashMap<Player, Integer> getLeaderboard(){
        HashMap<Player,Integer> scoreboard = createLeaderboard();

        // Create a list with map entries
        List<Map.Entry<Player, Integer>> orderedList = new LinkedList<>(scoreboard.entrySet());

        orderedList.sort((entry1, entry2) -> {
            // Decreasing order
            return entry2.getValue().compareTo(entry1.getValue());
        });

        // Create a new ordered LinkedHashMap
        LinkedHashMap<Player, Integer> orderedHash = new LinkedHashMap<>();

        // Add ordered couple player-score
        for (Map.Entry<Player, Integer> entry : orderedList) {
            orderedHash.put(entry.getKey(), entry.getValue());
        }

        return orderedHash;
    }

    private HashMap<Player,Integer> createLeaderboard(){
        HashMap<Player,Integer> scoreboard = new HashMap<>();
        for(Player player : gameTable.getPlayers()) {
            scoreboard.put(player, player.getScore());
        }
        return scoreboard;
    }

    /**
     * Method to calculate who are the winners
     *
     * @param leaderboard ordered from first to last
     * @return list of winners
     * @author Lorenzo Foini
     */
    public ArrayList<Player> calculateWinners(LinkedHashMap<Player, Integer> leaderboard){
        // Find max score
        int maxScore = 0;
        for (int score : leaderboard.values()) {
            maxScore = Math.max(maxScore, score);
        }

        // Get list of winners
        ArrayList<Player> winners = new ArrayList<>();
        for (Map.Entry<Player, Integer> entry : leaderboard.entrySet()) {
            if (entry.getValue() == maxScore) {
                winners.add(entry.getKey());
            }
        }
        return winners;
    }

    public void startGame() {
        gameTable.getPlayers().getFirst().setTurn(true);
    }
    public void nextTurn(){
        int i=0;
        for(Player player : gameTable.getPlayers()) {
            if(player.isTurn()) {
                player.setTurn(false);
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