package it.polimi.ingsw.model.cards;

/**
 * Class representing game's objective cards
 *
 * @author Di Carlo Andrea, Foini Lorenzo, Gallo Fabio
 */
public class ObjectiveCard {
    private final int points;
    private final GameObject[] objects; // all NONE if the objective is not related to objects
    private final Pattern pattern; // NONE if the objective is not related to pattern
    private final Kingdom frontKingdom; // NONE if the objects is not related to kingdom
    private final int ID;

    /**
     * Objective card constructor, it assigns all the parameters
     *
     * @param points for objective points
     * @param objects array of card's object (Can be NONE)
     * @param pattern for objective with pattern (Can be NONE)
     * @param frontKingdom representing card kingdom (Can be NONE)
     * @author Foini Lorenzo, Gallo Fabio
     */
    public ObjectiveCard(int points, GameObject[] objects, Pattern pattern, Kingdom frontKingdom, int ID) {
        this.points = points;
        this.objects = objects;
        this.pattern = pattern;
        this.frontKingdom = frontKingdom;
        this.ID = ID;
    }

    /**
     * points getter
     *
     * @return number of points assign by this card (multiply by the number of times of satisfied condition)
     * @author Di Carlo Andrea
     */
    public int getPoints() {
        return points;
    }

    /**
     * objects getter
     *
     * @return array of objects
     * @author Foini Lorenzo
     */
    public GameObject[] getObjects() {
        return objects;
    }

    /**
     * pattern getter
     *
     * @return type of pattern
     * @author Di Carlo Andrea
     */
    public Pattern getPattern() {
        return pattern;
    }

    /**
     * frontKingdom getter
     *
     * @return card's kingdom
     * @author Foini Lorenzo
     */
    public Kingdom getFrontKingdom() {
        return frontKingdom;
    }

    /**
     * ID getter
     *
     * @return card's ID
     * @author Gallo Fabio
     */
    public int getID() {
        return ID;
    }
}