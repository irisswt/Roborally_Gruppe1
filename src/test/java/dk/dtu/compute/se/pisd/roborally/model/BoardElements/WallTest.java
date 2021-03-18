package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;



class WallTest {


    private final int TEST_WIDTH = 75;
    private final int TEST_HEIGHT = 75;

    private GameController gameController;

    @BeforeEach
    void setUp() {
        Board board = new Board(TEST_WIDTH, TEST_HEIGHT);
        gameController = new GameController(board);
        for (int i = 0; i < 6; i++) {
            Player player = new Player(board, null,"Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(i, i));
            player.setHeading(Heading.WEST);
        }
        board.setCurrentPlayer(board.getPlayer(0));
    }

    @AfterEach
   void tearDown() {
        gameController = null;
    }

    @Test
    void move() {
        Board board = gameController.board;
        Player player1 = board.getPlayer(0);
        player1.setCheckpoint(0);
        player1.setSpace(board.getSpace(2,2));
        board.setCurrentPlayer(player1);
        gameController.moveForward(player1);
        assertEquals( player1.getSpace(),board.getSpace(2,2));
        player1.setHeading(Heading.NORTH);
        gameController.moveForward(player1);
        assertEquals( player1.getSpace(),board.getSpace(2,2));
        player1.setHeading(Heading.EAST);
        gameController.moveForward(player1);
        assertEquals( player1.getSpace(),board.getSpace(2,2));
        player1.setHeading(Heading.SOUTH);
        gameController.moveForward(player1);
        assertEquals( player1.getSpace(),board.getSpace(2,2));
    }
}