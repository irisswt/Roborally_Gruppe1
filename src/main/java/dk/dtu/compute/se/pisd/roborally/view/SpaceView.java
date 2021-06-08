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
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.*;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.BoardElementsView.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


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
            this.setStyle("-fx-background-color: #808080;");
        }

        // updatePlayer();
        // This space view should listen to changes of the space
        space.attach(this);
        update(space);
    }

    /**
     * Method to update GUI for a space if there's a player on that space.
     */
    private void updatePlayer() {


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
     * finds the heading of the wall and draws a yellow wall
     *
     * @auther Louis Monty-Krohn
     */
    private void drawWalls(){
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(5);
        gc.setLineCap(StrokeLineCap.ROUND);
        for(Heading heading: space.getWalls()) {
            switch (heading) {
                case SOUTH:
                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT - 73, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - 73);
                    break;
                case NORTH:

                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT - 2, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - 2);
                    break;
                case WEST:
                    gc.strokeLine(73, SpaceView.SPACE_HEIGHT - 2, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - 73);
                    break;
                case EAST:
                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT - 2, SpaceView.SPACE_WIDTH - 73, SpaceView.SPACE_HEIGHT - 73);
                    break;
            }
        }
        this.getChildren().add(canvas);
    }

    /**
     * The update method for the observer.
     * @param subject inherited from Subject attached to an observer.
     *
     * If a space has a space action the function to draw it will be called
     *
     * @auther Louis Monty-Krohn
     */
    @Override
    public void updateView(Subject subject){
        this.getChildren().clear();
        drawWalls();
        for (FieldAction action : space.getActions())
        if (subject == this.space) {
            if (action instanceof PushPanel) {
                PushPanelView.drawPushPanel(this,action);
            }
            if (action instanceof Gear) {
                GearView.drawGear(this,action);
            }
            if(action instanceof Laser){
                LaserView.drawLaser(this,action);
            }
            if (action instanceof Checkpoint) {
                CheckpointView.drawCheckpoint(this, action);
            }
            if(action instanceof PriorityAntenna){
                PriorityAntennaView.drawPriorityAntenna(this,space);
            }
            if(action instanceof Pit){
                PitView.drawPit(this,space);
            }
            if(action instanceof ConveyorBelt){
                ConveyorBeltView.drawConveyorBeltView(this,action);
            }
            if(action instanceof StartGear){
                startGearView.draw(this,action);
            }
            if(action instanceof RebootTokens){
                RebootTokenView.drawRebootToken(this,action);
            }

            }
        updatePlayer();

        }
    }
