package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.model.*;

/**
 * Class for the gear board element
 */
public class TCGear extends BoardElement {
    private Heading heading;
    /**
     * Constructor for Space.
     *
     * @param board       The board the space is on.
     * @param x           the position on the horizontal axis.
     * @param y           the position on the vertical axis.
     * @param description
     */
    public TCGear(Board board, int x, int y, String description) {
        super(board, x, y, description);
        this.canLandOn = true;
    }
    
    public Heading getHeading(){
        return null;
    }

}