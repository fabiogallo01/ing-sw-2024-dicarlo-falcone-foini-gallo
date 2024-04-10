package it.polimi.ingsw.model.cards;

public abstract class Card {
    // Private attributes of class Card
    private boolean side;
    // Private attributes of class Card
    private Corner[] frontCorners;
    private Corner[] backCorners;
    private int[] inGamePosition;
    private boolean counted;

    private Kingdom[] backKingdomsCentre;//backKingdoms in Centre not corner backKingdoms

    private Kingdom backKingdomCentre

    // Constructor of class Card
    public Card(boolean side, Corner[] frontCorners, Corner[] backCorners, Kingdom[] backKingdomsCentre) {
        this.side = side;
        this.frontCorners = frontCorners;
        this.backCorners = backCorners;
        this.inGamePosition = new int[2];
        this.inGamePosition[0] = -1;
        this.inGamePosition[1] = -1; // At the beginning the cards do not have a position
        this.counted = false; // Parameter used for counting pattern for objective cards
        this.backKingdomsCentre = backKingdomsCentre;
    }

    //For GoldCard and Resource Card
    public Card(boolean side, Corner[] frontCorners, Kingdom backKingdomCentre) {
        this.side = side;
        this.frontCorners = frontCorners;
        this.backCorners = new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
            };
        this.inGamePosition = new int[2];
        this.inGamePosition[0] = -1;
        this.inGamePosition[1] = -1; // At the beginning the cards do not have a position
        this.counted = false; // Parameter used for counting pattern for objective cards
        this.backKingdomCentre = backKingdomCentre;
    }

    public boolean getSide(){
        return side;
    }

    public void setSide(boolean side){
        this.side = side;
    }
    public Corner[] getBackCorners() {
        return backCorners;
    }

    public Corner[] getFrontCorners() {
        return frontCorners;
    }

    public void setBackCorners(Corner[] backCorners) {
        this.backCorners = backCorners;
    }

    public void setFrontCorners(Corner[] frontCorners) {
        this.frontCorners = frontCorners;
    }

    public int[] getInGamePosition(){
        return inGamePosition;
    }

    public void setInGamePosition(int[] inGamePosition){
        this.inGamePosition = inGamePosition;
    }

    public boolean getCounted(){
        return counted;
    }

    public void setCounted(boolean counted){
        this.counted = counted;
    }

    public void setVisibleCorner(int position)
    {
        if (side)
        { getFrontCorners()[position].setVisible(false); }
        else
        { getBackCorners()[position].setVisible(false); }
    }

    public Kingdom getBackKingdomCentre() {
        return backKingdomCentre;
    }

    public Kingdom[] getBackKingdomsCentre() {
        return backKingdomsCentre;
    }
}