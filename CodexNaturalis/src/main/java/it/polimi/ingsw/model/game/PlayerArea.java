package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;
import java.util.*;

public class PlayerArea{
    // Attributes
    private boolean[][] area;
    private ArrayList<Card> cards;

    // Methods
    public PlayerArea(boolean[][] area, ArrayList<Card> cards) {
        this.area = area;
        this.cards = cards;
    }

    public boolean[][] getArea() {
        return area;
    }

    public void setArea(boolean[][] area) {
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
        // It is guaranteed that teh position is valid.
        area[positionArea[0]][positionArea[1]] = false; // false = cell has a card inside.

        // Add card in the list
        cards.add(card); // Polymorphism

        // Assign the relative position in the game to the card
        card.setInGamePosition(positionArea);
    }

    public int countKingdoms(Kingdom kingdom){
        // TODO
    }

    public int countObject(GameObject object){
        // TODO
    }

    public int countPattern(Pattern pattern){
        // TODO
    }

    public int countHiddenCorner(){

    }
}