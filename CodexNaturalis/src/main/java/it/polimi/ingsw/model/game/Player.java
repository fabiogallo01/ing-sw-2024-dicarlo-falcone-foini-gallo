package it.polimi.ingsw.model.game;
import it.polimi.ingsw.model.cards.*;
import it.polimi.ingsw.model.exception.*;
import java.util.*;

/**
 * Class representing the players in the match
 * It contains parameters for player's cards, score and username
 *
 * @author Foini Lorenzo
 */
public class Player {
    private final String username;
    private int score;
    private PlayerArea playerArea;
    private final Color color;
    private final ObjectiveCard secretObjective;
    private final StarterCard starterCard;
    private ArrayList<GamingCard> hand;
    private boolean turn;

    public boolean isTurn() {
        return turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    /**
     * Player constructor, it assigns all the parameters
     *
     * @param username for player username
     * @param score for player score
     * @param playerArea for player's play area
     * @param color for player color
     * @param secretObjective for player's secret objective
     * @param starterCard for player's starter card
     * @param hand for player's hand
     * @author Foini Lorenzo
     */
    public Player(String username, int score, PlayerArea playerArea, Color color, ObjectiveCard secretObjective, StarterCard starterCard, ArrayList<GamingCard> hand){
        this.username = username;
        this.score = score;
        this.playerArea = playerArea;
        this.color = color;
        this.secretObjective = secretObjective;
        this.starterCard = starterCard;
        this.hand = hand;
        this.turn = false;
    }

    /**
     * Username getter
     *
     * @return player's username
     * @author Lorenzo Foini
     */
    public String getUsername(){
        return username;
    }

    /**
     * Score getter
     *
     * @return player's score
     * @author Lorenzo Foini
     */
    public int getScore(){
        return score;
    }

    /**
     * Score setter
     *
     * @param score for setting player's score
     * @throws NegativeScoreException if try to assign a negative score
     * @author Lorenzo Foini
     */
    public void setScore(int score) throws NegativeScoreException {
        if (score < 0) {
            throw new NegativeScoreException("You can't assign a negative score");
        } else {
            this.score = score;
        }
    }

    /**
     * Player's area getter
     *
     * @return player's area
     * @author Lorenzo Foini
     */
    public PlayerArea getPlayerArea(){
        return playerArea;
    }

    /**
     * Player's area setter
     *
     * @param playerArea for setting player's area
     * @author Lorenzo Foini
     */
    public void setPlayerArea(PlayerArea playerArea){
        this.playerArea = playerArea;
    }

    /**
     * Color getter
     *
     * @return player's color
     * @author Lorenzo Foini
     */
    public Color getColor(){
        return color;
    }

    /**
     * Secret objective getter
     *
     * @return player's secret objective
     * @author Lorenzo Foini
     */
    public ObjectiveCard getSecretObjective(){
        return secretObjective;
    }

    /**
     * Starter card getter
     *
     * @return starter card
     * @author Lorenzo Foini
     */
    public StarterCard getStarterCard(){
        return starterCard;
    }

    /**
     * Player's hand getter
     *
     * @return player's hand
     * @author Lorenzo Foini
     */
    public ArrayList<GamingCard> getHand(){
        return hand;
    }

    /**
     * Player's hand setter
     *
     * @param hand for setting player's hand
     * @throws InvalidNumCardsPlayerHandException if hand doesn't have exactly three cards
     * @author Lorenzo Foini
     */
    public void setHand(ArrayList<GamingCard> hand) throws InvalidNumCardsPlayerHandException{
        if(hand.size() != 3){
            throw new InvalidNumCardsPlayerHandException("Invalid number of cards for player's hand. Must be 3.");
        }
        else{
            this.hand = hand;
        }
    }

    /**
     * Method for adding a card to the player's hand
     *
     * @param card representing the card to be added to player's hand
     * @throws HandAlreadyFullException if player's hand is already full (three cards)
     * @author Lorenzo Foini
     */
    public void addCardHand(GamingCard card) throws HandAlreadyFullException{
        if(hand.size() == 3){
            throw new HandAlreadyFullException("You already have three cards, so you can't draw.");
        }else{
            hand.add(card);
        }
    }

    /**
     * Method for play a card from the hand.
     * At the end, the selected card is removed from the player's hand
     *
     * @param positionCardHand representing the position of the selected card to be played
     * @param positionArea representing where to put the selected card
     * @param side representing card's side
     * @throws InvalidPlayCardIndexException if player select an out of hand bound
     * @throws InvalidPositionAreaException if player select an out of area bound
     * @throws InvalidPlayException if player wants to play the card in an invalid position of his area
     * @author Lorenzo Foini
     */
    public void playCard(int positionCardHand, int[] positionArea, boolean side) throws InvalidPlayCardIndexException, InvalidPositionAreaException, InvalidPlayException {
        if (positionCardHand < 1 || positionCardHand > 3) {
            throw new InvalidPlayCardIndexException("Invalid selection of the card from hand.");
        }
        else if(!isValidPosition(positionArea)){
            throw new InvalidPositionAreaException("Not valid index's position.");
        }
        else{
            // Get selected card form hand
            GamingCard cardToPlay = hand.get(positionCardHand-1);
            //Set the card to the playing side chosen by the user
            cardToPlay.setSide(side);
            // Check if the card is actually playable given the game's rules
            String mistake = isPlayable(cardToPlay, positionArea); // Variable which contains the exception's message
            if (!mistake.equals("None")) { // Raised exception
                throw new InvalidPlayException("You can't play this card in this position. Mistake: " + mistake);
            }
            else{ //The card is playable
                // Add the card in the given position
                playerArea.addCard(cardToPlay, positionArea);

                // Remove the card from the player's hand
                hand.remove(positionCardHand-1);

                // Add points to the player
                // Check if the played card is a goldCard and if it is played on front
                if(cardToPlay instanceof GoldCard && side && ((GoldCard) cardToPlay).getConditionPoint() != ConditionPoint.NONE) {
                    // Assign points based on type of condition point
                    switch (((GoldCard) cardToPlay).getConditionPoint()){
                        case QUILL:
                            score += cardToPlay.getPoints() * playerArea.countObject(GameObject.QUILL);
                            break;
                        case INKWELL:
                            score += cardToPlay.getPoints() * playerArea.countObject(GameObject.INKWELL);
                            break;
                        case MANUSCRIPT:
                            score += cardToPlay.getPoints() * playerArea.countObject(GameObject.MANUSCRIPT);
                            break;
                        case HIDDENCORNER:
                            score += cardToPlay.getPoints() * playerArea.countHiddenCorner(cardToPlay.getInGamePosition());
                            break;
                    }
                }else if(side){
                    // Some resource cards assign one point
                    score += cardToPlay.getPoints();
                }

                // Assign such score in the scoreboard
                GameTable.getScoreboard().setScore(this, score);
            }
        }
    }

    /**
     * Method for checking if the position is not out of matrix's bound
     *
     * @param position representing the position of the selected card to be played
     * @return true if the position is valid, false otherwise
     * @author Lorenzo Foini
     */
    private boolean isValidPosition(int[] position) {
        // Check if the position is valid in the matrix
        // Return true if the position is valid, false otherwise
        return position[0] >= 0 && position[0] <= 80 && position[1] >= 0 && position[1] <= 80;
    }

    /**
     * Check if the card is actually playable given the game's rules and current player's area
     * PlayerArea's cells: true => cell is empty. false => there is a card in the cell
     *
     * @param cardToPlay representing the selected card to be played
     * @param position representing the position of the selected card to be played
     * @return "None" if the card is playable, otherwise what type of mistake
     * @author Lorenzo Foini
     */
    private String isPlayable(GamingCard cardToPlay, int[] position) {
        /* List of possible invalid plays:
            1. There is already a card in that position

            2. The card covers two corners of the same card.

            3. There are no cards in the corners of that position.

            4. One of the corners of the card covers one or more non-playable corners of the cards
               at the corners of that position.

            5. For gold cards played on front, check if there are enough resources for playing that card.
        */

        // Condition 1
        if(!checkCondition1(position)){
            return "There is already a card in that position.";
        }

        // Condition 2
        if(!checkCondition2(position)){
            return "The card you want to play can't cover two corners of the same card.";
        }

        // Condition 3
        if(!checkCondition3(position)){
            return "There are no cards in the corners of that position.";
        }

        // Condition 4
        if(!checkCondition4(position)){
            return "Card covers a corner which can't be covered";
        }

        // Condition 5
        if(!checkCondition5(cardToPlay)){
            return "There aren't enough resources to place the gold card on front.";
        }

        // All condition are valid, so the card can be played
        return "None";
    }

    /**
     * Check if there is a card in the given position
     *
     * @param position representing the position of the selected card to be played
     * @return true => Condition is valid. return false => Condition is not valid
     * @author Lorenzo Foini
     */
    private boolean checkCondition1(int[] position){
        // true => Cell is empty
        // false => There is a card in such position
        return playerArea.getArea()[position[0]][position[1]];
    }

    /**
     * Check if card played covers two corners of one or more cards
     *
     * @param position representing the position of the selected card to be played
     * @return true => Condition is valid. return false => Condition is not valid
     * @author Lorenzo Foini
     */
    private boolean checkCondition2(int[] position){
        // Need to check first row, last row, first column and last column
        if(position[0] == 0){
            if(position[1] == 0){ // Top left corner
                // Just check if there is a card that is located on one of the two sides of the chosen position.
                return playerArea.getArea()[position[0]][position[1] + 1] &&
                       playerArea.getArea()[position[0] + 1][position[1]];

            } else if(position[1] == 80){ // Top right corner
                // Just check if there is a card that is located on one of the two sides of the chosen position.
                return playerArea.getArea()[position[0]][position[1] - 1] &&
                       playerArea.getArea()[position[0] + 1][position[1]];

            } else{ // first row
                // Just check if there is a card that is located on one of the three sides of the chosen position.
                return playerArea.getArea()[position[0]][position[1] - 1] &&
                       playerArea.getArea()[position[0]][position[1] + 1] &&
                       playerArea.getArea()[position[0] + 1][position[1]];

            }

        } else if(position[0] == 80){
            if(position[1] == 0){ // bottom left corner
                // Just check if there is a card that is located on one of the two sides of the chosen position.
                return playerArea.getArea()[position[0]][position[1] + 1] &&
                       playerArea.getArea()[position[0] - 1][position[1]];

            } else if(position[1] == 80){ // Top right corner
                // Just check if there is a card that is located on one of the two sides of the chosen position.
                return playerArea.getArea()[position[0]][position[1] - 1] &&
                       playerArea.getArea()[position[0] - 1][position[1]];

            } else{ // last row
                // Just check if there is a card that is located on one of the three sides of the chosen position.
                return playerArea.getArea()[position[0]][position[1] - 1] &&
                       playerArea.getArea()[position[0]][position[1] + 1] &&
                       playerArea.getArea()[position[0] - 1][position[1]];
            }

        } else if(position[1] == 0){ // First column
            // Just check if there is a card that is located on one of the three sides of the chosen position.
            return playerArea.getArea()[position[0]][position[1] + 1] &&
                   playerArea.getArea()[position[0] - 1][position[1]] &&
                   playerArea.getArea()[position[0] + 1][position[1]];

        } else if(position[1] == 80){ // Last column
            return playerArea.getArea()[position[0]][position[1] - 1] &&
                   playerArea.getArea()[position[0] - 1][position[1]] &&
                   playerArea.getArea()[position[0] + 1][position[1]];

        } else{ // Not first/last row/column => Check all 4 sides
            return playerArea.getArea()[position[0]][position[1] - 1] &&
                   playerArea.getArea()[position[0]][position[1] + 1] &&
                   playerArea.getArea()[position[0] - 1][position[1]] &&
                   playerArea.getArea()[position[0] + 1][position[1]];
        }
    }

    /**
     * Check if there is a card in one of the possible corners of a given position
     * Need to invert condition: if playerArea.getArea()[row,column] is false,
     *                           => There is a card and return true
     *                           if playerArea.getArea()[row,column] is true,
     *                           => There isn't a card and return false
     *
     * @param position representing the position of the selected card to be played
     * @return true => Condition is valid. return false => Condition is not valid
     * @author Lorenzo Foini
     */
    private boolean checkCondition3(int[] position){
        // Need to check first row, last row, first column and last column
        if(position[0] == 0){
            if(position[1] == 0){ // Top left corner
                // Just check if there is a card that is located in the bottom right corner of this position
                // Need to invert condition
                return !playerArea.getArea()[position[0] + 1][position[1] + 1];

            } else if(position[1] == 80){ // Top right corner
                // Just check if there is a card that is located in the bottom left corner of this position
                // Need to invert condition
                return !playerArea.getArea()[position[0] + 1][position[1] - 1];

            } else{ // first row
                // Just check if there is a card that is located in the bottom left/right corners of this position
                // Need to invert condition
                return !(playerArea.getArea()[position[0] + 1][position[1] - 1] &&
                         playerArea.getArea()[position[0] + 1][position[1] + 1]);

            }

        } else if(position[0] == 80){
            if(position[1] == 0){ // bottom left corner
                // Just check if there is a card that is located in the top right corner of this position
                // Need to invert condition
                return !playerArea.getArea()[position[0] - 1][position[1] + 1];

            } else if(position[1] == 80){ // Top right corner
                // Just check if there is a card that is located in the top left corner of this position
                // Need to invert condition
                return !playerArea.getArea()[position[0] - 1][position[1] - 1];

            } else{ // last row
                // Just check if there is a card that is located in the top left/right corners of this position
                // Need to invert condition
                return !(playerArea.getArea()[position[0] - 1][position[1] - 1] &&
                         playerArea.getArea()[position[0] - 1][position[1] + 1]);
            }

        } else if(position[1] == 0){ // First column
            // Just check if there is a card that is located in the bottom/top right corners of this position
            // Need to invert condition
            return !(playerArea.getArea()[position[0] - 1][position[1] + 1] &&
                     playerArea.getArea()[position[0] + 1][position[1] + 1]);

        } else if(position[1] == 80){ // Last column
            // Just check if there is a card that is located in the bottom/top left corners of this position
            // Need to invert condition
            return !(playerArea.getArea()[position[0] - 1][position[1] - 1] &&
                     playerArea.getArea()[position[0] + 1][position[1] - 1]);

        } else{ // Not first/last row/column
            // Just check if there is a card that is located in one of the corners of this position
            // Need to invert condition
            return !(playerArea.getArea()[position[0] - 1][position[1] - 1] &&
                     playerArea.getArea()[position[0] - 1][position[1] + 1] &&
                     playerArea.getArea()[position[0] + 1][position[1] - 1] &&
                     playerArea.getArea()[position[0] + 1][position[1] + 1]);
        }
    }

    /**
     * Check if the played card covers a corner that can't be covered
     *
     * @param position representing the position of the selected card to be played
     * @return true => Condition is valid. return false => Condition is not valid
     * @author Lorenzo Foini
     */
    private boolean checkCondition4(int[] position){
        // Get the cards in the corners and check if the covered corner can be covered or not
        // Use try-catch clauses for handling the margins of playerArea
        // If getEmpty() is true => Corner can't be covered so return false
        Card checkedCard;
        int[] checkedPosition = new int[2];

        // Need to check first row, last row, first column and last column
        if(position[0] != 80){ // Not last row
            if(position[1] != 80){ // Not last column
                // Just check if the card in the bottom right corner has top left corner empty
                checkedPosition[0] = position[0] + 1;
                checkedPosition[1] = position[1] + 1;

                // Check if there is a card is the checked position
                if(!playerArea.getArea()[checkedPosition[0]][checkedPosition[1]]){
                    checkedCard = playerArea.getCardByPosition(checkedPosition);
                    if(checkedCard.getSide()){ // Card is played on the front
                        if(checkedCard.getFrontCorners()[0].getEmpty()){
                            return false; // Card in the bottom right corner has front top left corner empty
                        }
                    }
                    else{ // Card is played on the back
                        if(checkedCard.getBackCorners()[0].getEmpty()){
                            return false; // Card in the bottom right corner has back top left corner empty
                        }
                    }
                }
            }

            if(position[1] != 0){ // Not first column
                // Just check if the card in the bottom left corner has top right corner empty
                checkedPosition[0] = position[0] + 1;
                checkedPosition[1] = position[1] - 1;

                // Check if there is a card is the checked position
                if(!playerArea.getArea()[checkedPosition[0]][checkedPosition[1]]){
                    checkedCard = playerArea.getCardByPosition(checkedPosition);
                    if(checkedCard.getSide()){ // Card is played on the front
                        if(checkedCard.getFrontCorners()[1].getEmpty()){
                            return false; // Card in the bottom left corner has front top right corner empty
                        }
                    }
                    else{ // Card is played on the back
                        if(checkedCard.getBackCorners()[1].getEmpty()){
                            return false; // Card in the bottom left corner has back top right corner empty
                        }
                    }
                }
            }
        }

        if(position[0] != 0){ // Not first row
            if(position[1] != 80){ // Not last column
                // Just check if the card in the top right corner has bottom left corner empty
                checkedPosition[0] = position[0] - 1;
                checkedPosition[1] = position[1] + 1;

                // Check if there is a card is the checked position
                if(!playerArea.getArea()[checkedPosition[0]][checkedPosition[1]]){
                    checkedCard = playerArea.getCardByPosition(checkedPosition);
                    if(checkedCard.getSide()){ // Card is played on the front
                        if(checkedCard.getFrontCorners()[2].getEmpty()){
                            return false; // Card in the top right corner has front bottom left corner empty
                        }
                    }
                    else{ // Card is played on the back
                        if(checkedCard.getBackCorners()[2].getEmpty()){
                            return false; // Card in the top right corner has back bottom left corner empty
                        }
                    }
                }
            }

            if(position[1] != 0){ // Not first column
                // Just check if the card in the top left corner has bottom right corner empty
                checkedPosition[0] = position[0] - 1;
                checkedPosition[1] = position[1] - 1;

                // Check if there is a card is the checked position
                if(!playerArea.getArea()[checkedPosition[0]][checkedPosition[1]]){
                    checkedCard = playerArea.getCardByPosition(checkedPosition);
                    if (checkedCard.getSide()) { // Card is played on the front
                        if (checkedCard.getFrontCorners()[3].getEmpty()) {
                            return false; // Card in the top left corner has front bottom right corner empty
                        }
                    } else { // Card is played on the back
                        if (checkedCard.getBackCorners()[3].getEmpty()) {
                            return false; // Card in the top left corner has back bottom right corner empty
                        }
                    }
                }
            }
        }

        // Condition is valid, so return true
        return true;
    }


    /**
     * Check if there are enough resources for playing a gold card on front
     *
     * @param cardToPlay representing the played card
     * @return true => Condition is valid. return false => Condition is not valid
     * @author Lorenzo Foini
     */
    private boolean checkCondition5(GamingCard cardToPlay){
        // Check if the card is a gold card played on front
        if(cardToPlay instanceof GoldCard && cardToPlay.getSide()){
            // Count resources
            int countAnimalKingdom = 0;
            int countPlantKingdom = 0;
            int countFungiKingdom = 0;
            int countInsectKingdom = 0;
            for(Kingdom resource : ((GoldCard) cardToPlay).getResources()){
                switch (resource) {
                    case Kingdom.ANIMALKINGDOM:
                        countAnimalKingdom++;
                        break;
                    case Kingdom.PLANTKINGDOM:
                        countPlantKingdom++;
                        break;
                    case Kingdom.FUNGIKINGDOM:
                        countFungiKingdom++;
                        break;
                    case Kingdom.INSECTKINGDOM:
                        countInsectKingdom++;
                        break;
                }
            }
            // Check condition
            return playerArea.countKingdoms(Kingdom.ANIMALKINGDOM) >= countAnimalKingdom &&
                   playerArea.countKingdoms(Kingdom.PLANTKINGDOM) >= countPlantKingdom &&
                   playerArea.countKingdoms(Kingdom.FUNGIKINGDOM) >= countFungiKingdom &&
                   playerArea.countKingdoms(Kingdom.INSECTKINGDOM) >= countInsectKingdom;
        }

        // The played card isn't a gold card, so return true
        return true;
    }

    /**
     * Method for calculate total points scored by the player given the three objectives (2 common + secret)
     *
     * @param commonObjectives representing the two common objective for all players
     * @return number of points scored by player
     * @author Lorenzo Foini
     */
    public int calculateObjectivePoints(ObjectiveCard[] commonObjectives){
        // Collect all three objectives
        ObjectiveCard[] objectives = new ObjectiveCard[3];
        objectives[0] = commonObjectives[0];
        objectives[1] = commonObjectives[1];
        objectives[2] = secretObjective;

        int totalPoint = 0;

        // Points scored with objectives
        for(ObjectiveCard objective : objectives){
            if(objective.getFrontKingdom() == Kingdom.NONE){ // Points given by number of objects
                int totalObject = 0;
                int minOccurrence = 0;
                GameObject[] gameObjects = objective.getObjects();

                if(gameObjects[0] == GameObject.MANUSCRIPT){ // Case two manuscripts
                    totalObject += playerArea.countObject(GameObject.MANUSCRIPT);
                }else if(gameObjects[0] == GameObject.INKWELL){ // Case two inkwells
                    totalObject += playerArea.countObject(GameObject.INKWELL);
                }else if(gameObjects[1] == GameObject.QUILL){ // Case two quills
                    totalObject += playerArea.countObject(GameObject.QUILL);
                }else{ // Case all three objects
                    minOccurrence += Math.min(Math.min(playerArea.countObject(GameObject.QUILL), playerArea.countObject(GameObject.INKWELL)), playerArea.countObject(GameObject.MANUSCRIPT));
                }
                // Assign points
                totalPoint += (totalObject/2)*2 + minOccurrence*3;
            }else if(objective.getPattern() != Pattern.NONE){ // Points given by number of patterns
                Pattern pattern = objective.getPattern();
                int numPatterns = playerArea.countPattern(objective.getFrontKingdom(), pattern);
                if(pattern == Pattern.PRIMARYDIAGONAL || pattern == Pattern.SECONDARYDIAGONAL){ // Two points
                    totalPoint += numPatterns*2;
                }else{ // three points
                    totalPoint += numPatterns*3;
                }
            }else{ // Points given by number of resources
                int totalResources = playerArea.countKingdoms(objective.getFrontKingdom());
                totalPoint += (totalResources/3)*2;
            }
        }
        return totalPoint;
    }
}