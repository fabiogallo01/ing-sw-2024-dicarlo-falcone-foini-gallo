package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;
import java.util.*;

/**
 * Class representing the player's in game area
 * It contains a boolean matrix and a list of the played card
 * If a cell contains true => Cell is empty
 * If a cell contains false => There is a card in that celle
 *
 * @author Foini Lorenzo
 */
public class PlayerArea{
    private boolean[][] area; // Matrix
    private ArrayList<Card> cards; // List of played cards

    /**
     * Player's area constructor, it assigns all the parameters
     *
     * @param area for player's area (matrix)
     * @param cards list of played cards
     * @author Foini Lorenzo
     */
    public PlayerArea(boolean[][] area, ArrayList<Card> cards) {
        this.area = area;
        this.cards = cards;
    }

    /**
     * player's area getter
     *
     * @return player's area
     * @author Lorenzo Foini
     */
    public boolean[][] getArea() {
        return area;
    }

    /**
     * Player's area setter
     *
     * @param area representing player's area
     * @author Lorenzo Foini
     */
    public void setArea(boolean[][] area) {
        this.area = area;
    }

    /**
     * Played cards getter
     *
     * @return played cards
     * @author Lorenzo Foini
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Played cards getter
     *
     * @param cards list of played cards
     * @author Lorenzo Foini
     */
    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    /**
     * Method for adding a card to list of played cards.
     * It also set the select cell where to play teh card to false
     * It is guaranteed that the position is valid
     *
     * @param card presenting the selected card to be played
     * @param positionArea representing where the player has played the card
     * @author Lorenzo Foini
     */
    public void addCard(Card card, int[] positionArea){
        area[positionArea[0]][positionArea[1]] = false; // false = cell has a card inside.

        // Add card in the list
        cards.add(card); // Polymorphism

        // Assign the relative position in the game to the card
        card.setInGamePosition(positionArea);

        // Modify the status of the corners which are covered from this card (if present)
        // It is guaranteed that the added card doesn't cover a corner which can't be covered or a corner which is already covered
        for(Card cornerCard : cards) {
            int[] positionCornerCard = cornerCard.getInGamePosition(); // Get position
            if (positionCornerCard[0] == positionArea[0] - 1 && positionCornerCard[1] == positionArea[1] - 1) { // Top left card
                cornerCard.setVisibleCornerFalse(3); // Bottom right corner to false
            }
            else if (positionCornerCard[0] == positionArea[0] - 1 && positionCornerCard[1] == positionArea[1] + 1) { // Top right card
                cornerCard.setVisibleCornerFalse(2); // Bottom left corner to false
            }
            else if (positionCornerCard[0] == positionArea[0] + 1 && positionCornerCard[1] == positionArea[1] - 1) { // Bottom left card
                cornerCard.setVisibleCornerFalse(1); // Top right corner to false
            }
            else if (positionCornerCard[0] == positionArea[0] + 1 && positionCornerCard[1] == positionArea[1] + 1) { // Bottom right card
                cornerCard.setVisibleCornerFalse(0); // Top left corner to false
            }
        }
    }

    /**
     * Method for counting the total visible number of a given kingdom in the player's area
     *
     * @param kingdom type of kingdom to be counted
     * @return number of given kingdom in the area
     * @author Andrea Di Carlo, Lorenzo Foini
     */
    public int countKingdoms(Kingdom kingdom){
        int count = 0;
        for(Card card : cards) {
            // Get side of the card: true -> front, false -> back
            if (card.getSide()) {
                for (Corner corner : card.getFrontCorners()) {
                    if (corner.getVisible() && corner.getKingdom() == kingdom) {
                        count++; // Find a visible kingdom
                    }
                }

                // Count kingdoms at the centre of the starter card (it is played on the front side)
                if(card instanceof StarterCard) {
                    Kingdom[] frontKingdoms = ((StarterCard) card).getFrontKingdoms(); // Get centre kingdoms
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
                if (card instanceof GamingCard && ((GamingCard) card).getKingdom() == kingdom) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Method for counting the total visible number of a given object in the player's area
     *
     * @param object type of object to be counted
     * @return number of given object in the area
     * @author Lorenzo Foini
     */
    public int countObject(GameObject object){
        int count = 0;
        for(Card card : cards){
            // Get side of the card: true -> front, false -> back
            boolean side = card.getSide();
            if(side){
                // Check front corners
                for(Corner corner : card.getFrontCorners()){
                    if(corner.getVisible() && corner.getObject() == object){
                        count++; // find a visible object
                    }
                }
            }
            else {
                // Check back corners
                for (Corner corner : card.getBackCorners()) {
                    if (corner.getVisible() && corner.getObject() == object) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    /**
     * Method for counting the total number of hidden corner by the play of the selected card
     *
     * @param positionArea representing where to play the card
     * @return number of the hidden corner by this card
     * @author Giacomo Falcone, Lorenzo Foini
     */
    public int countHiddenCorner(int[] positionArea){
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

    /**
     * Helper method for ordering list of played cards
     *
     * @param playedCards list of played cards
     * @author Lorenzo Foini
     */
    private void helperOrderList(ArrayList<Card> playedCards){
        playedCards.sort(Comparator.comparingInt(card -> ((Card) card).getInGamePosition()[0])
                                   .thenComparingInt(card -> ((Card) card).getInGamePosition()[1])
        );
    }

    /**
     * Helper method for count the number of a given pattern
     *
     * @param secondCardPosition representing the second card's in game position
     * @param thirdCardPosition representing the third card's in game position
     * @param secondKingdom representing second card's kingdom
     * @param thirdKingdom representing third card's kingdom
     * @return true if the found pattern is valid, false otherwise
     * @author Lorenzo Foini
     */
    private boolean helperCountPattern(int[] secondCardPosition, int[] thirdCardPosition, Kingdom secondKingdom, Kingdom thirdKingdom){
        for(Card cardTwo:cards){
            if(!cardTwo.getCounted() && cardTwo.getInGamePosition()[0] == secondCardPosition[0] && cardTwo.getInGamePosition()[1] == secondCardPosition[1]) {
                // Find the second card
                GamingCard secondCard = (GamingCard) cardTwo;
                // Check second card's kingdom
                if (secondCard.getKingdom() == secondKingdom) {
                    // Get third card
                    for (Card cardThree : cards) {
                        if (!cardThree.getCounted() && cardThree.getInGamePosition()[0] == thirdCardPosition[0] && cardThree.getInGamePosition()[1] == thirdCardPosition[1]) {
                            // Find third card
                            GamingCard thirdCard = (GamingCard) cardThree;
                            // Check third card's kingdom
                            if (thirdCard.getKingdom() == thirdKingdom) {
                                // Set to true parameter counted and return true
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

    /**
     * Helper method for setting in game position of a card (given its indexes)
     *
     * @param row representing card's row
     * @param column representing card's column
     * @return the array of position
     * @author Lorenzo Foini
     */
    private int[] helperSetPosition(int row, int column){
        int[] position = new int[2];
        position[0] = row;
        position[1] = column;
        return position;
    }

    /**
     * Method for count the total number of a given pattern in the area
     * It uses two helper methods
     *
     * @param kingdom representing the kingdom of the pattern
     * @param pattern representing the type of pattern (enumeration)
     * @return total number of the given pattern in the area
     * @author Andrea Di Carlo, Lorenzo Foini
     */
    public int countPattern(Kingdom kingdom, Pattern pattern){
        int totalPattern = 0;

        // Call to helper method for order list of played cards
        //helperOrderList(cards);

        // Switch case base on kingdom's value
        switch (kingdom){
            case ANIMALKINGDOM : {
                if(pattern == Pattern.SECONDARYDIAGONAL){ // Case animal secondary diagonal pattern
                    for(Card cardOne : cards){
                        // Check indexes
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 41 && firstCardPosition[1] == 39) ||
                             (firstCardPosition[0] == 42 && firstCardPosition[1] == 38) ||
                             firstCardPosition[0] < 2 || firstCardPosition[1] > 78 )){

                            // Find first card and casting to gaming card
                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card using helper method
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]-1, firstCardPosition[1]+1);

                            // Get position of the third card using helper method
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

                            // Find first card and casting to gaming card
                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card using helper method
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]-2, firstCardPosition[1]);

                            // Get position of the third card using helper method
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

            case PLANTKINGDOM : {
                if(pattern == Pattern.PRIMARYDIAGONAL){ // Case plant primary diagonal pattern
                    for(Card cardOne : cards){
                        // Check indexes
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 39 && firstCardPosition[1] == 39) ||
                             (firstCardPosition[0] == 38 && firstCardPosition[1] == 38) ||
                             firstCardPosition[0] > 78 || firstCardPosition[1] > 78 )){

                            // Find first card and casting to gaming card
                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card using helper method
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]+1, firstCardPosition[1]+1);

                            // Get position of the third card using helper method
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

                            // Find first card and casting to gaming card
                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card using helper method
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]+2, firstCardPosition[1]);

                            // Get position of the third card using helper method
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

            case INSECTKINGDOM : {
                if(pattern == Pattern.PRIMARYDIAGONAL){ // Case insect primary diagonal pattern
                    for(Card cardOne : cards){
                        // Check indexes
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 39 && firstCardPosition[1] == 39) ||
                             (firstCardPosition[0] == 38 && firstCardPosition[1] == 38) ||
                             firstCardPosition[0] > 78 || firstCardPosition[1] > 78 )){

                            // Find first card and casting to gaming card
                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card using helper method
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]+1, firstCardPosition[1]+1);

                            // Get position of the third card using helper method
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

                            // Find first card and casting to gaming card
                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card using helper method
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]-2, firstCardPosition[1]);

                            // Get position of the third card using helper method
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

            case FUNGIKINGDOM : {
                if(pattern == Pattern.SECONDARYDIAGONAL){ // Case fungi secondary diagonal pattern
                    for(Card cardOne : cards){
                        // Check indexes
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 41 && firstCardPosition[1] == 39) ||
                             (firstCardPosition[0] == 42 && firstCardPosition[1] == 38) ||
                             firstCardPosition[0] < 2 || firstCardPosition[1] > 78 )){

                            // Find first card and casting to gaming card
                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card using helper method
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]-1, firstCardPosition[1]+1);

                            // Get position of the third card using helper method
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

                            // Find first card and casting to gaming card
                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card using helper method
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]+2, firstCardPosition[1]);

                            // Get position of the third card using helper method
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
