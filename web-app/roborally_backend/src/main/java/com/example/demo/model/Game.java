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
package com.example.demo.model;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.model.Phase.INITIALISATION;


/**
 * Game class
 *
 * @author Jonathan Zørn
 */
public class Game {

    private Integer gameId;
    private String boardName;

    private final List<Player> users = new ArrayList<Player>();

    public Game(@NotNull String boardName) {
        this.gameId = 1;
        this.boardName = boardName;

    }

    public Game() {
        this("defaultboard");
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId)  {
        this.gameId = gameId;
    }

    public int getPlayersNumber() {
        return users.size();
    }

    public void addPlayer(@NotNull Player player) {
        // TODO
    }

    public Player getPlayer(int i) {
        if (i >= 0 && i < users.size()) {
            return users.get(i);
        } else {
            return null;
        }
    }

    public Player getPlayerById(int playerId) {
        if (playerId >= 0) {
            for (Player player : users) {
                if (player.getPlayerId() == playerId) {
                    return player;
                }
            }
        }
        return null;
    }


}