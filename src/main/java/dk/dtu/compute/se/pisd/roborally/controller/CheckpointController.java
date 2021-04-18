package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;


public class CheckpointController extends FieldAction{

    private int checkpointnumber;

    public int getCheckpointnumber() {
        return checkpointnumber;
    }

    public void setCheckpointnumber(int checkpointnumber) {
        this.checkpointnumber =  checkpointnumber;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController,@NotNull Space space) {
        if(gameController.board.getCurrentPlayer().getCheckpoint() == 1-1){
            gameController.board.getCurrentPlayer().setCheckpoint(checkpointnumber);
        }
        return false;
    }
}
