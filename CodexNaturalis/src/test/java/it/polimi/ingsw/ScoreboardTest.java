package it.polimi.ingsw;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.game.Color;
import it.polimi.ingsw.model.game.Player;
import it.polimi.ingsw.model.game.PlayerArea;
import it.polimi.ingsw.model.game.Scoreboard;

import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * Class for testing Scoreboard class
 *
 * @author Foini Lorenzo
 */
public class ScoreboardTest {
    private Scoreboard scoreboard;
    private Player player1;
    private Player player2;

    /**
     * Method for set up test
     *
     * @author Foini Lorenzo
     */
    @Before
    public void setUp() {
        scoreboard = new Scoreboard();

        // Create the two players by calling createPlayer method
        // The two players have the same parameters (except for the username), because we won't test player's method in this class
        player1 = createPlayer("Player1");
        player2 = createPlayer("Player2");
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
     * Method for testing Scoreboard's constructor and check if it's empty at the beginning
     *
     * @author Foini Lorenzo
     */
    @Test
    public void constructorTest_initialEmptyScoreboard() {
        assertTrue("Scoreboard should be initially empty", scoreboard.getScores().isEmpty());
    }

    /**
     * Method for testing Scoreboard's setScores method
     * Should be set scores correctly
     *
     * @author Foini Lorenzo
     */
    @Test
    public void setScoresTest_correctlySetsScores() {
        HashMap<Player, Integer> scores = new HashMap<>();
        scores.put(player1, 10);
        scores.put(player2, 20);
        scoreboard.setScores(scores);
        assertEquals("Scores should be set correctly", scores, scoreboard.getScores());
    }

    /**
     * Method for testing Scoreboard's getScores method
     * Should be get scores correctly
     *
     * @author Foini Lorenzo
     */
    @Test
    public void getScoresTest_returnCorrectMap() {
        HashMap<Player, Integer> scores = new HashMap<>();
        scores.put(player1, 10);
        scores.put(player2, 20);
        scoreboard.setScores(scores);
        assertEquals("Should return the correct score map", scores, scoreboard.getScores());
    }

    /**
     * Method for testing Scoreboard's setScore method used for setting a player's score
     * Should be set scores correctly for every player
     *
     * @author Foini Lorenzo
     */
    @Test
    public void setScoreTest_correctlySetsScoreForPlayer() {
        scoreboard.setScore(player1, 30);
        assertEquals("Score for Player1 should be 30", (Integer) 30, scoreboard.getScores().get(player1));

        scoreboard.setScore(player2, 40);
        assertEquals("Score for Player2 should be 40", (Integer) 40, scoreboard.getScores().get(player2));
    }

    /**
     * Method for testing Scoreboard's setScore method used when update a player's score
     * Should be update scores correctly for every player
     *
     * @author Foini Lorenzo
     */
    @Test
    public void setScore_test_updateScoreForExistingPlayer() {
        scoreboard.setScore(player1, 30);
        assertEquals("Score for Player1 should be 30", (Integer) 30, scoreboard.getScores().get(player1));

        scoreboard.setScore(player1, 50);
        assertEquals("Score for Player1 should be updated to 50", (Integer) 50, scoreboard.getScores().get(player1));
    }
}