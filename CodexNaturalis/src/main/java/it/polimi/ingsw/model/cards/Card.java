package it.polimi.ingsw.model.cards;

public abstract class Card {
    // Private attributes of class Card
    private boolean side;
    // Private attributes of class Card
    private Corner[] frontCorners = new Corner[4];
    private Corner[] backCorners = new Corner[4];
    private int[] inGamePosition;

    // Constructor of class Card
    public Card(boolean side, Corner[] frontCorners, Corner[] backCorners ) {
        this.side = side;
        this.frontCorners = frontCorners;
        this.backCorners = backCorners;
        this.inGamePosition = new int[2];
        this.inGamePosition[0] = -1;
        this.inGamePosition[1] = -1; // At the beginning the cards do not have a position.
    }

    public Card(boolean side, Corner[] frontCorners) {
        this(side, frontCorners,new Corner[]{
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE),
                new Corner(true, false, GameObject.NONE, Kingdom.NONE)
        });
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

    public void setVisibleCorner(int position)
    {
        if (getSide())
        { getFrontCorners()[position].setVisible(false); }
        else
        { getBackCorners()[position].setVisible(false); }
    }
}