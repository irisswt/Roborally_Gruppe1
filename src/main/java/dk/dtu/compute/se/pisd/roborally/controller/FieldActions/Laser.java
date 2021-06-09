package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;

import dk.dtu.compute.se.pisd.roborally.model.Command;
import dk.dtu.compute.se.pisd.roborally.model.CommandCard;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Laser extends FieldAction {

    private whatKindOfLaser laserType;

    public void setAmountOFLaser(int amountOFLaser) {
        this.amountOFLaser = amountOFLaser;
    }

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
     *
     * @param gameController the gameController of the respective game
     * @param space the space this action should be executed for
     *
     * @author Louis Monty-Krohn
     */
    @Override
    public boolean doAction(GameController gameController, Space space) {
        for(int i = 0;i<amountOFLaser;i++){
            space.getPlayer().getDiscardPile().add(new CommandCard(Command.SPAM));
        }
        return false;
    }
}
