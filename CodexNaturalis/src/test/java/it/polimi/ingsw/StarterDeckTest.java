package it.polimi.ingsw;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.game.GamingDeck;
import java.util.ArrayList;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Class for testing GamingDeck class
 * It tests only for starter deck
 *
 * @author Foini Lorenzo
 */
public class StarterDeckTest {
    // Create an instance of deck
    GamingDeck deck;

    /**
     * Method for set up test
     *
     * @author Foini Lorenzo
     */
    @Before
    public void setUp() {
        ArrayList<Card> starterCards = new ArrayList<>();
        Corner[] frontCorners;
        Corner[] backCorners;
        Kingdom[] kingdoms;

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        backCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };
        kingdoms = new Kingdom[]{Kingdom.INSECTKINGDOM};
        starterCards.add(new StarterCard(false, frontCorners, backCorners, kingdoms));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };
        backCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };
        kingdoms = new Kingdom[]{Kingdom.FUNGIKINGDOM};
        starterCards.add(new StarterCard(false, frontCorners, backCorners, kingdoms));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        backCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };
        kingdoms = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.FUNGIKINGDOM};
        starterCards.add(new StarterCard(false, frontCorners, backCorners, kingdoms));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        backCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };
        kingdoms = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.INSECTKINGDOM};
        starterCards.add(new StarterCard(false, frontCorners, backCorners, kingdoms));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        backCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };
        kingdoms = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.PLANTKINGDOM};
        starterCards.add(new StarterCard(false, frontCorners, backCorners, kingdoms));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        backCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };
        kingdoms = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.FUNGIKINGDOM};
        starterCards.add(new StarterCard(false, frontCorners, backCorners, kingdoms));

        deck = new GamingDeck(starterCards);
    }

    /**
     * Method for tear down test
     *
     * @author Foini Lorenzo
     */
    @After
    public void tearDown() {
    }

    /**
     * Method for testing method deckSize()
     * Must be 6 at the start of the game
     *
     * @author Foini Lorenzo
     */
    @Test
    public void testDeckSize() {
        // Now there are 6 cards
        assertEquals("Not correct number of cards", 6, deck.deckSize());
    }

    /**
     * Method for testing method shuffleDeck()
     * Deck before shuffle and deck after shuffle must be different, the size is the same for both
     * The same happen for their list of cards
     *
     * @author Foini Lorenzo
     */
    @Test
    public void testShuffleDeck() {
        // Check if the decks have same size but different order
        ArrayList<Card> copyCards = new ArrayList<>(deck.getDeck());
        GamingDeck copyDeck = new GamingDeck(copyCards);

        // Call to method shuffle
        copyDeck.shuffleDeck();
        assertEquals("Decks have different size", copyDeck.deckSize(), deck.deckSize());
        assertNotEquals("Decks are equals after shuffle", copyDeck, deck);

        // Check the two lists
        assertNotEquals("Decks' lists are equals after shuffle", copyDeck.getDeck(), deck.getDeck());
    }

    /**
     * Method for testing method drawTopCard()
     * First check if draw all the cards is possible and correct
     * The card at the end of the list of cards must be the same as the drawn one
     * The size must be decreased by 1 after every call
     * In this test the exception mustn't be raised
     *
     * @author Foini Lorenzo
     */
    @Test
    public void testDrawTopCard() {
        try {
            int initialDeckSize = deck.deckSize();
            for (int i = 0; i < initialDeckSize; i++) {
                StarterCard expectedCard = (StarterCard) deck.getDeck().get(deck.deckSize() - 1); // Get last card
                StarterCard returnedCard = (StarterCard) deck.drawTopCard(); // Remove last card
                assertEquals(expectedCard, returnedCard); // Check if the two cards are equals
                assertEquals(initialDeckSize - (i + 1), deck.deckSize()); // Check if deck's size has decreased
            }
        } catch (EmptyDeckException e) {
            fail("Draw top card failed: " + e.getMessage());
        }
    }

    /**
     * Method for testing exception of method drawTopCard()
     * The method tries to draw from an empty deck
     * The exception must be raised adn catch
     *
     * @author Foini Lorenzo
     */
    @Test
    public void testDrawTopCardException() {
        // Create a new deck, it is empty
        GamingDeck emptyDeck = new GamingDeck(new ArrayList<>());

        try {
            emptyDeck.drawTopCard();
            fail("Expected EmptyDeckException, but no exception was thrown");
        } catch (EmptyDeckException e) {
            // Test passed: Exception thrown
        }
    }
}