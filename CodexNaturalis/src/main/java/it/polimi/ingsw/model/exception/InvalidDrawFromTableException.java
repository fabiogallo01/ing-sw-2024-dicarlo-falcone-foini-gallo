package it.polimi.ingsw.model.exception;

/**
 * Exception raised when player tries to draw from the visible cards in the table, but it failed
 *
 * @author Lorenzo Foini
 */
public class InvalidDrawFromTableException extends Exception{
    public InvalidDrawFromTableException(String message) {super(message);}
}