package it.polimi.ingsw;

//import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.InvalidPlayException;
import it.polimi.ingsw.model.exception.NegativeScoreException;
import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.exception.EmptyDeckException;
import it.polimi.ingsw.model.exception.EmptyObjectiveDeckException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;

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
     * Test drawing from resource deck
     * gameTable.drawResourceCardDeck()
     * Should not throw EmptyDeckException for a full deck
     */


    /**
     * Test drawing from an empty deck throws EmptyDeckException
     * gameTable.drawResourceCardDeck()
     * Should throw EmptyDeckException when drawing from an empty resource deck
     */

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
     * Test setting and getting visible cards
     * gameTable.setVisibleCard(visibleCards)
     * gameTable.getVisibleCard()
     * Visible cards should be set and get correctly
     * devono essere 4 (una lista di 2 + 2)
     */

    /*@DisplayName("Test setting and getting visible cards")
    void testVisibleCardsManipulation() {
        GamingCard card = new GamingCard(true, Kingdom.FUNGIKINGDOM, 1, new Corner[]{new Corner(true, true, GameObject.NONE, Kingdom.NONE)});
        ArrayList<GamingCard> visibleCards = gameTable.getVisibleCard().size();
        visibleCards.add(card);
        gameTable.setVisibleCard(visibleCards);
        assertEquals(visibleCards, gameTable.getVisibleCard(), "Visible cards should be set and get correctly");
    }*/


    /**
     * Ensure game can end under correct conditions
     * gameTable.isEnded()
     * Game should not end immediately after initialization
     * gameTable.getPlayers().forEach(player -> player.setScore(20))
     * Game should end when at least one player has 20 or more points
     */

    /*@Test
    @DisplayName("Ensure game can end under correct conditions")
    void testGameEndConditions() {
        assertFalse(gameTable.isEnded(), "Game should not end immediately after initialization");
        // Manipulate the game state to test ending condition
        gameTable.getPlayers().forEach(player -> player.setScore(20));
        assertTrue(gameTable.isEnded(), "Game should end when at least one player has 20 or more points");
    }*/

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
        /*try {
            gameTable.drawResourceCardDeck();
            fail("All went good.");
        } catch (EmptyDeckException exception) {
            assertEquals("This deck is empty, you can't draw from this deck.", exception.getMessage());
            System.out.println(exception.getMessage());
        }*/
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


        /*GameTable gameTable = new GameTable(4) {
            protected GamingDeck createResourceDeck() {
                return new GamingDeck(new ArrayList<>()); // return an empty deck
            }
            protected GamingDeck createGoldDeck() {
                return new GamingDeck(new ArrayList<>()); // return an empty deck
            }
        };
    }*/
    /*@Test(expected = EmptyObjectiveDeckException.class)
    public void testEmptyObjectiveDeckException() throws EmptyDeckException, EmptyObjectiveDeckException {
        GameTable gameTable = new GameTable(4) {
            protected ObjectiveDeck createObjectiveDeck() {
                return new ObjectiveDeck(new ArrayList<>()); // return an empty deck
            }
        };
    }*/



    @Test
    public void testGameEndsWhenPlayerReaches20Points() {
        // Impostiamo il punteggio di un giocatore a 20
        try {
            gameTable.getPlayers().get(0).setScore(20);
        } catch (NegativeScoreException e) {
            throw new RuntimeException(e);
        }
        assertTrue("Game should end when a player reaches 20 points", gameTable.isEnded());
    }

    /*@Test
    public void testGameDoesNotEndWhenNoPlayerReaches20Points() {
        // Assicuriamoci che nessun giocatore abbia 20 punti
        gameTable.getPlayers().forEach(player -> player.setScore(19));
        assertFalse("Game should not end if no player reaches 20 points", gameTable.isEnded());
    }

    @Test
    public void testGameEndsWhenAllDecksAreEmptyAndNoVisibleCards() {
        // Svuotiamo tutti i mazzi e rimuoviamo tutte le carte visibili
        emptyDeck(gameTable.getResourceDeck());
        emptyDeck(gameTable.getGoldDeck());
        emptyDeck(gameTable.getStarterDeck());
        gameTable.getVisibleCard().clear();

        assertTrue("Game should end when all decks are empty and no visible cards", gameTable.isEnded());
    }

    @Test
    public void testGameDoesNotEndWhenDecksNotEmptyOrVisibleCardsPresent() {
        // Assicuriamoci che almeno un mazzo o le carte visibili non siano vuote
        gameTable.addVisibleCard(new GamingCard(true, Kingdom.FUNGIKINGDOM, 1, new Corner[]{new Corner(true, true, GameObject.NONE, Kingdom.NONE)}));
        assertFalse("Game should not end when there are still cards in at least one deck or visible cards", gameTable.isEnded());
    }

    private void emptyDeck(GamingDeck deck) {
        while (deck.deckSize() > 0) {
            try {
                deck.drawTopCard();
            } catch (EmptyDeckException ignored) {
            }
        }
    }
*/
}
