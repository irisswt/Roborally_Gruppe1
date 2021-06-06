package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * @author Niklas, s205454
*/

class GameControllerTest {

    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;

    private GameController gameController;
    private Board board;

    /*
     * Setting up test environment
     */
    @BeforeEach
    void setUp() {
        board = new Board(TEST_WIDTH, TEST_HEIGHT);
        gameController = new GameController(board);
        for (int i = 0; i < 6; i++) {
            Player player = new Player(board, null, "Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(i, i));
            player.setHeading(Heading.values()[i % Heading.values().length]);
        }
        board.setCurrentPlayer(board.getPlayer(0));
    }

    /*
     * Restart test environment
     */
    @AfterEach
    void tearDown() {
        gameController = null;
    }

    /*
     * Start programming phase
     * Check if phase == PROGRAMMING and step == 0
     */
    @Test
    void startProgrammingPhase() {
        gameController.startProgrammingPhase();

        Assertions.assertEquals(Phase.PROGRAMMING, board.getPhase());
        Assertions.assertEquals(0, board.getStep());
    }

    /*
     * Finish programming phase
     * Check if phase == ACTIVATION and step == 0
     */
    @Test
    void finishProgrammingPhase() {

        gameController.finishProgrammingPhase();

        Assertions.assertEquals(Phase.ACTIVATION, board.getPhase());
        Assertions.assertEquals(0, board.getStep());

    }

}