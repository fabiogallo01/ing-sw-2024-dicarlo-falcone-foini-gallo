package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.game.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Controller's class
 * It contains gameTable and View parameters
 *
 * @author Foini Lorenzo
 */
public class Controller {
    private static GameTable gameTable;

    /**
     * Controller constructor, it creates a new instance of GameTable
     *
     * @param numPlayers representing the number of players in game Table
     * @author Foini Lorenzo
     */
    public Controller(int numPlayers) {
        try {
            gameTable = new GameTable(numPlayers);
        } catch (EmptyDeckException | EmptyObjectiveDeckException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Game table getter
     *
     * @return game table
     * @author Lorenzo Foini
     */
    public static GameTable getGameTable(){
        return gameTable;
    }

    /**
     * Method for create a new player given its parameters and add him in GameTable's arrayList of Players
     *
     * @param username player's username
     * @param color player's color
     * @param starterCard player's starter card
     * @param hand player's hand
     * @param secretObjectiveCard player's secret objective card
     * @author Lorenzo Foini
     */
    public static void createNewPlayer(String username, String color, StarterCard starterCard, ArrayList<GamingCard> hand, ObjectiveCard secretObjectiveCard){
        // Create and initialise new player area
        boolean[][] area = new boolean[81][81];
        for (boolean[] booleans : area) {
            Arrays.fill(booleans, true);
        }
        area[40][40] = false; // Add starter card
        ArrayList<Card> playedCards = new ArrayList<>();
        playedCards.add(starterCard); //Adding the played starter card from ViewClientHandler with the side already set
        PlayerArea playerArea = new PlayerArea(area, playedCards);

        // Assign color as an enum value
        Color colorEnum = Color.valueOf(color.toUpperCase());

        // Create new player and add it into gameTable
        Player player = new Player(username, 0, playerArea, colorEnum, secretObjectiveCard, starterCard, hand);
        gameTable.addPlayer(player);
    }
}