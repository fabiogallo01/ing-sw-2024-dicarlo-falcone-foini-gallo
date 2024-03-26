package it.polimi.ingsw.model.cards;

public class Corner {
    private boolean visible;
    private boolean empty;
    private Object object;
    private Kingdom kingdom;

    public Corner(boolean visible, boolean empty, Object object, Kingdom kingdom) {
        this.visible = visible;
        this.empty = empty;
        this.object = object;
        this.kingdom = kingdom;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean getEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }
}

