package it.polimi.ingsw.model.exception;

/**
 * Exception raised when try to assign a negative point to a player's score
 *
 * @author Foini Lorenzo
 */
public class NegativeScoreException extends Exception {
    public NegativeScoreException(String message) {
        super(message);
    }
}