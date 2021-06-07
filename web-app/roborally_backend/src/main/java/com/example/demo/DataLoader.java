package com.example.demo;


import com.example.demo.exceptions.DaoException;
import com.example.demo.exceptions.ServiceException;
import com.example.demo.model.Board;
import com.example.demo.model.Player;
import com.example.demo.service.interfaces.IGameService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * The run method is executed upon startup, this can be used to do some data seeding.
 */
@Component
public class DataLoader implements ApplicationRunner {
    private final IGameService gameService;

    public DataLoader(IGameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void run(ApplicationArguments args) throws ServiceException, DaoException {
        Board board = new Board(8, 8, "Board1");
        gameService.saveBoard(board);
        Player player = new Player(board, "blue", "Player1Name");
        gameService.addPlayer(board.getGameId(), player);
        gameService.setCurrentPlayer(board.getGameId(), player.getPlayerId());
        gameService.moveCurrentPlayer(board.getGameId(), 1, 1);
        player = new Player(board, "green", "Player2Name");
        gameService.addPlayer(board.getGameId(), player);
        gameService.movePlayer(board, 4, 4, player.getPlayerId());

        Board board2 = new Board(8, 8, "Board2");
        gameService.saveBoard(board2);
        Player player3 = new Player(board2, "blue", "Player1Name");
        gameService.addPlayer(board2.getGameId(), player3);
        gameService.setCurrentPlayer(board2.getGameId(), player3.getPlayerId());
        gameService.moveCurrentPlayer(board2.getGameId(), 1, 1);
        player3 = new Player(board2, "green", "Player2Name");
        gameService.addPlayer(board2.getGameId(), player3);
        gameService.movePlayer(board2, 4, 4, player3.getPlayerId());
        /*gameService.switchCurrentPlayer(board);*/

    }
}
