package dk.dtu.compute.se.pisd.roborally.model.BoardElements;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.BoardElement;
import dk.dtu.compute.se.pisd.roborally.model.Heading;

public class TCLaser extends BoardElement {
    private whatKindOfLaser laserType;
    private int amountOFLaser;
    private Heading headin;
    /**
     * Constructor for Laser.
     *
     * @param board       The board the space is on.
     * @param x           the position on the horizontal axis.
     * @param y           the position on the vertical axis.
     * @param description
     * @param laserType uses the ENUM whatKindOfLaser to make it the start middle and end
     * @param amountOFLaser the amount of lasers decides the damege taken by the player
     * @param heading the heading of the laser
     */
    public TCLaser(Board board, int x, int y, String description, whatKindOfLaser laserType, int amountOFLaser, Heading heading) {
        super(board, x, y, description);
        this.laserType = laserType;
        this.amountOFLaser = amountOFLaser;
        this.headin = heading;
    }

    /**
     * enum to classify the type of laser in question
     */
    public enum whatKindOfLaser{
        START,
        MIDDLE,
        END
    }

    public Heading getHeadin() {
        return headin;
    }

    public whatKindOfLaser getLaserType() {
        return laserType;
    }

    public int getAmountOFLaser() {
        return amountOFLaser;
    }

    /**
     * makes the player draw damage cards, decided by the amount of lasers
     */
    @Override
    public void landOnSpace() {
        for(int i = 0;i<amountOFLaser;i++){
            //draw damage card
        }
    }
}
