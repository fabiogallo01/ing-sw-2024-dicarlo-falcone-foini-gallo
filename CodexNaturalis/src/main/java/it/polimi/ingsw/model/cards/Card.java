package it.polimi.ingsw.model.cards;

public abstract class Card {
    // Private attributes of class Card
    private boolean side;
    // Private attributes of class Card
    private Corner[] frontCorners = new Corner[4];
    private Corner[] backCorners = new Corner[4];

    // Getter of side
    public boolean getSide() {
        return this.side;
    }

    /* Constructor of class Card
    public Card(boolean side) {
        this.side = side;
        // Initialize the corner arrays with Corner objects
        for (int i = 0; i < frontCorners.length; i++) {
            frontCorners[i] = new Corner();
            backCorners[i] = new Corner();
        }
    }*/


}
