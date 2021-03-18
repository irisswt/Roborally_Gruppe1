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

import dk.dtu.compute.se.pisd.roborally.model.*;
import org.jetbrains.annotations.NotNull;

/**
 * GameController contains the logic for the game. Is responsible for the logic behind taking a turn
 * and the movement of the players robots.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameController {

    final public Board board;

    /**
     * The constructor for GameController
     * @param board the gameboard itself.
     */
    public GameController(@NotNull Board board) {
        this.board = board;
    }

    /**
     * This is just some dummy controller operation to make a simple move to see something
     * happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space)  {
        // TODO Assignment V1: method should be implemented by the students:
        //   - the current player should be moved to the given space
        //     (if it is free()
        //   - and the current player should be set to the player
        //     following the current player
        //   - the counter of moves in the game should be increased by one
        //     if the player is moved
        if(space.getPlayer() == null)
        {
            board.getCurrentPlayer().setSpace(space);
            board.setStep(board.getStep() + 1);
            board.setCurrentPlayer(board.getPlayer((board.getPlayerNumber(board.getCurrentPlayer()) + 1) % board.getPlayersNumber()));
        }
    }

    /**
     * This method is the start of the programming phase,
     * and fills each players programming field with programming cards.
     */
    // XXX: V2
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
     * A method to take a random card from an array created from the Command class enums.
     * @return a random generated CommandCard in-game known as a programming card
     */
    // XXX: V2
    private CommandCard generateRandomCommandCard() {
        Command[] commands = Command.values();
        int random = (int) (Math.random() * commands.length);
        return new CommandCard(commands[random]);
    }

    /**
     * A method to declare the programming phase over.
     * Will make the command/programming cards invisible in the GUI and set the phase to ACTIVATION instead of PROGRAMMING.
     * Sets the first register visible and the rest invisible
     */
    // XXX: V2
    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.setCurrentPlayer(board.getPlayer(0));
        board.setStep(0);
    }

    /**
     * Makes the current programming card that's being executed visible.
     * @param register the current register that needs to be made visible.
     */
    // XXX: V2
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
     * Method to make all registers empty in the GUI.
     * Is used in finishProgrammingPhase method.
     */
    // XXX: V2
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
    // XXX: V2
    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }

    /**
     * Executes a single register.
     */
    // XXX: V2
    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }

    /**
     * Executes next card. Depending on the stepMode either runs a single time or continuously.
     */
    // XXX: V2
    private void continuePrograms() {
        do {
            executeNextStep();
        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    /**
     * Checks for which step the game is on and which players turn it is.
     * Then it executes the current card in the register for the current player.
     * If the card has an interaction it will set the phase to PLAYER_INTERACTION.
     */
    // XXX: V2
    private void executeNextStep() {
        Player currentPlayer = board.getCurrentPlayer();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                if (card != null) {
                    Command command = card.command;
                    if(command.isInteractive())
                    {
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
     * Will execute the option chosen by a player and continue the game.
     * Needs to be adjusted later to remove duplicated code.
     * @param option the option the player has chosen from the interactive card.
     */
    public void executeCommandOptionAndContinue(@NotNull Command option)
    {
        Player currentPlayer = board.getCurrentPlayer();
        if(currentPlayer!=null && board.getPhase() == Phase.PLAYER_INTERACTION && option != null)
        {
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
     * @param player the player that needs to move
     * @param command command depending on the card chosen. Will dictate how the player will move.
     */
    // XXX: V2
    private void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            // XXX This is a very simplistic way of dealing with some basic cards and
            //     their execution. This should eventually be done in a more elegant way
            //     (this concerns the way cards are modelled as well as the way they are executed).

            switch (command) {
                case FORWARD:
                    this.moveForward(player);
                    break;
                case RIGHT:
                    this.turnRight(player);
                    break;
                case LEFT:
                    this.turnLeft(player);
                    break;
                case FAST_FORWARD:
                    this.fastForward(player);
                    break;
                case MOVE_THREE_FORWARD:
                    this.moveThreeForward(player);
                    break;
                case U_TURN:
                    this.uTurn(player);
                    break;
                case BACKWARD:
                    this.moveBackward(player);
                    break;
                case AGAIN:
                    this.again(player);
                    break;
                default:
                    // DO NOTHING (for now)
            }
        }
    }

    /**
     * Method to move the player one field forward if nothing is occupying the space.
     * @param player the player that needs to move
     */
    // TODO Assignment V2
    public void moveForward(@NotNull Player player) {
        Heading heading = player.getHeading();
        Space target = board.getNeighbour(player.getSpace(), heading);
        if(target != null)
        {
            try
            {
             moveToSpace(player, target, heading);
            } catch (ImpossibleMoveException e){

            }

        }

    }
    public void moveToSpace(Player player, Space space, Heading heading) throws ImpossibleMoveException {
        Player other = space.getPlayer();
        if(other != null){
            Space target = board.getNeighbour(space,heading);
            if (target != null){
                moveToSpace(other, target, heading);
            }else{
                throw new ImpossibleMoveException(player,space,heading);
            }
        }
        player.setSpace(space);
    }


    /**
     * Moves the player forward twice.
     * @param player the player that needs to move
     */
    // TODO Assignment V2
    public void fastForward(@NotNull Player player) {
        moveForward(player);
        moveForward(player);
    }

    /**
     * Moves the player three fields forward
     * @param player the player that needs to move
     */
    public void moveThreeForward(@NotNull Player player) {
        fastForward(player);
        moveForward(player);
    }

    /**
     * Turns the player right without moving them.
     * @param player the player that needs to move
     */
    // TODO Assignment V2
    public void turnRight(@NotNull Player player) {
        player.setHeading(player.getHeading().next());
    }

    /**
     * Turns the player left without moving them.
     * @param player the player that needs to move
     */
    // TODO Assignment V2
    public void turnLeft(@NotNull Player player) {
        player.setHeading(player.getHeading().prev());
    }

    /**
     * Moves the player one field backwards without turning around.
     * @param player the player that needs to move
     */
    public void moveBackward(@NotNull Player player) {
        Space target = board.getNeighbour(player.getSpace(), player.getHeading().next().next());
        if(target!=null && target.getPlayer() == null)
        {
            player.setSpace(target);
        }
    }

    /**
     * Turns the player around to face the opposite direction without moving.
     * @param player the player that gets turned around
     */
    public void uTurn(@NotNull Player player) {
        player.setHeading(player.getHeading().next().next());
    }

    /**
     * Currently not working with interactive cards
     * @param player
     */
    public void again(@NotNull Player player){
        if(board.getStep()!=0){
            int i = board.getStep();
            int j = 0;
            while(player.getProgramField(i).getCard().command == Command.AGAIN && board.getStep() != 0){
                i--;
                j++;
            }
            if(player.getProgramField(board.getStep()-j).getCard().command.isInteractive()){
                executeCommandOptionAndContinue(player.getProgramField(board.getStep()-j).getCard().command);
            }
            else{
                executeCommand(player, player.getProgramField(board.getStep()-j).getCard().command);
            }

        }
    }



    /**
     * A method to move a card from on space to another.
     * Is used in the programming phase to move cards from the players card field to the register and vice versa.
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
     * A method called when no corresponding controller operation is implemented yet. This
     * should eventually be removed.
     */
    public void notImplemented() {
        // XXX just for now to indicate that the actual method is not yet implemented
        assert false;
    }

}
