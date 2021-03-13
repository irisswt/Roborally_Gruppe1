package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.BoardElement;

public class RebootTokens extends BoardElement {
    /**
     * Constructor for Space.
     *
     * @param board       The board the space is on.
     * @param x           the position on the horizontal axis.
     * @param y           the position on the vertical axis.
     * @param description
     */
    public RebootTokens(Board board, int x, int y, String description) {
        super(board, x, y, description);
    }

    @Override
    public void landOnSpace() {
        //todo when reboot is created
    }
}
