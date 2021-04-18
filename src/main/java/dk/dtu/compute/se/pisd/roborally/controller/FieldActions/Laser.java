package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.TCLaser;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Laser extends FieldAction {

    private whatKindOfLaser laserType;
    private int amountOFLaser;
    private Heading headin;

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
    public boolean doAction(GameController gameController, Space space) {
        for(int i = 0;i<amountOFLaser;i++){
            //draw damage card
        }
        return false;
    }
}
