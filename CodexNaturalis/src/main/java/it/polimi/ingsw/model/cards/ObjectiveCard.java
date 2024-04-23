package it.polimi.ingsw.model.cards;

/**
 * Class representing game's objective cards
 *
 * @author Andrea Di Carlo, Lorenzo Foini
 */
public class ObjectiveCard {
    private final int points;
    private boolean isSecret;
    private final GameObject[] objects;
    private final Pattern pattern;
    private final Kingdom frontKingdom;

    /**
     * Objective card constructor, it assigns all the parameters
     *
     * @param points for objective points
     * @param isSecret true => secret objective, false => not secret objective
     * @param objects array of card's object (Can be None)
     * @param pattern for objective with pattern (Can be None)
     * @param frontKingdom representing card kingdom (Can be None)
     * @author Foini Lorenzo
     */
    public ObjectiveCard(int points, boolean isSecret, GameObject[] objects, Pattern pattern, Kingdom frontKingdom) {
        this.points = points;
        this.isSecret = isSecret;
        this.objects = objects;
        this.pattern = pattern;
        this.frontKingdom = frontKingdom;
    }

    /**
     * Points getter
     *
     * @return number of points of that card
     * @author Andrea Di Carlo
     */
    public int getPoints() {
        return points;
    }

    /**
     * Is secret getter
     *
     * @return true => secret objective, false => not secret objective
     * @author Lorenzo Foini
     */
    public boolean getIsSecret() {
        return isSecret;
    }

    /**
     * Is secret setter
     *
     * @param isSecret true => secret objective, false => not secret objective
     * @author Lorenzo Foini
     */
    public void setIsSecret(boolean isSecret){
        this.isSecret = isSecret;
    }

    /**
     * Objects getter
     *
     * @return array of objects
     * @author Lorenzo Foini
     */
    public GameObject[] getObjects() {
        return objects;
    }

    /**
     * Pattern getter
     *
     * @return type of pattern
     * @author Andrea Di Carlo
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * Front kingdom getter
     *
     * @return card's kingdom
     * @author Lorenzo Foini
     */
    public Kingdom getFrontKingdom() {
        return frontKingdom;
    }
}