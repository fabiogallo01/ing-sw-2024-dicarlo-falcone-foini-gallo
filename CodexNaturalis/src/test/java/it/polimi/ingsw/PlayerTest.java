package it.polimi.ingsw;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.InvalidPlayCardIndexException;
import it.polimi.ingsw.model.exception.InvalidPlayException;
import it.polimi.ingsw.model.exception.InvalidPositionAreaException;
import it.polimi.ingsw.model.game.Color;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.game.PlayerArea;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;


//TO DO: test di playCard, calculateObjectivePoint
public class PlayerTest {
    private Player player, player2;
    private ObjectiveCard[] commonObjectives;


    @Before
    public void setUp() {
        boolean[][] area = new boolean[81][81];
        for (int i = 0; i < 81; i++) {
            for (int j = 0; j < 81; j++) {
                area[i][j] = true;
            }
        }


        ObjectiveCard secretObjective = new ObjectiveCard(2, true, new GameObject[]{GameObject.NONE}, Pattern.SECONDARYDIAGONAL, Kingdom.FUNGIKINGDOM);
        ObjectiveCard commonObjective1 = new ObjectiveCard(2,false, new GameObject[]{GameObject.NONE}, Pattern.NONE, Kingdom.FUNGIKINGDOM);
        ObjectiveCard commonObjective2 = new ObjectiveCard(3, false, new GameObject[]{GameObject.NONE}, Pattern.LOWERLEFT, Kingdom.PLANTKINGDOM);
        commonObjectives = new ObjectiveCard[]{commonObjective1, commonObjective2};


        Corner[] frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        Corner[] backCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };

        Kingdom[] kingdoms = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.FUNGIKINGDOM};

        StarterCard starterCard = new StarterCard(false, frontCorners, backCorners, kingdoms);
        //StarterCard starterCard2 = new StarterCard(false, frontCorners, backCorners, kingdoms);
        ArrayList<GamingCard> hand = new ArrayList<>();

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE)
        };

        Kingdom[] resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.ANIMALKINGDOM};

        GoldCard goldCard1 = new GoldCard(true, Kingdom.FUNGIKINGDOM, 1, frontCorners, resources, ConditionPoint.QUILL);


        hand.add(goldCard1);

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.PLANTKINGDOM};

        GoldCard goldCard2 = new GoldCard(true, Kingdom.FUNGIKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER);
        //GoldCard playedGoldCard = new GoldCard(true, Kingdom.FUNGIKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER);

        hand.add(goldCard2);

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        GamingCard resourceCard = new GamingCard(true, Kingdom.FUNGIKINGDOM, 0, frontCorners);
        //GamingCard playedResourceCard3 = new GamingCard(true, Kingdom.FUNGIKINGDOM, 0, frontCorners);

        hand.add(resourceCard);

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        GamingCard playedResourceCard1 = new GamingCard(true, Kingdom.FUNGIKINGDOM, 0, frontCorners);


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        GamingCard playedResourceCard2 = new GamingCard(true, Kingdom.PLANTKINGDOM, 0, frontCorners);


        ArrayList<Card> cards = new ArrayList<>();
        PlayerArea playerArea = new PlayerArea(area, cards);

        playerArea.addCard( starterCard, new int[]{40,40});
        playerArea.addCard( playedResourceCard1, new int[]{39,39});
        playerArea.addCard( playedResourceCard2, new int[]{39,41});

        player = new Player("Fabio", 0, playerArea, Color.GREEN, secretObjective, starterCard, hand);


        //setup for the counter of the points
        /*
        boolean[][] area2 = new boolean[81][81];
        for (int i = 0; i < 81; i++) {
            for (int j = 0; j < 81; j++) {
                area[i][j] = true;
            }
        }

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };

        GamingCard playedResourceCard4 = new GamingCard(true, Kingdom.PLANTKINGDOM, 0, frontCorners);

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        GamingCard playedResourceCard5 = new GamingCard(true, Kingdom.INSECTKINGDOM, 0, frontCorners);

        ArrayList<Card> cards2 = new ArrayList<>();
        PlayerArea playerArea2 = new PlayerArea(area2, cards2);

        playerArea2.addCard( starterCard, new int[]{40,40});
        playerArea2.addCard( playedResourceCard1, new int[]{39,39});
        playerArea2.addCard( playedResourceCard2, new int[]{39,41});
        //playerArea2.addCard( playedResourceCard3, new int[]{38, 40});
        //playerArea2.addCard( playedGoldCard, new int[]{37, 41});
        playerArea2.addCard( playedResourceCard4, new int[]{41,41});
        playerArea2.addCard( playedResourceCard5, new int[]{42,40});
        //player2 = new Player("Lorenzo", 0, playerArea2, Color.BLUE, secretObjective, starterCard2, hand);

        */
    }


    @After
    public void tearDown() {
    }


    @Test
    public void playCardTest_occupiedPositionToPlayGiven_ShouldThrowInvalidPlayException_ShouldShowErrorMessage() {
        try {
            player.playCard(3, new int[]{40, 40}, true);
            fail("All went good.");
        } catch (InvalidPlayException e) {
            assertEquals("You can't play this card in this position. Mistake: There is already a card in that position.", e.getMessage());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void playCardTest_positionWithNoCornersWhereToPlayGiven_ShouldThrowInvalidPlayException_ShouldShowErrorMessage() {
        try {
            player.playCard(3, new int[]{50, 50}, true);
            fail("All went good.");
        } catch (InvalidPlayException e) {
            assertEquals("You can't play this card in this position. Mistake: There are no cards in the corners of that position.", e.getMessage());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void playCardTest_positionCoveringTwoCornersOfTheSameCardGiven_ShouldThrowInvalidPlayException_ShouldShowErrorMessage() {
        try {
            player.playCard(3, new int[]{38, 41}, true);
            fail("All went good.");
        } catch (InvalidPlayException e) {
            assertEquals("You can't play this card in this position. Mistake: The card you want to play can't cover two corners of the same card.", e.getMessage()); //DA SISTEMARE IN PLAYER
            System.out.println(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void playCardTest_goldCardWithNotSatisfiedConditionGiven_ShouldThrowInvalidPlayException_ShouldShowErrorMessage() {
        try {
            player.playCard(2, new int[]{38, 42}, true);
            fail("All went good.");
        } catch (InvalidPlayException e) {
            assertEquals("You can't play this card in this position. Mistake: There aren't enough resources to place the gold card.", e.getMessage());
            //assertEquals("You can't play this card in this position. Mistake: Card covers a corner which can't be covered", e.getMessage());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void playCardTest_positionCoveringACornerThatCannotBeCoveredGiven_ShouldThrowInvalidPlayException_ShouldShowErrorMessage() {
        try {
            player.playCard(3, new int[]{40, 42}, true);
            fail("All went good.");
        } catch (InvalidPlayException e) {
            assertEquals("You can't play this card in this position. Mistake: Card covers a corner which can't be covered", e.getMessage());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void playCardTest_wrongPositionInHandsGiven_ShouldThrowInvalidPlayCardIndexException_ShouldShowErrorMessage() {

        try {
            player.playCard(4, new int[]{38, 42}, true);
            fail("All went good.");
        } catch (InvalidPlayCardIndexException e) {
            assertEquals("Invalid selection of the card from hand.", e.getMessage());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void playCardTest_outOfPlayerAreaPositionGiven_ShouldThrowInvalidPositionAreaException_ShouldShowErrorMessage() {

        try {
            player.playCard(3, new int[]{96, 39}, true);
            fail("All went good.");
        } catch (InvalidPositionAreaException e) {
            assertEquals("Not valid index's position.", e.getMessage());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void playCardTest_normalWorkingPlayGiven() {
        try {
            player.playCard(3, new int[]{41, 41}, true);
        } catch (Exception e) {
            fail(e.getMessage());
        }


    }


    @Test
    public void calculateObjectivePointsTest_noPoints() {
        assertEquals(0,player.calculateObjectivePoints(commonObjectives));
    }
/*
    @Test
    public void calculateObjectivePointsTest_points(){
        assertEquals(7, player2.calculateObjectivePoints(commonObjectives));
    }*/
}
