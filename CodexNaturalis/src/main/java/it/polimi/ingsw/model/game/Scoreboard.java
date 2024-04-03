package it.polimi.ingsw.model.game;
import java.util.*;

public class Scoreboard {
    private HashMap<Player, Integer> scores;
    public Scoreboard() {
        scores = new HashMap<Player, Integer>();
    }

    public HashMap<Player, Integer> getScores() {
        return scores;
    }


    public void setScores(HashMap<Player, Integer> scores) {
        this.scores = scores;
    }

    public void setScore(Player player, int score){
        scores.put(player, score);
    }
}


