package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class PriorityAntennaTest {
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
    void playerOrder() {
        gameController.board.getSpace(4,4).getActions().add(new PriorityAntenna());
        gameController.board.setPriorityAntenna(gameController.board.getSpace(4,4));

        gameController.board.getPlayer(0).setSpace(gameController.board.getSpace(0,7));
        gameController.board.getPlayer(1).setSpace(gameController.board.getSpace(0,0));
        gameController.board.getPlayer(2).setSpace(gameController.board.getSpace(4,3));
        Player[] playerList = ((PriorityAntenna) gameController.board.getPriorityAntenna().getActions().get(0)).playerOrder(gameController.board,gameController.board.getPriorityAntenna().x,gameController.board.getPriorityAntenna().y);
        assertEquals(playerList[0],gameController.board.getPlayer(4));
        gameController.board.getPlayer(1);
    }
}