package it.polimi.ingsw.model.game;

import java.util.*;

/**
 * Class to handle the scoreboard
 *
 * @author Fabio Gallo
 */
public class Scoreboard {
    private HashMap<Player, Integer> scores;

    /**
     * Scoreboard constructor
     *
     * @author Fabio Gallo
     */
    public Scoreboard() {
        scores = new HashMap<>();
    }

    /**
     * Scoreboard getter
     *
     * @return the scoreboard
     * @author Fabio Gallo
     */
    public HashMap<Player, Integer> getScores() {
        return scores;
    }

    /**
     * Scoreboard setter
     *
     * @param scores an updated version of the scoreboard
     * @author Fabio Gallo
     */
    public void setScores(HashMap<Player, Integer> scores) {
        this.scores = scores;
    }


    /**
     * To set or update the score of a specific player
     *
     * @param player Player whose score you want to set
     * @param score  the score
     * @author Fabio Gallo
     */
    public void setScore(Player player, int score) {
        scores.put(player, score);
    }
}