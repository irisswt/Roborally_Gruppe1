package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/*
* @author Niklas Jessen
*/
class GearTest {


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
        FieldAction fieldAction;
        fieldAction = new Gear();
        Space space = gameController.board.getSpace(2, 2);
        Player player = new Player(gameController.board, "green", "P");
        space.setPlayer(player);
        player.setHeading(Heading.SOUTH);
        fieldAction.doAction(gameController, space);
        Player playerOnSpace = space.getPlayer();
        assertEquals(Heading.WEST, playerOnSpace.getHeading());
    }

}