package com.example.demo.model.admin;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple game class
 * @Author: Jonathan Zørn
 */
public class Game {
    public String gameName;

    public int gameId;
    
    public boolean gameStarted;

    public List<User> gameUsers = new ArrayList<>();
    //public List<User> gameUsers = new ArrayList<>();


    public Game(String name, int gameId, boolean started) {
        this.gameName = name;
        this.gameId = gameId;
        this.gameStarted = started;
    }

    public Game(String name, int gameId, boolean started, List<User> gameUsers) {
        this.gameName = name;
        this.gameId = gameId;
        this.gameStarted = started;
        this.gameUsers = gameUsers;
    }

    public int getGameId() {return gameId;}
    public void setGameId(int gameId) {this.gameId = gameId;}

    public String getGameName() {return gameName;}
    public void setGameName(String name) {this.gameName = name;}

    public boolean getGameStarted() {return gameStarted;}
    public void setGameStarted(boolean bool) {this.gameStarted = bool;}

    public List<User> getGameUsers() {return gameUsers;}
}
