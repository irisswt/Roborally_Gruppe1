package com.example.demo.controller.GameController;

import com.example.demo.model.Player;

import java.util.ArrayList;
import java.util.List;

public class BoardDto {

    private Integer boardId;
    private String boardName;
    private int height;
    private int width;
    private SpaceDto[][] spaceDtos;
    private PlayerDto currentPlayerDto;
    private List<PlayerDto> playerDtos = new ArrayList<PlayerDto>();

    public List<PlayerDto> getPlayerDtos() {
        return playerDtos;
    }

    public void setPlayerDtos(List<PlayerDto> playerDtos) {
        this.playerDtos = playerDtos;
    }

    public void setBoardId(Integer boardId) {
        this.boardId = boardId;
    }

    public PlayerDto getCurrentPlayerDto() {
        return currentPlayerDto;
    }

    public void setCurrentPlayerDto(PlayerDto currentPlayerDto) {
        this.currentPlayerDto = currentPlayerDto;
    }

    public SpaceDto[][] getSpaceDtos() {
        return spaceDtos;
    }

    public void setSpaceDtos(SpaceDto[][] spaceDtos) {
        this.spaceDtos = spaceDtos;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
