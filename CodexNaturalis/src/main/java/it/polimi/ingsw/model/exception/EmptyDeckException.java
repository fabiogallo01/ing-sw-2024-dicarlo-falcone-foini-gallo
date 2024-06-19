package it.polimi.ingsw.model.exception;

/**
 * Exception raised when player tries to draw form an empty gaming deck (resource, gold, starter)
 *
 * @author Gallo Fabio
 */
public class EmptyDeckException extends Exception{
    public EmptyDeckException(String message) {super(message);}
}