package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LaserTest {
    GameController gameController;

    @BeforeEach
    void setUp() {
        Board board = new Board(8, 8);
        gameController = new GameController(board);
        for (int i = 0; i < 6; i++) {
            Player player = new Player(board, null, "Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(i, i));
            player.setHeading(Heading.values()[i % Heading.values().length]);
        }
        board.setCurrentPlayer(board.getPlayer(0));
    }

    @Test
    void doAction() {
        gameController.board.getSpace(0,1).setPlayer(gameController.board.getPlayer(0));
        gameController.board.getSpace(0,1).getActions().add(new Laser());
        gameController.board.getSpace(0,1).getActions().get(0).doAction(gameController,gameController.board.getSpace(0,1));

        assertEquals(gameController.board.getPlayer(0).discardPile.get(0).command.value,10);
    }
}