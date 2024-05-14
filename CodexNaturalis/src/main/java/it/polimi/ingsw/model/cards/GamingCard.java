package it.polimi.ingsw.model.cards;

/**
 * Abstract class representing game's resource cards
 * It is extended by GoldCard
 *
 * @author Andrea Di Carlo, Lorenzo Foini
 */
public class GamingCard extends Card{
    protected final Kingdom kingdom;
    protected final int points;

    /**
     * GamingCard constructor, it assigns all the parameters
     * Used by GoldCard
     *
     * @param side for card side. true => Front, false => Back
     * @param kingdom for card's kingdom
     * @param points for card's point
     * @param frontCorners for card's front corners
     * @author Andrea Di Carlo, Foini Lorenzo
     */
    public GamingCard(boolean side, Kingdom kingdom, int points, Corner[] frontCorners, int ID) {
        super(side, frontCorners, ID); // Using the constructor without backCorners
        this.kingdom = kingdom;
        this.points = points;
    }

    /**
     * Kingdom getter
     *
     * @return kingdom
     * @author Andrea Di Carlo
     */
    public Kingdom getKingdom() {
        return kingdom;
    }

    /**
     * Points getter
     *
     * @return number of points given by the card
     * @author Andrea Di Carlo
     */
    public int getPoints() {
        return points;
    }
}