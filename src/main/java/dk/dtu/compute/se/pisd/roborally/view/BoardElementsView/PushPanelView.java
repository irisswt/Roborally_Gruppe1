package dk.dtu.compute.se.pisd.roborally.view.BoardElementsView;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.PushPanel;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.TCPushPanel;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class PushPanelView {
    /**
     * Riped from drawWall
     * Pls make smarter
     * kh jonathan
     * Is drawn so the push panel will push in the direction of the heading
     */
    // TODO: Fix headings (reverse)
    public static void drawPushPanel(SpaceView spaceView, FieldAction fieldAction) {
        PushPanel tempSpace = (PushPanel) fieldAction;
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(20);
        gc.setLineCap(StrokeLineCap.ROUND);
        switch (tempSpace.getHeading()) {
            case SOUTH:
                gc.strokeLine(2, SpaceView.SPACE_HEIGHT-73, SpaceView.SPACE_WIDTH-2, SpaceView.SPACE_HEIGHT-73);
                break;
            case NORTH:

                gc.strokeLine(2, SpaceView.SPACE_HEIGHT - 2, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - 2);
                break;
            case WEST:
                gc.strokeLine(73, SpaceView.SPACE_HEIGHT-2, SpaceView.SPACE_WIDTH-2, SpaceView.SPACE_HEIGHT-73);
                break;
            case EAST:
                gc.strokeLine(2, SpaceView.SPACE_HEIGHT-2, SpaceView.SPACE_WIDTH-73, SpaceView.SPACE_HEIGHT-73);
                break;
        }
        spaceView.getChildren().add(canvas);

    }
}
