import { FunctionComponent, useContext } from "react";
import { Game } from "../types/Game";
import { User } from "../types/User";
import GameContext from "../context/GameContext";


export type GameComponentProps = {
    game: Game
}

export const GameComponent: FunctionComponent<GameComponentProps> = ({ game }) => {
    const { selectGame } = useContext(GameContext)

    const onClickGame = async () => {
        selectGame(game)
    }

    return (
        // TODO: Make divs with stylesheets instead
        <div onClick={onClickGame}>
            <h1>{game.gameId}: {game.name}</h1>
            <ul>
                {game.users.map((user, index) => <li> {user.playerName} (no function yet)</li>)}
            </ul>
        </div>
    )
}