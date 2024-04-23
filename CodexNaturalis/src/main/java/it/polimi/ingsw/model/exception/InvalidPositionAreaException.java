package it.polimi.ingsw.model.exception;

/**
 * Exception raised when player select an out of bound matrix indexes for where to play the card
 *
 * @author Lorenzo Foini
 */
public class InvalidPositionAreaException extends Exception{
    public InvalidPositionAreaException(String message) {
        super(message);
    }
}
