package it.polimi.ingsw;

import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.cards.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;



public class PlayerAreaTest {

    @Test
    public void testCountKingdoms() {
        //Create matrix area
        boolean[][] Area = new boolean[81][81];
        for (int row = 0; row < Area.length; row++) {
            for (int col = 0; col < Area[row].length; col++) {
                Area[row][col] = true;
            }
        }

        //devi iniziallizare la lista di carte giocate
        PlayerArea playerArea = new PlayerArea(Area, new ArrayList<Card>());

        // No card, should return 0
        assertEquals(0, playerArea.countKingdoms(Kingdom.ANIMALKINGDOM));
        assertEquals(0, playerArea.countKingdoms(Kingdom.FUNGIKINGDOM));
        assertEquals(0, playerArea.countKingdoms(Kingdom.PLANTKINGDOM));
        assertEquals(0, playerArea.countKingdoms(Kingdom.INSECTKINGDOM));

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

        Card startercard = new StarterCard(false, startcorners, Corners, new Kingdom[]{Kingdom.NONE});
        playerArea.addCard(startercard, new int[]{40, 40});

        //Should return 1
        assertEquals(1, playerArea.countKingdoms(Kingdom.ANIMALKINGDOM));
        assertEquals(1, playerArea.countKingdoms(Kingdom.FUNGIKINGDOM));
        assertEquals(1, playerArea.countKingdoms(Kingdom.PLANTKINGDOM));
        assertEquals(1, playerArea.countKingdoms(Kingdom.INSECTKINGDOM));



        // Add card with kingdom ANIMALKINGDOM
        Card card1 = new GamingCard(true, Kingdom.ANIMALKINGDOM, 0, Corners);
        playerArea.addCard(card1, new int[]{39, 39});

        // Should return 2
        assertEquals(2, playerArea.countKingdoms(Kingdom.ANIMALKINGDOM));

        // Should return 0 because PLANTKINGDOM is covered
        assertEquals(0, playerArea.countKingdoms(Kingdom.PLANTKINGDOM));


        // Should return 1 since there is a card with INSECTKINGDOM
        assertEquals(1, playerArea.countKingdoms(Kingdom.INSECTKINGDOM));


        // Add goldcard with kingdom INSECTKINGDOM
        Kingdom[] resources = new Kingdom[]{Kingdom.NONE};
        Card cardgold = new GoldCard(false, Kingdom.INSECTKINGDOM, 0, Corners, resources, ConditionPoint.NONE );
        playerArea.addCard(cardgold, new int[]{41, 41});

        // Should return 1 because INSECTKINGDOM is covered
        assertEquals(1, playerArea.countKingdoms(Kingdom.INSECTKINGDOM));
    }
}

