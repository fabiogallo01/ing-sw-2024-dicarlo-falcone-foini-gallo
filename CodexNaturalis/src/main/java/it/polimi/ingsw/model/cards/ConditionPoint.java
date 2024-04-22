package it.polimi.ingsw.model.cards;

/**
 * Enumeration of different ways to score points with gold cards.
 *
 * @author giacomofalcone
 */

public enum ConditionPoint {

    /**
     * Get points for every quill on the board
     */
    QUILL,

    /**
     * Get points for every inkwell on the board
     */
    INKWELL,

    /**
     * Get points for every manuscript on the board
     */
    MANUSCRIPT,

    /**
     * Get points for every hidden corner from the played card
     */
    HIDDENCORNER,

    /**
     * Get points without conditions
     */
    NONE
}