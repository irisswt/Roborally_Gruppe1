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
    private static void bubbleSort(Player[] a) {
        boolean sorted = false;
        Player temp;
        while(!sorted) {
            sorted = true;
            for (int i = 0; i < a.length - 1; i++) {
                if (a[i].getDisToPri() > a[i+1].getDisToPri()) {
                    temp = a[i];
                    a[i] = a[i+1];
                    a[i+1] = temp;
                    sorted = false;
                }
            }
        }
    }
}
