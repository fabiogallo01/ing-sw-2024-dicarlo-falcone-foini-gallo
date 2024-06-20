package it.polimi.ingsw;

import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.cards.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Class for testing PlayerArea class
 *
 * @author Di Carlo Andrea, Foini Lorenzo, Gallo Fabio
 */
public class PlayerAreaTest {
    // Initialize variables
    private PlayerArea playerArea;
    private Corner[] Corners;

    /**
     * Initialized PlayerArea with starter card already played on back
     *
     * @author Di Carlo Andrea, Foini Lorenzo, Gallo Fabio
     */
    @Before
    public void setUp() {
        //Create matrix area
        boolean[][] Area = new boolean[81][81];
        for (boolean[] booleans : Area) {
            Arrays.fill(booleans, true);
        }

        playerArea = new PlayerArea(Area, new ArrayList<>());


        // Create starterCard
        Corner[] startCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };

        Corner[] backCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        Corners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        Card startercard = new StarterCard(false, backCorners, startCorners, new Kingdom[]{Kingdom.NONE}, 0);
        playerArea.addCard(startercard, new int[]{40, 40});

    }

    /**
     * Method for testing countKingdoms() with the four kingdoms
     *
     * @author Di Carlo Andrea
     */
    @Test
    public void TestCountKingdomStarterCard_ShouldBe1ForAllKingdoms() {
        //Should return 1
        assertEquals(1, playerArea.countKingdoms(Kingdom.ANIMALKINGDOM));
        assertEquals(1, playerArea.countKingdoms(Kingdom.FUNGIKINGDOM));
        assertEquals(1, playerArea.countKingdoms(Kingdom.PLANTKINGDOM));
        assertEquals(1, playerArea.countKingdoms(Kingdom.INSECTKINGDOM));
    }

    /**
     * Method for testing countKingdoms() of animal kingdom after play a new card with 1 animal kingdom
     *
     * @author Di Carlo Andrea, Gallo Fabio
     */
    @Test
    public void TestTwoKingdom_AddNewCardWithOneAnimalKingdom() {
        // Add card with kingdom ANIMALKINGDOM
        Card card1 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        playerArea.addCard(card1, new int[]{39, 39});

        // Should return 2
        assertEquals(2, playerArea.countKingdoms(Kingdom.ANIMALKINGDOM));
    }

    /**
     * Method for testing countKingdoms() of planet kingdom after a corner is covered
     *
     * @author Di Carlo Andrea, Gallo Fabio
     */
    @Test
    public void TestKingdomCovered_CounterShouldBe0() {
        // Should return 0 because PLANTKINGDOM is covered
        Card card1 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        playerArea.addCard(card1, new int[]{39, 39});
        assertEquals(0, playerArea.countKingdoms(Kingdom.PLANTKINGDOM));

    }

    /**
     * Method for testing countKingdoms() of insect kingdom after a gold card has been played
     *
     * @author Di Carlo Andrea, Gallo Fabio
     */
    @Test
    public void TestGoldCardKingdom_AddAGoldCardAndCheckCounter(){
        // Add goldCard with kingdom INSECTKINGDOM
        Kingdom[] resources = new Kingdom[]{Kingdom.NONE};
        Card goldCard = new GoldCard(false, Kingdom.INSECTKINGDOM, 0, Corners, resources, ConditionPoint.NONE, 0);
        playerArea.addCard(goldCard, new int[]{41, 41});

        // Should return 1 because previous corner with INSECTKINGDOM is covered
        // But there is a new one with the gold card played on back
        assertEquals(1, playerArea.countKingdoms(Kingdom.INSECTKINGDOM));
    }

    /**
     * Re-initialization of PlayerArea for calculating the objects
     *
     * @author Di Carlo Andrea,  Gallo Fabio
     */
    @After
    public void setUpTestCountObject() {
        //Create matrix area
        boolean[][] Area = new boolean[81][81];
        for (boolean[] booleans : Area) {
            Arrays.fill(booleans, true);
        }

        playerArea = new PlayerArea(Area, new ArrayList<>());

        // Create starterCard
        Corner[] startCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };

        Corners = new Corner[]{
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        Card startercard = new StarterCard(false, Corners, startCorners, new Kingdom[]{Kingdom.NONE}, 0);
        playerArea.addCard(startercard, new int[]{40, 40});
    }

    /**
     * Method for testing countObject() with the three objects
     * No object is present => Should be 0 for all three and 4 for NONE
     *
     * @author Di Carlo Andrea
     */
    @Test
    public void TestCounterObjectsStarterCard_NoObjectsArePresent_NONEShouldBe4() {
        assertEquals(0, playerArea.countObject(GameObject.MANUSCRIPT));
        assertEquals(0, playerArea.countObject(GameObject.INKWELL));
        assertEquals(0, playerArea.countObject(GameObject.QUILL));
        assertEquals(4, playerArea.countObject(GameObject.NONE));
    }

    /**
     * Method for testing countObject() with the three objects
     * Add a card with 1 object for each objects
     *
     * @author Di Carlo Andrea, Gallo Fabio
     */
    @Test
    public void testAllCountObjects_1OccurrenceForEveryObject(){
        Corners = new Corner[]{
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        Card card1 = new GamingCard(true, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        playerArea.addCard(card1, new int[]{39, 39});

        assertEquals(1, playerArea.countObject(GameObject.MANUSCRIPT));
        assertEquals(1, playerArea.countObject(GameObject.INKWELL));
        assertEquals(1, playerArea.countObject(GameObject.QUILL));
        assertEquals(4, playerArea.countObject(GameObject.NONE));

        Card card2 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        playerArea.addCard(card2, new int[]{38, 38});

        // The corner with MANUSCRIPT is now covered
        assertEquals(0, playerArea.countObject(GameObject.MANUSCRIPT));
    }

    /**
     * Re-initialization of PlayerArea for calculating the hidden corners
     *
     * @author Di Carlo Andrea, Gallo Fabio
     */
    @After
    public void setUpTestCountHiddenCorner() {
        //Create matrix area
        boolean[][] Area = new boolean[81][81];
        for (boolean[] booleans : Area) {
            Arrays.fill(booleans, true);
        }

        PlayerArea playerArea = new PlayerArea(Area, new ArrayList<>());

        // Create starterCard
        Corner[] startCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };

        Corner[] Corners = new Corner[]{
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        Card startercard = new StarterCard(false, Corners, startCorners, new Kingdom[]{Kingdom.NONE}, 0);
        playerArea.addCard(startercard, new int[]{40, 40});
    }

    /**
     * Method for testing countHiddenCorner() after a gold card with such condition is played
     * The card covers one corner, so the method should return 1
     *
     * @author Di Carlo Andrea, Gallo Fabio
     */
    @Test
    public void TestCountHiddenCornerGoldCard_Covers1Corner() {
        // Add goldCard with kingdom INSECTKINGDOM
        Kingdom[] resources = new Kingdom[]{Kingdom.NONE};
        Card goldCard1 = new GoldCard(false, Kingdom.INSECTKINGDOM, 0, Corners, resources, ConditionPoint.HIDDENCORNER, 0);
        playerArea.addCard(goldCard1, new int[]{41, 41});

        // Cover one corner
        assertEquals(1, playerArea.countHiddenCorner(new int[]{41, 41}));
    }

    /**
     * Method for testing countHiddenCorner() after a gold card with such condition is played
     * The card covers two corners, so the method should return 2
     *
     * @author Di Carlo Andrea, Gallo Fabio
     */
    @Test
    public void TestCountHiddenCornerGoldCard_Cover2Corners() {
        Kingdom[] resources = new Kingdom[]{Kingdom.NONE};
        Card card1 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        Card goldCard2 = new GoldCard(false, Kingdom.INSECTKINGDOM, 0, Corners, resources, ConditionPoint.HIDDENCORNER, 0);
        playerArea.addCard(card1, new int[]{42, 40});
        playerArea.addCard(goldCard2, new int[]{41, 39});

        // Gold card covers two corners
        assertEquals(2, playerArea.countHiddenCorner(new int[]{41, 39}));
    }

    /**
     * Re-initialization of PlayerArea for calculating the pattern
     *
     * @author Di Carlo Andrea, Gallo Fabio
     */
    @After
    public void setUpTestCountPattern() {
        // Create matrix area
        boolean[][] Area = new boolean[81][81];
        for (boolean[] booleans : Area) {
            Arrays.fill(booleans, true);
        }

        PlayerArea playerArea = new PlayerArea(Area, new ArrayList<>());

        // Create starterCard
        Corner[] startCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };

        Corner[] Corners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        Card startercard = new StarterCard(false, Corners, startCorners, new Kingdom[]{Kingdom.NONE}, 0);
        playerArea.addCard(startercard, new int[]{40, 40});
    }

    /**
     * Method for testing countPattern() when there is the diagonal pattern with 3 plant cards
     *
     * @author Di Carlo Andrea, Gallo Fabio
     */
    @Test
    public void TestCountPatter_DiagonalPatternWith3PlantCards() {
        // Test diagonal pattern with 3 plant cards
        Card card1 = new GamingCard(false, Kingdom.PLANTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card1, new int[]{39, 39});

        Card card2 = new GamingCard(false, Kingdom.PLANTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card2, new int[]{38, 38});

        Card card3 = new GamingCard(false, Kingdom.PLANTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card3, new int[]{37, 37});

        // One patter is found
        assertEquals(1, playerArea.countPattern(Kingdom.PLANTKINGDOM, Pattern.PRIMARYDIAGONAL));
    }

    /**
     * Method for testing countPattern() when there is the diagonal pattern with 3 animal cards
     *
     * @author Di Carlo Andrea, Gallo Fabio
     */
    @Test
    public void TestCountPattern_DiagonalPatternWith3AnimalCards() {
        // Test diagonal pattern with 3 animal cards
        Card card4 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        playerArea.addCard(card4, new int[]{41, 39});

        Card card5 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        playerArea.addCard(card5, new int[]{42, 38});

        Card card6 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        playerArea.addCard(card6, new int[]{43, 37});

        // Found 1 pattern
        assertEquals(1, playerArea.countPattern(Kingdom.ANIMALKINGDOM, Pattern.SECONDARYDIAGONAL));
    }

    /**
     * Method for testing countPattern() when there is the diagonal pattern with 3 insect cards
     *
     * @author Di Carlo Andrea, Gallo Fabio
     */
    @Test
    public void TestCountPattern_DiagonalPatternWith3InsectCards() {
        // Test diagonal pattern with 3 insect cards
        Card card7 = new GamingCard(false, Kingdom.INSECTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card7, new int[]{41, 41});

        Card card8 = new GamingCard(false, Kingdom.INSECTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card8, new int[]{42, 42});

        Card card9 = new GamingCard(false, Kingdom.INSECTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card9, new int[]{43, 43});

        // Found 1 pattern
        assertEquals(1, playerArea.countPattern(Kingdom.INSECTKINGDOM, Pattern.PRIMARYDIAGONAL));
    }

    /**
     * Method for testing countPattern() when there is the diagonal pattern with 3 fungi cards
     *
     * @author Di Carlo Andrea, Gallo Fabio
     */
    @Test
    public void TestCountPattern_DiagonalPatternWith3FungiCards() {
        // Test diagonal pattern with 3 fungi cards
        Card card10 = new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, Corners, 0);
        playerArea.addCard(card10, new int[]{44, 36});

        Card card11 = new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, Corners, 0);
        playerArea.addCard(card11, new int[]{45, 35});

        Card card12 = new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, Corners, 0);
        playerArea.addCard(card12, new int[]{46, 34});

        // Found 1 pattern
        assertEquals(1, playerArea.countPattern(Kingdom.FUNGIKINGDOM, Pattern.SECONDARYDIAGONAL));
    }

    /**
     * Method for testing countPattern() when there is the pattern with 2 fungi cards and 1 plant card
     *
     * @author Di Carlo Andrea, Gallo Fabio
     */
    @Test
    public void TestCountPattern_PatternWith2FungiCardsAnd1PlantCard() {
        // Test pattern with 2 fungi cards and 1 plant card
        Card card13 = new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, Corners, 0);
        playerArea.addCard(card13, new int[]{44, 44});

        Card card14 = new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, Corners, 0);
        playerArea.addCard(card14, new int[]{46, 44});

        Card card15 = new GamingCard(false, Kingdom.PLANTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card15, new int[]{47, 45});

        // Found 1 pattern
        assertEquals(1, playerArea.countPattern(Kingdom.FUNGIKINGDOM, Pattern.LOWERRIGHT));
    }

    /**
     * Method for testing countPattern() when there is the pattern with 2 plant cards and 1 insect card
     *
     * @author Di Carlo Andrea, Gallo Fabio
     */
    @Test
    public void TestCountPattern_PatternWith2PlantCardsAnd1InsectCard() {
        // Test pattern with 2 plant cards and 1 insect card
        Card card3 = new GamingCard(false, Kingdom.PLANTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card3, new int[]{37, 37});

        Card card16 = new GamingCard(false, Kingdom.PLANTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card16, new int[]{39, 37});

        Card card17 = new GamingCard(false, Kingdom.INSECTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card17, new int[]{40, 36});

        // Found 1 pattern
        assertEquals(1, playerArea.countPattern(Kingdom.PLANTKINGDOM, Pattern.LOWERLEFT));
    }

    /**
     * Method for testing countPattern() when there is the pattern with 2 animal cards and 1 fungi card
     *
     * @author Di Carlo Andrea, Gallo Fabio
     */
    @Test
    public void TestCountPattern_PatternWith2AnimalCardsAnd1FungiCard() {
        // Test pattern with 2 animal cards and 1 fungi card
        Card card6 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        playerArea.addCard(card6, new int[]{43, 37});

        Card card18 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        playerArea.addCard(card18, new int[]{41, 37});

        Card card19 = new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, Corners, 0);
        playerArea.addCard(card19, new int[]{40, 38});

        // Found 1 pattern
        assertEquals(1, playerArea.countPattern(Kingdom.ANIMALKINGDOM, Pattern.UPPERRIGHT));
    }

    /**
     * Method for testing countPattern() when there is the pattern with 2 insect cards and 1 animal card
     *
     * @author Di Carlo Andrea, Gallo Fabio
     */
    @Test
    public void TestCountPattern_PatternWith2InsectCardsAnd1AnimalCard() {
        // Test pattern with 2 insect cards and 1 animal card
        Card card20 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        playerArea.addCard(card20, new int[]{45, 37});

        Card card21 = new GamingCard(false, Kingdom.INSECTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card21, new int[]{46, 38});

        Card card22 = new GamingCard(false, Kingdom.INSECTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card22, new int[]{48, 38});

        // Found 1 pattern
        assertEquals(1, playerArea.countPattern(Kingdom.INSECTKINGDOM, Pattern.UPPERLEFT));
    }
}