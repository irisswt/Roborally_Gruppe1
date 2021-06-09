package dk.dtu.compute.se.pisd.roborally.view.BoardElementsView;


import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.Checkpoint;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * CLass for the GUI part of the board element ConveyorBelt
 * @Author Louis Monty-Krohn
 */
public class ConveyorBeltView {
    public static void draw(SpaceView spaceView, FieldAction fieldAction) {
        ConveyorBelt tempcontroler = (ConveyorBelt) fieldAction;

            Polygon arrow = new Polygon(1.0, 1.0,
                    25.0, 50.0,
                    50.0, 0.0 );
            arrow.setStroke(Color.BLUE);
            arrow.setFill(Color.TRANSPARENT);
            arrow.setRotate((90*tempcontroler.getHeading().ordinal())%360);
            spaceView.getChildren().add(arrow);
    }
}
