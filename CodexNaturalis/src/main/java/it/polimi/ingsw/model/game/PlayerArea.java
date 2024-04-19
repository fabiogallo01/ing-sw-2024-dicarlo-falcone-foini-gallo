package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;
import java.util.*;

public class PlayerArea{
    // Attributes
    private boolean[][] area; // true => cell is empty. false => there is a card in the cell
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
        // "Add" card to the area
        // It is guaranteed that the position is valid.
        area[positionArea[0]][positionArea[1]] = false; // false = cell has a card inside.

        // Add card in the list
        cards.add(card); // Polymorphism

        // Assign the relative position in the game to the card
        card.setInGamePosition(positionArea);

        // Modify the status of the corners which are covered from this card
        // It is guaranteed that the added card doesn't cover a corner which can't be covered or a corner which is already covered
        for(Card cornerCard : cards) {
            int[] positionCornerCard = cornerCard.getInGamePosition();
            if (positionCornerCard[0] == positionArea[0] - 1 && positionCornerCard[1] == positionArea[1] - 1) { // Top left card
                cornerCard.setVisibleCornerFalse(3); // Bottom right corner
            }
            else if (positionCornerCard[0] == positionArea[0] - 1 && positionCornerCard[1] == positionArea[1] + 1) { // Top right card
                cornerCard.setVisibleCornerFalse(2); // Bottom left corner
            }
            else if (positionCornerCard[0] == positionArea[0] + 1 && positionCornerCard[1] == positionArea[1] - 1) { // Bottom left card
                cornerCard.setVisibleCornerFalse(1); // Top right corner
            }
            else if (positionCornerCard[0] == positionArea[0] + 1 && positionCornerCard[1] == positionArea[1] + 1) { // Bottom right card
                cornerCard.setVisibleCornerFalse(0); // Top left corner
            }
        }
    }

    public int countKingdoms(Kingdom kingdom){
        // Count the total number of visible parameter kingdom
        int count = 0;
        for(Card card : cards) {
            // Get side of the card: true -> front, false -> back
            if (card.getSide()) {
                for (Corner corner : card.getFrontCorners()) {
                    if (corner.getVisible() && corner.getKingdom() == kingdom) {
                        count++;
                    }
                }

                // Count kingdoms at the centre of the starter card (it is played on the front side)
                if(card instanceof StarterCard) {
                    Kingdom[] frontKingdoms = ((StarterCard) card).getFrontKingdoms();
                    for(Kingdom frontKingdom : frontKingdoms){
                        if(frontKingdom == kingdom){
                            count++;
                        }
                    }
                }
            } else {
                // Count kingdoms in the corners
                for (Corner corner : card.getBackCorners()) {
                    if (corner.getVisible() && corner.getKingdom() == kingdom) {
                        count++;
                    }
                }
                // Count kingdom in the centre of the card (it is played on the back side)
                if (card instanceof GamingCard gameCard && gameCard.getKingdom() == kingdom) {
                    count++;
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

    public int countHiddenCorner(int[] positionArea){
        // Count the total number of hidden corners
        int count = 0;
        // Check if there are cards in the corners of the new card's position
        // If area in these positions is false => There is a card in that corner
        if(!area[positionArea[0]-1][positionArea[1]-1]){
            count++;
        }
        if(!area[positionArea[0]-1][positionArea[1]+1]){
            count++;
        }
        if(!area[positionArea[0]+1][positionArea[1]-1]){
            count++;
        }
        if(!area[positionArea[0]+1][positionArea[1]+1]){
            count++;
        }

        return count;
    }

    private boolean helperCountPattern(int[] secondCardPosition, int[] thirdCardPosition, Kingdom secondKingdom, Kingdom thirdKingdom){
        for(Card cardTwo:cards){
            if(!cardTwo.getCounted() && cardTwo.getInGamePosition() == secondCardPosition) {
                GamingCard secondCard = (GamingCard) cardTwo;
                // Check kingdom
                if (secondCard.getKingdom() == secondKingdom) {
                    // Get third card
                    for (Card cardThree : cards) {
                        if (!cardThree.getCounted() && cardThree.getInGamePosition() == thirdCardPosition) {
                            GamingCard thirdCard = (GamingCard) cardThree;
                            // Check kingdom
                            if (thirdCard.getKingdom() == thirdKingdom) {
                                // Set to true parameter counted
                                cardTwo.setCounted(true);
                                cardThree.setCounted(true);
                                return true;
                            }
                            return false;
                        }
                    }
                }
                return false;
            }
        }
        return false;
    }

    private int[] helperSetPosition(int row, int column){
        int[] position = new int[2];
        position[0] = row;
        position[1] = column;
        return position;
    }

    public int countPattern(Kingdom kingdom, Pattern pattern){
        int totalPattern = 0;

        // Switch case base on kingdom's value
        switch (kingdom){
            case ANIMALKINGDOM -> {
                if(pattern == Pattern.SECONDARYDIAGONAL){ // Case animal secondary diagonal pattern
                    for(Card cardOne : cards){
                        // Check indexes
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 41 && firstCardPosition[1] == 39) ||
                             (firstCardPosition[0] == 42 && firstCardPosition[1] == 38) ||
                             firstCardPosition[0] < 2 || firstCardPosition[1] > 78 )){

                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]-1, firstCardPosition[1]+1);

                            // Get position of the third card
                            int[] thirdCardPosition = helperSetPosition(firstCardPosition[0]-2, firstCardPosition[1]+2);

                            // Check kingdom
                            // Check if the card is not counted
                            // Check if there are two cards in the diagonal => area[x][y] is false
                            if(firstCard.getKingdom() == Kingdom.ANIMALKINGDOM &&
                               !firstCard.getCounted() &&
                               !area[secondCardPosition[0]][secondCardPosition[1]] &&
                               !area[thirdCardPosition[0]][thirdCardPosition[1]]){
                                if(helperCountPattern(secondCardPosition, thirdCardPosition, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM)){
                                    cardOne.setCounted(true);
                                    totalPattern++;
                                }
                            }
                        }
                    }
                }
                else{ // Case animal upper right pattern
                    for(Card cardOne : cards){
                        // Check indexes
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 42 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 43 && firstCardPosition[1] == 39) ||
                             firstCardPosition[0] < 3 || firstCardPosition[1] > 79 )){

                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]-2, firstCardPosition[1]);

                            // Get position of the third card
                            int[] thirdCardPosition = helperSetPosition(firstCardPosition[0]-3, firstCardPosition[1]+1);

                            // Check kingdom
                            // Check if the card is not counted
                            // Check if there are two cards in the diagonal
                            if(firstCard.getKingdom() == Kingdom.ANIMALKINGDOM &&
                               !firstCard.getCounted() &&
                               !area[secondCardPosition[0]][secondCardPosition[1]] &&
                               !area[thirdCardPosition[0]][thirdCardPosition[1]]){
                                if(helperCountPattern(secondCardPosition, thirdCardPosition, Kingdom.ANIMALKINGDOM, Kingdom.FUNGIKINGDOM)){
                                    cardOne.setCounted(true);
                                    totalPattern++;
                                }
                            }
                        }
                    }
                }
                break;
            }

            case PLANTKINGDOM -> {
                if(pattern == Pattern.PRIMARYDIAGONAL){ // Case plant primary diagonal pattern
                    for(Card cardOne : cards){
                        // Check indexes
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 39 && firstCardPosition[1] == 39) ||
                             (firstCardPosition[0] == 38 && firstCardPosition[1] == 38) ||
                             firstCardPosition[0] > 78 || firstCardPosition[1] > 78 )){

                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]+1, firstCardPosition[1]+1);

                            // Get position of the third card
                            int[] thirdCardPosition = helperSetPosition(firstCardPosition[0]+2, firstCardPosition[1]+2);

                            // Check kingdom
                            // Check if the card is not counted
                            // Check if there are two cards in the diagonal
                            if(firstCard.getKingdom() == Kingdom.PLANTKINGDOM &&
                               !firstCard.getCounted() &&
                               !area[secondCardPosition[0]][secondCardPosition[1]] &&
                               !area[thirdCardPosition[0]][thirdCardPosition[1]]){
                                if(helperCountPattern(secondCardPosition, thirdCardPosition, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM)){
                                    cardOne.setCounted(true);
                                    totalPattern++;
                                }
                            }
                        }
                    }
                }
                else{ // Case plant lower left pattern
                    for(Card cardOne : cards){
                        // Check indexes
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 38 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 37 && firstCardPosition[1] == 41) ||
                             firstCardPosition[0] > 77 || firstCardPosition[1] < 1 )){

                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]+2, firstCardPosition[1]);

                            // Get position of the third card
                            int[] thirdCardPosition = helperSetPosition(firstCardPosition[0]+3, firstCardPosition[1]-1);

                            // Check kingdom
                            // Check if the card is not counted
                            // Check if there are two cards in the diagonal
                            if(firstCard.getKingdom() == Kingdom.PLANTKINGDOM &&
                               !firstCard.getCounted() &&
                               !area[secondCardPosition[0]][secondCardPosition[1]] &&
                               !area[thirdCardPosition[0]][thirdCardPosition[1]]){
                                if(helperCountPattern(secondCardPosition, thirdCardPosition, Kingdom.PLANTKINGDOM, Kingdom.INSECTKINGDOM)){
                                    cardOne.setCounted(true);
                                    totalPattern++;
                                }
                            }
                        }
                    }
                }

                break;
            }

            case INSECTKINGDOM -> {
                if(pattern == Pattern.PRIMARYDIAGONAL){ // Case insect primary diagonal pattern
                    for(Card cardOne : cards){
                        // Check indexes
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 39 && firstCardPosition[1] == 39) ||
                             (firstCardPosition[0] == 38 && firstCardPosition[1] == 38) ||
                             firstCardPosition[0] > 78 || firstCardPosition[1] > 78 )){

                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]+1, firstCardPosition[1]+1);

                            // Get position of the third card
                            int[] thirdCardPosition = helperSetPosition(firstCardPosition[0]+2, firstCardPosition[1]+2);

                            // Check kingdom
                            // Check if the card is not counted
                            // Check if there are two cards in the diagonal
                            if(firstCard.getKingdom() == Kingdom.INSECTKINGDOM &&
                               !firstCard.getCounted() &&
                               !area[secondCardPosition[0]][secondCardPosition[1]] &&
                               !area[thirdCardPosition[0]][thirdCardPosition[1]]){
                                if(helperCountPattern(secondCardPosition, thirdCardPosition, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM)){
                                    cardOne.setCounted(true);
                                    totalPattern++;
                                }
                            }
                        }
                    }
                }
                else{ // Case insect upper left pattern
                    for(Card cardOne : cards){
                        // Check indexes
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 42 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 43 && firstCardPosition[1] == 41) ||
                             firstCardPosition[0] < 3 || firstCardPosition[1] < 1 )){

                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]-2, firstCardPosition[1]);

                            // Get position of the third card
                            int[] thirdCardPosition = helperSetPosition(firstCardPosition[0]-3, firstCardPosition[1]-1);


                            // Check kingdom
                            // Check if the card is not counted
                            // Check if there are two cards in the diagonal
                            if(firstCard.getKingdom() == Kingdom.INSECTKINGDOM &&
                               !firstCard.getCounted() &&
                               !area[secondCardPosition[0]][secondCardPosition[1]] &&
                               !area[thirdCardPosition[0]][thirdCardPosition[1]]){
                                if(helperCountPattern(secondCardPosition, thirdCardPosition, Kingdom.INSECTKINGDOM, Kingdom.ANIMALKINGDOM)){
                                    cardOne.setCounted(true);
                                    totalPattern++;
                                }
                            }
                        }
                    }
                }

                break;
            }

            case FUNGIKINGDOM -> {
                if(pattern == Pattern.SECONDARYDIAGONAL){ // Case fungi secondary diagonal pattern
                    for(Card cardOne : cards){
                        // Check indexes
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 41 && firstCardPosition[1] == 39) ||
                             (firstCardPosition[0] == 42 && firstCardPosition[1] == 38) ||
                             firstCardPosition[0] < 2 || firstCardPosition[1] > 78 )){

                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]-1, firstCardPosition[1]+1);

                            // Get position of the third card
                            int[] thirdCardPosition = helperSetPosition(firstCardPosition[0]-2, firstCardPosition[1]+2);

                            // Check kingdom
                            // Check if the card is not counted
                            // Check if there are two cards in the diagonal
                            if(firstCard.getKingdom() == Kingdom.FUNGIKINGDOM &&
                               !firstCard.getCounted() &&
                               !area[secondCardPosition[0]][secondCardPosition[1]] &&
                               !area[thirdCardPosition[0]][thirdCardPosition[1]]){
                                if(helperCountPattern(secondCardPosition, thirdCardPosition, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM)){
                                    cardOne.setCounted(true);
                                    totalPattern++;
                                }
                            }
                        }
                    }
                }
                else{ // Case fungi lower right pattern
                    for(Card cardOne : cards){
                        // Check indexes
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 38 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 37 && firstCardPosition[1] == 39) ||
                             firstCardPosition[0] > 77 || firstCardPosition[1] > 79 )){

                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]+2, firstCardPosition[1]);

                            // Get position of the third card
                            int[] thirdCardPosition = helperSetPosition(firstCardPosition[0]+3, firstCardPosition[1]+1);

                            // Check kingdom
                            // Check if the card is not counted
                            // Check if there are two cards in the diagonal
                            if(firstCard.getKingdom() == Kingdom.FUNGIKINGDOM &&
                               !firstCard.getCounted() &&
                               !area[secondCardPosition[0]][secondCardPosition[1]] &&
                               !area[thirdCardPosition[0]][thirdCardPosition[1]]){
                                if(helperCountPattern(secondCardPosition, thirdCardPosition, Kingdom.FUNGIKINGDOM, Kingdom.PLANTKINGDOM)){
                                    cardOne.setCounted(true);
                                    totalPattern++;
                                }
                            }
                        }
                    }
                }
                break;
            }
        }

        // Reset to false parameter counted
        for(Card card : cards){
            card.setCounted(false);
        }

        return totalPattern;
    }
}