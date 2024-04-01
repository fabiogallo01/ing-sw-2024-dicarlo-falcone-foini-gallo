package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;
import java.util.*;

public class PlayerArea {
    // Attributes
    private HashMap<int[][], Boolean> area;
    private ArrayList<Card> cards;

    // Methods
    public PlayerArea(HashMap<int[][], Boolean> area, ArrayList<Card> cards) {
        this.area = area;
        this.cards = cards;
    }

    public HashMap<int[][], Boolean> getArea() {
        return area;
    }

    public void setArea(HashMap<int[][], Boolean> area) {
        this.area = area;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card, int[] positionArea){
        // "Add" card to the HashMap

        // Add card in the list
        cards.add(card); // Polymorphism
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
