package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.BoardElement;
import dk.dtu.compute.se.pisd.roborally.model.Heading;

public class Laser extends BoardElement {
    private whatKindOfLaser laserType;
    private int amountOFLaser;
    private Heading headin;
    /**
     * Constructor for Space.
     *
     * @param board       The board the space is on.
     * @param x           the position on the horizontal axis.
     * @param y           the position on the vertical axis.
     * @param description
     */
    public Laser(Board board, int x, int y, String description, whatKindOfLaser laserType, int amountOFLaser, Heading heading) {
        super(board, x, y, description);
        this.laserType = laserType;
        this.amountOFLaser = amountOFLaser;
        this.headin = heading;
    }

    public enum whatKindOfLaser{
        START,
        MIDDLE,
        END
    }

    public Heading getHeadin() {
        return headin;
    }


    @Override
    public void landOnSpace() {
        //draw damage card
    }
}
