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
package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import org.jetbrains.annotations.NotNull;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;

/**
 * Class for creating player objects.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Player extends Subject {

    final public static int NO_REGISTERS = 5;
    final public static int NO_CARDS = 8;

    final public Board board;

    private String name;
    private String color;

    private Space space;
    private Heading heading = SOUTH;

    private CommandCardField[] program;
    private CommandCardField[] cards;

    private int checkpoint = 0;


    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    private int damage = 0;

    /**
     * Constructor for Player.
     * @param board the board the player is playing on.
     * @param color Color of player.
     * @param name The players name.
     */
    public Player(@NotNull Board board, String color, @NotNull String name) {
        this.board = board;
        this.name = name;
        this.color = color;

        this.space = null;

        program = new CommandCardField[NO_REGISTERS];
        for (int i = 0; i < program.length; i++) {
            program[i] = new CommandCardField(this);
        }

        cards = new CommandCardField[NO_CARDS];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new CommandCardField(this);
        }
    }

    public int getCheckpoint() {
        return checkpoint;
    }

    public void setCheckpoint(int x){
        checkpoint = x;
    }

    /**
     * Get method for the players name.
     * @return the players name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set method for a Player objects name. Also updates GUI.
     * @param name the name given to a plaer.
     */
    public void setName(String name) {
        if (name != null && !name.equals(this.name)) {
            this.name = name;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    /**
     * Get method for a players color.
     * @return the players color.
     */
    public String getColor() {
        return color;
    }

    /**
     * Set method for a Player objects color. Also update GUI.
     * @param color the color the player gets.
     */
    public void setColor(String color) {
        this.color = color;
        notifyChange();
        if (space != null) {
            space.playerChanged();
        }
    }

    /**
     * Get method for the Space the player is on.
     * @return the space.
     */
    public Space getSpace() {
        return space;
    }

    /**
     * Set method for the space a Player object occupy. Checks if the new space is available.
     * Removes the player from the old space if new space is available and moves the player to the new space.
     * Also updates GUI.
     * @param space The new space the player object moves to.
     */
    public void setSpace(Space space) {
        Space oldSpace = this.space;
        if (space != oldSpace &&
                (space == null || space.board == this.board)) {
            this.space = space;
            if (oldSpace != null) {
                oldSpace.setPlayer(null);
            }
            if (space != null) {
                space.setPlayer(this);
            }
            notifyChange();
        }
    }

    /**
     * Get method for the heading of a player.
     * @return the heading.
     */
    public Heading getHeading() {
        return heading;
    }

    /**
     * Set method for a player objects heading. Also updates the GUI.
     * @param heading new heading.
     */
    public void setHeading(@NotNull Heading heading) {
        if (heading != this.heading) {
            this.heading = heading;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    /**
     * Get method for a players Program field.
     * @param i the number of the specific field.
     * @return the specific Program field.
     */
    public CommandCardField getProgramField(int i) {
        return program[i];
    }
    /**
     * Get method for a players Card field.
     * @param i the number of the specific field.
     * @return the specific Card field.
     */
    public CommandCardField getCardField(int i) {
        return cards[i];
    }

}
