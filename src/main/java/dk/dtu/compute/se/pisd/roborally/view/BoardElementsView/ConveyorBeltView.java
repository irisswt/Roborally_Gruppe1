package dk.dtu.compute.se.pisd.roborally.view.BoardElementsView;

import dk.dtu.compute.se.pisd.roborally.controller.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.TConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class ConveyorBeltView {
    public static void drawConveyorBeltView(SpaceView spaceView, FieldAction fieldAction) {
        //TConveyorBelt tempSpace = (TConveyorBelt) space;
        ConveyorBelt tempcontroler = (ConveyorBelt) fieldAction;
        spaceView.getChildren().clear();

            Polygon arrow = new Polygon(1.0, 1.0,
                    25.0, 50.0,
                    50.0, 0.0 );
            arrow.setStroke(Color.BLUE);
            arrow.setFill(Color.TRANSPARENT);
            arrow.setRotate((90*tempcontroler.getHeading().ordinal())%360);
            spaceView.getChildren().add(arrow);
    }
}
