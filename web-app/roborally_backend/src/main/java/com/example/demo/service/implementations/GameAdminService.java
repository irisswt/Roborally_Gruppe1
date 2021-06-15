package com.example.demo.service.implementations;

import com.example.demo.dal.interfaces.IGameDao;
import com.example.demo.exceptions.DaoException;
import com.example.demo.exceptions.ServiceException;
import com.example.demo.model.Board;
import com.example.demo.model.Player;
import com.example.demo.model.admin.Game;
import com.example.demo.model.admin.User;
import com.example.demo.service.interfaces.IGameAdminService;
import com.example.demo.service.interfaces.IGameService;
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
    private final IGameService gameService;

    public GameAdminService(IGameDao gameDao, IGameService gameService) {
        this.gameDao = gameDao;
        this.gameService = gameService;
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
        Board board = new Board(8, 8, game.gameName);
        gameService.saveBoard(board);

        if (savedGameId < 0) {
            throw new ServiceException("GameDao generated invalid gameId " + savedGameId, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return savedGameId;
    }

    @Override
    public void deleteGame(Game game) throws ServiceException, DaoException {
        gameDao.deleteGame(game.getGameId());
    }
}
