import React, { FunctionComponent, useContext } from 'react';
import GameContext from "../context/GameContext";
import { GameComponent } from "./GameComponent";
import styles from "../styling/BoardComponent.module.scss" //Import css module
import { CardContent, Box, Card, Typography, Button } from "@material-ui/core";
//https://material-ui.com

type GamesComponentProps = {}
const GamesComponent: FunctionComponent<GamesComponentProps> = () => {

    const { games, loaded, board } = useContext(GameContext)

    const newGame = () => { console.log("new game") }

    return (
        <div>
            {board.boardId == null ? <div>
                <Typography variant="h3">RoboRally</Typography>
                <Button size="large" variant="outlined" color="primary" onClick={newGame}>
                    New Game
                </Button>
                <Typography variant="h5">Games</Typography>
            </div> : <div />}
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


