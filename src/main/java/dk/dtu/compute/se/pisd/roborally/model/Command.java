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

    FORWARD("Move 1 forward",1),
    RIGHT("Turn Right",2),
    LEFT("Turn Left",3),
    FAST_FORWARD("Move 2 forward",4),
    MOVE_THREE_FORWARD("Move 3 forward",5),
    U_TURN("Make a U-turn",6),
    BACKWARD("Move 1 backwards",7),
    AGAIN("Repeat programming of last register",8),
    // XXX Assignment V3
    OPTION_LEFT_RIGHT("Left OR Right",9, LEFT, RIGHT),
    SPAM("Play the top card of your programming deck this register", 10),
    WORM("Immediately reboot your robot", 11),
    TROJANHORSE("Immediately take 2 SPAM. Play the top card of your programming deck this register", 12),
    VIRUS("Robots within a 6 space radius of your robot immediately take 1 SPAM. Play the top card of your programming deck this register", 13);


    final public String displayName;
    final public int value;

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
    Command(String displayName, int value, Command... options) {
        this.displayName = displayName;
        this.value = value;
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