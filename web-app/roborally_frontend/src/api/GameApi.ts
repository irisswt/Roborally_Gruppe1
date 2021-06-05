import axios from "axios";
import { Board } from "../types/Board";
import { Space } from "../types/Space";
import { Game } from "../types/Game";

class GameApi {
    private static instance: GameApi;
    private readonly BACKEND_URL = "http://localhost:8080"
    private constructor() { }

    public static getInstance(): GameApi {
        if (!GameApi.instance) {
            GameApi.instance = new GameApi();
        }
        return GameApi.instance;
    }

    public getBoard(boardId: number) {
        return axios.get<Board>(`${this.BACKEND_URL}/board/${boardId}`).then(value => value.data)
    }

    public moveCurrentPlayer(boardId: number, space: Space) {
        return axios.put(`${this.BACKEND_URL}/board/${boardId}/move`, space)
    }

    public switchPlayer(boardId: number) {
        return axios.put(`${this.BACKEND_URL}/board/${boardId}/switchplayer`)
    }
    public getGames(gameId: number) {
        return axios.get<Game>(`${this.BACKEND_URL}/game/${gameId}`).then(value => value.data)
    }
}

export default GameApi.getInstance()