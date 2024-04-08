package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import java.util.*;

public class GameTable {

    private GamingDeck resourceDeck;
    private GamingDeck goldDeck;
    private GamingDeck starterDeck;
    private ObjectiveDeck objectiveDeck;
    private ArrayList<GamingCard> visibleCards;
    private static ObjectiveCard[] commonObjectives;
    private final int numPlayers;
    private ArrayList<Player> players;
    private Scoreboard scoreboard;

    public GameTable(int numPlayers) {
        this.numPlayers = numPlayers;
        createResourceDeck();
        createGoldDeck();
        createStarterDeck();
        createObjectiveDeck();
        resourceDeck.shuffleDeck();
        goldDeck.shuffleDeck();
        starterDeck.shuffleDeck();
        objectiveDeck.shuffleDeck();

    }

    public GamingDeck getResourceDeck() {
        return resourceDeck;
    }

    public void setResourceDeck(GamingDeck resourceDeck) {
        this.resourceDeck = resourceDeck;
    }

    private void createResourceDeck() {
        ArrayList<Card> resourceCards = new ArrayList<>();
        /*resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());
        resourceCards.add( new GamingCard());*/
        this.resourceDeck = new GamingDeck(resourceCards);
    }

    public GamingDeck getGoldDeck() {
        return goldDeck;
    }

    public void setGoldDeck(GamingDeck goldDeck) {
        this.goldDeck = goldDeck;
    }
    
    private void createGoldDeck() {
        ArrayList<Card> goldCards = new ArrayList<>();
        /*goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());
        goldCards.add( new GoldCard());*/
        this.goldDeck = new GamingDeck(goldCards);
    }

    public GamingDeck getStarterDeck() {
        return starterDeck;
    }

    public void setStarterDeck(GamingDeck starterDeck) {
        this.starterDeck = starterDeck;
    }
    
    private void createStarterDeck() {
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

        backCorners = new Corner[] {
            new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
            new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
            new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
            new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };

        kingdoms = new Kingdom[]{ Kingdom.INSECTKINGDOM, Kingdom.NONE, Kingdom.NONE};

        starterCards.add( new StarterCard(false, frontCorners, backCorners, kingdoms));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };

        backCorners = new Corner[] {
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };

        kingdoms = new Kingdom[]{ Kingdom.FUNGIKINGDOM, Kingdom.NONE, Kingdom.NONE};

        starterCards.add( new StarterCard(false, frontCorners, backCorners, kingdoms));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        backCorners = new Corner[] {
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM)
        };

        kingdoms = new Kingdom[]{ Kingdom.PLANTKINGDOM, Kingdom.FUNGIKINGDOM, Kingdom.NONE};

        starterCards.add( new StarterCard(false, frontCorners, backCorners, kingdoms));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        };

        backCorners = new Corner[] {
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM)
        };

        kingdoms = new Kingdom[]{ Kingdom.ANIMALKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.NONE};

        starterCards.add( new StarterCard(false, frontCorners, backCorners, kingdoms));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        backCorners = new Corner[] {
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM)
        };

        kingdoms = new Kingdom[]{ Kingdom.ANIMALKINGDOM, Kingdom.INSECTKINGDOM, Kingdom.PLANTKINGDOM};

        starterCards.add( new StarterCard(false, frontCorners, backCorners, kingdoms));


        frontCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE),
                new Corner(true, true, GameObject.NONE, Kingdom.NONE)
        };

        backCorners = new Corner[] {
                new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM),
                new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM)
        };

        kingdoms = new Kingdom[]{ Kingdom.PLANTKINGDOM, Kingdom.ANIMALKINGDOM, Kingdom.FUNGIKINGDOM};

        starterCards.add( new StarterCard(false, frontCorners, backCorners, kingdoms));


        this.starterDeck = new GamingDeck(starterCards);
    }

    public ObjectiveDeck getObjectiveDeck() {
        return objectiveDeck;
    }

    public void setObjectiveDeck(ObjectiveDeck objectiveDeck) {
        this.objectiveDeck = objectiveDeck;
    }

    private void createObjectiveDeck(){
        ArrayList<ObjectiveCard> objectiveCards = new ArrayList<>();

        GameObject[] objects;

        objects = new GameObject[]{GameObject.NONE};
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.SECONDARYDIAGONAL, Kingdom.FUNGIKINGDOM));

        objects = new GameObject[]{GameObject.NONE};
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.PRIMARYDIAGONAL, Kingdom.PLANTKINGDOM));

        objects = new GameObject[]{GameObject.NONE};
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.SECONDARYDIAGONAL, Kingdom.ANIMALKINGDOM));

        objects = new GameObject[]{GameObject.NONE};
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.PRIMARYDIAGONAL, Kingdom.INSECTKINGDOM));

        // in the next 4 cases, given the pattern, we put as the kingdom the one which appears in 2 out of 3 cards of the pattern
        objects = new GameObject[]{GameObject.NONE};
        objectiveCards.add(new ObjectiveCard(3, true, objects, Pattern.LOWERRIGHT, Kingdom.FUNGIKINGDOM));

        objects = new GameObject[]{GameObject.NONE};
        objectiveCards.add(new ObjectiveCard(3, true, objects, Pattern.LOWERLEFT, Kingdom.PLANTKINGDOM));

        objects = new GameObject[]{GameObject.NONE};
        objectiveCards.add(new ObjectiveCard(3, true, objects, Pattern.UPPERRIGHT, Kingdom.ANIMALKINGDOM));

        objects = new GameObject[]{GameObject.NONE};
        objectiveCards.add(new ObjectiveCard(3, true, objects, Pattern.UPPERLEFT, Kingdom.INSECTKINGDOM));

        objects = new GameObject[]{GameObject.NONE};
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.FUNGIKINGDOM));

        objects = new GameObject[]{GameObject.NONE};
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.PLANTKINGDOM));

        objects = new GameObject[]{GameObject.NONE};
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.ANIMALKINGDOM));

        objects = new GameObject[]{GameObject.NONE};
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.INSECTKINGDOM));

        objects = new GameObject[]{GameObject.QUILL, GameObject.INKWELL, GameObject.MANUSCRIPT};
        objectiveCards.add(new ObjectiveCard(3, true, objects, Pattern.NONE, Kingdom.NONE));

        objects = new GameObject[]{GameObject.MANUSCRIPT, GameObject.MANUSCRIPT};
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.NONE));

        objects = new GameObject[]{GameObject.INKWELL, GameObject.INKWELL};
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.NONE));

        objects = new GameObject[]{GameObject.QUILL, GameObject.QUILL};
        objectiveCards.add(new ObjectiveCard(2, true, objects, Pattern.NONE, Kingdom.NONE));

        this.objectiveDeck = new ObjectiveDeck(objectiveCards);
    }

    public ArrayList<GamingCard> getVisibleCard() {
        return visibleCards;
    }

    public void setVisibleCard(ArrayList<GamingCard> visibleCard) {
        this.visibleCards = visibleCard;
    }

    public void addVisibleCard(GamingCard gamingCard){
        visibleCards.add(gamingCard);
    }

    public static ObjectiveCard[] getCommonObjectives() {
        return commonObjectives;
    }

    public static void setCommonObjectives(ObjectiveCard[] commonObjectives) {
        GameTable.commonObjectives = commonObjectives;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public void playTurn(Player player){

    }

    public boolean isEnded(){
        return true;
    }

    public GamingCard drawResourceCardDeck() throws EmptyDeckException {
        try {
            return (GamingCard) resourceDeck.drawTopCard();
        } catch (EmptyDeckException e) {
            throw e;
        }
    }

    public GamingCard drawGoldCardDeck() throws EmptyDeckException {
        try {
            return (GamingCard) goldDeck.drawTopCard();
        } catch (EmptyDeckException e) {
            throw e;
        }
    }

    public GamingCard drawCardFromTable(int position) throws InvalidDrawFromTableException{
        if (position < 0 || position > visibleCards.size()-1){
            throw new InvalidDrawFromTableException("Invalid draw from table. Select one of the cards or draw from a deck.");
        }else{
            GamingCard selectedCard = visibleCards.get(position);
            /*
                Replace the selected card with a new one
                Three cases:
                1 - The corresponding deck is not empty => Draw from that one.
                2 - The corresponding deck is empty => Draw from the other one.
                3 - Both decks are empty => Do not add any card.
            */

            try{ // Case 1
                GamingCard topCardResource = (GamingCard) resourceDeck.drawTopCard();
                visibleCards.set(position, topCardResource);
            }
            catch (EmptyDeckException e) {
                try{ // Case 2
                    GoldCard topCardGold = (GoldCard) goldDeck.drawTopCard();
                    visibleCards.set(position, topCardGold);
                } catch(EmptyDeckException ex){ // Case 3
                    // Shift to the left
                    for(int i = position; i < visibleCards.size()-1; i++){
                        visibleCards.set(i, visibleCards.get(i+1));
                    }
                    //Remove last element
                    visibleCards.removeLast();
                }
            }
            return selectedCard;
        }
    }
}