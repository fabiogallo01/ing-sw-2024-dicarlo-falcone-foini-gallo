package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.view.ViewTUI;
import it.polimi.ingsw.view.ViewGUI;

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
    private final ViewGUI viewGui;

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
            viewGui = new ViewGUI();
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
     * ViewGUI getter
     *
     * @return ViewGui
     * @author Foini Lorenzo
     */
    public ViewGUI getViewGui(){
        return viewGui;
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
        GameTable.getScoreboard().setScore(player, 0);
    }

    //da cancellare
    /* public void playGame(){
        ArrayList<Player> players = gameTable.getPlayers();
        Player player;
        while(!gameTable.isEnded()){
            for(int i=0; i<gameTable.getNumPlayers(); i++) {
                //clients.get(i).setTurn(true);
                //turn finished => Set false
            }
        }

        for(int i=0; i<gameTable.getNumPlayers(); i++) {
            player = players.get(i);
            //ASKS FOR WHICH CARD TO PLAY FROM THE HAND, WHICH SIDE AND WHERE TO PLACE IT
            //player.playCard(   MISSING PARAMETERS   );
            //ASKS FROM WHICH DECK TO DRAW


            if( MISSING CONDITION ){
              player.addCardHand( gameTable.drawResourceCardDeck());
            } else if( MISSING CONDITION ){
                player.addCardHand(gameTable.drawGoldCardDeck());
            } else {
                player.addCardHand(gameTable.drawCardFromTable(    MISSING PARAMETERS  ));
            }


            //clients.get(i).setTurn;
            //turn finished
        }

        //calculate objective points and sum them to their actual points
        for(int i=0; i<gameTable.getNumPlayers(); i++) {
            player = players.get(i);
            int points = player.getScore();
            points += player.calculateObjectivePoints(gameTable.getCommonObjectives());
            try {
                player.setScore(points);
            } catch (NegativeScoreException e) {
                throw new RuntimeException(e);
            }
        }


        //make leaderboard
        HashMap<Player,Integer> scoreboard =  gameTable.getScoreboard().getScores();

        List<Map.Entry<Player, Integer>> list = new LinkedList<>(scoreboard.entrySet());

        list.sort(Map.Entry.comparingByValue());

        HashMap<Object, Integer> leaderboard = new LinkedHashMap<>();
        for (Map.Entry<Player, Integer> entry : list) {
            leaderboard.put(entry.getKey(), entry.getValue());
        }

        System.out.println("Leaderboard:");
        for (Map.Entry<Player, Integer> entry : list) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }.

    }
    */

    /**
     * Method to calculate the total final points for all the players
     *
     * @author Fabio Gallo
     */
    public void calculateFinalPoints(){
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
    }

    /**
     * Method to set final points in the scoreboard
     *
     * @author Lorenzo Foini
     */
    public void finalScoreboard(){
        for(Player player : gameTable.getPlayers()) {
            GameTable.getScoreboard().setScore(player, player.getScore());
        }
    }

    /**
     * Method to order the scoreboard, from the player with max points to the one with less
     *
     * @return the ordered scoreboard
     * @author Lorenzo Foini, Fabio Gallo
     */
    public LinkedHashMap<Player, Integer> getLeaderboard(){
        HashMap<Player,Integer> scoreboard =  GameTable.getScoreboard().getScores();

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
}