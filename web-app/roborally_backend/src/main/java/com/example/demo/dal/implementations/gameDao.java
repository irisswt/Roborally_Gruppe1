package com.example.demo.dal.implementations;

import com.example.demo.dal.interfaces.IGameDao;
import com.example.demo.model.Game;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

/**
 * Primitive implementation of a board dao, using a HashMap.
 * Author: Jonathan Zørn
 */
@Repository
public class gameDao implements IGameDao {
    //BoardId, Board
    static final HashMap<Integer, Game> games = new HashMap<Integer, Game>();
    static private int gameIdCounter = 0;

    @Override
    public Game getGame(int gameId) {
        return games.get(gameId);
    }


    @Override
    public int createGame(Game game) {
        gameIdCounter++;
        game.setGameId(gameIdCounter);
        games.put(game.getGameId(), game);
        return gameIdCounter;
    }

    @Override
    public void updateGame(Game game, int gameId) {
        games.replace(gameId, game);
    }

    @Override
    public void deleteGame(int gameId) {
        games.remove(gameId);
    }
}
