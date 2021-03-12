package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.BoardElement;

public class Checkpoint extends BoardElement {
    private int numberOfCheckpoint;
    /**
     * Constructor for Space.
     *
     * @param board       The board the space is on.
     * @param x           the position on the horizontal axis.
     * @param y           the position on the vertical axis.
     * @param description
     */
    public Checkpoint(Board board, int x, int y, String description,int numberOfCheckpoint) {
        super(board, x, y, description);
        this.numberOfCheckpoint = numberOfCheckpoint;
    }

    @Override
    public void landOnSpace() {
        if(board.getCurrentPlayer().getCheckpoint() == numberOfCheckpoint-1){
            board.getCurrentPlayer().setCheckpoint(numberOfCheckpoint);
        }
    }
}
