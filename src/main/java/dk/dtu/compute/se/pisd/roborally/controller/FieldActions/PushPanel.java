package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class PushPanel extends FieldAction {
    public Heading getHeading() {
        return heading;
    }

    public int getNumber() {
        return number;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }
    public void setNumber(int number){this.number = number;}

    private Heading heading;
    private int number;
    @Override
    public boolean doAction(GameController gameController, Space space) {
        return false;
    }
}
