package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;

import java.util.List;

import org.jetbrains.annotations.NotNull;

public class RobotController {

    final public Board board;
    final private GameController gameController;

    /**
     * The constructor for GameController
     *
     * @param board the gameboard itself.
     */
    public RobotController(@NotNull Board board, @NotNull GameController gameController) {
        this.board = board;
        this.gameController = gameController;
    }

    /**
     * Method to move the player one field forward if nothing is occupying the
     * space.
     *
     * @param player the player that needs to move
     */
    public void moveForward(@NotNull Player player) {
        Heading heading = player.getHeading();
        Space target = board.getNeighbour(player.getSpace(), player.getHeading());
        if (target != null) {
            try {
                moveToSpace(player, target, heading);
            } catch (ImpossibleMoveException e) {

            }

        }

    }

        /**
     *
     * @param player
     * @param space
     * @param heading
     * @throws ImpossibleMoveException Checks if the player can move to desired
     *                                 space. If another player is on that space, it
     *                                 tries to move the second player before the
     *                                 first one. If a player cannot move, the
     *                                 impossible move exception is thrown
     */
    public void moveToSpace(Player player, Space space, Heading heading) throws ImpossibleMoveException {
        Boolean canMove;
        canMove = isMovePossible(player, heading);

        if (space != null && canMove) {
            Player other = space.getPlayer();
            Space target = board.getNeighbour(space, heading);
            if (other != null) {
                moveToSpace(other, target, heading);
            }
            player.setSpace(space);
        }else{
            throw new ImpossibleMoveException(player,space,heading);
        }
    }

    /**
     * Moves the player forward twice.
     * @param player the player that needs to move
     */
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
    public void turnRight(@NotNull Player player) {
        player.setHeading(player.getHeading().next());
    }

    /**
     * Turns the player left without moving them.
     * @param player the player that needs to move
     */
    public void turnLeft(@NotNull Player player) {
        player.setHeading(player.getHeading().prev());
    }

    /**
     * Moves the player one field backwards without turning around.
     * @param player the player that needs to move
     */
    public void moveBackward(@NotNull Player player) {
        Heading heading = player.getHeading().next().next();
        Space target = board.getNeighbour(player.getSpace(), heading);

        if(target!=null)
        {
            try
            {
                moveToSpace(player, target, heading);
            } catch (ImpossibleMoveException e){

            }
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
                gameController.executeCommandOptionAndContinue(player.getProgramField(board.getStep()-j).getCard().command);
            }
            else{
                gameController.executeCommand(player, player.getProgramField(board.getStep()-j).getCard().command);
            }

        }
    }

    //FIXME: Not used
    /**
     * This is just some dummy controller operation to make a simple move to see
     * something happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space) {
        if (space.getPlayer() == null) {
            board.getCurrentPlayer().setSpace(space);
            board.setStep(board.getStep() + 1);
            board.setCurrentPlayer(
                    board.getPlayer((board.getPlayerNumber(board.getCurrentPlayer()) + 1) % board.getPlayersNumber()));
        }
    }

    /**
     *
     * @param player
     * @param heading
     * @return Boolean Checks if a move is possible in regard to tokens
     *
     */
    public Boolean isMovePossible(Player player, Heading heading) {

        Space target = board.getNeighbour(player.getSpace(), heading);

        if (target != null) {
            List<Heading> wallHeadings = target.getWalls();
            if (!wallHeadings.isEmpty()) {
                for (Heading h : wallHeadings) {
                    if (h == heading.next().next()) {
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
                if (h == heading) {
                    return false;
                }
            }
        }
        return true;
    }

}