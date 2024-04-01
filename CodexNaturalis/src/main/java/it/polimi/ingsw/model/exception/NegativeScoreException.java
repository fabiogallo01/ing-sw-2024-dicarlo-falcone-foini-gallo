package it.polimi.ingsw.model.exception;
public class NegativeScoreException extends Exception {
    public NegativeScoreException(String message) {
        super(message);
    }
}