package it.polimi.ingsw;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
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
    private Player player;
    private ObjectiveCard[] commonObjectives, commonObjectives2;


    @Before
    public void setUp() {


        //2 sets of common objectives, to test different cases, only one secret objective

        ObjectiveCard secretObjective = new ObjectiveCard(2, true, new GameObject[]{GameObject.NONE}, Pattern.UPPERRIGHT, Kingdom.ANIMALKINGDOM, 0);

        ObjectiveCard commonObjective3 = new ObjectiveCard(2, false, new GameObject[]{GameObject.NONE}, Pattern.NONE, Kingdom.ANIMALKINGDOM, 0);
        ObjectiveCard commonObjective4 = new ObjectiveCard(3, false, new GameObject[]{GameObject.QUILL, GameObject.INKWELL, GameObject.MANUSCRIPT}, Pattern.NONE, Kingdom.NONE, 0);
        commonObjectives = new ObjectiveCard[]{commonObjective3, commonObjective4};

        ObjectiveCard commonObjective1 = new ObjectiveCard(2, false, new GameObject[]{GameObject.NONE}, Pattern.NONE, Kingdom.FUNGIKINGDOM, 0);
        ObjectiveCard commonObjective2 = new ObjectiveCard(3, false, new GameObject[]{GameObject.NONE}, Pattern.LOWERLEFT, Kingdom.PLANTKINGDOM, 0);
        commonObjectives2 = new ObjectiveCard[]{commonObjective1, commonObjective2};

        //starter card

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

        StarterCard starterCard = new StarterCard(false, frontCorners, backCorners, kingdoms, 0);

        //cards in the hand of the player

        ArrayList<GamingCard> hand = new ArrayList<>();

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE)
        };

        Kingdom[] resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.ANIMALKINGDOM};

        GoldCard goldCard1 = new GoldCard(true, Kingdom.FUNGIKINGDOM, 1, frontCorners, resources, ConditionPoint.QUILL, 0);

        hand.add(goldCard1);


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.PLANTKINGDOM};

        GoldCard goldCard2 = new GoldCard(true, Kingdom.FUNGIKINGDOM, 2, frontCorners, resources, ConditionPoint.INKWELL, 0);

        hand.add(goldCard2);


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        GamingCard resourceCard = new GamingCard(true, Kingdom.INSECTKINGDOM, 1, frontCorners, 0);

        hand.add(resourceCard);


        //cards already played on the player area

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        GamingCard playedResourceCard1 = new GamingCard(true, Kingdom.FUNGIKINGDOM, 0, frontCorners, 0);


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        GamingCard playedResourceCard2 = new GamingCard(true, Kingdom.PLANTKINGDOM, 0, frontCorners, 0);

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        GamingCard playedResourceCard3 = new GamingCard(true, Kingdom.FUNGIKINGDOM, 0, frontCorners, 0);


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.PLANTKINGDOM};

        GoldCard playedGoldCard1 = new GoldCard(true, Kingdom.FUNGIKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER, 0);


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };

        GamingCard playedResourceCard4 = new GamingCard(true, Kingdom.PLANTKINGDOM, 0, frontCorners, 0);

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        GamingCard playedResourceCard5 = new GamingCard(true, Kingdom.INSECTKINGDOM, 0, frontCorners, 0);


        //creation of the player area

        boolean[][] area = new boolean[81][81];
        for (int i = 0; i < 81; i++) {
            for (int j = 0; j < 81; j++) {
                area[i][j] = true;
            }
        }

        ArrayList<Card> cards = new ArrayList<>();

        PlayerArea playerArea = new PlayerArea(area, cards);

        playerArea.addCard(starterCard, new int[]{40, 40});
        playerArea.addCard(playedResourceCard1, new int[]{39, 39});
        playerArea.addCard(playedResourceCard2, new int[]{39, 41});
        playerArea.addCard(playedResourceCard3, new int[]{38, 40});
        playerArea.addCard(playedGoldCard1, new int[]{37, 41});
        playerArea.addCard(playedResourceCard4, new int[]{41, 41});
        playerArea.addCard(playedResourceCard5, new int[]{42, 40});

        //player

        player = new Player("Fabio", 0, playerArea, Color.GREEN, secretObjective, starterCard, hand);


    }


    @After
    public void tearDown() {
    }

    /**
     * Test for setHand method with exactly three cards.
     * Expected behavior: hand set successfully without exceptions.
     */
    @Test
    public void setHandTest_handWithThreeCards_handSetSuccessfully() {
        ArrayList<GamingCard> hand = new ArrayList<>();
        Corner[] frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };

        hand.add(new GamingCard(true, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));
        hand.add(new GamingCard(true, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));
        hand.add(new GamingCard(true, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));
        try {
            player.setHand(hand);
        } catch (InvalidNumCardsPlayerHandException e) {
            fail("Exception should not be thrown for hand with exactly three cards");
        }
        assertEquals(hand, player.getHand());
    }

    /**
     * Test for setHand method with less than three cards.
     * Expected behavior: InvalidNumCardsPlayerHandException thrown.
     */
    @Test(expected = InvalidNumCardsPlayerHandException.class)
    public void setHandTest_handWithLessThanThreeCards_exceptionThrown() throws InvalidNumCardsPlayerHandException {
        ArrayList<GamingCard> hand = new ArrayList<>();
        Corner[] frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };
        hand.add(new GamingCard(true, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));

        player.setHand(hand);
    }

    /**
     * Test for setHand method with more than three cards.
     * Expected behavior: InvalidNumCardsPlayerHandException thrown.
     */
    @Test(expected = InvalidNumCardsPlayerHandException.class)
    public void setHandTest_handWithMoreThanThreeCards_exceptionThrown() throws InvalidNumCardsPlayerHandException {
        ArrayList<GamingCard> hand = new ArrayList<>();
        Corner[] frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };

        hand.add(new GamingCard(true, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));
        hand.add(new GamingCard(true, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));
        hand.add(new GamingCard(true, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));
        hand.add(new GamingCard(true, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));

        player.setHand(hand);
    }

    /**
     * Test for addCardHand method adding a card to hand with two cards.
     * Expected behavior: card added successfully.
     */
    @Test
    public void addCardHandTest_addCardToHandWithTwoCards_cardAddedSuccessfully() {
        Corner[] frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };

        try {
            // Set player's hand side to 2 by removing a card
            player.getHand().removeFirst();

            // Set player's hand side to 3 by adding a card
            player.addCardHand(new GamingCard(true, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));
            assertEquals(3, player.getHand().size());
        } catch (HandAlreadyFullException e) {
            fail("Exception should not be thrown for hand with less than three cards");
        }
    }

    /**
     * Test for addCardHand method adding a card to a full hand.
     * Expected behavior: HandAlreadyFullException thrown.
     */
    @Test(expected = HandAlreadyFullException.class)
    public void addCardHandTest_addCardToFullHand_exceptionThrown() throws HandAlreadyFullException, InvalidNumCardsPlayerHandException {
        ArrayList<GamingCard> hand = new ArrayList<>();
        Corner[] frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };

        hand.add(new GamingCard(true, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));
        hand.add(new GamingCard(true, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));
        hand.add(new GamingCard(true, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));

        player.setHand(hand);
        player.addCardHand(new GamingCard(true, Kingdom.ANIMALKINGDOM, 0, frontCorners, 0));
    }


    @Test
    public void playCardTest_occupiedPositionToPlayGiven_ShouldThrowInvalidPlayException_ShouldShowErrorMessage() {
        try {
            player.playCard(3, new int[]{42, 40}, true);
            fail("All went good.");
        } catch (InvalidPlayException e) {
            assertEquals("You can't play this card in this position.\nMistake: There is already a card in that position.", e.getMessage());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void playCardTest_positionWithNoCornersWhereToPlayGiven_ShouldThrowInvalidPlayException_ShouldShowErrorMessage() {
        try {
            player.playCard(3, new int[]{60, 60}, true);
            fail("All went good.");
        } catch (InvalidPlayException e) {
            assertEquals("You can't play this card in this position.\nMistake: There are no cards in the corners of that position.", e.getMessage());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void playCardTest_positionCoveringTwoCornersOfTheSameCardGiven_ShouldThrowInvalidPlayException_ShouldShowErrorMessage() {
        try {
            player.playCard(3, new int[]{36, 41}, true);
            fail("All went good.");
        } catch (InvalidPlayException e) {
            assertEquals("You can't play this card in this position.\nMistake: The card you want to play can't cover two corners of the same card.", e.getMessage());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void playCardTest_goldCardWithNotSatisfiedConditionGiven_ShouldThrowInvalidPlayException_ShouldShowErrorMessage() {
        try {
            player.playCard(1, new int[]{36, 42}, true);
            fail("All went good.");
        } catch (InvalidPlayException e) {
            assertEquals("You can't play this card in this position.\nMistake: There aren't enough resources to place the gold card on front.", e.getMessage());
            //assertEquals("You can't play this card in this position.\nMistake: Card covers a corner which can't be covered", e.getMessage());
            System.out.println(e.getMessage());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void playCardTest_positionCoveringACornerThatCannotBeCoveredGiven_ShouldThrowInvalidPlayException_ShouldShowErrorMessage() {
        try {
            player.playCard(3, new int[]{40, 38}, true);
            fail("All went good.");
        } catch (InvalidPlayException e) {
            assertEquals("You can't play this card in this position.\nMistake: Card covers a corner which can't be covered.", e.getMessage());
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
            player.playCard(2, new int[]{42, 42}, true);
        } catch (Exception e) {
            fail(e.getMessage());
        }


    }

    @Test
    public void calculateObjectivePointsTest_noPoints() {
        assertEquals(0, player.calculateObjectivePoints(commonObjectives));
    }

    @Test
    public void calculateObjectivePointsTest_points() {
        assertEquals(5, player.calculateObjectivePoints(commonObjectives2));
    }
}
