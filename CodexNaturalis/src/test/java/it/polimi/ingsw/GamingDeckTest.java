package it.polimi.ingsw;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.game.GamingDeck;
import java.util.ArrayList;

import org.junit.*;
import static org.junit.Assert.*;

// Test of GamingDeck class
// Test gold deck
public class GamingDeckTest {

    GamingDeck deck;

    @Before
    public void setUp() {
        // Create gold card
        ArrayList<Card> goldCards = new ArrayList<>();
        Corner[] frontCorners;
        Kingdom[] resources;

        //FUNGI cards
        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.ANIMALKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners, resources, ConditionPoint.QUILL));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.PLANTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners, resources, ConditionPoint.INKWELL));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.INSECTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners, resources, ConditionPoint.MANUSCRIPT));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.ANIMALKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.PLANTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.INSECTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 5, frontCorners, resources, ConditionPoint.NONE));

        //PLANT cards
        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.INSECTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 1, frontCorners, resources, ConditionPoint.QUILL));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.FUNGIKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 1, frontCorners, resources, ConditionPoint.MANUSCRIPT));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.ANIMALKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 1, frontCorners, resources, ConditionPoint.INKWELL));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.INSECTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.ANIMALKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.FUNGIKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 5, frontCorners, resources, ConditionPoint.NONE));

        //ANIMAL cards
        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.INSECTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 1, frontCorners, resources, ConditionPoint.INKWELL));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.PLANTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 1, frontCorners, resources, ConditionPoint.MANUSCRIPT));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.FUNGIKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 1, frontCorners, resources, ConditionPoint.QUILL));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.INSECTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.FUNGIKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.PLANTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 5, frontCorners, resources, ConditionPoint.NONE));

        //INSECT cards
        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.PLANTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 1, frontCorners, resources, ConditionPoint.QUILL));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.ANIMALKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 1, frontCorners, resources, ConditionPoint.MANUSCRIPT));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.FUNGIKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 1, frontCorners, resources, ConditionPoint.INKWELL));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.ANIMALKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.PLANTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.FUNGIKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM};
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 5, frontCorners, resources, ConditionPoint.NONE));

        // Create gold deck
        deck = new GamingDeck(goldCards);
    }

    @After
    public void tearDown()
    {}

    @Test
    public void testDeckSize(){
        // Now there are 40 cards
        assertEquals("Not correct number of cards", 40, deck.deckSize());
    }

    @Test
    public void testShuffleDeck(){
        // Check if the decks have same size but different order
        ArrayList<Card> copyCards = new ArrayList<>(deck.getDeck());
        GamingDeck copyDeck = new GamingDeck(copyCards);

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
                GoldCard expectedCard = (GoldCard) deck.getDeck().get(deck.deckSize() - 1); // Get last card
                GoldCard returnedCard = (GoldCard) deck.drawTopCard(); // Remove last card
                assertEquals(expectedCard, returnedCard); // Check if the two cards are equals
                assertEquals(initialDeckSize - (i + 1), deck.deckSize()); // Check if deck's size has decreased
            }
        } catch (EmptyDeckException e) {
            fail("Draw top card failed: " + e.getMessage());
        }
    }

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