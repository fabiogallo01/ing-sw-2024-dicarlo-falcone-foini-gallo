package it.polimi.ingsw.model.cards;

public abstract class Card {
    // Private attributes of class Card
    private boolean side;
    // Private attributes of class Card
    private Corner[] frontCorners = new Corner[4];
    private Corner[] backCorners = new Corner[4];

    // Getter of side
    public boolean getSide() {
        return side;
    }

    // Constructor of class Card
    public Card(boolean side, Corner[] frontCorners, Corner[] backCorners ) {
        this.side = side;
        this.frontCorners = frontCorners;
        this.backCorners = backCorners;
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
}
