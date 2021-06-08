package com.example.demo.controller.GameController;

import com.example.demo.model.admin.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Data transfer class for the Game class
 * @Author: Jonathan Zørn
 */
public class GameDto {
    public String name;

    public int gameId;

    public boolean started;

    public List<User> users = new ArrayList<>();

    public String getGameName() {return name;}

    public int getGameId() {return gameId;}

    public boolean getGameStarted() {return started;}

    public List<User> getUsers() {return users;}

    public void setGameName(String name) {this.name = name;}

    public void setGameId(int gameId) {this.gameId = gameId;}

    public void setGameStarted(boolean started) {this.started = started;}

    public void setUsers(List<User> users) {this.users = users;}

}
