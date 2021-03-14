package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.BoardElement;

public class Checkpoint extends BoardElement {
    private int numberOfCheckpoint;
    /**
     * Constructor for Checkpoint.
     *
     * @param board       The board the space is on.
     * @param x           the position on the horizontal axis.
     * @param y           the position on the vertical axis.
     * @param description
     * @param numberOfCheckpoint the order the checkpoints need to be checked in
     */
    public Checkpoint(Board board, int x, int y, String description,int numberOfCheckpoint) {
        super(board, x, y, description);
        this.numberOfCheckpoint = numberOfCheckpoint;
    }
    public int getNumberOfCheckpoint() {
        return numberOfCheckpoint;
    }

    /**
     * when a player lands it checks if the player has been at the previous checkpoints
     */
    @Override
    public void landOnSpace() {
        if(board.getCurrentPlayer().getCheckpoint() == numberOfCheckpoint-1){
            board.getCurrentPlayer().setCheckpoint(numberOfCheckpoint);
        }
    }
}
