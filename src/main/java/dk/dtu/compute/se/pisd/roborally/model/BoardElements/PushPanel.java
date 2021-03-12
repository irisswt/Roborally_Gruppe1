package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.model.*;

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

    @Override
    public void landOnSpace() {
        Player curPlayer = this.getPlayer();
        Space nextSpace = this.board.getNeighbour(this, heading);
        notifyChange();
        // TODO: Change to push other players
        nextSpace.setPlayer(curPlayer);
        notifyChange();
    }

}
