package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import java.util.*;

public class GameTable {

    private GamingDeck resourceDeck;
    private GamingDeck goldDeck;
    private GamingDeck starterDeck;
    private ObjectiveDeck objectiveDeck;
    private ArrayList<GamingCard> visibleCard;
    private static ObjectiveCard[] commonObjectives;
    private final int numPlayers;
    private ArrayList<Player> players;
    private Scoreboard scoreboard;

    public GameTable(int numPlayers) {
        this.numPlayers = numPlayers;
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
        Corner[] frontCorners = new Corner[4];
        Corner[] backCorners = new Corner[4];


        Corner frontCorner0 = new Corner(true, true, GameObject.NONE, Kingdom.NONE);
        Corner frontCorner1 = new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM);
        Corner frontCorner2 = new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM);
        Corner frontCorner3 = new Corner(true, true, GameObject.NONE, Kingdom.NONE);

        frontCorners[0]=frontCorner0;
        frontCorners[1]=frontCorner1;
        frontCorners[2]=frontCorner2;
        frontCorners[3]=frontCorner3;

        Corner backCorner0 = new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM);
        Corner backCorner1 = new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM);
        Corner backCorner2 = new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM);
        Corner backCorner3 = new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM);

        backCorners[0]=backCorner0;
        backCorners[1]=backCorner1;
        backCorners[2]=backCorner2;
        backCorners[3]=backCorner3;

        Kingdom[] kingdoms = new Kingdom[3];
        kingdoms[0] = Kingdom.INSECTKINGDOM;
        kingdoms[1] = Kingdom.NONE;
        kingdoms[2] = Kingdom.NONE;

        starterCards.add( new StarterCard(false, frontCorners, backCorners, kingdoms));


        frontCorner0 = new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM);
        frontCorner1 = new Corner(true, true, GameObject.NONE, Kingdom.NONE);
        frontCorner2 = new Corner(true, true, GameObject.NONE, Kingdom.NONE);
        frontCorner3 = new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM);

        frontCorners[0]=frontCorner0;
        frontCorners[1]=frontCorner1;
        frontCorners[2]=frontCorner2;
        frontCorners[3]=frontCorner3;

        backCorner0 = new Corner(true, false, GameObject.NONE, Kingdom.PLANTKINGDOM);
        backCorner1 = new Corner(true, false, GameObject.NONE, Kingdom.ANIMALKINGDOM);
        backCorner2 = new Corner(true, false, GameObject.NONE, Kingdom.FUNGIKINGDOM);
        backCorner3 = new Corner(true, false, GameObject.NONE, Kingdom.INSECTKINGDOM);

        backCorners[0]=backCorner0;
        backCorners[1]=backCorner1;
        backCorners[2]=backCorner2;
        backCorners[3]=backCorner3;

        kingdoms = new Kingdom[3];
        kingdoms[0] = Kingdom.FUNGIKINGDOM;
        kingdoms[1] = Kingdom.NONE;
        kingdoms[2] = Kingdom.NONE;

        starterCards.add( new StarterCard(false, frontCorners, backCorners, kingdoms));


        /*starterCards.add( new StarterCard());
        starterCards.add( new StarterCard());
        starterCards.add( new StarterCard());
        starterCards.add( new StarterCard());*/
        this.starterDeck = new GamingDeck(starterCards);
    }

    public ObjectiveDeck getObjectiveDeck() {
        return objectiveDeck;
    }

    public void setObjectiveDeck(ObjectiveDeck objectiveDeck) {
        this.objectiveDeck = objectiveDeck;
    }

    private void createObjectiveCards(){
        ArrayList<ObjectiveCard> objectiveCards = new ArrayList<>();
        /*objectiveCards.add(new ObjectiveCard());
        objectiveCards.add(new ObjectiveCard());
        objectiveCards.add(new ObjectiveCard());
        objectiveCards.add(new ObjectiveCard());
        objectiveCards.add(new ObjectiveCard());
        objectiveCards.add(new ObjectiveCard());
        objectiveCards.add(new ObjectiveCard());
        objectiveCards.add(new ObjectiveCard());
        objectiveCards.add(new ObjectiveCard());
        objectiveCards.add(new ObjectiveCard());
        objectiveCards.add(new ObjectiveCard());
        objectiveCards.add(new ObjectiveCard());
        objectiveCards.add(new ObjectiveCard());
        objectiveCards.add(new ObjectiveCard());
        objectiveCards.add(new ObjectiveCard());
        objectiveCards.add(new ObjectiveCard());*/
        this.objectiveDeck = new ObjectiveDeck(objectiveCards);
    }

    public ArrayList<GamingCard> getVisibleCard() {
        return visibleCard;
    }

    public void setVisibleCard(ArrayList<GamingCard> visibleCard) {
        this.visibleCard = visibleCard;
    }

    public void addVisibleCard(GamingCard gamingCard){

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

    public void addPlayer(Player player){}

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public void setScoreboard(Scoreboard scoreboard) {
        this.scoreboard = scoreboard;
    }

    public void playTurn(Player player){}

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
        if (position < 1 || position > visibleCard.size()){
            throw new InvalidDrawFromTableException("Invalid draw from table. Select one of the cards.");
        }else{
            GamingCard selectedCard = visibleCard.get(position);

            /*
                Replace the selected card with a new one
                Three cases:
                1 - The corresponding deck is not empty => Draw from that one.
                2 - The corresponding deck is empty => Draw from the other one.
                3 - Both decks are empty => Do not add any card.
            */
            // TODO

            return selectedCard;
        }
    }
}