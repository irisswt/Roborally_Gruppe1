package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.Gear;
import dk.dtu.compute.se.pisd.roborally.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class GameControllerTest {

    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;

    private GameController gameController;

    @BeforeEach
    void setUp() {
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
    void moveBackward() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        gameController.fastForward(current);

        gameController.moveBackward(current);

        Assertions.assertEquals(current, board.getSpace(0, 1).getPlayer(),
                "Player " + current.getName() + " should beSpace (0,1)!");
        Assertions.assertEquals(Heading.SOUTH, current.getHeading(), "Player 0 should be heading SOUTH!");
        Assertions.assertNull(board.getSpace(0, 0).getPlayer(), "Space (0,0) should be empty!");
        Assertions.assertNull(board.getSpace(0, 2).getPlayer(), "Space (0,2) should be empty!");
    }

    @Test
    void moveForward() throws ImpossibleMoveException {
        Board board = gameController.board;
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        gameController.moveToSpace(player1, board.getSpace(0,0),Heading.SOUTH );
        gameController.moveToSpace(player2,board.getSpace(0,1),Heading.SOUTH);
        player1.setHeading(Heading.SOUTH);
        gameController.moveForward(player1);
        Assertions.assertEquals(player1,board.getSpace(0,1).getPlayer(),"Player " + player1.getName() + " should beSpace (0,1)!");
        Assertions.assertEquals(player2,board.getSpace(0,2).getPlayer(),"Player " + player2.getName() + " should beSpace (0,1)!");

    }

    @Test
    void fastForward() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        gameController.fastForward(current);

        Assertions.assertEquals(current, board.getSpace(0, 2).getPlayer(),
                "Player " + current.getName() + " should beSpace (0,2)!");
        Assertions.assertEquals(Heading.SOUTH, current.getHeading(), "Player 0 should be heading SOUTH!");
        Assertions.assertNull(board.getSpace(0, 0).getPlayer(), "Space (0,0) should be empty!");
    }

    @Test
    void moveThreeForward() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        gameController.moveThreeForward(current);

        Assertions.assertEquals(current, board.getSpace(0, 3).getPlayer(),
                "Player " + current.getName() + " should beSpace (0,3)!");
        Assertions.assertEquals(Heading.SOUTH, current.getHeading(), "Player 0 should be heading SOUTH!");
        Assertions.assertNull(board.getSpace(0, 0).getPlayer(), "Space (0,0) should be empty!");
        Assertions.assertNull(board.getSpace(0, 1).getPlayer(), "Space (0,1) should be empty!");
        Assertions.assertNull(board.getSpace(0, 2).getPlayer(), "Space (0,2) should be empty!");
    }

    @Test
    void turnRight() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        gameController.turnRight(current);
        Assertions.assertEquals(Heading.WEST, current.getHeading(), "Player 0 should be heading WEST!");

    }

    @Test
    void turnLeft() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();

        gameController.turnLeft(current);
        Assertions.assertEquals(Heading.EAST, current.getHeading(), "Player 0 should be heading EAST!");

    }

    @Test
    void uTurn() {
        Board board = gameController.board;
        Player current = board.getCurrentPlayer();
        Heading oldHeading = current.getHeading();
        gameController.uTurn(current);
        Assertions.assertEquals(oldHeading.next().next(), current.getHeading(),
                "West should be East, North should be South, East should be West, South should be North");
    }
}