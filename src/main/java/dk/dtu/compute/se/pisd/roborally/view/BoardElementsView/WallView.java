package dk.dtu.compute.se.pisd.roborally.view.BoardElementsView;

import dk.dtu.compute.se.pisd.roborally.model.BoardElements.Wall;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class WallView {
    public static void drawWall(SpaceView spaceView, Space space) {
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(5);
        gc.setLineCap(StrokeLineCap.ROUND);
        for (Heading x : space.getWalls()) {
            switch (x) {
                case SOUTH:
                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT - 2, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - 2);
                    break;
                case NORTH:
                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT-73, SpaceView.SPACE_WIDTH-2, SpaceView.SPACE_HEIGHT-73);
                    break;
                case WEST:
                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT-2, SpaceView.SPACE_WIDTH-73, SpaceView.SPACE_HEIGHT-73);
                    break;
                case EAST:
                    gc.strokeLine(73, SpaceView.SPACE_HEIGHT-2, SpaceView.SPACE_WIDTH-2, SpaceView.SPACE_HEIGHT-73);
                    break;
            }

        }spaceView.getChildren().add(canvas);

    }
}
