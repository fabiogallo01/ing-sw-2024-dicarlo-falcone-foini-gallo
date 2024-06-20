package it.polimi.ingsw;

import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import it.polimi.ingsw.model.game.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotEquals;

/**
 * Class for testing GameTable class
 *
 * @author Falcone Giacomo, Foini Lorenzo, Gallo Fabio
 */
public class GameTableTest {
    // Initialize variables
    GameTable gameTable = null;
    private final int numPlayers = 4;

    /**
     * Initialized GameTable with a numPlayers number of players
     *
     * @author Falcone Giacomo
     */
    @Before
    public void setUp() {
        try {
            gameTable = new GameTable(numPlayers);
        }
        catch (EmptyDeckException | EmptyObjectiveDeckException exception) {
            fail("Initialization failed: " + exception.getMessage());
        }
    }

    /**
     * Tests if the gameTable initialization is not null and if the number of players is correct
     *
     * @author Falcone Giacomo
     */
    @Test
    public void testInitialization_GameTableNotNull_CorrectNumberOfPlayers() {
        assertNotNull("GameTable object is null", gameTable); //"GameTable object is not null"
        assertEquals("Not correct number of players", numPlayers, gameTable.getNumPlayers()); //"Correct number of players"
    }

    /**
     * Tests that decks are not empty on initialization
     * Tests that deck's sizes are correct
     *
     * @author Falcone Giacomo
     */
    @Test
    public void testDecksSize_CheckCorrectSizeOfDecks_ForAllTypeOfDecks() {
        assertNotEquals("Resource deck is empty", 0, gameTable.getResourceDeck().deckSize());
        assertNotEquals("Gold deck is empty", 0, gameTable.getGoldDeck().deckSize());
        assertNotEquals("Starter deck is empty", 0, gameTable.getStarterDeck().deckSize());
        assertEquals("The size of the resource deck is not 38", 38, gameTable.getResourceDeck().deckSize());
        assertEquals("The size of the gold deck is not 38", 38, gameTable.getGoldDeck().deckSize());
        assertEquals("The size of the starter deck is not 6", 6, gameTable.getStarterDeck().deckSize());
        assertNotEquals("Objective deck is empty", 0, gameTable.getObjectiveDeck().deckSize());
        assertEquals("The size of the objective deck is not 14", 14, gameTable.getObjectiveDeck().deckSize());
    }

    /**
     * Tests that there are 4 visible cards on the board
     *
     * @author Falcone Giacomo
     */
    @Test
    public void testNumberOfVisibleCards_ShouldBe4() {
        assertEquals("Visible cards are not 4", 4,gameTable.getVisibleCard().size());
    }

    /**
     * Tests that after drawing all card from the resource deck I get an exception
     * if I draw another time
     *
     * @throws EmptyDeckException if one of resource/gold/starter deck is empty
     * @throws EmptyObjectiveDeckException if objective deck is empty
     * @author Falcone Giacomo
     */
    @Test(expected = EmptyDeckException.class)
    public void testEmptyResourceDeckException_ResourceDeckEmpty_ShouldRaiseEmptyDeckException() throws EmptyDeckException, EmptyObjectiveDeckException {
        gameTable = new GameTable(numPlayers);
        // Getting resource deck empty
        while (gameTable.getResourceDeck().deckSize() > 0) {
            gameTable.drawResourceCardDeck();
        }
        // No more cards, should raise exception
        gameTable.drawResourceCardDeck();
    }

    /**
     * Tests that after drawing all card from the resource deck I get an exception
     * if I draw another time
     *
     * @throws EmptyDeckException if one of resource/gold/starter deck is empty
     * @throws EmptyObjectiveDeckException if objective deck is empty
     * @author Falcone Giacomo
     */
    @Test(expected = EmptyDeckException.class)
    public void testEmptyGoldDeckException_GoldDeckEmpty_ShouldRaiseEmptyDeckException() throws EmptyDeckException, EmptyObjectiveDeckException {
        gameTable = new GameTable(numPlayers);
        // Getting gold deck empty
        while (gameTable.getGoldDeck().deckSize() > 0) {
            gameTable.drawGoldCardDeck();
        }
        // No more cards, should raise exception
        gameTable.drawGoldCardDeck();
    }

    /**
     * Tests that I actually get a card by drawing it from the visible deck
     *
     * @throws InvalidDrawFromTableException if incorrect draw
     * @author Falcone Giacomo
     */
    @Test
    public void testValidDraw_IndexIsValid_NoExceptionThrow() throws InvalidDrawFromTableException {
        GamingCard card = gameTable.drawCardFromTable(1);
        assertNotNull("Card should be drawn", card);
    }

    /**
     * Tests that I get an exception by drawing from a wrong position
     *
     * @throws InvalidDrawFromTableException if incorrect draw
     * @author Falcone Giacomo
     */
    @Test(expected = InvalidDrawFromTableException.class)
    public void testDrawWithInvalidPositionTooLow_ShouldThrowInvalidDrawFromTableException() throws InvalidDrawFromTableException {
        gameTable.drawCardFromTable(-1);  // Position is too low, should throw exception
    }

    /**
     * Tests that I get an exception by drawing from a wrong position
     *
     * @throws InvalidDrawFromTableException if incorrect draw
     * @author Falcone Giacomo
     */
    @Test(expected = InvalidDrawFromTableException.class)
    public void testDrawWithInvalidPositionTooHigh_ShouldThrowInvalidDrawFromTableException() throws InvalidDrawFromTableException {
        gameTable.drawCardFromTable(gameTable.getVisibleCard().size());  // Position is too high, should throw exception
    }

    /**
     * Test to ensure a new card is placed when the original deck is not empty
     *
     * @throws InvalidDrawFromTableException if incorrect draw
     * @author Falcone Giacomo
     */
    @Test
    public void testCardReplacementWhenDecksAreNotEmpty_SizeOfVisibleCardRemainsEqual() throws InvalidDrawFromTableException {
        int initialSize = gameTable.getVisibleCard().size();
        gameTable.drawCardFromTable(1);
        assertEquals("Size should remain constant after drawing and replacing", initialSize, gameTable.getVisibleCard().size());
    }

    /**
     * Tests that I get an exception if I draw after drawing all cards from both decks,
     * so without possibility to replace visible cards after drawing them
     *
     * @throws InvalidDrawFromTableException if incorrect draw
     * @throws EmptyDeckException is deck is empty
     * @author Falcone Giacomo
     */
    @Test
    public void testNoReplacementWhenBothDecksAreEmpty_SizeOfVisibleCardShouldDecreaseBy1() throws InvalidDrawFromTableException, EmptyDeckException {
        // Empty both decks
        while (gameTable.getGoldDeck().deckSize() > 0) {
            gameTable.drawGoldCardDeck();
        }
        while (gameTable.getResourceDeck().deckSize() > 0) {
            gameTable.drawResourceCardDeck();
        }
        int initialSize = gameTable.getVisibleCard().size();
        gameTable.drawCardFromTable(1);
        assertEquals("Size should decrease when no replacement is possible", initialSize - 1, gameTable.getVisibleCard().size());
    }

    /**
     * Tests that game ends if a player reaches 20 points
     * and that doesn't end before with all the players with less than 20 points
     *
     * @author Falcone Giacomo, Foini Lorenzo, Gallo Fabio
     */
    @Test
    public void testGameEndsWhenPlayerReaches20Points() {
        //creating all necessaries parameters for a player

        //creating player area
        boolean[][] Area = new boolean[81][81];
        for (boolean[] booleans : Area) {
            Arrays.fill(booleans, true);
        }
        PlayerArea playerArea = new PlayerArea(Area, new ArrayList<>());

        //creating all the cards needed for the player to start the game
        ObjectiveCard secretObjective = new ObjectiveCard(1, null, Pattern.NONE, Kingdom.NONE, 0);
        StarterCard starterCard = new StarterCard(true, new Corner[4], new Corner[4], new Kingdom[]{Kingdom.NONE}, 0);
        ArrayList<GamingCard> hand = new ArrayList<>();  //empty hand

        Color playerColor = Color.GREEN;

        //assigning all the created values to player1 and adding it to the game table
        Player player1 = new Player("player", 19, playerArea, playerColor, secretObjective, starterCard, hand);
        gameTable.addPlayer(player1);

        // adding other players with less than 20 points
        Player player;
        for (int i = 0; i < 3; i++) {
            player = new Player("Player" + i, 5 + i, playerArea, Color.RED, null, null, hand);
            gameTable.addPlayer(player);
        }

        //Checking if the game ends before a player has reached 20 points
        assertFalse("No one reached 20 points, but the game ended", gameTable.isEnded());

        //setting the score of player1 to 20
        try {
            player1.setScore(20);
        } catch (NegativeScoreException exception) {
            System.out.println(exception.getMessage());
        }

        //Checking if the game is over given that a player has reached 20 points
        assertTrue("A player reached 20 points, but the game didn't end", gameTable.isEnded());
    }

    /**
     * Tests that the visible cards are updated correctly after a draw
     * and replacement from a non-empty deck.
     *
     * @throws InvalidDrawFromTableException if incorrect draw
     * @author Falcone Giacomo
     */
    @Test
    public void testCardReplacement_SizeRemainTheSame_NoExceptionThrow() throws InvalidDrawFromTableException {
        // Ensure the card is drawn and replaced correctly
        GamingCard drawnCard = gameTable.drawCardFromTable(0);
        assertNotNull("Drawn card should not be null", drawnCard);
        assertEquals("Visible cards size should remain 4 after replacement", 4, gameTable.getVisibleCard().size());
    }

    /**
     * Tests if the common objectives are set correctly during initialization
     *
     * @author Falcone Giacomo
     */
    @Test
    public void testCommonObjectivesInitialization_ShouldBe2CommonObjectiveCards() {
        ObjectiveCard[] commonObjectives = gameTable.getCommonObjectives();
        assertNotNull("Common objectives should not be null", commonObjectives);
        assertEquals("There should be exactly 2 common objectives", 2, commonObjectives.length);
    }

    /**
     * Tests the getPlayerByUsername method to ensure it correctly get players when exist
     *
     * @author Falcone Giacomo, Foini Lorenzo
     */
    @Test
    public void testGetPlayerByUsername_PlayersExist() {
        Player player1 = new Player("Player1", 0, new PlayerArea(new boolean[81][81], new ArrayList<>()), Color.GREEN, new ObjectiveCard(1, null, Pattern.NONE, Kingdom.NONE, 0), new StarterCard(true, new Corner[4], new Corner[4], new Kingdom[]{Kingdom.NONE}, 0), new ArrayList<>());
        Player player2 = new Player("Player2", 0, new PlayerArea(new boolean[81][81], new ArrayList<>()), Color.RED, new ObjectiveCard(1, null, Pattern.NONE, Kingdom.NONE, 0), new StarterCard(true, new Corner[4], new Corner[4], new Kingdom[]{Kingdom.NONE}, 0), new ArrayList<>());

        gameTable.addPlayer(player1);
        gameTable.addPlayer(player2);

        try {
            Player retrievedPlayer1 = gameTable.getPlayerByUsername("Player1");
            assertEquals("Player1 should be retrieved correctly", player1, retrievedPlayer1);

            Player retrievedPlayer2 = gameTable.getPlayerByUsername("Player2");
            assertEquals("Player2 should be retrieved correctly", player2, retrievedPlayer2);
        } catch (NoPlayerWithSuchUsernameException e) {
            fail("Players should exist");
        }
    }

    /**
     * Tests the getPlayerByUsername method to ensure it correctly get player when doesn't exist
     *
     * @author Foini Lorenzo
     */
    @Test
    public void testGetPlayerByUsername_PlayerDontExists_ExpectNoPlayerWithSuchUsernameException() {
        try {
            gameTable.getPlayerByUsername("NonExistentPlayer");
            fail("Expected NoPlayerWithSuchUsernameException to be thrown");
        } catch (NoPlayerWithSuchUsernameException e) {
            assertTrue("Exception thrown correctly", true);
        }
    }

    /**
     * Tests the set and get methods for scoreboard
     *
     * @author Falcone Giacomo
     */
    @Test
    public void testSetAndGetScoreboard() {
        Scoreboard newScoreboard = new Scoreboard();
        gameTable.setScoreboard(newScoreboard);
        assertEquals("Scoreboard should be updated correctly", newScoreboard, GameTable.getScoreboard());
    }

    /**
     * Tests if the game ends correctly when resource and gold decks are empty
     *
     * @author Falcone Giacomo, Foini Lorenzo
     */
    @Test
    public void testGameEndsWhenDecksAreEmpty_isEndedShouldBeTrue() {
        while (gameTable.getGoldDeck().deckSize() > 0) {
            try {
                gameTable.drawGoldCardDeck();
            } catch (EmptyDeckException e) {
                fail("Unexpected EmptyDeckException: " + e.getMessage());
            }
        }
        while (gameTable.getResourceDeck().deckSize() > 0) {
            try {
                gameTable.drawResourceCardDeck();
            } catch (EmptyDeckException e) {
                fail("Unexpected EmptyDeckException: " + e.getMessage());
            }
        }
        assertTrue("Game should end when all decks are empty", gameTable.isEnded());
    }

    /**
     * Tests if the finish game getter and setter works correctly
     *
     * @author Falcone Giacomo
     */
    @Test
    public void testSetFinished_testStartOfGame_TestEndOfGame() {
        assertFalse("Game should not be ended at the start", gameTable.isFinished());
        gameTable.setFinished();
        assertTrue("Game should be ended", gameTable.isFinished());
    }

    /**
     * Tests if the "last turn" getter and setter works correctly
     *
     * @author Falcone Giacomo
     */
    @Test
    public void testIsFull_falseAtStart_TrueWhenAllPlayersJoinTheGame() {
        // Initially, no players have joined, so isFull should return false
        assertFalse("Game should not be full initially", gameTable.isFull());

        // Simulate players joining
        for (int i = 0; i < numPlayers; i++) {
            gameTable.setJoined(true);
        }

        // Now the number of joined players should be equal to numPlayers, so isFull should return true
        assertTrue("Game should be full when all players have joined", gameTable.isFull());

        // Simulate a player leaving
        gameTable.setJoined(false);

        // Now isFull should return false again
        assertFalse("Game should not be full if a player leaves", gameTable.isFull());
    }

    /**
     * Tests if the "last turn" getter and setter works correctly
     *
     * @author Falcone Giacomo
     */
    @Test
    public void testSetLastTurn_NotLastTurnAtStart_LastTurnAtEnd() {
        assertFalse("Game should not be ended at the start", gameTable.isLastTurn());
        gameTable.setLastTurn();
        assertTrue("Game should be ended", gameTable.isLastTurn());
    }


    /**
     * Tests if NoPlayerWithSuchUsernameException is thrown when searching for a non-existent username
     *
     * @throws NoPlayerWithSuchUsernameException if player doesn't exist
     * @author Falcone Giacomo
     */
    @Test(expected = NoPlayerWithSuchUsernameException.class)
    public void testGetPlayerByUsernameException_ShouldThrowNoPlayerWithSuchUsernameException() throws NoPlayerWithSuchUsernameException {
        // Attempt to get a player with a username that doesn't exist
        gameTable.getPlayerByUsername("nonExistentUsername");
    }

    /**
     * Tests drawing a GoldCard from the table and the replacement of the drawn card
     * Covers the cases when:
     * 1. The gold deck is not empty
     * 2. The gold deck is empty, but the resource deck is not empty
     *
     * @throws InvalidDrawFromTableException if the position is invalid
     * @throws EmptyDeckException if the decks are empty when drawing cards
     * @author Falcone Giacomo
     */
    @Test
    public void testDrawGoldCardFromTable_GoldDeckIsNotEmpty_GoldDeckIsEmpty() throws InvalidDrawFromTableException, EmptyDeckException {
        // Assuming the first card in visibleCards is a GoldCard
        gameTable.getVisibleCard().set(0, new GoldCard(false, Kingdom.FUNGIKINGDOM, 1, new Corner[4], new Kingdom[3], ConditionPoint.QUILL, 1));

        // Case 1: Gold deck is not empty
        GamingCard card = gameTable.drawCardFromTable(0);
        assertNotNull("Card should be drawn", card);
        assertTrue("New card should be a GoldCard", gameTable.getVisibleCard().getFirst() instanceof GoldCard);

        // Case 2: Gold deck is empty, resource deck is not empty
        while (gameTable.getGoldDeck().deckSize() > 0) {
            gameTable.drawGoldCardDeck();
        }
        card = gameTable.drawCardFromTable(0);
        assertNotNull("Card should be drawn", card);
        assertNotNull("New card should be a GamingCard", gameTable.getVisibleCard().getFirst());

    }

    /**
     * Tests drawing a GamingCard from the table and the replacement of the drawn card
     * Covers the cases when:
     * 1. The resource deck is not empty
     * 2. The resource deck is empty, but the gold deck is not empty
     * 3. Both the resource deck and the gold deck are empty
     *
     * @throws InvalidDrawFromTableException if the position is invalid
     * @throws EmptyDeckException if the decks are empty when drawing cards
     * @author Falcone Giacomo
     */
    @Test
    public void testDrawResourceCardFromTable_ResourceDeckIsNotEmpty_ResourceDeckIsEmpty() throws InvalidDrawFromTableException, EmptyDeckException {
        // Assuming the first card in visibleCards is a GamingCard
        gameTable.getVisibleCard().set(0, new GamingCard(false, Kingdom.FUNGIKINGDOM, 1, new Corner[4], 1));

        // Case 1: Resource deck is not empty
        GamingCard card = gameTable.drawCardFromTable(0);
        assertNotNull("Card should be drawn", card);
        assertNotNull("New card should be a GamingCard", gameTable.getVisibleCard().getFirst());

        // Case 2: Resource deck is empty, gold deck is not empty
        while (gameTable.getResourceDeck().deckSize() > 0) {
            gameTable.drawResourceCardDeck();
        }
        card = gameTable.drawCardFromTable(0);
        assertNotNull("Card should be drawn", card);
        assertTrue("New card should be a GoldCard", gameTable.getVisibleCard().getFirst() instanceof GoldCard);

        // Case 3: Both decks are empty
        while (gameTable.getGoldDeck().deckSize() > 0) {
            gameTable.drawGoldCardDeck();
        }
        int initialSize = gameTable.getVisibleCard().size();
        gameTable.drawCardFromTable(0);
        assertEquals("Visible cards size should decrease", initialSize - 1, gameTable.getVisibleCard().size());
    }
}