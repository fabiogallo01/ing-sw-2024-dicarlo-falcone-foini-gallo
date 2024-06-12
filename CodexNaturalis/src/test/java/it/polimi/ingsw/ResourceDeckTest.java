package it.polimi.ingsw;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.game.GamingDeck;
import java.util.ArrayList;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Class for testing GamingDeck class
 * It tests only for resource deck
 *
 * @author Foini Lorenzo
 */
public class ResourceDeckTest {
    // Create an instance of deck
    GamingDeck deck;

    /**
     * Method for set up test
     *
     * @author Foini Lorenzo
     */
    @Before
    public void setUp() {
        ArrayList<Card> resourceCards = new ArrayList<>();
        Corner[] frontCorners;

        //FUNGI
        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners, 0));

        //PLANT
        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 1, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 1, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 1, frontCorners, 0));

        //ANIMAL
        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 1, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 1, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 1, frontCorners, 0));

        //INSECT
        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 1, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 1, frontCorners, 0));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 1, frontCorners, 0));

        deck = new GamingDeck(resourceCards);
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
     * Must be 40 at the start of the game
     *
     * @author Foini Lorenzo
     */
    @Test
    public void testDeckSize_ShouldBe40() {
        // Now there are 40 cards
        assertEquals("Not correct number of cards", 40, deck.deckSize());
    }

    /**
     * Method for testing method shuffleDeck()
     * Deck before shuffle and deck after shuffle must be different, the size is the same for both
     * The same happen for their list of cards
     *
     * @author Foini Lorenzo
     */
    @Test
    public void testShuffleDeck_NormalDeckAndShuffledDeckShouldBeDifferent_SameForListsOfCards() {
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
    public void testDrawTopCard_CorrectDrawAllCards_ShouldNotRaiseException() {
        try {
            int initialDeckSize = deck.deckSize();
            for (int i = 0; i < initialDeckSize; i++) {
                GamingCard expectedCard = (GamingCard) deck.getDeck().get(deck.deckSize() - 1); // Get last card
                GamingCard returnedCard = (GamingCard) deck.drawTopCard(); // Remove last card
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
    public void testDrawTopCardException_DrawFromEmptyDeck_ShouldRaiseException() {
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