package com.example.demo.model.admin;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple game class
 * @Author: Jonathan Zørn
 */
public class Game {
    public String name;

    public int gameId;

    public boolean started;

    public List<User> users = new ArrayList<>();


    public Game(String name, int gameId, boolean started, List<User> users) {
        this.name = name;
        this.gameId = gameId;
        this.started = started;
        this.users = users;
    }

    public int getGameId() {return gameId;}
    public void setGameId(int gameId) {this.gameId = gameId;}

    public String getGameName() {return name;}
    public void setGameName(String name) {this.name = name;}

    public boolean getGameStarted() {return started;}
    public void setGameStarted(boolean bool) {this.started = bool;}

    public List<User> getGameUsers() {return users;}
}
