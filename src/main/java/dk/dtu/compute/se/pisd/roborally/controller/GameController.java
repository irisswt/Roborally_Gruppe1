/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.controller;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.Gear;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.Pit;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.PushPanel;
import dk.dtu.compute.se.pisd.roborally.model.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * GameController contains the logic for the game. Is responsible for the logic
 * behind taking a turn and the movement of the players robots.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameController {

    final public Board board;
    public RobotController robotController;

    /**
     * The constructor for GameController
     *
     * @param board the gameboard itself.
     */
    public GameController(@NotNull Board board) {
        this.board = board;
        robotController = new RobotController(board, this);
    }

    /**
     * This method is the start of the programming phase, and fills each players
     * programming field with programming cards.
     */
    public void startProgrammingPhase() {
        board.setPhase(Phase.PROGRAMMING);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    field.setCard(null);
                    field.setVisible(true);
                }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);
                    field.setCard(generateRandomCommandCard());
                    field.setVisible(true);
                }
            }
        }
    }

    /**
     * A method to take a random card from an array created from the Command class
     * enums.
     *
     * @return a random generated CommandCard in-game known as a programming card
     */
    private CommandCard generateRandomCommandCard() {
        Command[] commands = Command.values();
        int random = (int) (Math.random() * commands.length);
        return new CommandCard(commands[random]);
    }
    /**
     * A method to declare the programming phase over. Will make the
     * command/programming cards invisible in the GUI and set the phase to
     * ACTIVATION instead of PROGRAMMING. Sets the first register visible and the
     * rest invisible
     */
    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);
    }

    /**
     * Makes the current programming card that's being executed visible.
     *
     * @param register the current register that needs to be made visible.
     */
    private void makeProgramFieldsVisible(int register) {
        if (register >= 0 && register < Player.NO_REGISTERS) {
            for (int i = 0; i < board.getPlayersNumber(); i++) {
                Player player = board.getPlayer(i);
                CommandCardField field = player.getProgramField(register);
                field.setVisible(true);
            }
        }
    }

    /**
     * Method to make all registers empty in the GUI. Is used in
     * finishProgrammingPhase method.
     */
    private void makeProgramFieldsInvisible() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }

    /**
     * Executes all the cards in the registers.
     */
    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }

    /**
     * Executes a single register.
     */
    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }

    /**
     * Executes next card. Depending on the stepMode either runs a single time or
     * continuously.
     */
    private void continuePrograms() {
        do {
            executeNextStep();
        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    /**
     * Checks for which step the game is on and which players turn it is. Then it
     * executes the current card in the register for the current player. If the card
     * has an interaction it will set the phase to PLAYER_INTERACTION.
     */
    // TODO: here is end register
    private void executeNextStep() {
        try {
            endRegister(board.getCurrentPlayer());
        } catch (ImpossibleMoveException e) {

        }
        Player currentPlayer = board.getCurrentPlayer();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                if (card != null) {
                    Command command = card.command;
                    if (command.isInteractive()) {
                        board.setPhase(Phase.PLAYER_INTERACTION);
                        return;
                    }
                    executeCommand(currentPlayer, command);
                }
                int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
                if (nextPlayerNumber < board.getPlayersNumber()) {
                    board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
                } else {
                    step++;
                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step);
                        board.setStep(step);
                        board.setCurrentPlayer(board.getPlayer(0));
                    } else {
                        startProgrammingPhase();
                    }
                }
            } else {
                // this should not happen
                assert false;
            }
        } else {
            // this should not happen
            assert false;
        }
    }

    /**
     * Will execute the option chosen by a player and continue the game. Needs to be
     * adjusted later to remove duplicated code.
     *
     * @param option the option the player has chosen from the interactive card.
     */
    public void executeCommandOptionAndContinue(@NotNull Command option) {
        Player currentPlayer = board.getCurrentPlayer();
        if (currentPlayer != null && board.getPhase() == Phase.PLAYER_INTERACTION && option != null) {
            board.setPhase(Phase.ACTIVATION);
            executeCommand(currentPlayer, option);
            int nextPlayerNumber = board.getPlayerNumber(currentPlayer) + 1;
            if (nextPlayerNumber < board.getPlayersNumber()) {
                board.setCurrentPlayer(board.getPlayer(nextPlayerNumber));
            } else {
                int step = board.getStep() + 1;
                if (step < Player.NO_REGISTERS) {
                    makeProgramFieldsVisible(step);
                    board.setStep(step);
                    board.setCurrentPlayer(board.getPlayer(0));
                } else {
                    startProgrammingPhase();
                }
            }
        }
    }

    /**
     * The actual logic for moving the players robot
     *
     * @param player  the player that needs to move
     * @param command command depending on the card chosen. Will dictate how the
     *                player will move.
     */
    public void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            // XXX This is a very simplistic way of dealing with some basic cards and
            // their execution. This should eventually be done in a more elegant way
            // (this concerns the way cards are modelled as well as the way they are
            // executed).

            switch (command) {
                case FORWARD:
                    robotController.moveForward(player);
                    break;
                case RIGHT:
                    robotController.turnRight(player);
                    break;
                case LEFT:
                    robotController.turnLeft(player);
                    break;
                case FAST_FORWARD:
                    robotController.fastForward(player);
                    break;
                case MOVE_THREE_FORWARD:
                    robotController.moveThreeForward(player);
                    break;
                case U_TURN:
                    robotController.uTurn(player);
                    break;
                case BACKWARD:
                    robotController.moveBackward(player);
                    break;
                case AGAIN:
                    robotController.again(player);
                    break;
                default:
                    // DO NOTHING (for now)
            }
        }
    }

    /**
     * Checks if a player is standing on a board-element, and makes it act
     * corresponding to said board-element
     *
     * @param player that needs to be checked
     * @throws ImpossibleMoveException if the player is going to make an illegal
     *                                 move
     */

    public void endRegister(Player player) throws ImpossibleMoveException {

        Space space = player.getSpace();
        for (FieldAction action : space.getActions()) {
            if (action instanceof ConveyorBelt)
                for (int i = 0; i < ((ConveyorBelt) action).getSpeed(); i++) {
                    Heading heading = ((ConveyorBelt) action).getHeading();
                    Space target = board.getNeighbour(player.getSpace(), heading);
                    if (target != null) {
                        try {
                            robotController.moveToSpace(player, target, heading);
                        } catch (ImpossibleMoveException e) {

                        }

                    }

                    //FIXME: Delete if not used
                    /*
                     * if (board.getNeighbour(space, ((ConveyorBelt)
                     * action).getHeading()).getPlayer() == null) { moveToSpace(player,
                     * board.getNeighbour(space, ((ConveyorBelt) action).getHeading()),
                     * ((ConveyorBelt) action).getHeading()); }
                     * 
                     */

                }

            if (action instanceof PushPanel) {
                Heading heading = ((PushPanel) action).getHeading();
                Space target = board.getNeighbour(player.getSpace(), heading);
                if (target != null) {
                    try {
                        robotController.moveToSpace(player, target, heading);
                    } catch (ImpossibleMoveException e) {

                    }

                }

            }

            if (action instanceof Pit) {

            }

            if (action instanceof Gear) {
                Heading playerHeading = player.getHeading();
                player.setHeading(playerHeading.next());
            }

            space = player.getSpace();
            space.landOnSpace();
            if (!space.getActions().isEmpty()) {
                space.getActions().get(0).doAction(this, space);
            }
        }
    }

    /**
     * A method to move a card from on space to another. Is used in the programming
     * phase to move cards from the players card field to the register and vice
     * versa.
     * 
     * @param source Which field the card is placed on.
     * @param target The field the card is ending on.
     * @return a true if a card has been moved and a false if not.
     */
    public boolean moveCards(@NotNull CommandCardField source, @NotNull CommandCardField target) {
        CommandCard sourceCard = source.getCard();
        CommandCard targetCard = target.getCard();
        if (sourceCard != null && targetCard == null) {
            target.setCard(sourceCard);
            source.setCard(null);
            return true;
        } else {
            return false;
        }
    }

    /**
     * A method called when no corresponding controller operation is implemented
     * yet. This should eventually be removed.
     */
    public void notImplemented() {
        // XXX just for now to indicate that the actual method is not yet implemented
        assert false;
    }

}
