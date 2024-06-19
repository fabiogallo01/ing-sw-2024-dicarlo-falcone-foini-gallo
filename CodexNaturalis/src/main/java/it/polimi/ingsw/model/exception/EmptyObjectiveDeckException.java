package it.polimi.ingsw.model.exception;

/**
 * Exception raised when player tries to draw form objective deck, but it is empty
 *
 * @author Foini Lorenzo
 */
public class EmptyObjectiveDeckException extends Exception {
    public EmptyObjectiveDeckException(String message) {
        super(message);
    }
}