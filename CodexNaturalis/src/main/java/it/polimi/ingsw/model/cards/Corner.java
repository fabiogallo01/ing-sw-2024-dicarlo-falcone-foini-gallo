package it.polimi.ingsw.model.cards;

/**
 * Class representing card's corner
 *
 * @author Andrea Di Carlo, Giacomo Falcone, Lorenzo Foini
 */
public class Corner {
    private boolean visible;
    private final boolean empty;
    private final GameObject object;
    private final Kingdom kingdom;

    /**
     * Corner constructor, it assigns all the parameters (back corners)
     *
     * @param visible for corner visibility. true => Corner is visible, false => Corner not visible
     * @param empty for corner emptiness. true => Corner empty, false => Corner not empty
     * @param object for corner's object (Can be NONE)
     * @param kingdom for corner's kingdom (Can be NONE)
     * @author Andrea Di Carlo
     */
    public Corner(boolean visible, boolean empty, GameObject object, Kingdom kingdom) {
        this.visible = visible;
        this.empty = empty;
        this.object = object;
        this.kingdom = kingdom;
    }

    /**
     * Visible getter
     *
     * @return boolean for visibility. true => Corner is visible, false => Corner not visible
     * @author Andrea Di Carlo
     */
    public boolean getVisible() {
        return visible;
    }

    /**
     * Visible setter
     *
     * @param visible boolean for visibility. true => Corner is visible, false => Corner not visible
     * @author Andrea Di Carlo
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Empty getter
     *
     * @return boolean for emptiness. true => Corner is empty, false => Corner not empty
     * @author Andrea Di Carlo
     */
    public boolean getEmpty() {
        return empty;
    }

    /**
     * Object getter
     *
     * @return corner's object
     * @author Lorenzo Foini
     */
    public GameObject getObject() {
        return object;
    }

    /**
     * Object getter
     *
     * @return corner's kingdom
     * @author Andrea Di Carlo
     */
    public Kingdom getKingdom() {
        return kingdom;
    }
}