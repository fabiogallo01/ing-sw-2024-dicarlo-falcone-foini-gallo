package it.polimi.ingsw.model.exception;

/**
 * Exception raised when player tries to draw from the visible cards in the table, but it failed
 *
 * @author Foini Lorenzo
 */
public class InvalidDrawFromTableException extends Exception{
    public InvalidDrawFromTableException(String message) {super(message);}
}