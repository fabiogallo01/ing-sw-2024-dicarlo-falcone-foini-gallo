package it.polimi.ingsw;

import it.polimi.ingsw.model.game.GameTable;
import it.polimi.ingsw.model.exception.EmptyDeckException;
import it.polimi.ingsw.model.exception.EmptyObjectiveDeckException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;

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
    void testInitialization() {
        assertNotNull(gameTable, "GameTable object is not null");
        assertEquals(numPlayers, gameTable.getNumPlayers(), "Correct number of players");
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
