package it.polimi.ingsw;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.game.ObjectiveDeck;
import java.util.ArrayList;

import org.junit.*;
import static org.junit.Assert.*;

// Test of ObjectiveDeck class
// Test objective deck
public class ObjectiveDeckTest {
    ObjectiveDeck deck;

    @Before
    public void setUp() {
        // Create all cards
        ArrayList<ObjectiveCard> objectiveCards = new ArrayList<>();
        GameObject[] objects = new GameObject[]{GameObject.NONE};

        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.SECONDARYDIAGONAL, Kingdom.FUNGIKINGDOM));
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.PRIMARYDIAGONAL, Kingdom.PLANTKINGDOM));
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.SECONDARYDIAGONAL, Kingdom.ANIMALKINGDOM));
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.PRIMARYDIAGONAL, Kingdom.INSECTKINGDOM));
        objectiveCards.add(new ObjectiveCard(3, true, objects, Pattern.LOWERRIGHT, Kingdom.FUNGIKINGDOM));
        objectiveCards.add(new ObjectiveCard(3, true, objects, Pattern.LOWERLEFT, Kingdom.PLANTKINGDOM));
        objectiveCards.add(new ObjectiveCard(3, true, objects, Pattern.UPPERRIGHT, Kingdom.ANIMALKINGDOM));
        objectiveCards.add(new ObjectiveCard(3, true, objects, Pattern.UPPERLEFT, Kingdom.INSECTKINGDOM));
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.FUNGIKINGDOM));
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.PLANTKINGDOM));
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.ANIMALKINGDOM));
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.INSECTKINGDOM));

        objects = new GameObject[]{GameObject.QUILL, GameObject.INKWELL, GameObject.MANUSCRIPT};
        objectiveCards.add(new ObjectiveCard(3, true, objects, Pattern.NONE, Kingdom.NONE));

        objects = new GameObject[]{GameObject.MANUSCRIPT, GameObject.MANUSCRIPT};
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.NONE));

        objects = new GameObject[]{GameObject.INKWELL, GameObject.INKWELL};
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.NONE));

        objects = new GameObject[]{GameObject.QUILL, GameObject.QUILL};
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.NONE));

        deck = new ObjectiveDeck(objectiveCards);
    }

    @After
    public void tearDown()
    {}

    @Test
    public void testDeckSize(){
        // Now there are 16 cards
        assertEquals("Not correct number of cards", 16, deck.deckSize());
    }

    @Test
    public void testShuffleDeck(){
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

    @Test
    public void testDrawTopCard(){
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

    @Test
    public void testDrawTopCardException() {
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