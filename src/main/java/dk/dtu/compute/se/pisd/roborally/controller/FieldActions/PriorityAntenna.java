package dk.dtu.compute.se.pisd.roborally.controller.FieldActions;

import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.ArrayList;

public class PriorityAntenna extends FieldAction {
    @Override
    public boolean doAction(GameController gameController, Space space) {
        return false;
    }

    /**
     * gives the player a distance to the priority antenna and sorts them
     * @param board the bord that they are playing on
     * @param x x position of the player
     * @param y y position of the player
     * @return returnees the a list of players sorted
     *
     * @author Louis Monty-Krohn
     */
    public Player[] playerOrder(Board board, int x, int y) {
        Player[] tempList = new Player[board.getPlayersNumber()];
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Space tempSpace = board.getPlayer(i).getSpace();
            board.getPlayer(i).setDisToPri(Math.sqrt((tempSpace.x - x) * (tempSpace.x - x) + (tempSpace.y - y) * (tempSpace.y - y)));
            tempList[i] = board.getPlayer(i);
        }
        bubbleSort(tempList);
        return tempList;
    }

    /**
     * sortes the players by who is closest to the priority antennas
     *
     * @param Array array of Players in game
     *
     * @author https://stackabuse.com/sorting-algorithms-in-java
     *
     * @modified Louis Monty-Krohn
     *
     *
     */
    private static void bubbleSort(Player[] Array) {
        boolean sorted = false;
        Player tempPlayer;
        while(!sorted) {
            sorted = true;
            for (int i = 0; i < Array.length - 1; i++) {
                if (Array[i].getDisToPri() > Array[i+1].getDisToPri()) {
                    tempPlayer = Array[i];
                    Array[i] = Array[i+1];
                    Array[i+1] = tempPlayer;
                    sorted = false;
                }
            }
        }
    }
}
