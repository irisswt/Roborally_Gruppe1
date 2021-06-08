import React, { FunctionComponent, useContext } from 'react';
import GameContext from "../context/GameContext";
import { GameComponent } from "./GameComponent";
import styles from "../styling/BoardComponent.module.scss" //Import css module

type GamesComponentProps = {}
const GamesComponent: FunctionComponent<GamesComponentProps> = () => {

    const { games, loaded } = useContext(GameContext)

    return (
        !loaded ?
            <div className={styles.container} >
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


