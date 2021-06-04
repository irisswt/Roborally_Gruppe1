import React, { FunctionComponent, useContext } from 'react';
import GameContext from "../context/GameContext";
import { GameComponent } from "./GameComponent";
import { Game } from "../types/Game";
/*
If the board component took any props/arguments they would be declared inside the type below
see the space component for an example.
 */

type GamesComponentProps = {}

const GamesComponent: FunctionComponent<GamesComponentProps> = () => {

    const { games, loaded } = useContext(GameContext)

    return (
        !loaded ?
            <div>
                {games.map((game, index) =>
                    <GameComponent key={"game" + index} game={game} />
                )
                }
            </div>
            :
            <div />
    )

}

export default GamesComponent


