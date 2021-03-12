/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.Gear;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.Laser;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.PushPanel;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.model.BoardElements.Wall;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import org.jetbrains.annotations.NotNull;

/**
 * Class responsible for each Space GUI that a board is made of.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class SpaceView extends StackPane implements ViewObserver {

    final public static int SPACE_HEIGHT = 75; // 60; // 75;
    final public static int SPACE_WIDTH = 75;  // 60; // 75;

    public final Space space;

    /**
     * Constructor for SpaceView. Creates a board in a checkerboard pattern.
     * @param space The actual Space that needs GUI.
     */
    public SpaceView(@NotNull Space space) {
        this.space = space;

        // XXX the following styling should better be done with styles
        this.setPrefWidth(SPACE_WIDTH);
        this.setMinWidth(SPACE_WIDTH);
        this.setMaxWidth(SPACE_WIDTH);

        this.setPrefHeight(SPACE_HEIGHT);
        this.setMinHeight(SPACE_HEIGHT);
        this.setMaxHeight(SPACE_HEIGHT);

        if ((space.x + space.y) % 2 == 0) {
            this.setStyle("-fx-background-color: white;");
        } else {
            this.setStyle("-fx-background-color: black;");
        }

        // updatePlayer();

        // This space view should listen to changes of the space
        space.attach(this);
        update(space);
    }
    private void drawWall() {
        Wall tempSpace = (Wall) space;
        Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(5);
        gc.setLineCap(StrokeLineCap.ROUND);
        for (Heading x : tempSpace.getHeading()) {
            switch (x) {
                case SOUTH:
                    gc.strokeLine(2, SPACE_HEIGHT - 2, SPACE_WIDTH - 2, SPACE_HEIGHT - 2);
                    break;
                case NORTH:
                    gc.strokeLine(2, SPACE_HEIGHT-73, SPACE_WIDTH-2, SPACE_HEIGHT-73);
                    break;
                case WEST:
                    gc.strokeLine(2, SPACE_HEIGHT-2, SPACE_WIDTH-73, SPACE_HEIGHT-73);
                    break;
                case EAST:
                    gc.strokeLine(73, SPACE_HEIGHT-2, SPACE_WIDTH-2, SPACE_HEIGHT-73);
                    break;
            }

        }this.getChildren().add(canvas);

    }
    private void drawLaser() {
        Laser tempSpace = (Laser) space;
        Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(5);
        gc.setLineCap(StrokeLineCap.ROUND);
        if(tempSpace.getLaserType() == Laser.whatKindOfLaser.START){
            switch (tempSpace.getHeadin()) {
                case SOUTH:
                    gc.strokeLine(2, SPACE_HEIGHT-73, SPACE_WIDTH-2, SPACE_HEIGHT-73);
                    break;
                case NORTH:

                    gc.strokeLine(2, SPACE_HEIGHT - 2, SPACE_WIDTH - 2, SPACE_HEIGHT - 2);
                    break;
                case WEST:
                    gc.strokeLine(73, SPACE_HEIGHT-2, SPACE_WIDTH-2, SPACE_HEIGHT-73);
                    break;
                case EAST:
                    gc.strokeLine(2, SPACE_HEIGHT-2, SPACE_WIDTH-73, SPACE_HEIGHT-73);
                    break;
            }
        }
        else if(tempSpace.getLaserType() == Laser.whatKindOfLaser.END){
            switch (tempSpace.getHeadin()) {
                case SOUTH:
                    gc.strokeLine(2, SPACE_HEIGHT - 2, SPACE_WIDTH - 2, SPACE_HEIGHT - 2);
                    break;
                case NORTH:
                    gc.strokeLine(2, SPACE_HEIGHT-73, SPACE_WIDTH-2, SPACE_HEIGHT-73);
                    break;
                case WEST:
                    gc.strokeLine(2, SPACE_HEIGHT-2, SPACE_WIDTH-73, SPACE_HEIGHT-73);
                    break;
                case EAST:
                    gc.strokeLine(73, SPACE_HEIGHT-2, SPACE_WIDTH-2, SPACE_HEIGHT-73);
                    break;
            }
        }
        gc.setStroke(Color.RED);
        switch (tempSpace.getHeadin()){
            case EAST:
            case WEST:
                for(int i = 1;i<tempSpace.getAmountOFLaser()+1;i++) {
                    gc.strokeLine(2, SPACE_HEIGHT - i * 20, SPACE_WIDTH - 2, SPACE_HEIGHT - i * 20);
                }
                break;
            case NORTH:
            case SOUTH:
                for(int i = 1;i<tempSpace.getAmountOFLaser()+1;i++) {
                    gc.strokeLine(SPACE_WIDTH -i*20, 2, SPACE_WIDTH -i*20, SPACE_HEIGHT - 2);
                }
                break;

        }
        this.getChildren().add(canvas);

    }

    /**
     * Riped from drawWall
     * Pls make smarter
     * kh jonathan
     * Is drawn so the push panel will push in the direction of the heading
     */
    // TODO: Fix headings (reverse)
    private void drawPushPanel() {
        PushPanel tempSpace = (PushPanel) space;
        Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(20);
        gc.setLineCap(StrokeLineCap.ROUND);
        switch (tempSpace.getHeading()) {
            case SOUTH:
                gc.strokeLine(2, SPACE_HEIGHT-73, SPACE_WIDTH-2, SPACE_HEIGHT-73);
                break;
            case NORTH:

                gc.strokeLine(2, SPACE_HEIGHT - 2, SPACE_WIDTH - 2, SPACE_HEIGHT - 2);
                break;
            case WEST:
                gc.strokeLine(73, SPACE_HEIGHT-2, SPACE_WIDTH-2, SPACE_HEIGHT-73);
                break;
            case EAST:
                gc.strokeLine(2, SPACE_HEIGHT-2, SPACE_WIDTH-73, SPACE_HEIGHT-73);
                break;
        }
        this.getChildren().add(canvas);

    }

    private void drawGear() {
        Canvas canvas = new Canvas(SPACE_WIDTH, SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.GREY);
        gc.setLineWidth(7);
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.strokeOval(2,2,70,70);
        this.getChildren().add(canvas);

    }

    /**
     * Method to update GUI for a space if there's a player on that space.
     */
    private void updatePlayer() {
        this.getChildren().clear();

        Player player = space.getPlayer();
        if (player != null) {
            Polygon arrow = new Polygon(0.0, 0.0,
                    10.0, 20.0,
                    20.0, 0.0 );
            try {
                arrow.setFill(Color.valueOf(player.getColor()));
            } catch (Exception e) {
                arrow.setFill(Color.MEDIUMPURPLE);
            }

            arrow.setRotate((90*player.getHeading().ordinal())%360);
            this.getChildren().add(arrow);
        }
    }

    /**
     * The update method for the observer.
     * @param subject inherited from Subject attached to an observer.
     */
    @Override
    public void updateView(Subject subject) {
        if (subject == this.space) {
            updatePlayer();
            if(this.space instanceof Wall){
                drawWall();
            }
            if (this.space instanceof PushPanel) {
                drawPushPanel();
            }
            if (this.space instanceof Gear) {
                drawGear();
            }
            if(this.space instanceof  Laser){
                drawLaser();
            }
        }
    }

}
