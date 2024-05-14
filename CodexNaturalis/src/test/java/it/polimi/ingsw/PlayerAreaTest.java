package it.polimi.ingsw;

import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.cards.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;



public class PlayerAreaTest {

    private PlayerArea playerArea;
    private Corner[] Corners;

    @Before
    public void setUp() {
        //Create matrix area
        boolean[][] Area = new boolean[81][81];
        for (int row = 0; row < Area.length; row++) {
            for (int col = 0; col < Area[row].length; col++) {
                Area[row][col] = true;
            }
        }

        playerArea = new PlayerArea(Area, new ArrayList<Card>());


        // Create startercard
        Corner[] startcorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };

        Corner[] backcorners = new Corner[]{
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

        Card startercard = new StarterCard(false, backcorners, startcorners, new Kingdom[]{Kingdom.NONE}, 0);
        playerArea.addCard(startercard, new int[]{40, 40});

    }

    @After
    public void tearDown() {
    }

    @Test
    public void TestCountKingdomStarterCard() {
        //Should return 1
        assertEquals(1, playerArea.countKingdoms(Kingdom.ANIMALKINGDOM));
        assertEquals(1, playerArea.countKingdoms(Kingdom.FUNGIKINGDOM));
        assertEquals(1, playerArea.countKingdoms(Kingdom.PLANTKINGDOM));
        assertEquals(1, playerArea.countKingdoms(Kingdom.INSECTKINGDOM));
    }
    @Test
    public void TestTwoKingdom() {
        // Add card with kingdom ANIMALKINGDOM
        Card card1 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        playerArea.addCard(card1, new int[]{39, 39});

        // Should return 2
        assertEquals(2, playerArea.countKingdoms(Kingdom.ANIMALKINGDOM));
    }

    @Test
    public void TestKingdomCovered() {
        // Should return 0 because PLANTKINGDOM is covered
        Card card1 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        playerArea.addCard(card1, new int[]{39, 39});
        assertEquals(0, playerArea.countKingdoms(Kingdom.PLANTKINGDOM));

    }

    @Test
    public void TestGoldCardKingdom(){
        // Add goldcard with kingdom INSECTKINGDOM
        Kingdom[] resources = new Kingdom[]{Kingdom.NONE};
        Card cardgold = new GoldCard(false, Kingdom.INSECTKINGDOM, 0, Corners, resources, ConditionPoint.NONE, 0);
        playerArea.addCard(cardgold, new int[]{41, 41});

        // Should return 1 because INSECTKINGDOM is covered
        assertEquals(1, playerArea.countKingdoms(Kingdom.INSECTKINGDOM));
    }

    @After
    public void setUptestCountObject() {
        //Create matrix area
        boolean[][] Area = new boolean[81][81];
        for (int row = 0; row < Area.length; row++) {
            for (int col = 0; col < Area[row].length; col++) {
                Area[row][col] = true;
            }
        }

        playerArea = new PlayerArea(Area, new ArrayList<Card>());

        // Create startercard
        Corner[] startcorners = new Corner[]{
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

        Card startercard = new StarterCard(false, Corners, startcorners, new Kingdom[]{Kingdom.NONE}, 0);
        playerArea.addCard(startercard, new int[]{40, 40});
    }
    @Test
    public void TestCounterObjectsStarterCard() {
        assertEquals(0, playerArea.countObject(GameObject.MANUSCRIPT));
        assertEquals(0, playerArea.countObject(GameObject.INKWELL));
        assertEquals(0, playerArea.countObject(GameObject.QUILL));
        assertEquals(4, playerArea.countObject(GameObject.NONE));
    }
    @Test
    public void testAllCountObjects(){
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

        assertEquals(0, playerArea.countObject(GameObject.MANUSCRIPT));

    }

    @After
    public void setUptestCountHiddenCorner() {
        //Create matrix area
        boolean[][] Area = new boolean[81][81];
        for (int row = 0; row < Area.length; row++) {
            for (int col = 0; col < Area[row].length; col++) {
                Area[row][col] = true;
            }
        }

        PlayerArea playerArea = new PlayerArea(Area, new ArrayList<Card>());

        // Create startercard
        Corner[] startcorners = new Corner[]{
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

        Card startercard = new StarterCard(false, Corners, startcorners, new Kingdom[]{Kingdom.NONE}, 0);
        playerArea.addCard(startercard, new int[]{40, 40});}
    @Test
    public void Test1CountHiddenCornerGoldCard() {

        // Add goldcard with kingdom INSECTKINGDOM
        Kingdom[] resources = new Kingdom[]{Kingdom.NONE};
        Card cardgold1 = new GoldCard(false, Kingdom.INSECTKINGDOM, 0, Corners, resources, ConditionPoint.HIDDENCORNER, 0);
        playerArea.addCard(cardgold1, new int[]{41, 41});

        assertEquals(1, playerArea.countHiddenCorner(new int[]{41, 41}));
    }
    @Test
    public void Test2CountHiddenCornerGoldCard() {
        Kingdom[] resources = new Kingdom[]{Kingdom.NONE};
        Card card1 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        Card cardgold2 = new GoldCard(false, Kingdom.INSECTKINGDOM, 0, Corners, resources, ConditionPoint.HIDDENCORNER, 0);
        playerArea.addCard(card1, new int[]{42, 40});
        playerArea.addCard(cardgold2, new int[]{41, 39});

        assertEquals(2, playerArea.countHiddenCorner(new int[]{41, 39}));
    }

    @After
    public void setUptestCountPattern() {
        //Create matrix area
        boolean[][] Area = new boolean[81][81];
        for (int row = 0; row < Area.length; row++) {
            for (int col = 0; col < Area[row].length; col++) {
                Area[row][col] = true;
            }
        }

        PlayerArea playerArea = new PlayerArea(Area, new ArrayList<Card>());

        // Create startercard
        Corner[] startcorners = new Corner[]{
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

        Card startercard = new StarterCard(false, Corners, startcorners, new Kingdom[]{Kingdom.NONE}, 0);
        playerArea.addCard(startercard, new int[]{40, 40});
    }

    @Test
    public void TestDiag3PlantKingdom() {
        //Test diagon 3 Plantkingdom
        Card card1 = new GamingCard(false, Kingdom.PLANTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card1, new int[]{39, 39});

        Card card2 = new GamingCard(false, Kingdom.PLANTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card2, new int[]{38, 38});

        Card card3 = new GamingCard(false, Kingdom.PLANTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card3, new int[]{37, 37});

        assertEquals(1, playerArea.countPattern(Kingdom.PLANTKINGDOM, Pattern.PRIMARYDIAGONAL));
    }

    @Test
    public void TestDiag3AnimalKingdom() {
        //Test diagon 3 Animalkingdom
        Card card4 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        playerArea.addCard(card4, new int[]{41, 39});

        Card card5 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        playerArea.addCard(card5, new int[]{42, 38});

        Card card6 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        playerArea.addCard(card6, new int[]{43, 37});

        assertEquals(1, playerArea.countPattern(Kingdom.ANIMALKINGDOM, Pattern.SECONDARYDIAGONAL));
    }

    @Test
    public void TestDiag3InsectKingdom() {
        //Test diagon 3 Insectkingdom
        Card card7 = new GamingCard(false, Kingdom.INSECTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card7, new int[]{41, 41});

        Card card8 = new GamingCard(false, Kingdom.INSECTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card8, new int[]{42, 42});

        Card card9 = new GamingCard(false, Kingdom.INSECTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card9, new int[]{43, 43});

        assertEquals(1, playerArea.countPattern(Kingdom.INSECTKINGDOM, Pattern.PRIMARYDIAGONAL));
    }

    @Test
    public void TestDiag3FungiKingdom() {
        //Test diagon 3 FungiKingdom
        Card card10 = new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, Corners, 0);
        playerArea.addCard(card10, new int[]{44, 36});

        Card card11 = new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, Corners, 0);
        playerArea.addCard(card11, new int[]{45, 35});

        Card card12 = new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, Corners, 0);
        playerArea.addCard(card12, new int[]{46, 34});

        assertEquals(1, playerArea.countPattern(Kingdom.FUNGIKINGDOM, Pattern.SECONDARYDIAGONAL));
    }


    @Test
    public void Test2Fungi1PlantKingdom() {
        //Test 2Fungi and 1Plant
        Card card13 = new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, Corners, 0);
        playerArea.addCard(card13, new int[]{44, 44});

        Card card14 = new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, Corners, 0);
        playerArea.addCard(card14, new int[]{46, 44});

        Card card15 = new GamingCard(false, Kingdom.PLANTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card15, new int[]{47, 45});

        assertEquals(1, playerArea.countPattern(Kingdom.FUNGIKINGDOM, Pattern.LOWERRIGHT));
    }

    @Test
    public void Test2Plant1InsectKingdom() {
        //Test 2Plant 1 Insect
        Card card3 = new GamingCard(false, Kingdom.PLANTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card3, new int[]{37, 37});

        Card card16 = new GamingCard(false, Kingdom.PLANTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card16, new int[]{39, 37});

        Card card17 = new GamingCard(false, Kingdom.INSECTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card17, new int[]{40, 36});

        assertEquals(1, playerArea.countPattern(Kingdom.PLANTKINGDOM, Pattern.LOWERLEFT));
    }

    @Test
    public void Test2Animal1FungiKingdom() {
        //Test 2Animal 1 Fungi
        Card card6 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        playerArea.addCard(card6, new int[]{43, 37});

        Card card18 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        playerArea.addCard(card18, new int[]{41, 37});

        Card card19 = new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, Corners, 0);
        playerArea.addCard(card19, new int[]{40, 38});

        assertEquals(1, playerArea.countPattern(Kingdom.ANIMALKINGDOM, Pattern.UPPERRIGHT));
    }

    @Test
    public void Test2Insect1AnimalKingdom() {
        //Test 2Insect 1 Animal
        Card card20 = new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, Corners, 0);
        playerArea.addCard(card20, new int[]{45, 37});

        Card card21 = new GamingCard(false, Kingdom.INSECTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card21, new int[]{46, 38});

        Card card22 = new GamingCard(false, Kingdom.INSECTKINGDOM, 0, Corners, 0);
        playerArea.addCard(card22, new int[]{48, 38});

        assertEquals(1, playerArea.countPattern(Kingdom.INSECTKINGDOM, Pattern.UPPERLEFT));

    }
}

