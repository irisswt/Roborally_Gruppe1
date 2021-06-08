import React, { FunctionComponent, useContext } from 'react';
import GameContext from "../context/GameContext";
import { GameComponent } from "./GameComponent";
import styles from "../styling/BoardComponent.module.scss" //Import css module
import { Typography, Button } from "@material-ui/core";

type GamesComponentProps = {}
const GamesComponent: FunctionComponent<GamesComponentProps> = () => {

    const { games, loaded } = useContext(GameContext)

    const newGame = () => { console.log("ned game") }

    return (
        <div>
            <div>
                <Typography variant="h3">RoboRally</Typography>
                <Button size="large" variant="outlined" color="primary" onClick={newGame}>
                    New Game
                </Button>
            </div>
            <Typography variant="h5">Games</Typography>
            {
                !loaded ?
                    <div className={styles.container} >
                        {games.map((game, index) =>
                            <GameComponent key={"game" + index} game={game} />
                        )
                        }
                    </div>
                    :
                    <div />
            }
        </div >
    )
}

export default GamesComponent


