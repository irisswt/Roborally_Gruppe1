package com.example.demo;


import com.example.demo.exceptions.DaoException;
import com.example.demo.exceptions.ServiceException;
import com.example.demo.model.Board;
import com.example.demo.model.Player;
import com.example.demo.model.admin.Game;
import com.example.demo.model.admin.User;
import com.example.demo.service.interfaces.IGameAdminService;
import com.example.demo.service.interfaces.IGameService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The run method is executed upon startup, this can be used to do some data seeding.
 */
@Component
public class DataLoader implements ApplicationRunner {
    private final IGameService gameService;
    private final IGameAdminService gameAdminService;

    public ArrayList<Game> gamesLists;

    public DataLoader(IGameService gameService, IGameAdminService gameAdminService) {
        this.gameService = gameService;
        this.gameAdminService = gameAdminService;
    }

    public void createGame(String Name) throws ServiceException, DaoException {
        Board board = new Board(8, 8, Name);
        List<User> emptyList = new ArrayList<>();


        gameService.saveBoard(board);
        Player player = new Player(board, "blue", "Player1Name");
        User user1 = new User(player.getPlayerId(), "Player1Name");
        gameService.addPlayer(board.getGameId(), player);
        gameService.setCurrentPlayer(board.getGameId(), player.getPlayerId());
        gameService.moveCurrentPlayer(board.getGameId(), 1, 1);
        player = new Player(board, "green", "Player2Name");
        User user2 = new User(player.getPlayerId(), "Player2Name");
        gameService.addPlayer(board.getGameId(), player);
        gameService.movePlayer(board, 4, 4, player.getPlayerId());
        Game game = new Game(Name, board.getGameId(), false, emptyList);
        game.users.add(user1);
        game.users.add(user2);
        gameAdminService.saveGame(game);
    }

    @Override
    public void run(ApplicationArguments args) throws ServiceException, DaoException {
        createGame("Hallo");
        createGame("Hej");
        createGame("Hola");




    }
}
