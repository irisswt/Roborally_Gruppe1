package com.example.demo.service.interfaces;

import com.example.demo.exceptions.DaoException;
import com.example.demo.exceptions.ServiceException;
import com.example.demo.model.Board;
import com.example.demo.model.Player;
import com.example.demo.model.admin.Game;

import java.util.List;


public interface IGameAdminService {

    public List<Game> getGames() throws ServiceException, DaoException;


}
