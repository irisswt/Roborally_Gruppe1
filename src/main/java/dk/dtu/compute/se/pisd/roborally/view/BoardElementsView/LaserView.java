package dk.dtu.compute.se.pisd.roborally.view.BoardElementsView;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.Laser;

import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class LaserView {
    /**
     *Draws the amount of lasers by the integer saved in the field element Laser
     *@param spaceView space that needs to be drawn
     *@param fieldAction the action that needs to be drawn in this case a laser this is used to find the amount of lasers and the type
     *
     * @author Louis Monty-Krohn
     */
        public  static void draw(SpaceView spaceView, FieldAction fieldAction) {
        Laser tempSpace = (Laser) fieldAction;
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(5);
        gc.setLineCap(StrokeLineCap.ROUND);
        if(tempSpace.getLaserType() == Laser.whatKindOfLaser.START){
            switch (tempSpace.getHeadin()) {
                case SOUTH:
                    gc.strokeLine(2, spaceView.SPACE_HEIGHT - spaceView.SPACE_HEIGHT+2, spaceView.SPACE_WIDTH - 2, spaceView.SPACE_HEIGHT - spaceView.SPACE_HEIGHT+2);
                    break;
                case NORTH:
                    gc.strokeLine(2, spaceView.SPACE_HEIGHT -2, spaceView.SPACE_WIDTH - 2, spaceView.SPACE_HEIGHT - 2);

                    break;
                case WEST:

                    gc.strokeLine(spaceView.SPACE_HEIGHT-2, spaceView.SPACE_HEIGHT - 2, spaceView.SPACE_WIDTH - 2, spaceView.SPACE_HEIGHT - spaceView.SPACE_WIDTH-2);
                    break;
                case EAST:

                    gc.strokeLine(2, spaceView.SPACE_HEIGHT - 2, spaceView.SPACE_WIDTH - spaceView.SPACE_HEIGHT+2, spaceView.SPACE_HEIGHT - spaceView.SPACE_HEIGHT - 2);
                    break;
            }
        }
        else if(tempSpace.getLaserType() == Laser.whatKindOfLaser.END){
            switch (tempSpace.getHeadin()) {
                case SOUTH:
                    gc.strokeLine(2, spaceView.SPACE_HEIGHT -2, spaceView.SPACE_WIDTH - 2, spaceView.SPACE_HEIGHT - 2);
                    break;
                case NORTH:
                    gc.strokeLine(2, spaceView.SPACE_HEIGHT - spaceView.SPACE_HEIGHT+2, spaceView.SPACE_WIDTH - 2, spaceView.SPACE_HEIGHT - spaceView.SPACE_HEIGHT+2);
                    break;
                case WEST:
                    gc.strokeLine(2, spaceView.SPACE_HEIGHT - 2, spaceView.SPACE_WIDTH - spaceView.SPACE_HEIGHT+2, spaceView.SPACE_HEIGHT - spaceView.SPACE_HEIGHT - 2);
                    break;
                case EAST:
                    gc.strokeLine(spaceView.SPACE_HEIGHT-2, spaceView.SPACE_HEIGHT - 2, spaceView.SPACE_WIDTH - 2, spaceView.SPACE_HEIGHT - spaceView.SPACE_WIDTH-2);
                    break;
            }
        }
        gc.setStroke(Color.RED);
        switch (tempSpace.getHeadin()){
            case EAST:
            case WEST:
                for(int i = 1;i<tempSpace.getAmountOFLaser()+1;i++) {
                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT - i * 20, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - i * 20);
                }
                break;
            case NORTH:
            case SOUTH:
                for(int i = 1;i<tempSpace.getAmountOFLaser()+1;i++) {
                    gc.strokeLine(SpaceView.SPACE_WIDTH -i*20, 2, SpaceView.SPACE_WIDTH -i*20, SpaceView.SPACE_HEIGHT - 2);
                }
                break;
        }
        spaceView.getChildren().add(canvas);
    }
}
