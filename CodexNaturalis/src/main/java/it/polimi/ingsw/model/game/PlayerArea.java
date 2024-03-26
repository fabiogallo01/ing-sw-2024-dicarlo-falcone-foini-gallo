package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;
import java.util.HashMap;

public class PlayerArea {
    // Attributes
    private HashMap<int[][], Boolean> area;
    private Card[] cards;

    // Methods
    public PlayerArea(HashMap<int[][], Boolean> area, Card[] cards) {
        this.area = area;
        this.cards = cards;
    }

    public HashMap<int[][], Boolean> getArea() {
        return area;
    }

    public void setArea(HashMap<int[][], Boolean> area) {
        this.area = area;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public int countKingdoms(Kingdom kingdom){
        // TODO
    }

    public int countObject(Object object){
        // TODO
    }

    public int countPattern(Pattern pattern){
        // TODO
    }
}
