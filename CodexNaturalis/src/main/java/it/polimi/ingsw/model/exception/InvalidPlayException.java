package it.polimi.ingsw.model.exception;

/**
 * Exception raised when player plays a card in an invalid position in his game area
 *
 * @author Foini Lorenzo
 */
public class InvalidPlayException extends Exception{
    public InvalidPlayException(String message) {
        super(message);
    }
}
