package it.polimi.ingsw;

import it.polimi.ingsw.model.game.GameTable;
import it.polimi.ingsw.model.game.ObjectiveDeck;
import it.polimi.ingsw.model.exception.EmptyDeckException;
import it.polimi.ingsw.model.exception.EmptyObjectiveDeckException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//import static org.junit.Assert.assertEquals;
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
        assertEquals("The size of the resource deck is not 40", 40, gameTable.getResourceDeck().deckSize());
        assertEquals("The size of the gold deck is not 40", 40, gameTable.getGoldDeck().deckSize());
        assertEquals("The size of the starter deck is not 6", 6, gameTable.getStarterDeck().deckSize());
        //ObjectiveDeck deck = gameTable.getObjectiveDeck();
        assertNotEquals("Objective deck is empty", 0, gameTable.getObjectiveDeck().deckSize());
        assertEquals("The size of the objective deck is not 16", 16, gameTable.getObjectiveDeck().deckSize());
    }


    //@Test
    //a method to test that I have created 40 resource cards


    //@Test
    //a method to test that I have created 40 gold cards

    //@Test
    //a method to test that I have created 6 starter cards

    //@Test
    //a method to test that I have created 16 objective cards


}
