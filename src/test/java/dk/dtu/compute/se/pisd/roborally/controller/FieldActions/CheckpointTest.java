package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckpointTest {


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
        Board board  = gameController.board;
        board.getPlayer(0).setCheckpoint(0);
        board.getPlayer(1).setCheckpoint(1);

        board.getSpace(0,0).getActions().add(new Checkpoint());
        ((Checkpoint)board.getSpace(0,0).getActions().get(0)).setCheckpointnumber(2);
        board.getSpace(0,0).setPlayer(board.getPlayer(0));
        board.getSpace(0,0).getActions().get(0).doAction(gameController,board.getSpace(0,0));
        board.getSpace(0,0).setPlayer(null);
        board.getSpace(0,0).setPlayer(board.getPlayer(1));
        board.getSpace(0,0).getActions().get(0).doAction(gameController,board.getSpace(0,0));

        assertEquals(board.getPlayer(0).getCheckpoint(),0);
        assertEquals(board.getPlayer(1).getCheckpoint(),2);


    }

}