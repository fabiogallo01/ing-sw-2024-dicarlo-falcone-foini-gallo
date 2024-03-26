package it.polimi.ingsw.model.game;
import java.util.*;
public class Scoreboard {
    HashMap<Player, Integer> scores;

    public Scoreboard(HashMap<Player, Integer> scores) {
        this.scores = scores;
    }

    public Scoreboard(HashMap<Player, Integer> scores) {
        this.scores = scores;
    }

    public HashMap<Player, Integer> getScores() {
        return scores;
    }

    public void setScores(HashMap<Player, Integer> scores) {
        this.scores = scores;
    }

}
