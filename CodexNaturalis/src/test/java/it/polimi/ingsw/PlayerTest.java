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

import java.io.IOException;
import java.util.ArrayList;


//TO DO: test di playCard, calculateObjectivePoint
public class PlayerTest {
    private Player player;
    private PlayerArea playerArea;

    @Before public void setUp() {
        boolean[][] area = new boolean[81][81];
        for (int i = 0; i < 81; i++) {
            for (int j = 0; j < 81; j++) {
                area[i][j] = true;
            }
        }

        ObjectiveCard secretObjective = new ObjectiveCard(2, true, new GameObject[]{GameObject.NONE},Pattern.SECONDARYDIAGONAL,Kingdom.FUNGIKINGDOM);

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

        StarterCard starterCard = new StarterCard(true, frontCorners, backCorners, kingdoms);

        ArrayList<GamingCard> hand = new ArrayList<>();

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE)
        };

        Kingdom[] resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.ANIMALKINGDOM};

        GoldCard goldCard1 = new GoldCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners, resources, ConditionPoint.QUILL);


        hand.add(goldCard1);

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.PLANTKINGDOM};

        GoldCard goldCard2 = new GoldCard(false, Kingdom.FUNGIKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER);

        hand.add(goldCard2);

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        GamingCard resourceCard = new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners);

        hand.add(resourceCard);

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        GamingCard playedResourceCard1 = new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners);


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        GamingCard playedResourceCard2 = new GamingCard(true, Kingdom.PLANTKINGDOM, 0, frontCorners);



        area[41][41]=false;
        starterCard.setInGamePosition(new int[]{41, 41});


        area[40][40]=false;
        playedResourceCard1.setInGamePosition(new int[]{40, 40});

        area[40][42]=false;
        playedResourceCard2.setInGamePosition(new int[]{40, 42});

        ArrayList<Card> cards = new ArrayList<>();

        cards.add(starterCard);
        cards.add(playedResourceCard1);
        cards.add(playedResourceCard2);

        playerArea = new PlayerArea( area, cards );

        player = new Player("Fabio", 0, playerArea, Color.GREEN, secretObjective, starterCard, hand);
    }





    @After public void tearDown(){

    }


    @Test public void playCardTest(){

        try {
            player.playCard(3, new int[]{42, 42}, true);
        } catch (InvalidPlayException e) {
            //assertEquals("You can't play this card in this position. Mistake: There is already a card in that position.", e.getMessage());
            //assertEquals("You can't play this card in this position. Mistake: There are no cards in the corners of that position.", e.getMessage());
            //assertEquals("You can't play this card in this position. Mistake: The card you want to play can't cover two corners of the same card.", e.getMessage()); //DA SISTEMARE IN PLAYER
            //assertEquals("You can't play this card in this position. Mistake: There aren't enough resources to place the gold card.", e.getMessage());
            assertEquals("You can't play this card in this position. Mistake: Card covers a corner which can't be covered", e.getMessage());
            System.out.println(e.getMessage());
        } catch (InvalidPlayCardIndexException e) {
            assertEquals("Invalid selection of the card from hand.", e.getMessage());
            System.out.println(e.getMessage());
        } catch (InvalidPositionAreaException e) {
            assertEquals("Not valid index's position.", e.getMessage());
            System.out.println(e.getMessage());
        }

       // assertEquals("You can't play this card in this position. Mistake: There are no cards in the corners of that position.", e);
    }


}
