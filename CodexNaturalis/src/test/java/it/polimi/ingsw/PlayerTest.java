package it.polimi.ingsw;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.game.Color;
import it.polimi.ingsw.model.game.ObjectiveDeck;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.game.PlayerArea;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


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

        playerArea = new PlayerArea(new boolean[81][81], new ArrayList<>());

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

        StarterCard starterCard = new StarterCard(false, frontCorners, backCorners, kingdoms);

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

        playerArea

        player = new Player("Fabio", 0, playerArea, Color.GREEN, secretObjective, starterCard, hand);
    }
    @After public void tearDown(){}


}
