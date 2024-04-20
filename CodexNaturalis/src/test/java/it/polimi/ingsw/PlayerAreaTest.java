package java.it.polimi.ingsw;

import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.model.cards.*;


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

        Card startercard = new StarterCard(false, startcorners, Corners, new Kingdom[]{Kingdom.NONE});
        playerArea.addCard(startercard, new int[]{40, 40});

        // No card, should return 0
        assertEquals(0, playerArea.countKingdoms(Kingdom.ANIMALKINGDOM));
        assertEquals(0, playerArea.countKingdoms(Kingdom.FUNGIKINGDOM));
        assertEquals(0, playerArea.countKingdoms(Kingdom.PLANTKINGDOM));
        assertEquals(0, playerArea.countKingdoms(Kingdom.INSECTKINGDOM));

        //Devi vedere l'ordine dei kingdom della starter per capire in che posizione mettere le
        //altre cards, poi dovrai anche mettere qualche corner con qualche kingdom in modo da poter
        //piazzare piu carte e devi iniziallizare la lista di carte giocate


        // Add card with kingdom ANIMALKINGDOM
        Card card1 = new GamingCard(true, Kingdom.ANIMALKINGDOM, 0, Corners);
        playerArea.addCard(card1, new int[]{5, 5});

        // Should return 1 since there is a card with ANIMALKINGDOM
        assertEquals(1, playerArea.countKingdoms(Kingdom.ANIMALKINGDOM));

        // Add card with kingdom PLANTKINGDOM
        Card card2 = new GamingCard(true, Kingdom.PLANTKINGDOM, 0, Corners);
        playerArea.addCard(card2, new int[]{6, 4});

        // Should return 1 since there is a card with PLANTKINGDOM
        assertEquals(1, playerArea.countKingdoms(Kingdom.PLANTKINGDOM));

        // Add card with kingdom FUNGIKINGDOM
        Card card3 = new GamingCard(true, Kingdom.FUNGIKINGDOM, 0, Corners);
        playerArea.addCard(card3, new int[]{3, 5});

        // Should return 1 since there is a card with FUNGIKINGDOM
        assertEquals(1, playerArea.countKingdoms(Kingdom.FUNGIKINGDOM));

        // Add card with kingdom INSECTKINGDOM
        Card card4 = new GamingCard(true, Kingdom.INSECTKINGDOM, 0, Corners);
        playerArea.addCard(card4, new int[]{5, 5});

        // Should return 1 since there is a card with INSECTKINGDOM
        assertEquals(1, playerArea.countKingdoms(Kingdom.INSECTKINGDOM));


        // Add another card with kingdom ANIMALKINGDOM
        Card card5 = new GamingCard(true, Kingdom.ANIMALKINGDOM, 0, Corners);
        playerArea.addCard(card5, new int[]{5, 5});

        // Should return 2 since there is a card with ANIMALKINGDOM
        assertEquals(2, playerArea.countKingdoms(Kingdom.ANIMALKINGDOM));

        // Add goldcard with kingdom INSECTKINGDOM
        Kingdom[] resources = new Kingdom[]{Kingdom.NONE};
        Card cardgold = new GoldCard(false, Kingdom.INSECTKINGDOM, 0, Corners, resources, ConditionPoint.NONE )
        playerArea.addCard(cardgold, new int[]{5, 5});

        // Should return 2 since there is a card with INSECTKINGDOM
        assertEquals(2, playerArea.countKingdoms(Kingdom.INSECTKINGDOM))
    }
}

