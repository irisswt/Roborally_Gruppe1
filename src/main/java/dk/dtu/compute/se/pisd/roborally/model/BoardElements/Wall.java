package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.BoardElement;
import dk.dtu.compute.se.pisd.roborally.model.Heading;

public class Wall extends BoardElement {
    private Heading heading[];
    /**
     * Constructor for Space.
     *
     * @param board       The board the space is on.
     * @param x           the position on the horizontal axis.
     * @param y           the position on the vertical axis.
     * @param description
     */
    public Wall(Board board, int x, int y, String description, Heading heading[]) {
        super(board, x, y, description);
        this.heading = heading;
    }

    public Heading[] getHeading(){
        return heading;
    }

    @Override
    public void landOnSpace() {
        super.landOnSpace();
    }
}
