package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;


public class Checkpoint extends FieldAction {

    private int checkpointnumber;

    public int getCheckpointnumber() {
        return checkpointnumber;
    }

    public void setCheckpointnumber(int checkpointnumber) {
        this.checkpointnumber =  checkpointnumber;
    }

    /**
     * If player lands on a checkpoint gives the player the checkpoint
     *
     * @param gameController the gameController of the respective game
     * @param space the space this action should be executed for
     * @return
     *
     * @author Louis Monty-Krohn
     */
    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        if(space.getPlayer().getCheckpoint() == checkpointnumber-1){
            space.getPlayer().setCheckpoint(checkpointnumber);
        }
        return false;
    }
}
