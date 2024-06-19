package it.polimi.ingsw.model.exception;

/**
 * Exception raised when player select an out of bound index for selecting the card to be played
 *
 * @author Foini Lorenzo
 */
public class InvalidPlayCardIndexException extends Exception{
    public InvalidPlayCardIndexException(String message) {
        super(message);
    }
}