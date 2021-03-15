package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.*;

/**
 * Class for the push panel board element
 */
public class PushPanel extends BoardElement {
    private Heading heading;
    /**
     * Constructor for Space.
     *
     * @param board       The board the space is on.
     * @param x           the position on the horizontal axis.
     * @param y           the position on the vertical axis.
     * @param description
     */
    public PushPanel(Board board, int x, int y, String description, Heading heading) {
        super(board, x, y, description);
        this.heading = heading;
        this.canLandOn = true;
    }

    public Heading getHeading(){
        return heading;
    }

    /**
     * Takes current player on space
     * // TODO: Throws exception to call method
     */
    @Override
    public void landOnSpace() {
        Player curPlayer = this.getPlayer();
        Space nextSpace = this.board.getNeighbour(this, heading);
        // TODO: Change to show player is pushed
        notifyChange();
        // TODO: Change to push other players
        nextSpace.setPlayer(curPlayer);
        notifyChange();
    }

}
