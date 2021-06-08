package dk.dtu.compute.se.pisd.roborally.view.BoardElementsView;

import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PriorityAntennaView {
    public static void drawPriorityAntenna(SpaceView spaceView, Space space){
        FileInputStream inputstream = null;
        try {
            inputstream = new FileInputStream("src/main/resources/Img/PriorityAntenna.PNG");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(inputstream,SpaceView.SPACE_WIDTH,SpaceView.SPACE_WIDTH,false,false);
        ImageView selectedImage = new ImageView();
        selectedImage.setImage(image);
        spaceView.getChildren().addAll(selectedImage);

    }
}