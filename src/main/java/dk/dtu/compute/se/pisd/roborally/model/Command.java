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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Class containing the different commands that can be on a command card.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public enum Command {

    // This is a very simplistic way of realizing different commands.

    FORWARD("Move 1 forward"),
    RIGHT("Turn Right"),
    LEFT("Turn Left"),
    FAST_FORWARD("Move 2 forward"),
    MOVE_THREE_FORWARD("Move 3 forward"),
    U_TURN("Make a U-turn"),
    BACKWARD("Move 1 backwards"),
    AGAIN("Repeat programming of last register"),
    // XXX Assignment V3
    OPTION_LEFT_RIGHT("Left OR Right", LEFT, RIGHT);

    final public String displayName;

    // XXX Assignment V3
    // Command(String displayName) {
    //     this.displayName = displayName;
    // }
    //
    // replaced by the code below:

    final private List<Command> options;

    /**
     * A method that can take multiple options.
     * @param displayName the name of the card
     * @param options the options the card offers.
     */
    Command(String displayName, Command... options) {
        this.displayName = displayName;
        this.options = Collections.unmodifiableList(Arrays.asList(options));
    }

    /**
     * Get method for interactive.
     * @return returns a true if there are options. Otherwise returns false.
     */
    public boolean isInteractive() {
        return !options.isEmpty();
    }

    /**
     * Get method for options.
     * @return options.
     */
    public List<Command> getOptions() {
        return options;
    }

}