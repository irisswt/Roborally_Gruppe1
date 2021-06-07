package com.example.demo.gameAdminController;

import com.example.demo.exceptions.DaoException;
import com.example.demo.exceptions.MappingException;
import com.example.demo.exceptions.ServiceException;
import com.example.demo.model.admin.Game;
import com.example.demo.service.interfaces.IGameAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class GameAdminController {

    private final IGameAdminService gameAdminService;

    public GameAdminController(IGameAdminService gameAdminService) {
        this.gameAdminService = gameAdminService;
    }

    @GetMapping
    public ResponseEntity<List<Game>> getGames() throws ServiceException, MappingException, DaoException {
        List<Game> games = gameAdminService.getGames();
        // TODO: Change to make better
        return new ResponseEntity<>(games, HttpStatus.OK);
    }
}
