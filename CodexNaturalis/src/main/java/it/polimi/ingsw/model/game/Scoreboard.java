package it.polimi.ingsw.model.game;

import java.util.*;

/**
 * Class to handle the scoreboard
 *
 * @author Gallo Fabio
 */
public class Scoreboard {
    // HashMap for couples (Player -> score)
    private HashMap<Player, Integer> scores;

    /**
     * Scoreboard constructor, it creates a new HashMap
     *
     * @author Gallo Fabio
     */
    public Scoreboard() {
        scores = new HashMap<>();
    }

    /**
     * Scoreboard getter
     *
     * @return the scoreboard
     * @author Gallo Fabio
     */
    public HashMap<Player, Integer> getScores() {
        return scores;
    }

    /**
     * Scoreboard setter
     *
     * @param scores an updated version of the scoreboard
     * @author Gallo Fabio
     */
    public void setScores(HashMap<Player, Integer> scores) {
        this.scores = scores;
    }


    /**
     * Method for setting or updating the score of a specific player
     * If the player doesn't exist yet in the scoreboard, then he will be added with his score
     *
     * @param player: player whose score you want to set
     * @param score: player's score
     * @author Gallo Fabio
     */
    public void setScore(Player player, int score) {
        scores.put(player, score);
    }
}