package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.BoardElement;
import dk.dtu.compute.se.pisd.roborally.model.Heading;

public class TConveyorBelt extends BoardElement {
    /**
     * Constructor for Space.
     *
     * @param board       The board the space is on.
     * @param x           the position on the horizontal axis.
     * @param y           the position on the vertical axis.
     * @param description
     */
    int speed;
    Heading heading;
    public TConveyorBelt(Board board, int x, int y, String description, int speed, Heading heading) {
        super(board, x, y, description);
        this.speed = speed;
        this.heading = heading;
    }

    public int getSpeed() {
        return speed;
    }

    public Heading getHeading() {
        return heading;
    }
}
