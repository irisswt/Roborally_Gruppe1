package dk.dtu.compute.se.pisd.roborally.view.BoardElementsView;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.Checkpoint;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.StartGear;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class startGearView {
    /**
     * Draws a image of a gear from "src/main/resources/Img/startGear.PNG"
     * @param spaceView space that needs to be drawn
     * @param fieldAction the action that needs to be drawn in this case a startGear
     *
     * @author Louis Monty-Krohn
     */
    public static void draw(SpaceView spaceView, FieldAction fieldAction) {
        StartGear startGear = (StartGear) fieldAction;
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
}}
