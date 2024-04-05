package it.polimi.ingsw.model.cards;

public class ObjectiveCard {
    private final int points;
    private boolean isSecret;
    private final GameObject[] objects;
    private final Pattern pattern;
    private final Kingdom frontKingdom;

    public ObjectiveCard(int points, boolean isSecret, GameObject[] objects, Pattern pattern, Kingdom frontKingdom) {
        this.points = points;
        this.isSecret = isSecret;
        this.objects = objects;
        this.pattern = pattern;
        this.frontKingdom = frontKingdom;
    }

    public int getPoints() {
        return points;
    }

    public boolean getIsSecret() {
        return isSecret;
    }

    public void setIsSecret(boolean isSecret){
        this.isSecret = isSecret;
    }

    public GameObject[] getObjects() {
        return objects;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Kingdom getFrontKingdom() {
        return frontKingdom;
    }
}