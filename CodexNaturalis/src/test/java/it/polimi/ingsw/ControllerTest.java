package it.polimi.ingsw;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.game.*;
import it.polimi.ingsw.controller.Controller;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Class for testing Controller class
 *
 * @author Foini Lorenzo
 */
public class ControllerTest {
    private Controller controller;
    private GameTable gameTable;
    private Player player1;
    private Player player2;
    private Player player3;

    /**
     * Method for set up test
     *
     * @author Foini Lorenzo
     */
    @Before
    public void setUp() {
        // Create new controller whit 3 players
        controller = new Controller(3);
        gameTable = controller.getGameTable();

        // Create the three players by calling createPlayer method
        // The three players have the same parameters (except for the username), because we won't test player's method in this class
        player1 = createPlayer("Player1");
        player2 = createPlayer("Player2");
        player3 = createPlayer("Player3");
        gameTable.addPlayer(player1);
        gameTable.addPlayer(player2);
        gameTable.addPlayer(player3);
    }

    /**
     * Method for create a new player
     *
     * @param username: player's username
     * @return new player
     * @author Foini Lorenzo
     */
    private Player createPlayer(String username) {
        // Create secret objective card
        ObjectiveCard secretObjective = new ObjectiveCard(2, new GameObject[]{GameObject.NONE}, Pattern.UPPERRIGHT, Kingdom.ANIMALKINGDOM, 0);

        // Create starter card
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

        // Create cards in player's hand
        ArrayList<GamingCard> hand = new ArrayList<>();

        // Create resource card 1
        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        GamingCard resourceCard1 = new GamingCard(true, Kingdom.INSECTKINGDOM, 1, frontCorners, 0);
        hand.add(resourceCard1);

        // Create resource card 2
        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        GamingCard resourceCard2 = new GamingCard(true, Kingdom.FUNGIKINGDOM, 0, frontCorners, 0);
        hand.add(resourceCard2);

        // Create gold card 3
        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE)
        };
        Kingdom[] resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.ANIMALKINGDOM};
        GoldCard goldCard3 = new GoldCard(true, Kingdom.FUNGIKINGDOM, 1, frontCorners, resources, ConditionPoint.QUILL, 0);
        hand.add(goldCard3);

        // Create player's area
        boolean[][] area = new boolean[81][81];
        for (int i = 0; i < 81; i++) {
            for (int j = 0; j < 81; j++) {
                area[i][j] = true;
            }
        }
        ArrayList<Card> cards = new ArrayList<>();
        PlayerArea playerArea = new PlayerArea(area, cards);
        playerArea.addCard(starterCard, new int[]{40, 40}); // Add starter card

        // Return new player
        return new Player(username, 0, playerArea, Color.BLUE, secretObjective, starterCard, hand);
    }

    /**
     * Method for testing controller's getDisconnectedPlayers method.
     * It tests if disconnectedPlayers is 0 when creating the controller
     *
     * @author Foini Lorenzo
     */
    @Test
    public void getDisconnectedPlayerTest_ShouldBe0WhenCreatingController () {
        assertEquals(controller.getDisconnectedPlayers(), 0);
    }

    /**
     * Method for testing controller's setDisconnectedPlayers method.
     * It tests if the disconnectedPlayers is increased by 1 when a client disconnect
     *
     * @author Foini Lorenzo
     */
    @Test
    public void setDisconnectedPlayerTest_IncreasedBy1WhenAClientDisconnect() {
        // At first, disconnectedPlayers is 0
        assertEquals(controller.getDisconnectedPlayers(), 0);

        // Now suppose that a client disconnect
        // => Increment the counter
        controller.setDisconnectedPlayers();

        // Now check if the counter goes up by 1
        assertEquals(controller.getDisconnectedPlayers(), 1);
    }

    /**
     * Method for testing controller's isCrashed method.
     * It tests if the crashed state is correctly set to false.
     *
     * @author Foini Lorenzo
     */
    @Test
    public void isCrashedTest_expectedFalse_initialState() {
        assertFalse(controller.isCrashed());
    }

    /**
     * Method for testing controller's setCrashed method.
     * It tests if the crashed state is correctly set to true.
     *
     * @author Foini Lorenzo
     */
    @Test
    public void setCrashedTest_expectedTrue_afterSetCrashed() {
        controller.setCrashed();
        assertTrue(controller.isCrashed());
    }

    /**
     * Method for testing controller's getReady method.
     * Test if the ready counter is set to 0.
     *
     * @author Foini Lorenzo
     */
    @Test
    public void getReadyTest_expectedZero_initialState() {
        assertEquals(0, controller.getReady());
    }

    /**
     * Method for testing controller's setReady method.
     * Test if the ready counter is incremented to 1.
     *
     * @author Foini Lorenzo
     */
    @Test
    public void setReadyTest_expectedOne_afterSetReady() {
        controller.setReady();
        assertEquals(1, controller.getReady());
    }

    /**
     * Method for testing controller's setReady method.
     * Test if the ready counter is incremented to 2.
     *
     * @author Foini Lorenzo
     */
    @Test
    public void setReadyTest_expectedTwo_afterSetReadyTwice() {
        controller.setReady();
        controller.setReady();
        assertEquals(2, controller.getReady());
    }

    /**
     * Method for testing controller's getGameTable method.
     * Test if the game table is correctly initialized.
     *
     * @author Foini Lorenzo
     */
    @Test
    public void getGameTableTest_expectedNonNull_initialization() {
        assertNotNull(controller.getGameTable());
    }

    /**
     * Method for testing controller's getViewTui method.
     * Test if the viewTui is correctly initialized.
     *
     * @author Foini Lorenzo
     */
    @Test
    public void getViewTuiTest_expectedNonNull_initialization() {
        assertNotNull(controller.getViewTui());
    }

    /**
     * Method for testing controller's getAvailableColors method.
     * Test if available colors are initialized correctly.
     *
     * @author Foini Lorenzo
     */
    @Test
    public void getAvailableColorsTest_expectedColors_initialization() {
        ArrayList<String> expectedColors = new ArrayList<>();
        expectedColors.add("blue");
        expectedColors.add("green");
        expectedColors.add("red");
        expectedColors.add("yellow");

        assertEquals(expectedColors, controller.getAvailableColors());
    }

    /**
     * Method for testing controller's removeAvailableColor method.
     * Test if a color is removed correctly from available colors.
     *
     * @author Foini Lorenzo
     */
    @Test
    public void removeAvailableColorTest_expectedColorRemoved_afterRemoval() {
        controller.removeAvailableColor("blue");
        assertFalse(controller.getAvailableColors().contains("blue"));
    }

    /**
     * Method for testing controller's getPlayers method.
     * Test if a new player is created and added to the game table correctly.
     *
     * @author Foini Lorenzo
     */
    @Test
    public void createNewPlayerTest_expectedPlayerAdded_afterCreation() {
        int initialSize = gameTable.getPlayers().size();
        Player player4 = createPlayer("Player4");
        controller.createNewPlayer(player4.getUsername(), player4.getColor().toString(), player4.getStarterCard(), player4.getHand(), player4.getSecretObjective());
        assertEquals(initialSize + 1, gameTable.getPlayers().size());
    }

    /**
     * Method for testing controller's calculateFinalPoints method.
     * Test if the final points are calculated correctly.
     * In this case we just check if the method works
     * => We don't check if the call player.calculateObjectivePoints() in the controller is actually correct
     * => Such method is tested in PlayerTest
     *
     * @author Foini Lorenzo
     */
    @Test
    public void calculateFinalPointsTest_expectedNonZeroScores_afterCalculation() {
        try {
            player1.setScore(10);
            player2.setScore(15);
            player3.setScore(20);
        } catch (NegativeScoreException e) {
            fail("Score should not be negative");
        }
        controller.calculateFinalPoints();

        assertTrue(player1.getScore() > 0);
        assertTrue(player2.getScore() > 0);
        assertTrue(player3.getScore() > 0);
    }

    /**
     * Method for testing controller's finalScoreboard method.
     * Test if the final scores are set in the scoreboard correctly.
     *
     * @author Foini Lorenzo
     */
    @Test
    public void finalScoreboardTest_expectedCorrectScores_afterSetting() {
        try {
            player1.setScore(10);
            player2.setScore(15);
            player3.setScore(20);
        } catch (NegativeScoreException e) {
            fail("Score should not be negative");
        }
        controller.finalScoreboard();

        assertEquals((Integer) 10, GameTable.getScoreboard().getScores().get(player1));
        assertEquals((Integer) 15, GameTable.getScoreboard().getScores().get(player2));
        assertEquals((Integer) 20, GameTable.getScoreboard().getScores().get(player3));
    }

    /**
     * Method for testing if setting a negative score throws the NegativeScoreException.
     *
     * @author Foini Lorenzo
     */
    @Test(expected = NegativeScoreException.class)
    public void setScoreTest_expectedException_negativeScore() throws NegativeScoreException {
        player1.setScore(-10);
    }

    /**
     * Method for testing controller's getLeaderboard method.
     * Test if the leaderboard is ordered correctly.
     *
     * @author Foini Lorenzo
     */
    @Test
    public void getLeaderboardTest_expectedOrderedScores_afterSorting() {
        try {
            player1.setScore(10);
            player2.setScore(20);
            player3.setScore(15);
        } catch (NegativeScoreException e) {
            fail("Score should not be negative");
        }
        // Get ordered leaderboard
        LinkedHashMap<Player, Integer> leaderboard = controller.getLeaderboard();
        List<Player> orderedPlayers = new ArrayList<>(leaderboard.keySet());

        assertEquals(player2, orderedPlayers.get(0));
        assertEquals(player3, orderedPlayers.get(1));
        assertEquals(player1, orderedPlayers.get(2));
    }

    /**
     * Method for testing controller's calculateWinners method.
     * Test if the winners are calculated correctly.
     *
     * @author Foini Lorenzo
     */
    @Test
    public void calculateWinnersTest_expectedCorrectWinner_afterCalculation() {
        try {
            player1.setScore(10);
            player2.setScore(20);
            player3.setScore(15);
        } catch (NegativeScoreException e) {
            fail("Score should not be negative");
        }
        // Get ordered leaderboard
        LinkedHashMap<Player, Integer> leaderboard = controller.getLeaderboard();
        ArrayList<Player> winners = controller.calculateWinners(leaderboard);

        // Player 2 won
        assertEquals(1, winners.size());
        assertEquals(player2, winners.getFirst());
    }

    /**
     * Method for testing controller's startGame method.
     * Test if the turn is set correctly at the start of the game.
     *
     * @author Foini Lorenzo
     */
    @Test
    public void startGameTest_expectedFirstPlayerTurnSet_afterStart() {
        controller.startGame();
        assertTrue(gameTable.getPlayers().getFirst().isTurn());
    }

    /**
     * Method for testing controller's startGame method.
     * Test if the turn is passed correctly to the next player.
     *
     * @author Foini Lorenzo
     */
    @Test
    public void nextTurnTest_expectedNextPlayerTurnSet_afterTurn() {
        controller.startGame();
        controller.nextTurn();
        assertTrue(gameTable.getPlayers().get(1).isTurn());
    }
}