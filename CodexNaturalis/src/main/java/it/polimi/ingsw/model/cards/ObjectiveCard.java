package it.polimi.ingsw.model.cards;

public class ObjectiveCard {
    private int points;
    private boolean isSecret;
    private GameObject[] object;
    private Pattern pattern;
    private Kingdom frontKingdom;

    public ObjectiveCard(boolean side, int p, boolean secret, int[] numObject, Pattern pattern) {
        this.points = p;
        this.
    }

    public int getPoints() {
        return points;
    }

    public boolean getHidden() {
        return isSecret;
    }

    public GameObject[] getObject() {
        return object;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Kingdom getFrontKingdom() {
        return frontKingdom;
    }

    public int calculatePoints() {
        return 0;
        // TO DO
    }
}

