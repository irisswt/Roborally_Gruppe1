package dk.dtu.compute.se.pisd.roborally.view.BoardElementsView;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
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

public class PitView {
    /**
     * Draws the image in the path "src/main/resources/Img/Pit.PNG"
     * @param spaceView space that needs to be drawn
     * @param fieldAction the action that needs to be drawn in this case a Pit
     *
     * @author Louis Monty-Krohn
     */
    public static void draw(SpaceView spaceView, FieldAction fieldAction){
        FileInputStream inputstream = null;
        try {
            inputstream = new FileInputStream("src/main/resources/Img/Pit.PNG");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image image = new Image(inputstream,SpaceView.SPACE_WIDTH,SpaceView.SPACE_WIDTH,false,false);
        ImageView selectedImage = new ImageView();
        selectedImage.setImage(image);
        spaceView.getChildren().addAll(selectedImage);
    }
}
