package com.example.demo.service.implementations;

import com.example.demo.dal.interfaces.IGameDao;
import com.example.demo.exceptions.DaoException;
import com.example.demo.exceptions.ServiceException;
import com.example.demo.model.admin.Game;
import com.example.demo.service.interfaces.IGameAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class GameAdminService implements IGameAdminService {
    /**
     * Utility class for interaction with games
     * @Author: Jonathan Zørn
     */
    private final IGameDao gameDao;

    public GameAdminService(IGameDao gameDao) {
        this.gameDao = gameDao;
    }

    @Override
    public List<Game> getGames() throws ServiceException, DaoException {
        Collection<Game> gamesMap = gameDao.getGames();
        return new ArrayList<>(gamesMap);
    }

    @Override
    public Game getGame(int gameId) throws DaoException {
        return gameDao.getGame(gameId);
    }

    @Override
    public void startGame(int gameId) throws ServiceException, DaoException {
        gameDao.getGame(gameId).setGameStarted(true);
    }

    @Override
    public void endGame(int gameId) throws ServiceException, DaoException {
        gameDao.getGame(gameId).setGameStarted(false);
    }

    @Override
    public int saveGame(Game game) throws ServiceException, DaoException {
        int savedGameId = gameDao.createGame(game);
        if (savedGameId < 0) {
            throw new ServiceException("GameDao generated invalid gameId " + savedGameId, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return savedGameId;
    }
}
