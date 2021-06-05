package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.RoboRally;
import dk.dtu.compute.se.pisd.roborally.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppControllerTest {

    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;

    private GameController gameController;
    private RoboRally roboRally;

    private AppController appController;

    @BeforeEach
    void setUp() {
        appController = new AppController(roboRally);
        Board board = new Board(TEST_WIDTH, TEST_HEIGHT);
        gameController = new GameController(board);
        for (int i = 0; i < 6; i++) {
            Player player = new Player(board, null, "Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(i, i));
            player.setHeading(Heading.values()[i % Heading.values().length]);
        }
        board.setCurrentPlayer(board.getPlayer(0));
    }

    @AfterEach
    void tearDown() {
        gameController = null;
    }

    @Test
    void newGame() {
        Assertions.assertEquals(0, 0);
    }

    @Test
    void loadGame() {
        Assertions.assertEquals(0, 0);
    }

    @Test
    void saveGame() {
        Assertions.assertEquals(0, 0);
    }

    @Test
    void stopGame() {
        Assertions.assertEquals(0, 0);
    }

    @Test
    void isGameRunning() {
        Assertions.assertEquals(0, 0);
    }

}