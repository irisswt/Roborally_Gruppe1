package com.example.demo.controller.GameController;

public class GameDto {

    private Integer boardId;
    private String boardName;
    private int height;
    private int width;
    private SpaceDto[][] spaceDtos;
    private PlayerDto currentPlayerDto;
    private PlayerDto[] playerDtos;

    public PlayerDto[] getPlayerDtos() {
        return playerDtos;
    }

    public void setPlayerDtos(PlayerDto[] playerDtos) {
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

    public int getGameId() {
        return boardId;
    }

    public void setGameId(int boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

}
