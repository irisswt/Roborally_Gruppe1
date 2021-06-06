package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.Checkpoint;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.Gear;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.PushPanel;
import dk.dtu.compute.se.pisd.roborally.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * @author Niklas, s205454
*/
class FieldActionsTest {

    private final int TEST_WIDTH = 8;
    private final int TEST_HEIGHT = 8;

    private GameController gameController;

    /*
     * Setting up test environment
     */
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

    /*
     * Restart test environment
     */
    @AfterEach
    void tearDown() {
        gameController = null;
    }

    /*
     * Tesing doAction for Checkpoint
     */
    @Test
    void CheckPoint() {
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

        Assertions.assertEquals(0, board.getPlayer(0).getCheckpoint());
        Assertions.assertEquals(2, board.getPlayer(1).getCheckpoint());


    }

    @Test
    void ConveyorBelt() {
        Assertions.assertEquals(0, 0);
    }

    /*
     * Tesing doAction for Gear
     */
    @Test
    void Gear() {
        FieldAction fieldAction;
        fieldAction = new Gear();
        Space space = gameController.board.getSpace(2, 2);
        Player player = new Player(gameController.board, "green", "P");
        space.setPlayer(player);
        player.setHeading(Heading.SOUTH);
        fieldAction.doAction(gameController, space);
        Player playerOnSpace = space.getPlayer();
        Assertions.assertEquals(Heading.WEST, playerOnSpace.getHeading());
    }

    @Test
    void Laser() {
        gameController.board.getSpace(0,1).setPlayer(gameController.board.getPlayer(0));
        gameController.board.getSpace(0,1).getActions().add(new Laser());
        gameController.board.getSpace(0,1).getActions().get(0).doAction(gameController,gameController.board.getSpace(0,1));

        Assertions.assertEquals(10, gameController.board.getPlayer(0).discardPile.get(0).command.value);
    }

    @Test
    void Pit() {
        Assertions.assertEquals(0, 0);
    }

    /*
     * Testing PriorityAntenna().playerOrder
     */
    @Test
    void PriorityAntenna() {
        gameController.board.getSpace(4,4).getActions().add(new PriorityAntenna());
        gameController.board.setPriorityAntenna(gameController.board.getSpace(4,4));

        gameController.board.getPlayer(0).setSpace(gameController.board.getSpace(0,8));
        gameController.board.getPlayer(1).setSpace(gameController.board.getSpace(0,0));
        gameController.board.getPlayer(2).setSpace(gameController.board.getSpace(4,3));
        Player[] playerList = ((PriorityAntenna) gameController.board.getPriorityAntenna().getActions().get(0)).playerOrder(gameController.board,gameController.board.getPriorityAntenna().x,gameController.board.getPriorityAntenna().y);
        Assertions.assertEquals(playerList[0],gameController.board.getPlayer(2));
        gameController.board.getPlayer(1);
    }

    /*
     * Testing PushPanel.setHeading()
     */
    @Test
    void PushPanel() {
        PushPanel pushPanel = new PushPanel();
        pushPanel.setHeading(Heading.SOUTH);
        Assertions.assertEquals(Heading.SOUTH, pushPanel.getHeading());
    }

    @Test
    void RebootTokens() {
        Assertions.assertEquals(0, 0);
    }

}