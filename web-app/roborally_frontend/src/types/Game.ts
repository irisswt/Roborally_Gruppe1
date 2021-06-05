import { Player } from "./Player";

export type Game = {
    gameId: number,
    boardName: string,
    users: Player[]
}