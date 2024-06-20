package it.polimi.ingsw;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.game.ObjectiveDeck;
import java.util.ArrayList;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Class for testing ObjectiveDeck class
 *
 * @author Foini Lorenzo, Gallo Fabio
 */
public class ObjectiveDeckTest {
    // Create an instance of deck
    ObjectiveDeck deck;

    /**
     * Method for set up test
     *
     * @author Foini Lorenzo
     */
    @Before
    public void setUp() {
        // Create all objective cards and insert them in the list
        ArrayList<ObjectiveCard> objectiveCards = new ArrayList<>();
        GameObject[] objects = new GameObject[]{GameObject.NONE};

        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.SECONDARYDIAGONAL, Kingdom.FUNGIKINGDOM, 0));
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.PRIMARYDIAGONAL, Kingdom.PLANTKINGDOM, 0));
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.SECONDARYDIAGONAL, Kingdom.ANIMALKINGDOM, 0));
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.PRIMARYDIAGONAL, Kingdom.INSECTKINGDOM, 0));
        objectiveCards.add(new ObjectiveCard(3, objects, Pattern.LOWERRIGHT, Kingdom.FUNGIKINGDOM, 0));
        objectiveCards.add(new ObjectiveCard(3, objects, Pattern.LOWERLEFT, Kingdom.PLANTKINGDOM, 0));
        objectiveCards.add(new ObjectiveCard(3, objects, Pattern.UPPERRIGHT, Kingdom.ANIMALKINGDOM, 0));
        objectiveCards.add(new ObjectiveCard(3, objects, Pattern.UPPERLEFT, Kingdom.INSECTKINGDOM, 0));
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.NONE, Kingdom.FUNGIKINGDOM, 0));
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.NONE, Kingdom.PLANTKINGDOM, 0));
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.NONE, Kingdom.ANIMALKINGDOM, 0));
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.NONE, Kingdom.INSECTKINGDOM, 0));

        objects = new GameObject[]{GameObject.QUILL, GameObject.INKWELL, GameObject.MANUSCRIPT};
        objectiveCards.add(new ObjectiveCard(3, objects, Pattern.NONE, Kingdom.NONE, 0));

        objects = new GameObject[]{GameObject.MANUSCRIPT, GameObject.MANUSCRIPT};
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.NONE, Kingdom.NONE, 0));

        objects = new GameObject[]{GameObject.INKWELL, GameObject.INKWELL};
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.NONE, Kingdom.NONE, 0));

        objects = new GameObject[]{GameObject.QUILL, GameObject.QUILL};
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.NONE, Kingdom.NONE, 0));

        // Create new objective deck given the list of cards
        deck = new ObjectiveDeck(objectiveCards);
    }

    /**
     * Method for testing method deckSize()
     * Must be 16 at the start of the game
     *
     * @author Foini Lorenzo
     */
    @Test
    public void testDeckSize_ShouldBe16AtStart(){
        // Now there are 16 cards
        assertEquals("Not correct number of cards", 16, deck.deckSize());
    }

    /**
     * Method for testing method shuffleDeck()
     * Deck before shuffle and deck after shuffle must be different, the size is the same for both
     * The same happen for their list of cards
     *
     * @author Foini Lorenzo
     */
    @Test
    public void testShuffleDeck_NormalDeckAndShuffledDeckShouldBeDifferent_SameForListsOfCards(){
        // Check if the decks have same size but different order
        ArrayList<ObjectiveCard> copyCards = new ArrayList<>(deck.getDeck());
        ObjectiveDeck copyDeck = new ObjectiveDeck(copyCards);

        // Call to method shuffle
        copyDeck.shuffleDeck();
        assertEquals("Decks have different size",copyDeck.deckSize(), deck.deckSize());
        assertNotEquals("Decks are equals after shuffle",copyDeck, deck);

        // Check the two lists
        assertNotEquals("Decks' lists are equals after shuffle",copyDeck.getDeck(), deck.getDeck());
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
    public void testDrawTopCard_CorrectDrawAllCards_ShouldNotRaiseEmptyObjectiveDeckException(){
        try{
            int initialDeckSize = deck.deckSize();
            for (int i = 0; i < initialDeckSize; i++) {
                ObjectiveCard expectedCard = deck.getDeck().get(deck.deckSize() - 1); // Get last card
                ObjectiveCard returnedCard = deck.drawTopCard(); // Remove last card
                assertEquals(expectedCard, returnedCard); // Check if the two cards are equals
                assertEquals(initialDeckSize - (i + 1), deck.deckSize()); // Check if deck's size has decreased
            }
        } catch (EmptyObjectiveDeckException e) {
            fail("Draw top card failed: " + e.getMessage());
        }
    }

    /**
     * Method for testing exception to method drawTopCard()
     * The method tries to draw from an empty deck
     * The exception must be raised adn catch
     *
     * @author Foini Lorenzo
     */
    @Test
    public void testDrawTopCardException_DrawFromEmptyDeck_ShouldRaiseEmptyObjectiveDeckException() {
        // Create a new deck, it is empty
        ObjectiveDeck emptyDeck = new ObjectiveDeck(new ArrayList<>());

        try {
            emptyDeck.drawTopCard();
            fail("Expected EmptyObjectiveDeckException, but no exception was thrown");
        } catch (EmptyObjectiveDeckException e) {
            // Test passed: Exception thrown
        }
    }
}