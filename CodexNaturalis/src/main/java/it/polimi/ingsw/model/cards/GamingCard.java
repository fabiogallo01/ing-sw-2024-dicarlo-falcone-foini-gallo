package it.polimi.ingsw.model.cards;

/**
 * Class representing game's resource cards
 * It extends Card
 * It is extended by GoldCard
 *
 * @author Di Carlo Andrea, Foini Lorenzo, Gallo Fabio
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
     * @author Di Carlo Andrea, Foini Lorenzo, Gallo Fabio
     */
    public GamingCard(boolean side, Kingdom kingdom, int points, Corner[] frontCorners, int ID) {
        super(side, frontCorners, ID); // Using the constructor without backCorners
        this.kingdom = kingdom;
        this.points = points;
    }

    /**
     * kingdom getter
     *
     * @return card's main kingdom
     * @author Di Carlo Andrea
     */
    public Kingdom getKingdom() {
        return kingdom;
    }

    /**
     * points getter
     *
     * @return number of points given by the gold card when played on front
     * @author Di Carlo Andrea
     */
    public int getPoints() {
        return points;
    }
}