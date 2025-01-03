package it.polimi.ingsw.model.exception;

/**
 * Exception raised when try to add card in player's hand but is has already three cards
 *
 * @author Foini Lorenzo
 */
public class HandAlreadyFullException extends Exception{
    public HandAlreadyFullException(String message) {super(message);}
}