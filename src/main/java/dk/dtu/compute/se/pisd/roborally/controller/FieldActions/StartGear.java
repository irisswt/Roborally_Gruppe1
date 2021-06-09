package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * Simple board element representing a starting position for the robots at the beginning of the game.
 * @Author Louis Monty-Krohn
 */
public class StartGear  extends FieldAction {
    @Override
    public boolean doAction(GameController gameController, Space space) {
        return false;
    }
}
