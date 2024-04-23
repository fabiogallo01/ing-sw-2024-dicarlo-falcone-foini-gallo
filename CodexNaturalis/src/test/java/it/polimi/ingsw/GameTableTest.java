package it.polimi.ingsw;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.game.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;


public class GameTableTest {

    GameTable gameTable = null;
    private int numPlayers = 4;


    /**
     * Initialized GameTable with a numPlayers number of players
     *
     * @author giacomofalcone
     */
    @Before
    public void setUp() {
        //gameTable = new GameTable(numPlayers);
        try {
            gameTable = new GameTable(numPlayers);
        }
        catch (EmptyDeckException | EmptyObjectiveDeckException exception) {
            fail("Initialization failed: " + exception.getMessage());
        }
    }


    @After
    public void tearDown()
    {}


    /**
     * Tests if the gameTable initialization is not null
     * and if the number of players is correct
     *
     * @author giacomofalcone
     */
    @Test
    public void testInitialization() {
        assertNotNull("GameTable object is null", gameTable); //"GameTable object is not null"
        assertEquals("Not correct number of players", numPlayers, gameTable.getNumPlayers()); //"Correct number of players"
    }


    /**
     * Tests that decks are not empty on initialization
     * Tests that deck's sizes are correct
     *
     * @author giacomofalcone
     */
    @Test
    public void testDeckSize() {
        assertNotEquals("Resource deck is empty", 0, gameTable.getResourceDeck().deckSize());
        assertNotEquals("Gold deck is empty", 0, gameTable.getGoldDeck().deckSize());
        assertNotEquals("Starter deck is empty", 0, gameTable.getStarterDeck().deckSize());
        assertEquals("The size of the resource deck is not 38", 38, gameTable.getResourceDeck().deckSize());
        assertEquals("The size of the gold deck is not 38", 38, gameTable.getGoldDeck().deckSize());
        assertEquals("The size of the starter deck is not 6", 6, gameTable.getStarterDeck().deckSize());
        assertNotEquals("Objective deck is empty", 0, gameTable.getObjectiveDeck().deckSize());
        assertEquals("The size of the objective deck is not 14", 14, gameTable.getObjectiveDeck().deckSize());
    }


    /**
     * Tests that there are 4 visible cards on the board
     *
     * @author giacomofalcone
     */
    @Test
    public void testNumberOfVisibleCards() {
        assertEquals("Visible cards are not 4", 4,gameTable.getVisibleCard().size());
    }


    /**
     * Tests that after drawing all card from the resource deck i get an exception
     * if I draw another time
     *
     * @throws EmptyDeckException
     * @throws EmptyObjectiveDeckException
     * @author giacomofalcone
     */
    @Test(expected = EmptyDeckException.class)
    public void testEmptyResourceDeckException() throws EmptyDeckException, EmptyObjectiveDeckException {
        gameTable = new GameTable(numPlayers);
        // Svuotiamo il mazzo risorsa per testare l'eccezione
        while (gameTable.getResourceDeck().deckSize() > 0) {
            //gameTable.getResourceDeck().drawTopCard();
            gameTable.drawResourceCardDeck();
        }
        gameTable.drawResourceCardDeck();
    }


    /**
     * Tests that after drawing all card from the resource deck i get an exception
     * if I draw another time
     *
     * @throws EmptyDeckException
     * @throws EmptyObjectiveDeckException
     * @author giacomofalcone
     */
    @Test(expected = EmptyDeckException.class)
    public void testEmptyGoldDeckException() throws EmptyDeckException, EmptyObjectiveDeckException {
        gameTable = new GameTable(numPlayers);
        // Svuotiamo il mazzo risorsa per testare l'eccezione
        while (gameTable.getGoldDeck().deckSize() > 0) {
            //gameTable.getResourceDeck().drawTopCard();
            gameTable.drawGoldCardDeck();
        }
        gameTable.drawGoldCardDeck();
    }


    /**
     * Tests that game ends if a player reaches 20 points
     * and that doesn't end before with all the players with less than 20 points
     *
     * @author giacomofalcone
     */
    @Test
    public void testGameEndsWhenPlayerReaches20Points() {
        //creating all necessaries parameters for a player

        //creating player area
        boolean[][] Area = new boolean[81][81];
        for (int row = 0; row < Area.length; row++) {
            for (int col = 0; col < Area[row].length; col++) {
                Area[row][col] = true;
            }
        }
        PlayerArea playerArea = new PlayerArea(Area, new ArrayList<Card>());

        //creating all the cards needed for the player to start the game
        ObjectiveCard secretObjective = new ObjectiveCard(1, true, null, Pattern.NONE, Kingdom.NONE);
        StarterCard starterCard = new StarterCard(true, new Corner[4], new Corner[4], new Kingdom[]{Kingdom.NONE});
        ArrayList<GamingCard> hand = new ArrayList<>();  //empty hand

        Color playerColor = Color.GREEN;

        //assigning all the created values to player1 and adding it to the game table
        Player player1 = new Player("player", 19, playerArea, playerColor, secretObjective, starterCard, hand);
        gameTable.addPlayer(player1);

        // adding other players with less than 20 points
        Player player = null;
        for (int i = 0; i < 3; i++) {
            player = new Player("Player" + i, 5 + i, playerArea, Color.RED, null, null, hand);
            gameTable.addPlayer(player);
        }

        //Checking if the game ends before a player has reached 20 points
        assertFalse("No one reached 20 points, but the game ended", gameTable.isEnded());

        //setting the score of player1 to 20
        try {
            player1.setScore(20);
        } catch (NegativeScoreException exception) {
            System.out.println(exception.getMessage());
        }

        //Checking if the game is over given that a player has reached 20 points
        assertTrue("A player reached 20 points, but the game didn't end", gameTable.isEnded());
    }

}
