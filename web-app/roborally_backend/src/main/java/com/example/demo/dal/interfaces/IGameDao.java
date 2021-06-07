package com.example.demo.dal.interfaces;

import com.example.demo.exceptions.DaoException;
import com.example.demo.model.Board;
import com.example.demo.model.admin.Game;

import java.util.Collection;
import java.util.HashMap;

/**
 * Interface for Game DAO class
 */
public interface IGameDao {

    Collection<Game> getGames() throws DaoException;

    Game getGame(int gameId) throws DaoException;

    int createGame(Game game) throws DaoException;

    void deleteGame(int gameId) throws DaoException;
}