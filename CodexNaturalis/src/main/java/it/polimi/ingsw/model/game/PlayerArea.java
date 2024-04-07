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
        // It is guaranteed that the position is valid.
        area[positionArea[0]][positionArea[1]] = false; // false = cell has a card inside.

        // Add card in the list
        cards.add(card); // Polymorphism

        // Assign the relative position in the game to the card
        card.setInGamePosition(positionArea);

        // Modify the status of the corners which are covered from this card
        // It is guaranteed that the added card doesn't cover a corner which can't be covered
        for(Card cornerCard : cards) {
            int[] positionCornerCard = cornerCard.getInGamePosition();
            boolean side = cornerCard.getSide();
            if (positionCornerCard[0] == positionArea[0] - 1 && positionCornerCard[1] == positionArea[1] - 1) { // Top left card
                cornerCard.setVisibleCorner(side, 3); // Bottom right corner
            }
            else if (positionCornerCard[0] == positionArea[0] - 1 && positionCornerCard[1] == positionArea[1] + 1) { // Top right card
                cornerCard.setVisibleCorner(side, 2); // Bottom left corner
            }
            else if (positionCornerCard[0] == positionArea[0] + 1 && positionCornerCard[1] == positionArea[1] - 1) { // Bottom left card
                cornerCard.setVisibleCorner(side, 1); // Top right corner
            }
            else if (positionCornerCard[0] == positionArea[0] + 1 && positionCornerCard[1] == positionArea[1] + 1) { // Bottom right card
                cornerCard.setVisibleCorner(side, 0); // Top left corner
            }
        }
    }

    public int countKingdoms(Kingdom kingdom){
        // Count the total number of visible parameter kingdom
        int count = 0;
        for(Card card : cards){
            // Get side of the card: true -> front, false -> back
            boolean side = card.getSide();
            if(side){
                for(Corner corner : card.getFrontCorners()){
                    if(corner.getVisible() && corner.getKingdom() == kingdom){
                        count++;
                    }
                }
            }
            else {
                for (Corner corner : card.getBackCorners()) {
                    if (corner.getVisible() && corner.getKingdom() == kingdom) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public int countObject(GameObject object){
        // Count the total number of visible parameter object
        int count = 0;
        for(Card card : cards){
            // Get side of the card: true -> front, false -> back
            boolean side = card.getSide();
            if(side){
                for(Corner corner : card.getFrontCorners()){
                    if(corner.getVisible() && corner.getObject() == object){
                        count++;
                    }
                }
            }
            else {
                for (Corner corner : card.getBackCorners()) {
                    if (corner.getVisible() && corner.getObject() == object) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public int countPattern(Kingdom kingdom, Pattern pattern){
        // TODO
    }

    public int countHiddenCorner(){
        // Count the total number of hidden corners
        int count = 0;
        for(Card card : cards){
            // Get side of the card: true -> front, false -> back
            boolean side = card.getSide();
            if(side){
                for(Corner corner : card.getFrontCorners()){
                    if(!corner.getVisible()){
                        count++;
                    }
                }
            }
            else {
                for (Corner corner : card.getBackCorners()) {
                    if (!corner.getVisible()) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}