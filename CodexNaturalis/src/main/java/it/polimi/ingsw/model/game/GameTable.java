package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import java.util.*;

/**
 * Class to manage the game with all his players and cards
 * It contains the 4 decks, the face up (visible) cards and common objectives that will be on the table, the players and the scoreboard
 *
 * @author Fabio Gallo
 */
public class GameTable {
    private GamingDeck resourceDeck;
    private GamingDeck goldDeck;
    private GamingDeck starterDeck;
    private ObjectiveDeck objectiveDeck;
    private ArrayList<GamingCard> visibleCards;
    private ObjectiveCard[] commonObjectives;
    private int numPlayers;
    private ArrayList<Player> players;
    private Scoreboard scoreboard;

    /**
     * GameTable constructor, it builds all the decks and initializes the game
     *
     * @param numPlayers number of players
     * @throws EmptyDeckException          if a player tries to draw from an empty deck
     * @throws EmptyObjectiveDeckException if somehow a player tries to draw from an empty objective deck
     * @author Fabio Gallo
     */
    public GameTable(int numPlayers) throws EmptyDeckException, EmptyObjectiveDeckException {
        this.scoreboard = new Scoreboard();
        this.numPlayers = numPlayers;
        this.resourceDeck = createResourceDeck();
        this.goldDeck = createGoldDeck();
        this.starterDeck = createStarterDeck();
        this.objectiveDeck = createObjectiveDeck();
        this.players = new ArrayList<>();
        resourceDeck.shuffleDeck();
        goldDeck.shuffleDeck();
        starterDeck.shuffleDeck();
        objectiveDeck.shuffleDeck();
        this.visibleCards = new ArrayList<>();
        addVisibleCard((GamingCard) resourceDeck.drawTopCard());
        addVisibleCard((GamingCard) resourceDeck.drawTopCard());
        addVisibleCard((GoldCard) goldDeck.drawTopCard());
        addVisibleCard((GoldCard) goldDeck.drawTopCard());

        // at this point all the players should play their initial card, then they all should draw the 3 cards

        commonObjectives = new ObjectiveCard[]{
                objectiveDeck.drawTopCard(),
                objectiveDeck.drawTopCard()
        };
        //now the players should draw the secret objective cards, and they should pick one to keep

        //the game starts
    }

    /**
     * Resource deck getter
     *
     * @return the resource deck
     * @author Fabio Gallo
     */
    public GamingDeck getResourceDeck() {
        return resourceDeck;
    }

    /**
     * Resource deck setter
     *
     * @param resourceDeck updated resource deck
     * @author Fabio Gallo
     */
    public void setResourceDeck(GamingDeck resourceDeck) {
        this.resourceDeck = resourceDeck;
    }

    /**
     * It creates the 40 resource cards and put them in the resource deck
     *
     * @author Fabio Gallo
     */
    private GamingDeck createResourceDeck() {
        ArrayList<Card> resourceCards = new ArrayList<>();
        Corner[] frontCorners;


        //FUNGI

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners));

        //PLANT

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 1, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 1, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.PLANTKINGDOM, 1, frontCorners));


        //ANIMAL

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 1, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 1, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.ANIMALKINGDOM, 1, frontCorners));


        //INSECT

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 0, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 1, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };

        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 1, frontCorners));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resourceCards.add(new GamingCard(false, Kingdom.INSECTKINGDOM, 1, frontCorners));


        return new GamingDeck(resourceCards);
    }

    /**
     * Golden deck getter
     *
     * @return the golden deck
     * @author Fabio Gallo
     */
    public GamingDeck getGoldDeck() {
        return goldDeck;
    }

    /**
     * Golden deck setter
     *
     * @param goldDeck updated golden deck
     * @author Fabio Gallo
     */
    public void setGoldDeck(GamingDeck goldDeck) {
        this.goldDeck = goldDeck;
    }

    /**
     * It creates the 40 golden cards and put them in the golden deck
     *
     * @author Fabio Gallo
     */
    private GamingDeck createGoldDeck() {
        ArrayList<Card> goldCards = new ArrayList<>();
        Corner[] frontCorners;
        Kingdom[] resources;

        //FUNGI

        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.ANIMALKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners, resources, ConditionPoint.QUILL));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.PLANTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners, resources, ConditionPoint.INKWELL));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.INSECTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 1, frontCorners, resources, ConditionPoint.MANUSCRIPT));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.ANIMALKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.PLANTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.INSECTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.FUNGIKINGDOM, 5, frontCorners, resources, ConditionPoint.NONE));

        //PLANT

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.INSECTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 1, frontCorners, resources, ConditionPoint.QUILL));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.FUNGIKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 1, frontCorners, resources, ConditionPoint.MANUSCRIPT));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.ANIMALKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 1, frontCorners, resources, ConditionPoint.INKWELL));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.INSECTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.ANIMALKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.FUNGIKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.PLANTKINGDOM, 5, frontCorners, resources, ConditionPoint.NONE));


        //ANIMAL

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.INSECTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 1, frontCorners, resources, ConditionPoint.INKWELL));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.PLANTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 1, frontCorners, resources, ConditionPoint.MANUSCRIPT));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.FUNGIKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 1, frontCorners, resources, ConditionPoint.QUILL));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.INSECTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.FUNGIKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.PLANTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.ANIMALKINGDOM, 5, frontCorners, resources, ConditionPoint.NONE));


        //INSECT

        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.PLANTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 1, frontCorners, resources, ConditionPoint.QUILL));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.ANIMALKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 1, frontCorners, resources, ConditionPoint.MANUSCRIPT));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.FUNGIKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 1, frontCorners, resources, ConditionPoint.INKWELL));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.ANIMALKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.PLANTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.FUNGIKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 2, frontCorners, resources, ConditionPoint.HIDDENCORNER));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.INKWELL, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.MANUSCRIPT, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));


        frontCorners = new Corner[]{
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.QUILL, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 3, frontCorners, resources, ConditionPoint.NONE));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        resources = new Kingdom[]{Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM};

        goldCards.add(new GoldCard(false, Kingdom.INSECTKINGDOM, 5, frontCorners, resources, ConditionPoint.NONE));


        return new GamingDeck(goldCards);
    }

    /**
     * Starter deck getter
     *
     * @return the starter deck
     * @author Fabio Gallo
     */
    public GamingDeck getStarterDeck() {
        return starterDeck;
    }

    /**
     * Starter deck setter
     *
     * @param starterDeck updated starter deck
     * @author Fabio Gallo
     */
    public void setStarterDeck(GamingDeck starterDeck) {
        this.starterDeck = starterDeck;
    }

    /**
     * It creates the 6 starting cards and put them in the starting deck
     *
     * @author Fabio Gallo
     */
    private GamingDeck createStarterDeck() {
        ArrayList<Card> starterCards = new ArrayList<>();
        Corner[] frontCorners;
        Corner[] backCorners;
        Kingdom[] kingdoms;


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

        starterCards.add(new StarterCard(false, frontCorners, backCorners, kingdoms));


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

        starterCards.add(new StarterCard(false, frontCorners, backCorners, kingdoms));


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

        starterCards.add(new StarterCard(false, frontCorners, backCorners, kingdoms));


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

        starterCards.add(new StarterCard(false, frontCorners, backCorners, kingdoms));


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

        starterCards.add(new StarterCard(false, frontCorners, backCorners, kingdoms));


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

        starterCards.add(new StarterCard(false, frontCorners, backCorners, kingdoms));


        return new GamingDeck(starterCards);
    }

    /**
     * Objective deck getter
     *
     * @return the objective deck
     * @author Fabio Gallo
     */
    public ObjectiveDeck getObjectiveDeck() {
        return objectiveDeck;
    }

    /**
     * Objective deck setter
     *
     * @param objectiveDeck updated objective deck
     * @author Fabio Gallo
     */
    public void setObjectiveDeck(ObjectiveDeck objectiveDeck) {
        this.objectiveDeck = objectiveDeck;
    }

    /**
     * It creates the 16 objective cards and put them in the objective deck
     *
     * @author Fabio Gallo
     */
    private ObjectiveDeck createObjectiveDeck() {
        ArrayList<ObjectiveCard> objectiveCards = new ArrayList<>();

        GameObject[] objects = new GameObject[]{GameObject.NONE};

        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.SECONDARYDIAGONAL, Kingdom.FUNGIKINGDOM));

        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.PRIMARYDIAGONAL, Kingdom.PLANTKINGDOM));

        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.SECONDARYDIAGONAL, Kingdom.ANIMALKINGDOM));

        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.PRIMARYDIAGONAL, Kingdom.INSECTKINGDOM));

        // in the next 4 cases, given the pattern, we put as the kingdom the one which appears in 2 out of 3 cards of the pattern
        objectiveCards.add(new ObjectiveCard(3, true, objects, Pattern.LOWERRIGHT, Kingdom.FUNGIKINGDOM));

        objectiveCards.add(new ObjectiveCard(3, true, objects, Pattern.LOWERLEFT, Kingdom.PLANTKINGDOM));

        objectiveCards.add(new ObjectiveCard(3, true, objects, Pattern.UPPERRIGHT, Kingdom.ANIMALKINGDOM));

        objectiveCards.add(new ObjectiveCard(3, true, objects, Pattern.UPPERLEFT, Kingdom.INSECTKINGDOM));

        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.FUNGIKINGDOM));

        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.PLANTKINGDOM));

        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.ANIMALKINGDOM));

        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.INSECTKINGDOM));

        objects = new GameObject[]{GameObject.QUILL, GameObject.INKWELL, GameObject.MANUSCRIPT};
        objectiveCards.add(new ObjectiveCard(3, true, objects, Pattern.NONE, Kingdom.NONE));

        objects = new GameObject[]{GameObject.MANUSCRIPT, GameObject.MANUSCRIPT};
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.NONE));

        objects = new GameObject[]{GameObject.INKWELL, GameObject.INKWELL};
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.NONE));

        objects = new GameObject[]{GameObject.QUILL, GameObject.QUILL};
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.NONE));

        return new ObjectiveDeck(objectiveCards);
    }

    /**
     * Visible cards getter
     *
     * @return the visible cards
     * @author Fabio Gallo
     */
    public ArrayList<GamingCard> getVisibleCard() {
        return visibleCards;
    }


    /**
     * Visible cards setter
     *
     * @param visibleCard updated visible cards
     * @author Fabio Gallo
     */
    public void setVisibleCard(ArrayList<GamingCard> visibleCard) {
        this.visibleCards = visibleCard;
    }

    /**
     * It adds a card to the visible cards
     *
     * @param gamingCard it is the card that is going to be put face up with the visible cards
     * @author Fabio Gallo
     */
    // TO DO exception?
    public void addVisibleCard(GamingCard gamingCard) {
        visibleCards.add(gamingCard);
    }

    /**
     * Common objectives getter
     *
     * @return the common objectives
     * @author Fabio Gallo
     */
    public ObjectiveCard[] getCommonObjectives() {
        return commonObjectives;
    }

    /**
     * Common objectives setter
     *
     * @param commonObjectives updated common objectives
     * @author Fabio Gallo
     */
    public void setCommonObjectives(ObjectiveCard[] commonObjectives) {
        this.commonObjectives = commonObjectives;
    }

    /**
     * NumPlayers getter
     *
     * @return the number of players
     * @author Fabio Gallo
     */
    public int getNumPlayers() {
        return numPlayers;
    }

    /**
     * NumPlayers setter
     *
     * @param num number of players
     * @author Fabio Gallo
     */
    public void setNumPlayers(int num) {
        this.numPlayers = num;
    }

    /**
     * Players getter
     *
     * @return the list (arraylist) of players
     * @author Fabio Gallo
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Player getter by username
     *
     * @param username player username
     * @return player with such username
     * @throws NoPlayerWithSuchUsernameException if there isn't a player witch such username
     * @author Foini Lorenzo
     */
    public Player getPlayerByUsername(String username) throws NoPlayerWithSuchUsernameException {
        for(Player player : players) {
            if(player.getUsername().equals(username)) {
                return player;
            }
        }
        throw new NoPlayerWithSuchUsernameException("There isn't a player in the game with such username.");
    }

    /**
     * Players setter
     *
     * @param players updated players list
     * @author Fabio Gallo
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Adds a player to the game
     *
     * @param player to be added
     * @author Fabio Gallo
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Scoreboard getter
     *
     * @return Scoreboard
     * @author Fabio Gallo
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    /**
     * Scoreboard setter
     *
     * @param scoreboard updated scoreboard
     * @author Fabio Gallo
     */
    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    /**
     * Checks if a player got to at least 20 points and eventually starts the end of the game
     *
     * @return true or false
     * @author Fabio Gallo
     */
    public boolean isEnded() {
        for (Player player : players) {
            if (player.getScore() >= 1)
                return true;
        }
        return resourceDeck.deckSize()==0 && goldDeck.deckSize()==0 && visibleCards.isEmpty();
    }

    /**
     * Method for the players to draw a resource card from the resource deck
     *
     * @return the drawn card
     * @throws EmptyDeckException if the resource deck is empty
     * @author Lorenzo Foini
     */
    public GamingCard drawResourceCardDeck() throws EmptyDeckException {
        try {
            return (GamingCard) resourceDeck.drawTopCard();
        } catch (EmptyDeckException e) {
            throw e;
        }
    }

    /**
     * Method for the players to draw a golden card from the golden deck
     *
     * @return the drawn card
     * @throws EmptyDeckException if the golden deck is empty
     * @author Lorenzo Foini
     */
    public GoldCard drawGoldCardDeck() throws EmptyDeckException {
        try {
            return (GoldCard) goldDeck.drawTopCard();
        } catch (EmptyDeckException e) {
            throw e;
        }
    }

    /**
     * Method for the players to draw a card from the face up (visible) cards (for both the golden and resource cards)
     *
     * @param position is the position where the card to be drawn is places
     * @return the drawn card
     * @throws InvalidDrawFromTableException if the players tries to draw a card out of the visibleCards range
     * @author Lorenzo Foini
     */
    public GamingCard drawCardFromTable(int position) throws InvalidDrawFromTableException {
        if (position < 0 || position > visibleCards.size() - 1) {
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
                    visibleCards.set(position, topCardResource);
                } catch (EmptyDeckException e) {
                    try { // Case 2
                        GamingCard topCardGold = (GamingCard) resourceDeck.drawTopCard();
                        visibleCards.set(position, topCardGold);
                    } catch (EmptyDeckException ex) { // Case 3
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
                    visibleCards.set(position, topCardResource);
                } catch (EmptyDeckException e) {
                    try { // Case 2
                        GoldCard topCardGold = (GoldCard) goldDeck.drawTopCard();
                        visibleCards.set(position, topCardGold);
                    } catch (EmptyDeckException ex) { // Case 3
                        // Shift to the left
                        for (int i = position; i < visibleCards.size() - 1; i++) {
                            visibleCards.set(i, visibleCards.get(i + 1));
                        }
                        //Remove last element
                        visibleCards.removeLast();
                    }
                }
            }
            return selectedCard;
        }
    }
}