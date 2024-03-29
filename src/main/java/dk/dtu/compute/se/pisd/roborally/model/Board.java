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
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.Gear;
import dk.dtu.compute.se.pisd.roborally.controller.FieldActions.PushPanel;
import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadBoard;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static dk.dtu.compute.se.pisd.roborally.model.Phase.INITIALISATION;

/**
 * A class for the gameboard itself.
 * Also keeps track of the number of players and the current one along with the phase for the game.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Board extends Subject {

    public final int width;

    public final int height;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String name;

    public final String boardName;

    private Integer gameId;

    private final Space[][] spaces;


    private Player[] playerList;

    private final List<Player> players = new ArrayList<>();

    public List<Space> getRebootTokens() {
        return rebootTokens;
    }

    private final List<Space> rebootTokens = new ArrayList<>();

    private Player current;

    private Phase phase = INITIALISATION;

    private int step = 0;

    private boolean stepMode;

    private Space priorityAntenna;

    public Space getPriorityAntenna() {
        return priorityAntenna;
    }

    public void setPriorityAntenna(Space priorityAntenna) {
        this.priorityAntenna = priorityAntenna;
    }

    public Player[] getPlayerList() {
        return playerList;
    }

    public void setPlayerList(Player[] playerList) {
        this.playerList = playerList;
    }

    public int getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(int checkpoints) {
        this.checkpoints = checkpoints;
    }

    private int checkpoints = 0;

    /**
     * Constructor for Board.
     * If the boardName is included will create a custom board.
     * @param width the width of the board.
     * @param height the height of the board.
     * @param boardName the custom/premade board name.
     * @Author Ekkart Kindler
     */
    public Board(int width, int height, @NotNull String boardName) {
        this.boardName = boardName;
        this.width = width;
        this.height = height;
        spaces = new Space[width][height];
        for (int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Space space = new Space(this, x, y);
                spaces[x][y] = space;
            }
        }
        /*spaces[7][1].getActions().add(new Gear());
        spaces[7][2].getActions().add(new PushPanel());
        ((PushPanel)spaces[7][2].getActions().get(0)).setHeading(Heading.WEST);

        LoadBoard.saveBoard(this,"fuck2");

         */
        this.stepMode = false;
    }

    /**
     * Constructor for the board with overload. Made for a default board.
     * @param width the width of the board.
     * @param height the height of the board.
     * @Author Ekkart Kindler
     */
    public Board(int width, int height) {
        this(width, height, "defaultboard");
    }

    /**
     * Get method for gameId.
     * @return gameId.
     */
    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        if (this.gameId == null) {
            this.gameId = gameId;
        } else {
            if (!this.gameId.equals(gameId)) {
                throw new IllegalStateException("A game with a set id may not be assigned a new id!");
            }
        }
    }

    /**
     * Get method for a space on a x/y coordinate.
     * @param x the position on the horizontal axis
     * @param y the position on the vertical axis
     * @return the space object on the coordinates.
     * @Author Ekkart Kindler
     */
    public Space getSpace(int x, int y) {
        if (x >= 0 && x < width &&
                y >= 0 && y < height) {
            return spaces[x][y];
        } else {
            return null;
        }
    }

    /**
     * Get method for the number of players
     * @return the number of players in int.
     */
    public int getPlayersNumber() {
        return players.size();
    }

    /**
     * Adds a player to the arraylist of players. Updates the GUI.
     * @param player the player being added.
     * @Author Ekkart Kindler
     */
    public void addPlayer(@NotNull Player player) {
        if (player.board == this && !players.contains(player)) {
            players.add(player);
            notifyChange();
        }
    }

    /**
     * Get method for a player object.
     * @param i the number the player has.
     * @return the player object.
     */
    public Player getPlayer(int i) {
        if (i >= 0 && i < players.size()) {
            return players.get(i);
        } else {
            return null;
        }
    }

    /**
     * Get method for the current player.
     * @return the current player object.
     */
    public Player getCurrentPlayer() {
        return current;
    }

    /**
     * Set method for the current player. Also updates the GUI.
     * @param player the player object that becomes the current player.
     */
    public void setCurrentPlayer(Player player) {
        if (player != this.current && players.contains(player)) {
            this.current = player;
            notifyChange();
        }
    }

    /**
     * Get method for the phase.
     * @return the phase the game is in.
     */
    public Phase getPhase() {
        return phase;
    }

    /**
     * Set method for the phase and updates the GUI.
     * @param phase the phase the game is going to be set in.
     */
    public void setPhase(Phase phase) {
        if (phase != this.phase) {
            this.phase = phase;
            notifyChange();
        }
    }

    /**
     * Get method for the step.
     * @return the current step.
     */
    public int getStep() {
        return step;
    }

    /**
     * Set method for the step. Also updates the GUI.
     * @param step The new current step.
     */
    public void setStep(int step) {
        if (step != this.step) {
            this.step = step;
            notifyChange();
        }
    }

    /**
     * Get method for stepMode.
     * @return stepMode
     */
    public boolean isStepMode() {
        return stepMode;
    }

    /**
     * Set method for stepMode. Also updates the GUI.
     * @param stepMode if stepMode is on or off.
     */
    public void setStepMode(boolean stepMode) {
        if (stepMode != this.stepMode) {
            this.stepMode = stepMode;
            notifyChange();
        }
    }

    /**
     * Get method for the number of a player.
     * @param player The player object that one wants to find the number of.
     * @return The players number. If no board exists returns a -1.
     */
    public int getPlayerNumber(@NotNull Player player) {
        if (player.board == this) {
            return players.indexOf(player);
        } else {
            return -1;
        }
    }

    /**
     * Returns the neighbour of the given space of the board in the given heading.
     * The neighbour is returned only, if it can be reached from the given space
     * (no walls or obstacles in either of the involved spaces); otherwise,
     * null will be returned.
     *
     * @param space the space for which the neighbour should be computed
     * @param heading the heading of the neighbour
     * @return the space in the given direction; null if there is no (reachable) neighbour
     * @author Ekkart Kindler, ekki@dtu.dk
     * @author Jens Will Iversen
     * @author Jonathan Zørn
     *
     */
    public Space getNeighbour(@NotNull Space space, @NotNull Heading heading) {
        int x = space.x;
        int y = space.y;
        switch (heading) {
            case SOUTH:
                if (y+1 < height) {
                    y = (y + 1) % height;
                    break;
                }else{
                    return null;
                }

            case WEST:
                if((x - 1) >= 0){
                    x = (x + width - 1) % width;
                    break;
                }else{
                    return null;
                }

            case NORTH:

                if ((y - 1) >= 0) {
                    y = (y - 1) % height;
                    break;
                }else{
                    return null;
                }

            case EAST:
                if (( (x + 1)) < width) {
                    x = (x + 1) % width;
                    break;
                }else{
                    return null;
                }
        }

        return getSpace(x, y);
    }

    /**
     * Method to display a status message in the bottom of the window.
     * @return the message to be displayed.
     */
    public String getStatusMessage() {
        // this is actually a view aspect, but for making assignment V1 easy for
        // the students, this method gives a string representation of the current
        // status of the game

        // XXX: V2 changed the status so that it shows the phase, the player and the step
        return "Phase: " + getPhase().name() +
                ", Player = " + getCurrentPlayer().getName() +
                ", Step: " + getStep()+
                ", Checkpoint "+ getCurrentPlayer().getCheckpoint();
    }


}
