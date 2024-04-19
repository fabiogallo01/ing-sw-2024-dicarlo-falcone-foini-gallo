package it.polimi.ingsw.model.cards;

public abstract class Card {
    // Private attributes of class Card
    protected boolean side;
    // Private attributes of class Card
    protected Corner[] frontCorners;
    protected Corner[] backCorners;
    protected int[] inGamePosition;
    protected boolean counted;

    // Constructor for ResourceCard
    // It takes the back corners as parameter
    public Card(boolean side, Corner[] frontCorners, Corner[] backCorners) {
        this.side = side;
        this.frontCorners = frontCorners;
        this.backCorners = backCorners;
        this.inGamePosition = new int[2];
        this.inGamePosition[0] = -1;
        this.inGamePosition[1] = -1; // At the beginning the cards do not have a position
        this.counted = false; // Parameter used for counting pattern for objective cards
    }

    // Constructor for GamingCard and GoldCard
    // It doesn't take the back corners as parameter, they are defined in the constructor
    public Card(boolean side, Corner[] frontCorners) {
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

    public void setVisibleCornerFalse(int position)
    {
        if (side)
        { getFrontCorners()[position].setVisible(false); }
        else
        { getBackCorners()[position].setVisible(false); }
    }
}