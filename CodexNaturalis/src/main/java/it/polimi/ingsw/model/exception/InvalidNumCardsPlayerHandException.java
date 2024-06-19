package it.polimi.ingsw.model.exception;

/**
 * Exception raised when try to assign an invalid number of cards (not three) to player's hand
 *
 * @author Foini Lorenzo
 */
public class InvalidNumCardsPlayerHandException extends Exception{
    public InvalidNumCardsPlayerHandException(String message) {
        super(message);
    }
}