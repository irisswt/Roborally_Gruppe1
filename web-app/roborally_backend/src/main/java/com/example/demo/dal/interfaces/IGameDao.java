package com.example.demo.dal.interfaces;

import com.example.demo.exceptions.DaoException;
import com.example.demo.model.Game;


public interface IGameDao {
    Game getGame(int gameId) throws DaoException;

    int createGame(Game game) throws DaoException;

    void updateGame(Game game, int gameId) throws DaoException;

    void deleteGame(int gameId) throws DaoException;
}
