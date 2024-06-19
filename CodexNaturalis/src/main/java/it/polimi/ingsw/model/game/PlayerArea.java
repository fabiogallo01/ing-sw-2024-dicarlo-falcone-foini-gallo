package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;
import java.util.*;

/**
 * Class representing the player's in game area
 * It contains a boolean matrix and a list of the played card
 * If a cell contains true => Cell is empty, so there isn't a card inside of that cell
 * If a cell contains false => There is a card in that celle
 *
 * @author Di Carlo Andrea, Falcone Giacomo, Foini Lorenzo, Gallo Fabio
 */
public class PlayerArea{
    private boolean[][] area; // Matrix
    private ArrayList<Card> cards; // List of played cards

    /**
     * PlayerArea constructor, it assigns all the parameters
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
     * area getter
     *
     * @return player's area
     * @author Foini Lorenzo
     */
    public boolean[][] getArea() {
        return area;
    }

    /**
     * area setter
     *
     * @param area representing player's area to set
     * @author Foini Lorenzo
     */
    public void setArea(boolean[][] area) {
        this.area = area;
    }

    /**
     * cards getter
     *
     * @return played cards by the player
     * @author Foini Lorenzo
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * cards setter
     *
     * @param cards list of played cards to be set
     * @author Foini Lorenzo
     */
    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    /**
     * Method for returning a card given a position (as an int[])
     * It is guaranteed that in such position there is a card
     *
     * @param position position of the card to be returned
     * @return such card
     * @author Foini Lorenzo
     */
    public Card getCardByPosition(int[] position){
        Card cardByPosition = null;
        // Iterate through cards for searching the correct one
        for(Card card:cards){
            // Get card inGamePosition and check them with the parameter
            int[] cardPosition = card.getInGamePosition();
            if(cardPosition[0] == position[0] && cardPosition[1] == position[1]){
                cardByPosition = card;
                return cardByPosition;
            }
        }
        // Return the card is that position
        return cardByPosition;
    }

    /**
     * Method for adding a card to list of played cards.
     * It also set the select cell where to play the card to false
     * It is guaranteed that the position is valid
     *
     * @param card representing the selected card to be played
     * @param positionArea representing where the player has played the card
     * @author Foini Lorenzo
     */
    public void addCard(Card card, int[] positionArea){
        area[positionArea[0]][positionArea[1]] = false; // false => cell has a card inside.

        // Add card in the list
        cards.add(card); // Polymorphism

        // Assign the relative position in the game to the card
        card.setInGamePosition(positionArea);

        // Modify the status of the corners which are covered from this card (if present) from true to false
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
     * It doesn't count the kingdom that are in a corner which is covered by another card
     *
     * @param kingdom type of kingdom to be counted
     * @return number of given kingdom in the area
     * @author Di Carlo Andrea, Foini Lorenzo
     */
    public int countKingdoms(Kingdom kingdom){
        int count = 0; // Set total counter to 0
        // Iterate through cards
        for(Card card : cards) {
            // Get side of the card: true -> front, false -> back
            if (card.getSide()) { // front
                // Check its front corner: if they are visible and equals to the searched one
                // => Increment the counter by 1
                for (Corner corner : card.getFrontCorners()) {
                    if (corner.getVisible() && corner.getKingdom() == kingdom) {
                        count++; // Find a visible kingdom
                    }
                }

                // Count kingdoms at the centre of the starter card (it is played on the front side)
                if(card instanceof StarterCard) {
                    Kingdom[] frontKingdoms = ((StarterCard) card).getFrontKingdoms(); // Get centre kingdoms
                    // Iterate through front kingdoms and check if they are equals to the searched one
                    for(Kingdom frontKingdom : frontKingdoms){
                        if(frontKingdom == kingdom){
                            count++;
                        }
                    }
                }
            } else { // back
                // Check its back corner: if they are visible and equals to the searched one
                // => Increment the counter by 1
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
        // Return the counter of the kingdom
        return count;
    }

    /**
     * Method for counting the total visible number of a given object in the player's area
     *
     * @param object type of object to be counted
     * @return number of given object in the area
     * @author Foini Lorenzo
     */
    public int countObject(GameObject object){
        int count = 0; // Set total counter to 0
        // Iterate through cards
        for(Card card : cards){
            // Get side of the card: true -> front, false -> back
            boolean side = card.getSide();
            if(side){ // front
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
        // Return the counter of the object
        return count;
    }

    /**
     * Method for counting the total number of hidden corner by the play of the selected card
     *
     * @param positionArea representing where to play the card
     * @return number of the hidden corner by this card
     * @author Falcone Giacomo, Foini Lorenzo
     */
    public int countHiddenCorner(int[] positionArea){
        int count = 0;
        // Check if there are cards in the corners of the new card's position
        // If area[][] in these positions is false => There is a card in that corner
        // => Increment the counter by 1
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

        // Return the counter of hidden corner
        return count;
    }

    /**
     * Helper method for ordering list of played cards by their inGamePosition
     * First comparing their row, if equals then comparing their column
     *
     * @param playedCards list of played cards
     * @author Foini Lorenzo
     */
    private void helperOrderList(ArrayList<Card> playedCards){
        // Use method sort() for sorting such list of cards
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
     * @author Foini Lorenzo
     */
    private boolean helperCountPattern(int[] secondCardPosition, int[] thirdCardPosition, Kingdom secondKingdom, Kingdom thirdKingdom){
        // Iterate through cards
        for(Card cardTwo:cards){
            // Check if the card is not counted and has the same inGamePosition as secondCardPosition parameter
            if(!cardTwo.getCounted() && cardTwo.getInGamePosition()[0] == secondCardPosition[0] && cardTwo.getInGamePosition()[1] == secondCardPosition[1]) {
                // Found the second card, now cast it to GamingCard for checking his kingdom
                GamingCard secondCard = (GamingCard) cardTwo;
                // Check second card's kingdom, have to be equals to secondKingdom parameter
                if (secondCard.getKingdom() == secondKingdom) {
                    // There is such card, now need to check if there is a third one with the correct criteria
                    // Get third card
                    for (Card cardThree : cards) {
                        // Check if the card is not counted and has the same inGamePosition as thirdCardPosition parameter
                        if (!cardThree.getCounted() && cardThree.getInGamePosition()[0] == thirdCardPosition[0] && cardThree.getInGamePosition()[1] == thirdCardPosition[1]) {
                            // Find third card, now cast it to GamingCard for checking his kingdom
                            GamingCard thirdCard = (GamingCard) cardThree;
                            // Check third card's kingdom, have to be equals to thirdKingdom parameter
                            if (thirdCard.getKingdom() == thirdKingdom) {
                                // Found third card
                                // Set to true parameter counted for second and third card, then return true
                                cardTwo.setCounted(true);
                                cardThree.setCounted(true);
                                return true;
                            }
                            // The third card with such criteria doesn't exist
                            return false;
                        }
                    }
                }
                // The second card with such criteria doesn't exist
                return false;
            }
        }
        // Check all the cards and exit loop => No cards with the correct criteria founded
        return false;
    }

    /**
     * Helper method for setting in game position of a card (given its indexes of row and column)
     *
     * @param row representing card's row
     * @param column representing card's column
     * @return the array of position
     * @author Foini Lorenzo
     */
    private int[] helperSetPosition(int row, int column){
        int[] position = new int[2];
        position[0] = row;
        position[1] = column;
        return position;
    }

    /**
     * Method for count the total number of a given pattern in the area
     * It uses three helper methods for doing so
     *
     * @param kingdom representing the kingdom of the pattern
     * @param pattern representing the type of pattern (enumeration)
     * @return total number of the given pattern in the area
     * @author Di Carlo Andrea, Foini Lorenzo
     */
    public int countPattern(Kingdom kingdom, Pattern pattern){
        int totalPattern = 0; // total number of pattern with the correct criteria

        // Call to helper method for order list of played cards
        helperOrderList(cards);

        // Switch case base on kingdom's value
        switch (kingdom){
            case ANIMALKINGDOM : {
                if(pattern == Pattern.SECONDARYDIAGONAL){ // Case animal secondary diagonal pattern
                    // Iterate through cards
                    for(Card cardOne : cards){
                        // Check valid indexes for finding the first card
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 41 && firstCardPosition[1] == 39) ||
                             (firstCardPosition[0] == 42 && firstCardPosition[1] == 38) ||
                             firstCardPosition[0] < 2 || firstCardPosition[1] > 78 )){
                            // Exclude starter card in the three card of the pattern and check board of the area

                            // First card founded and casting it to GamingCard
                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card using helper method
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]-1, firstCardPosition[1]+1);

                            // Get position of the third card using helper method
                            int[] thirdCardPosition = helperSetPosition(firstCardPosition[0]-2, firstCardPosition[1]+2);

                            // Check first card's kingdom
                            // Check if the card is not already counted in another pattern
                            // Check if there are two cards in the diagonal => area[x][y] is false
                            if(firstCard.getKingdom() == Kingdom.ANIMALKINGDOM &&
                               !firstCard.getCounted() &&
                               !area[secondCardPosition[0]][secondCardPosition[1]] &&
                               !area[thirdCardPosition[0]][thirdCardPosition[1]]){
                                // Call to method helperCountPattern
                                if(helperCountPattern(secondCardPosition, thirdCardPosition, Kingdom.ANIMALKINGDOM, Kingdom.ANIMALKINGDOM)){
                                    // true => Such pattern exists
                                    // Set card one's counted to true and increment totalPattern
                                    cardOne.setCounted(true);
                                    totalPattern++;
                                }
                            }
                        }
                    }
                }
                else{ // Case animal upper right pattern
                    // Iterate through cards
                    for(Card cardOne : cards){
                        // Check valid indexes for finding the first card
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 42 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 43 && firstCardPosition[1] == 39) ||
                             firstCardPosition[0] < 3 || firstCardPosition[1] > 79 )){
                            // Exclude starter card in the three card of the pattern and check board of the area

                            // First card founded and casting to GamingCard
                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card using helper method
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]-2, firstCardPosition[1]);

                            // Get position of the third card using helper method
                            int[] thirdCardPosition = helperSetPosition(firstCardPosition[0]-3, firstCardPosition[1]+1);

                            // Check first card's kingdom
                            // Check if the card is not counted
                            // Check if there are two cards in the diagonal
                            if(firstCard.getKingdom() == Kingdom.ANIMALKINGDOM &&
                               !firstCard.getCounted() &&
                               !area[secondCardPosition[0]][secondCardPosition[1]] &&
                               !area[thirdCardPosition[0]][thirdCardPosition[1]]){
                                // Call to method helperCountPattern
                                if(helperCountPattern(secondCardPosition, thirdCardPosition, Kingdom.ANIMALKINGDOM, Kingdom.FUNGIKINGDOM)){
                                    // true => Such pattern exists
                                    // Set card one's counted to true and increment totalPattern
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
                    // Iterate through cards
                    for(Card cardOne : cards){
                        // Check valid indexes for finding the first card
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 39 && firstCardPosition[1] == 39) ||
                             (firstCardPosition[0] == 38 && firstCardPosition[1] == 38) ||
                             firstCardPosition[0] > 78 || firstCardPosition[1] > 78 )){
                            // Exclude starter card in the three card of the pattern and check board of the area

                            // First card founded and casting to GamingCard
                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card using helper method
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]+1, firstCardPosition[1]+1);

                            // Get position of the third card using helper method
                            int[] thirdCardPosition = helperSetPosition(firstCardPosition[0]+2, firstCardPosition[1]+2);

                            // Check first card's kingdom
                            // Check if the card is not counted
                            // Check if there are two cards in the diagonal
                            if(firstCard.getKingdom() == Kingdom.PLANTKINGDOM &&
                               !firstCard.getCounted() &&
                               !area[secondCardPosition[0]][secondCardPosition[1]] &&
                               !area[thirdCardPosition[0]][thirdCardPosition[1]]){
                                // Call to method helperCountPattern
                                if(helperCountPattern(secondCardPosition, thirdCardPosition, Kingdom.PLANTKINGDOM, Kingdom.PLANTKINGDOM)){
                                    // true => Such pattern exists
                                    // Set card one's counted to true and increment totalPattern
                                    cardOne.setCounted(true);
                                    totalPattern++;
                                }
                            }
                        }
                    }
                }
                else{ // Case plant lower left pattern
                    // Iterate through cards
                    for(Card cardOne : cards){
                        // Check valid indexes for finding the first card
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 38 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 37 && firstCardPosition[1] == 41) ||
                             firstCardPosition[0] > 77 || firstCardPosition[1] < 1 )){
                            // Exclude starter card in the three card of the pattern and check board of the area

                            // First card founded and casting to GamingCard
                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card using helper method
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]+2, firstCardPosition[1]);

                            // Get position of the third card using helper method
                            int[] thirdCardPosition = helperSetPosition(firstCardPosition[0]+3, firstCardPosition[1]-1);

                            // Check first card's kingdom
                            // Check if the card is not counted
                            // Check if there are two cards in the diagonal
                            if(firstCard.getKingdom() == Kingdom.PLANTKINGDOM &&
                               !firstCard.getCounted() &&
                               !area[secondCardPosition[0]][secondCardPosition[1]] &&
                               !area[thirdCardPosition[0]][thirdCardPosition[1]]){
                                // Call to method helperCountPattern
                                if(helperCountPattern(secondCardPosition, thirdCardPosition, Kingdom.PLANTKINGDOM, Kingdom.INSECTKINGDOM)){
                                    // true => Such pattern exists
                                    // Set card one's counted to true and increment totalPattern
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
                    // Iterate through cards
                    for(Card cardOne : cards){
                        // Check valid indexes for finding the first card
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 39 && firstCardPosition[1] == 39) ||
                             (firstCardPosition[0] == 38 && firstCardPosition[1] == 38) ||
                             firstCardPosition[0] > 78 || firstCardPosition[1] > 78 )){
                            // Exclude starter card in the three card of the pattern and check board of the area

                            // First card founded and casting to GamingCard
                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card using helper method
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]+1, firstCardPosition[1]+1);

                            // Get position of the third card using helper method
                            int[] thirdCardPosition = helperSetPosition(firstCardPosition[0]+2, firstCardPosition[1]+2);

                            // Check first card's kingdom
                            // Check if the card is not counted
                            // Check if there are two cards in the diagonal
                            if(firstCard.getKingdom() == Kingdom.INSECTKINGDOM &&
                               !firstCard.getCounted() &&
                               !area[secondCardPosition[0]][secondCardPosition[1]] &&
                               !area[thirdCardPosition[0]][thirdCardPosition[1]]){
                                // Call to method helperCountPattern
                                if(helperCountPattern(secondCardPosition, thirdCardPosition, Kingdom.INSECTKINGDOM, Kingdom.INSECTKINGDOM)){
                                    // true => Such pattern exists
                                    // Set card one's counted to true and increment totalPattern
                                    cardOne.setCounted(true);
                                    totalPattern++;
                                }
                            }
                        }
                    }
                }
                else{ // Case insect upper left pattern
                    // Iterate through cards
                    for(Card cardOne : cards){
                        // Check valid indexes for finding the first card
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 42 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 43 && firstCardPosition[1] == 41) ||
                             firstCardPosition[0] < 3 || firstCardPosition[1] < 1 )){
                            // Exclude starter card in the three card of the pattern and check board of the area

                            // First card founded and casting to GamingCard
                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card using helper method
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]-2, firstCardPosition[1]);

                            // Get position of the third card using helper method
                            int[] thirdCardPosition = helperSetPosition(firstCardPosition[0]-3, firstCardPosition[1]-1);


                            // Check first card's kingdom
                            // Check if the card is not counted
                            // Check if there are two cards in the diagonal
                            if(firstCard.getKingdom() == Kingdom.INSECTKINGDOM &&
                               !firstCard.getCounted() &&
                               !area[secondCardPosition[0]][secondCardPosition[1]] &&
                               !area[thirdCardPosition[0]][thirdCardPosition[1]]){
                                // Call to method helperCountPattern
                                if(helperCountPattern(secondCardPosition, thirdCardPosition, Kingdom.INSECTKINGDOM, Kingdom.ANIMALKINGDOM)){
                                    // true => Such pattern exists
                                    // Set card one's counted to true and increment totalPattern
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
                    // Iterate through cards
                    for(Card cardOne : cards){
                        // Check valid indexes for finding the first card
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 41 && firstCardPosition[1] == 39) ||
                             (firstCardPosition[0] == 42 && firstCardPosition[1] == 38) ||
                             firstCardPosition[0] < 2 || firstCardPosition[1] > 78 )){
                            // Exclude starter card in the three card of the pattern and check board of the area

                            // First card founded and casting to GamingCard
                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card using helper method
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]-1, firstCardPosition[1]+1);

                            // Get position of the third card using helper method
                            int[] thirdCardPosition = helperSetPosition(firstCardPosition[0]-2, firstCardPosition[1]+2);

                            // Check first card's kingdom
                            // Check if the card is not counted
                            // Check if there are two cards in the diagonal
                            if(firstCard.getKingdom() == Kingdom.FUNGIKINGDOM &&
                               !firstCard.getCounted() &&
                               !area[secondCardPosition[0]][secondCardPosition[1]] &&
                               !area[thirdCardPosition[0]][thirdCardPosition[1]]){
                                // Call to method helperCountPattern
                                if(helperCountPattern(secondCardPosition, thirdCardPosition, Kingdom.FUNGIKINGDOM, Kingdom.FUNGIKINGDOM)){
                                    // true => Such pattern exists
                                    // Set card one's counted to true and increment totalPattern
                                    cardOne.setCounted(true);
                                    totalPattern++;
                                }
                            }
                        }
                    }
                }
                else{ // Case fungi lower right pattern
                    // Iterate through cards
                    for(Card cardOne : cards){
                        // Check valid indexes for finding the first card
                        int[] firstCardPosition = cardOne.getInGamePosition();
                        if(!((firstCardPosition[0] == 40 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 38 && firstCardPosition[1] == 40) ||
                             (firstCardPosition[0] == 37 && firstCardPosition[1] == 39) ||
                             firstCardPosition[0] > 77 || firstCardPosition[1] > 79 )){
                            // Exclude starter card in the three card of the pattern and check board of the area

                            // First card founded and casting to GamingCard
                            GamingCard firstCard = (GamingCard) cardOne;

                            // Get position of the second card using helper method
                            int[] secondCardPosition = helperSetPosition(firstCardPosition[0]+2, firstCardPosition[1]);

                            // Get position of the third card using helper method
                            int[] thirdCardPosition = helperSetPosition(firstCardPosition[0]+3, firstCardPosition[1]+1);

                            // Check first card's kingdom
                            // Check if the card is not counted
                            // Check if there are two cards in the diagonal
                            if(firstCard.getKingdom() == Kingdom.FUNGIKINGDOM &&
                               !firstCard.getCounted() &&
                               !area[secondCardPosition[0]][secondCardPosition[1]] &&
                               !area[thirdCardPosition[0]][thirdCardPosition[1]]){
                                // Call to method helperCountPattern
                                if(helperCountPattern(secondCardPosition, thirdCardPosition, Kingdom.FUNGIKINGDOM, Kingdom.PLANTKINGDOM)){
                                    // true => Such pattern exists
                                    // Set card one's counted to true and increment totalPattern
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

        // Reset to false parameter counted of all the cards
        for(Card card : cards){
            card.setCounted(false);
        }

        // Return total number of founded pattern
        return totalPattern;
    }
}