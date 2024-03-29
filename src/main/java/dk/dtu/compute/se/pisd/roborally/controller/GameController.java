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
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.*;
import dk.dtu.compute.se.pisd.roborally.model.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GameController contains the logic for the game. Is responsible for the logic
 * behind taking a turn and the movement of the players robots.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class GameController {

    final public Board board;

    private int CurrentPlayerIndex;

    private Player[] playerOrder;


    /**
     * The constructor for GameController
     *
     * @param board the gameboard itself.
     */
    public GameController(@NotNull Board board) {
        this.board = board;
    }

    /**
     * This is just some dummy controller operation to make a simple move to see
     * something happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space) {
        Player current = board.getCurrentPlayer();
        Boolean isOnGear = false;
        /*for (FieldAction action : space.getActions()){
            if (action instanceof Gear){
                isOnGear = true;
            }
        }*/
        if (space.getPlayer() == null && current != null && current.getInPit() && isOnGear) {
            current.setSpace(space);
            board.setPhase(Phase.ACTIVATION);
            executeNextStep();

        }
    }

    /**
     * This method is the start of the programming phase, and fills each players
     * programming field with programming cards.
     */
    public void startProgrammingPhase() {

        board.setPhase(Phase.PROGRAMMING);
        CurrentPlayerIndex = 0;
        playerOrder = ((PriorityAntenna)board.getPriorityAntenna().getActions().get(0)).playerOrder(board,board.getPriorityAntenna().x,board.getPriorityAntenna().y);
        board.setCurrentPlayer(playerOrder[CurrentPlayerIndex]);
        board.setStep(0);
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    field.setCard(null);
                    field.setVisible(true);
                }
                if(board.getPlayer(i).getCardPile().size()<Player.NO_CARDS) {

                    shuffelCards(board.getPlayer(i));
                }
                    for (int j = 0; j < Player.NO_CARDS; j++) {
                        CommandCardField field = player.getCardField(j);
                        if(field.getCard() == null) {
                            field.setCard(board.getPlayer(i).getCardPile().remove(0));
                        }
                        field.setVisible(true);
                    }

            }
        }
        for(int i = 0 ; i < board.getPlayersNumber();i++){
            if(board.getPlayer(i).getCheckpoint() >= board.getCheckpoints()){
                this.board.setPhase(Phase.INITIALISATION);

            }
        }
    }

    /**
     *Shuffles the cards from the discardPile into the CardPile
     * @param currentPlayer
     *
     * @author Louis Monty-Krohn
     */
    private void shuffelCards( Player currentPlayer){
        int runs =  currentPlayer.getDiscardPile().size();
        int random;
        for(int i = 0; i < runs;i++) {
            random = (int) (Math.random() * (currentPlayer.getDiscardPile().size()));
            currentPlayer.getCardPile().add(currentPlayer.getDiscardPile().remove(random));
        }

    }

    /**
     * A method to take a random card from an array created from the Command class
     * enums.
     *
     * @return a random generated CommandCard in-game known as a programming card
     */
    /*private CommandCard generateRandomCommandCard(Player player) {
        Command[] commands = Command.values();
        int random = (int) (Math.random() * (commands.length-4));
        if(player.getDamage() > 0){
          int damageCardsOnHand = 0;
            for(int i = 0; i<8; i++){
                if(player.getCardField(i).getCard()==null){}
                else if(player.getCardField(i).getCard().command == Command.SPAM){
                    damageCardsOnHand++;
                }
            }
            if(damageCardsOnHand<player.getDamage()){
            random = (int) (Math.random() * (commands.length-3));
            }

        else{
           random = (int) (Math.random() * (commands.length-4));
        }
        }
        return new CommandCard(commands[random]);




    }

        Command[] commands = Command.values();
        int random = (int) (Math.random() * (commands.length-4));
        return new CommandCard(commands[random]);
    }*/


    /**
     * A method to declare the programming phase over. Will make the
     * command/programming cards invisible in the GUI and set the phase to
     * ACTIVATION instead of PROGRAMMING. Sets the first register visible and the
     * rest invisible
     *
     * @author  Ekkart Kindler, ekki@dtu.dk
     * @author Louis Monty-Krohn
     */
    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        CurrentPlayerIndex = 0;
        board.setCurrentPlayer(playerOrder[CurrentPlayerIndex]);
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
     * @author Ekkart Kindler, ekki@dtu.dk
     * @author Louis Monty-Krohn
     * @author Jens Will Iversen
     */
    // TODO: here is end register
    private void executeNextStep() {


        Player currentPlayer = board.getCurrentPlayer();
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            int step = board.getStep();
            if (step >= 0 && step < Player.NO_REGISTERS) {
                CommandCard card = currentPlayer.getProgramField(step).getCard();
                if (card != null) {
                    currentPlayer.getDiscardPile().add(card);
                    Command command = card.command;
                    if (command.isInteractive()) {
                        board.setPhase(Phase.PLAYER_INTERACTION);
                        return;
                    }
                    executeCommand(currentPlayer, command);
                    if(board.getPhase() == Phase.PLAYER_INTERACTION){
                        return;
                    }
                }
                if(currentPlayer.getInPit()){
                    currentPlayer.setInPit(false);
                }
                CurrentPlayerIndex++;
                if (CurrentPlayerIndex < board.getPlayersNumber()) {
                    board.setCurrentPlayer(playerOrder[CurrentPlayerIndex]);
                } else {
                    try {
                        for (int i = 0; i < board.getPlayersNumber();i++){
                            endRegister(board.getPlayer(i));

                        }

                    } catch (ImpossibleMoveException e) {

                    }

                    step++;
                    if (step < Player.NO_REGISTERS) {
                        makeProgramFieldsVisible(step);
                        board.setStep(step);
                        CurrentPlayerIndex = 0;
                        board.setCurrentPlayer(playerOrder[CurrentPlayerIndex]);
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
     *
     * Will execute the option chosen by a player and continue the game. Needs to be
     * adjusted later to remove duplicated code.
     *
     * @param option the option the player has chosen from the interactive card.
     * @author Ekkart Kindler, ekki@dtu.dk
     * @author Louis Monty-Krohn
     */
    public void executeCommandOptionAndContinue(@NotNull Command option) {
        Player currentPlayer = board.getCurrentPlayer();
        if (currentPlayer != null && board.getPhase() == Phase.PLAYER_INTERACTION && option != null) {
            board.setPhase(Phase.ACTIVATION);
            executeCommand(currentPlayer, option);
            CurrentPlayerIndex++;
            if (CurrentPlayerIndex < board.getPlayersNumber()) {
                board.setCurrentPlayer(playerOrder[CurrentPlayerIndex]);
            } else {
                int step = board.getStep() + 1;
                if (step < Player.NO_REGISTERS) {
                    makeProgramFieldsVisible(step);
                    board.setStep(step);
                    CurrentPlayerIndex = 0;
                    board.setCurrentPlayer(playerOrder[CurrentPlayerIndex]);
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
     *
     */
    private void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            // XXX This is a very simplistic way of dealing with some basic cards and
            // their execution. This should eventually be done in a more elegant way
            // (this concerns the way cards are modelled as well as the way they are
            // executed).

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
                case SPAM:
                    this.spam(player);
                    break;
                default:
                    // DO NOTHING
            }
        }
    }

    /**
     * Method to move the player one field forward if nothing is occupying the
     * space.
     *
     * @param player the player that needs to move
     * @author Ekkart Kindler, ekki@dtu.dk
     * @author Jens Will Iversen
     * @author Jonathan Zørn
     */
    public void moveForward(@NotNull Player player) {
        Heading heading = player.getHeading();
        if (player.getSpace() != null){
            Space target = board.getNeighbour(player.getSpace(), player.getHeading());
            if (target != null) {
                try {
                    moveToSpace(player, target, heading);
                } catch (ImpossibleMoveException e) {

                }
            }else{
                reboot(player);
            }
        }
    }

    /**
     * @param player
     * @param heading
     * @return Boolean Checks if a move is possible in regard to tokens
     * @author Jens Will Iversen
     * @author Jonathan Zørn
     * @author Louis Monty-Krohn
     */
    public Boolean isMovePossible(Player player, Heading heading) {

        Space target = board.getNeighbour(player.getSpace(), heading);

        if (target != null) {
            List<Heading> wallHeadings = target.getWalls();
            if (!wallHeadings.isEmpty()) {
                for (Heading h : wallHeadings) {
                    if (h == heading) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }

        List<Heading> wallHeadings = player.getSpace().getWalls();
        if (!wallHeadings.isEmpty()) {
            for (Heading h : wallHeadings) {
                if (h == heading.next().next()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param player
     * @param space
     * @param heading
     * @throws ImpossibleMoveException Checks if the player can move to desired
     *                                 space. If another player is on that space, it
     *                                 tries to move the second player before the
     *                                 first one. If a player cannot move, the
     *                                 impossible move exception is thrown
     * @author Ekkart Kindler, ekki@dtu.dk
     * @author Jens Will Iversen
     *
     */
    public void moveToSpace(Player player, Space space, Heading heading) throws ImpossibleMoveException {
        Boolean canMove;
        canMove = isMovePossible(player, heading);
        if (!player.getInPit() && canMove){
            if (space != null) {
                Player other = space.getPlayer();
                Space target = board.getNeighbour(space, heading);
                if (other != null) {
                    moveToSpace(other, target, heading);
                }
                player.setSpace(space);
                checkForPit(player);
            } else {
                reboot(player);
            }

        }else {
            throw new ImpossibleMoveException(player,space,heading);
        }


    }

    /**
     * Checks if a player is standing on a board-element, and makes it act
     * corresponding to said board-element
     *
     * @param player that needs to be checked
     * @throws ImpossibleMoveException if the player is going to make an illegal
     *                                 move
     * @author Jens Will Iversen
     * @author Louis Monty-Krohn
     */

    public void endRegister(Player player) throws ImpossibleMoveException {


        Space space = player.getSpace();
        if (space != null && space.getActions() != null){
            for (FieldAction action : space.getActions()) {
                if (action instanceof ConveyorBelt){
                    for (int i = 0; i < ((ConveyorBelt) action).getSpeed(); i++) {
                        Heading heading = ((ConveyorBelt) action).getHeading();
                        Space target = board.getNeighbour(player.getSpace(), heading);
                        if (target != null) {
                            try {
                                moveToSpace(player, target, heading);
                            } catch (ImpossibleMoveException e) {

                            }

                        }
                    }
                }else if(action instanceof PushPanel){

                    for (int n : ((PushPanel) action).getNumber()) {

                        if (n == board.getStep() || n == 9) {
                            try {
                                moveToSpace(player, board.getNeighbour(space, ((PushPanel) action).getHeading()), ((PushPanel) action).getHeading());
                            } catch (ImpossibleMoveException e) {

                            }
                        }
                    }

                }
                action.doAction(this,player.getSpace());
        }


        }

            robotsAttack(player);



    }
    
    /**
     * Checks in a line from the player in the direction of their heading if there's another player.
     * If so, they will be "attacked" and there will be added a SPAM damage card to their discard pile.
     * @Author Isak
     * @param player the player that deals the damage to another.
     */
    public void robotsAttack(@NotNull Player player){
        if(player.getHeading()==Heading.EAST){
            for(int i = player.getSpace().x; i<board.width; i++){
                if(board.getSpace(i, player.getSpace().y).getPlayer() != null && board.getSpace(i, player.getSpace().y).getPlayer() != player){
                    damagePlayersInHorizontalLine(player, i);
                    break;
                }
                if(!player.getSpace().getWalls().isEmpty()){
                    break;
                }
            }
        }
        if(player.getHeading()==Heading.WEST){
            for(int i = player.getSpace().x; i>=0; i--){
                if(board.getSpace(i, player.getSpace().y).getPlayer() != null && board.getSpace(i, player.getSpace().y).getPlayer() != player){
                    damagePlayersInHorizontalLine(player, i);
                    break;
                }
                if(!player.getSpace().getWalls().isEmpty()){
                    break;
                }
            }
        }
        if(player.getHeading()==Heading.SOUTH){
            for(int i = player.getSpace().y; i<board.height; i++){
                if(board.getSpace(player.getSpace().x, i).getPlayer() != null && board.getSpace(player.getSpace().x, i).getPlayer() != player){
                    damagePlayersInVerticalLine(player, i);
                    break;
                }
                if(!player.getSpace().getWalls().isEmpty()){
                    break;
                }
            }
        }
        if(player.getHeading()==Heading.NORTH){
            for(int i = player.getSpace().y; i>=0; i--){
                if(board.getSpace(player.getSpace().x,i).getPlayer() != null && board.getSpace(player.getSpace().x, i).getPlayer() != player){
                    damagePlayersInVerticalLine(player, i);
                    break;
                }
                if(!player.getSpace().getWalls().isEmpty()){
                    break;
                }
            }
        }
    }

    /**
     * Assist method to robotsAttack method.
     * Currently just adding a SPAM damage card to the discard pile.
     * Outcommented can be restored to add damage, though it might need fixing for dealing 2 instead of 1 damage.
     * @Author Isak
     * @param player the player that deals damage to another
     * @param i the y position of the player
     */
    public void damagePlayersInVerticalLine(@NotNull Player player, int i){
        //board.getSpace(player.getSpace().x,i).getPlayer().setDamage(board.getSpace(player.getSpace().x, i).getPlayer().getDamage()+1);
        board.getSpace(player.getSpace().x,i).getPlayer().getDiscardPile().add(new CommandCard(Command.SPAM));
    }

    /**
     * Assist method to robotsAttack method.
     * Currently just adding a SPAM damage card to the discard pile.
     * Outcommented can be restored to add damage, though it might need fixing for dealing 2 instead of 1 damage.
     * @Author Isak
     * @param player the player that deals damage to another
     * @param i the x position of the player
     */
    public void damagePlayersInHorizontalLine(@NotNull Player player, int i){
        //board.getSpace(i, player.getSpace().y).getPlayer().setDamage(board.getSpace(i, player.getSpace().y).getPlayer().getDamage()+1);
        board.getSpace(i, player.getSpace().y).getPlayer().getDiscardPile().add(new CommandCard(Command.SPAM));
    }

    /**
     * Checks if a player is standing on a pit, and punishes player if this is true
     *
     * @param player that needs to be checked
     *
     * @author Jens Will Iversen
     */
    public void checkForPit(Player player){
        Space space = player.getSpace();
        for (FieldAction action : space.getActions()) {
            if (action instanceof Pit){

                reboot(player);


            }
        }

    }

    /**
     * Reboots a players robot
     * @param player which robot is being rebooted
     * @author Jens Will Iversen
     * @author Louis Monty-Krohn
     */
    public void reboot(Player player) {
        player.resetCards();
        List<Space> tokens = board.getRebootTokens();
        Space tempSpace = getClosestRebootToken(tokens,player);
        Heading heading = ((RebootTokens) tempSpace.getActions().get(0)).getHeading();
        while(tempSpace.getPlayer() != null){
            tempSpace = board.getNeighbour(tempSpace,heading);
        }
        player.setSpace(tempSpace);
        player.getDiscardPile().add(new CommandCard(Command.SPAM));
        player.getDiscardPile().add(new CommandCard(Command.SPAM));

        if (board.getCurrentPlayer() != player){
            player.setInPit(false);
        }
    }

    /**
     * Finds the closest space to a player, which has a reboot token
     * @param spaces all spaces which has a reboot token
     * @param player Player which is being rebooted
     * @return The closest space with a reboot token
     * @author Jens Will Iversen
     * @author Louis Monty-Krohn
     */
    public Space getClosestRebootToken(List<Space> spaces, Player player) {
        Space retSpace = null;
        int length = Integer.MAX_VALUE;
        int x = player.getSpace().x;
        int y = player.getSpace().y;

        for (Space tempSpace :spaces){
            int calc = (int) Math.sqrt((tempSpace.x - x) * (tempSpace.x - x) + (tempSpace.y - y) * (tempSpace.y - y));

            if(calc < length){
                length = calc;
                retSpace = tempSpace;
            }
        }

        return retSpace;
    }
    /**
     * Moves the player forward twice.
     *
     * @param player the player that needs to move
     */
    public void fastForward(@NotNull Player player) {
        moveForward(player);
        moveForward(player);
    }

    /**
     * Moves the player three fields forward
     *
     * @param player the player that needs to move
     */
    public void moveThreeForward(@NotNull Player player) {
        fastForward(player);
        moveForward(player);
    }

    /**
     * Turns the player right without moving them.
     *
     * @param player the player that needs to move
     */
    public void turnRight(@NotNull Player player) {
        player.setHeading(player.getHeading().next());
    }

    /**
     * Turns the player left without moving them.
     *
     * @param player the player that needs to move
     */
    public void turnLeft(@NotNull Player player) {
        player.setHeading(player.getHeading().prev());
    }

    /**
     * Moves the player one field backwards without turning around.
     *
     * @param player the player that needs to move
     * @author Jens Will Iversen
     * @author Louis Monty-Krohn
     * @author Niklas Jessen
     * @author Isak Risager
     */
    public void moveBackward(@NotNull Player player) {
        Heading heading = player.getHeading().next().next();
        Space target = board.getNeighbour(player.getSpace(), heading);

        if (target != null) {
            try {
                moveToSpace(player, target, heading);
            } catch (ImpossibleMoveException e) {

            }
        }else{
            reboot(player);
        }
    }

    /**
     * Turns the player around to face the opposite direction without moving.
     *
     * @param player the player that gets turned around
     */
    public void uTurn(@NotNull Player player) {
        player.setHeading(player.getHeading().next().next());
    }

    /**
     * Currently not working with interactive cards
     *
     * @param player
     */

    public void again(@NotNull Player player) {
        if (board.getStep() != 0) {
            int i = board.getStep();
            int j = 0;
            if(player.getProgramField(board.getStep() - 1).getCard() != null) {
            while (player.getProgramField(i).getCard().command == Command.AGAIN && board.getStep() != 0) {
                i--;
                j++;
            }

                if (player.getProgramField(board.getStep() - j).getCard().command.isInteractive()) {
                    board.setPhase(Phase.PLAYER_INTERACTION);
                    return;
                } else {
                    executeCommand(player, player.getProgramField(board.getStep() - j).getCard().command);
                }
            }
        }
    }

    public void spam(@NotNull Player player){
        player.getDiscardPile().remove(player.getDiscardPile().size()-1);
        CommandCard card = player.getCardPile().remove(0);
        player.getDiscardPile().add(card);
        executeCommand(player, card.command );
    }


    /**
     * A method to move a card from on space to another.
     * Is used in the programming phase to move cards from the players card field to the register and vice versa.
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
     * A method called when no corresponding controller operation is implemented yet. This
     * should eventually be removed.
     */
    public void notImplemented() {
        // XXX just for now to indicate that the actual method is not yet implemented
        assert false;
    }

}
