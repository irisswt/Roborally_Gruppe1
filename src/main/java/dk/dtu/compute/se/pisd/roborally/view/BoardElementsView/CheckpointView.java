package dk.dtu.compute.se.pisd.roborally.view.BoardElementsView;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.Checkpoint;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CheckpointView {
    public static void drawCheckpoint(SpaceView spaceView, FieldAction fieldAction) {
        Checkpoint checpoint = (Checkpoint) fieldAction;
        FileInputStream inputstream = null;
        try {
            inputstream = new FileInputStream("src/main/resources/Img/startGear.PNG");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(inputstream,SpaceView.SPACE_WIDTH,SpaceView.SPACE_WIDTH,false,false);
        ImageView selectedImage = new ImageView();
        selectedImage.setImage(image);
        spaceView.getChildren().addAll(selectedImage);
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_WIDTH);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.strokeText(String.valueOf(checpoint.getCheckpointnumber()), SpaceView.SPACE_WIDTH/2, SpaceView.SPACE_WIDTH/2);
        spaceView.getChildren().add(canvas);

    }
}