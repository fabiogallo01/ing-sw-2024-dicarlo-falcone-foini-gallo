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
    public Card(boolean side) {
        this.side = side;
        // Initialize the corner arrays with Corner objects
        for (int i = 0; i < frontCorners.length; i++) {
            frontCorners[i] = new Corner();
        }
        for (int i = 0; i < backCorners.length; i++) {
            backCorners[i] = new Corner();
        }
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
