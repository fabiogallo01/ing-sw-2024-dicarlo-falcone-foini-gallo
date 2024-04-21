package it.polimi.ingsw;

import it.polimi.ingsw.model.game.GameTable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameTableTest {

    GameTable gameTable = null;

    @Before
    public void setUp() {
        gameTable = new GameTable();
    }

    @After
    public void tearDown()
    {}

}
