package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import java.util.*;

/**
 * Class to manage the game with all his players and cards
 * It contains the 4 decks, the face up (visible) cards and common objectives that will be on the table, the players and the scoreboard
 *
 * @author Andrea Di Carlo, Foini Lorenzo, Gallo Fabio
 */
public class GameTable {
    private final GamingDeck resourceDeck;
    private final GamingDeck goldDeck;
    private final GamingDeck starterDeck;
    private final ObjectiveDeck objectiveDeck;
    private final ArrayList<GamingCard> visibleCards; // Represents the 4 visible cards in the table
    private final ObjectiveCard[] commonObjectives;
    private final int numPlayers;
    private final ArrayList<Player> players; // List of players in the game
    private static Scoreboard scoreboard; // Static reference of the scoreboard
    private boolean lastTurn = false; // If true => It's last turn for all player
                                      // If false => it's not last turn
    private boolean finished = false; // If true => Game is finished
                                      // If false => Game isn't finished yet
    private int joined = 0; // Counter of total of players connected to the game

    /**
     * GameTable constructor, it builds all the decks and initializes the game
     *
     * @param numPlayers number of players
     * @throws EmptyDeckException if a player tries to draw from an empty deck
     * @throws EmptyObjectiveDeckException if somehow a player tries to draw from an empty objective deck
     * @author Foini Lorenzo, Gallo Fabio
     */
    public GameTable(int numPlayers) throws EmptyDeckException, EmptyObjectiveDeckException {
        // Initialize/Set all parameters
        scoreboard = new Scoreboard();
        this.numPlayers = numPlayers;
        this.resourceDeck = createResourceDeck(); // Call to private method for create the deck
        this.goldDeck = createGoldDeck(); // Call to private method for create the deck
        this.starterDeck = createStarterDeck(); // Call to private method for create the deck
        this.objectiveDeck = createObjectiveDeck(); // Call to private method for create the deck
        // Shuffle all the deck using shuffleDeck() method
        resourceDeck.shuffleDeck();
        goldDeck.shuffleDeck();
        starterDeck.shuffleDeck();
        objectiveDeck.shuffleDeck();

        this.players = new ArrayList<>();
        this.visibleCards = new ArrayList<>();
        // Draw card and add them in the list visibleCards
        addVisibleCard((GamingCard) resourceDeck.drawTopCard());
        addVisibleCard((GamingCard) resourceDeck.drawTopCard());
        addVisibleCard((GoldCard) goldDeck.drawTopCard());
        addVisibleCard((GoldCard) goldDeck.drawTopCard());

        // Set common objectives
        commonObjectives = new ObjectiveCard[]{
                objectiveDeck.drawTopCard(),
                objectiveDeck.drawTopCard()
        };
    }

    /**
     * resourceDeck getter
     *
     * @return the resource deck
     * @author Gallo Fabio
     */
    public GamingDeck getResourceDeck() {
        return resourceDeck;
    }


    /**
     * Method for creating the 40 resource cards and put them in the resource deck
     *
     * @author Gallo Fabio
     */
    private GamingDeck createResourceDeck() {
        // Create lists of Card and array of front corners
        ArrayList<Card> resourceCards = new ArrayList<>();
        Corner[] frontCorners;

        // Create FUNGI resource cards and start from ID 1

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        // 1
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners, 1));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        // 2
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners, 2));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };
        // 3
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners, 3));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };
        // 4
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners, 4));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };
        // 5
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners, 5));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };
        // 6
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners, 6));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        // 7
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners, 7));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        // 8
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners, 8));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        // 9
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners, 9));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        // 10
        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners, 10));


        // Create PLANT resource cards and start from ID 11

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        // 11
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners, 11));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        // 12
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners, 12));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };
        // 13
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners, 13));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };
        // 14
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners, 14));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };
        // 15
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners, 15));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE)
        };
        // 16
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners, 16));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };
        // 17
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners, 17));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        // 18
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 1, frontCorners ,18));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };
        // 19
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 1, frontCorners, 19));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        // 20
        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 1, frontCorners, 20));


        // Create ANIMAL resource cards and start from ID 21

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        // 21
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners, 21));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };
        // 22
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners, 22));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        // 23
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners, 23));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };
        // 24
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners, 24));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };
        // 25
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners, 25));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE)
        };
        // 26
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners, 26));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };
        // 27
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners, 27));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        // 28
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 1, frontCorners, 28));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };
        // 29
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 1, frontCorners, 29));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        // 30
        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 1, frontCorners, 30));


        // Create INSECT resource cards and start from ID 31

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        // 31
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners, 31));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };
        // 32
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners, 32));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        // 33
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners, 33));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };
        // 34
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners, 34));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };
        // 35
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners, 35));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };
        // 36
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners, 36));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        // 37
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners, 37));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        // 38
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 1, frontCorners, 38));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };
        // 39
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 1, frontCorners, 39));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        // 40
        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 1, frontCorners, 40));

        // Return new instance of GamingDeck
        return new GamingDeck(resourceCards);
    }

    /**
     * goldenDeck getter
     *
     * @return the golden deck
     * @author Gallo Fabio
     */
    public GamingDeck getGoldDeck() {
        return goldDeck;
    }


    /**
     * Method for creating the 40 golden cards and put them in the golden deck
     *
     * @author Gallo Fabio
     */
    private GamingDeck createGoldDeck() {
        // Create list of Card and array of front corners and resources
        ArrayList<Card> goldCards = new ArrayList<>();
        Corner[] frontCorners;
        Kingdom[] resources;

        // Create FUNGI gold cards and start from ID 41

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.ANIMALKINGDOM};
        // 41
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners, resources, ConditionPoint.QUILL, 41));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.PLANTKINGDOM};
        // 42
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners, resources, ConditionPoint.INKWELL, 42));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.INSECTKINGDOM};
        // 43
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners, resources, ConditionPoint.MANUSCRIPT, 43));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.ANIMALKINGDOM};
        // 44
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER, 44));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.PLANTKINGDOM};
        // 45
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER, 45));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.INSECTKINGDOM};
        // 46
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER, 46));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM};
        // 47
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE ,47));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM};
        // 48
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE ,48));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM};
        // 49
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE ,49));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM};
        // 50
        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 5, frontCorners, resources, ConditionPoint.NONE, 50));


        // Create PLANT gold cards and start from ID 51

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.INSECTKINGDOM};
        // 51
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 1, frontCorners, resources, ConditionPoint.QUILL, 51));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.FUNGIKINGDOM};
        // 52
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 1, frontCorners, resources, ConditionPoint.MANUSCRIPT, 52));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.ANIMALKINGDOM};
        // 53
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 1, frontCorners, resources, ConditionPoint.INKWELL, 53));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.INSECTKINGDOM};
        // 54
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER, 54));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.ANIMALKINGDOM};
        // 55
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER, 55));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.FUNGIKINGDOM};
        // 56
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER, 56));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM};
        // 57
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE ,57));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM};
        // 58
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE, 58));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM};
        // 59
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE, 59));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM};
        // 60
        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 5, frontCorners, resources, ConditionPoint.NONE, 60));


        // Create ANIMAL gold cards and start from ID 61

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.INSECTKINGDOM};
        // 61
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 1, frontCorners, resources, ConditionPoint.INKWELL, 61));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.PLANTKINGDOM};
        // 62
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 1, frontCorners, resources, ConditionPoint.MANUSCRIPT, 62));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.FUNGIKINGDOM};
        // 63
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 1, frontCorners, resources, ConditionPoint.QUILL, 63));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.INSECTKINGDOM};
        // 64
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER, 64));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.FUNGIKINGDOM};
        // 65
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER, 65));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.PLANTKINGDOM};
        // 66
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER, 66));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM};
        // 67
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE, 67));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM};
        // 68
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE, 68));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM};
        // 69
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE, 69));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM};
        // 70
        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 5, frontCorners, resources, ConditionPoint.NONE, 70));


        // Create INSECT gold cards and start from ID 71

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.PLANTKINGDOM};
        // 71
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 1, frontCorners, resources, ConditionPoint.QUILL, 71));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.ANIMALKINGDOM};
        // 72
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 1, frontCorners, resources, ConditionPoint.MANUSCRIPT, 72));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.FUNGIKINGDOM};
        // 73
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 1, frontCorners, resources, ConditionPoint.INKWELL, 73));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.ANIMALKINGDOM};
        // 74
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER, 74));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.PLANTKINGDOM};
        // 75
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER, 75));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.FUNGIKINGDOM};
        // 76
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER, 76));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM};
        // 77
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE, 77));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM};
        // 78
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE, 78));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM};
        // 79
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE, 79));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM};
        // 80
        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 5, frontCorners, resources, ConditionPoint.NONE, 80));

        // Return new instance of GamingDeck
        return new GamingDeck(goldCards);
    }

    /**
     * starterDeck getter
     *
     * @return the starter deck
     * @author Gallo Fabio
     */
    public GamingDeck getStarterDeck() {
        return starterDeck;
    }


    /**
     * Method for creating the 6 starting cards and put them in the starting deck
     *
     * @author Gallo Fabio
     */
    private GamingDeck createStarterDeck() {
        // Create list of Card and array of front corners, back corners and kingdoms
        ArrayList<Card> starterCards = new ArrayList<>();
        Corner[] frontCorners;
        Corner[] backCorners;
        Kingdom[] kingdoms;

        // Create starter cards and start from ID 81
        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        backCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };
        kingdoms = new Kingdom[]{Kingdom.INSECTKINGDOM};
        // 81
        starterCards.add(new StarterCard(false, frontCorners, backCorners, kingdoms, 81));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };
        backCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };
        kingdoms = new Kingdom[]{Kingdom.FUNGIKINGDOM};
        // 82
        starterCards.add(new StarterCard(false, frontCorners, backCorners, kingdoms, 82));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        backCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };
        kingdoms = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.FUNGIKINGDOM};
        // 83
        starterCards.add(new StarterCard(false, frontCorners, backCorners, kingdoms, 83));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };
        backCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };
        kingdoms = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.INSECTKINGDOM};
        // 84
        starterCards.add(new StarterCard(false, frontCorners, backCorners, kingdoms, 84));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        backCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };
        kingdoms = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.PLANTKINGDOM};
        // 85
        starterCards.add(new StarterCard(false, frontCorners, backCorners, kingdoms, 85));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };
        backCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };
        kingdoms = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.FUNGIKINGDOM};
        // 86
        starterCards.add(new StarterCard(false, frontCorners, backCorners, kingdoms, 86));

        // Return new instance of GamingDeck
        return new GamingDeck(starterCards);
    }

    /**
     * objectiveDeck getter
     *
     * @return the objective deck
     * @author Gallo Fabio
     */
    public ObjectiveDeck getObjectiveDeck() {
        return objectiveDeck;
    }


    /**
     * Method for creating the 16 objective cards and put them in the objective deck
     *
     * @author Gallo Fabio
     */
    private ObjectiveDeck createObjectiveDeck() {
        // Create list of Card and array of objects
        ArrayList<ObjectiveCard> objectiveCards = new ArrayList<>();
        GameObject[] objects = new GameObject[]{GameObject.NONE};

        // Create objective cards start form ID 87
        // 87
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.SECONDARYDIAGONAL, Kingdom.FUNGIKINGDOM, 87));

        // 88
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.PRIMARYDIAGONAL, Kingdom.PLANTKINGDOM, 88));

        // 89
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.SECONDARYDIAGONAL, Kingdom.ANIMALKINGDOM, 89));

        // 90
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.PRIMARYDIAGONAL, Kingdom.INSECTKINGDOM, 90));

        // in the next 4 cases, given the pattern, we put as the kingdom the one which appears in 2 out of 3 cards of the pattern
        // 91
        objectiveCards.add(new ObjectiveCard(3, objects, Pattern.LOWERRIGHT, Kingdom.FUNGIKINGDOM, 91));

        // 92
        objectiveCards.add(new ObjectiveCard(3, objects, Pattern.LOWERLEFT, Kingdom.PLANTKINGDOM, 92));

        // 93
        objectiveCards.add(new ObjectiveCard(3, objects, Pattern.UPPERRIGHT, Kingdom.ANIMALKINGDOM, 93));

        // 94
        objectiveCards.add(new ObjectiveCard(3, objects, Pattern.UPPERLEFT, Kingdom.INSECTKINGDOM, 94));

        // 95
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.NONE, Kingdom.FUNGIKINGDOM, 95));

        // 96
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.NONE, Kingdom.PLANTKINGDOM, 96));

        // 97
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.NONE, Kingdom.ANIMALKINGDOM, 97));

        // 98
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.NONE, Kingdom.INSECTKINGDOM, 98));

        // 99
        objects = new GameObject[]{GameObject.QUILL, GameObject.INKWELL, GameObject.MANUSCRIPT};
        objectiveCards.add(new ObjectiveCard(3, objects, Pattern.NONE, Kingdom.NONE, 99));

        // 100
        objects = new GameObject[]{GameObject.MANUSCRIPT, GameObject.MANUSCRIPT};
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.NONE, Kingdom.NONE, 100));

        // 101
        objects = new GameObject[]{GameObject.INKWELL, GameObject.INKWELL};
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.NONE, Kingdom.NONE, 101));

        // 102
        objects = new GameObject[]{GameObject.QUILL, GameObject.QUILL};
        objectiveCards.add(new ObjectiveCard(2, objects, Pattern.NONE, Kingdom.NONE, 102));

        // Return new instance of ObjectiveDeck
        return new ObjectiveDeck(objectiveCards);
    }

    /**
     * visibleCards getter
     *
     * @return the visible cards
     * @author Gallo Fabio
     */
    public ArrayList<GamingCard> getVisibleCard() {
        return visibleCards;
    }


    /**
     * Method for adding a card to the visible cards
     *
     * @param gamingCard it is the card that is going to be put face up with the visible cards
     * @author Gallo Fabio
     */
    public void addVisibleCard(GamingCard gamingCard) {
        visibleCards.add(gamingCard);
    }

    /**
     * commonObjectives getter
     *
     * @return the common objectives
     * @author Gallo Fabio
     */
    public ObjectiveCard[] getCommonObjectives() {
        return commonObjectives;
    }


    /**
     * numPlayers getter
     *
     * @return the number of players
     * @author Gallo Fabio
     */
    public int getNumPlayers() {
        return numPlayers;
    }


    /**
     * players getter
     *
     * @return the list (as an arraylist) of players
     * @author Gallo Fabio
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * lastTurn getter
     *
     * @return boolean representing if it is last turn or not.
     *         true => It is last turn
     *         false => It is not last turn
     * @author Gallo Fabio
     */
    public boolean isLastTurn() {
        return lastTurn;
    }

    /**
     * lastTurn setter to true => It is last turn
     *
     * @author Gallo Fabio
     */
    public void setLastTurn() {
        this.lastTurn = true;
    }

    /**
     * setJoined setter
     *
     * @param joined: if true => Increment joined by 1
     *                if false => Decrement joined by 1
     * @author Gallo Fabio
     */
    public void setJoined(boolean joined) {
        if(joined) this.joined++;
        else this.joined--;
    }

    /**
     * Method for getting a player by his username
     *
     * @param username player username
     * @return player with such username
     * @throws NoPlayerWithSuchUsernameException if there isn't a player witch such username
     * @author Foini Lorenzo
     */
    public Player getPlayerByUsername(String username) throws NoPlayerWithSuchUsernameException {
        // Iterate through players for finding the chosen one
        for(Player player : players) {
            if(player.getUsername().equals(username)) {
                return player;
            }
        }
        // There isn't a player with such username
        throw new NoPlayerWithSuchUsernameException("There isn't a player in the game with such username.");
    }

    /**
     * Method for adding a player to the game and set score to 0
     *
     * @param player to be added
     * @author Foini Lorenzo, Gallo Fabio
     */
    public void addPlayer(Player player) {
        players.add(player); // Add player in the list of players
        scoreboard.setScore(player, 0); // Add score in the scoreboard
    }

    /**
     * scoreboard getter
     *
     * @return static scoreboard
     * @author Foini Lorenzo, Gallo Fabio
     */
    public static Scoreboard getScoreboard() {
        return scoreboard;
    }

    /**
     * scoreboard setter
     *
     * @param scoreboard updated scoreboard
     * @author Fabio Gallo
     */
    public void setScoreboard(Scoreboard scoreboard) {
        GameTable.scoreboard = scoreboard;
    }

    /**
     * Method for adding a score to the player, given by parameters
     *
     * @param player: player to add the score
     * @param score: score to add to the player
     * @author Foini Lorenzo
     */
    public void assignScore(Player player, int score){
        scoreboard.setScore(player, score);
    }

    /**
     * Checks if a player got to at least 20 points and eventually starts the end of the game
     * => When a player reaches at least 20 points or the decks are empty
     *
     * @return true => The game is ended
     *         false => The game is not ended
     * @author Foini Lorenzo, Gallo Fabio
     */
    public boolean isEnded() {
        // Check if a player has reached at least 20 points
        for (Player player : players) {
            if (player.getScore() >= 20)
                return true;
        }
        // Check if the decks are empty
        return resourceDeck.deckSize()==0 && goldDeck.deckSize()==0;
    }

    /**
     * Method for determinate if the game is full of players or not
     *
     * @return true => The game is full, so no player can join this game
     *         false => The game is not full, so a player can join this game
     * @author Gallo Fabio
     */
    public boolean isFull(){
        return getNumPlayers()==joined;
    }

    /**
     * finished getter
     *
     * @return true => The game is finish
     *         false => The game is not finish
     * @author Gallo Fabio
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * finished setter to true
     * It is called when the game is finished
     *
     * @author Gallo Fabio
     */
    public void setFinished() {
        this.finished = true;
    }

    /**
     * Method for drawing a resource card from the resource deck
     *
     * @return the drawn card as a GamingCard
     * @throws EmptyDeckException if the resource deck is empty
     * @author Foini Lorenzo
     */
    public GamingCard drawResourceCardDeck() throws EmptyDeckException {
        // Cast to GamingCard (It is a Card)
        return (GamingCard) resourceDeck.drawTopCard();
    }

    /**
     * Method for drawing a golden card from the golden deck
     *
     * @return the drawn card as a GoldCard
     * @throws EmptyDeckException if the golden deck is empty
     * @author Foini Lorenzo
     */
    public GoldCard drawGoldCardDeck() throws EmptyDeckException {
        // Cast
        return (GoldCard) goldDeck.drawTopCard();
    }

    /**
     * Method for drawing a card from the face up (visible) cards (for both the golden and resource cards)
     *
     * @param position is the position where the card to be drawn is places
     * @return the drawn card
     * @throws InvalidDrawFromTableException if the players tries to draw a card out of the visibleCards range (1 to size()-1)
     * @author Di Carlo Andrea, Foini Lorenzo
     */
    public GamingCard drawCardFromTable(int position) throws InvalidDrawFromTableException {
        if (position < 0 || position > visibleCards.size() - 1) {
            // Invalid index
            throw new InvalidDrawFromTableException("Invalid draw from table. Select one of the cards or draw from a deck.");
        } else {
            GamingCard selectedCard = visibleCards.get(position);
            /*
                Replace the selected card with a new one
                Three cases:
                1 - The corresponding deck is not empty => Draw from that one.
                2 - The corresponding deck is empty => Draw from the other one.
                3 - Both decks are empty => Do not add any card.
            */

            // Check if the selected card is a gold card or a gaming card
            if(selectedCard instanceof GoldCard){
                try { // Case 1 with a gold card
                    GoldCard topCardResource = (GoldCard) goldDeck.drawTopCard();
                    // goldDeck is not empty => Add top card to the visible ones
                    visibleCards.set(position, topCardResource);
                } catch (EmptyDeckException e) { // goldDeck is empty
                    try { // Case 2
                        GamingCard topCardGold = (GamingCard) resourceDeck.drawTopCard();
                        // resourceDeck is not empty => Add top card to the visible ones
                        visibleCards.set(position, topCardGold);
                    } catch (EmptyDeckException ex) { // resourceDesk is empty
                        // Case 3
                        // Shift to the left
                        for (int i = position; i < visibleCards.size() - 1; i++) {
                            visibleCards.set(i, visibleCards.get(i + 1));
                        }
                        //Remove last element
                        visibleCards.removeLast();
                    }
                }
            }
            else{
                try { // Case 1 with a gaming card
                    GamingCard topCardResource = (GamingCard) resourceDeck.drawTopCard();
                    // resourceDeck is not empty => Add top card to the visible ones
                    visibleCards.set(position, topCardResource);
                } catch (EmptyDeckException e) { // resourceDesk is empty
                    try { // Case 2
                        GoldCard topCardGold = (GoldCard) goldDeck.drawTopCard();
                        // goldDeck is not empty => Add top card to the visible ones
                        visibleCards.set(position, topCardGold);
                    } catch (EmptyDeckException ex) { // goldDeck is empty
                        // Case 3
                        // Shift to the left
                        for (int i = position; i < visibleCards.size() - 1; i++) {
                            visibleCards.set(i, visibleCards.get(i + 1));
                        }
                        //Remove last element
                        visibleCards.removeLast();
                    }
                }
            }
            // Return selected drawn card
            return selectedCard;
        }
    }
}