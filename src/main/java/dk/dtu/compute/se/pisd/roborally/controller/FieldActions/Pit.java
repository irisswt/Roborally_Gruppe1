package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Command;
import dk.dtu.compute.se.pisd.roborally.model.CommandCard;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Pit extends FieldAction {
    @Override
    public boolean doAction(GameController gameController, Space space) {
        space.getPlayer().discardPile.add(new CommandCard(Command.SPAM));
        space.getPlayer().discardPile.add(new CommandCard(Command.SPAM));
        return false;
    }
}
