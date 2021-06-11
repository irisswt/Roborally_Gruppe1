import React, { FunctionComponent, useContext, useState } from 'react';
import GameContext from "../context/GameContext";
import { GameComponent } from "./GameComponent";
import styles from "../styling/BoardComponent.module.scss" //Import css module
import { Typography, Button, FormControl, InputLabel, Input } from "@material-ui/core";

/**
 * @Author: Jonathan Zørn
 * @Author: Niklas Jessen
 */


type GamesComponentProps = {}
const GamesComponent: FunctionComponent<GamesComponentProps> = () => {

    const { games, loaded } = useContext(GameContext)

    const newGame = () => { console.log("new game name: " + inputName) }

    const [formData, setFormData] = useState({ inputName: String })

    let inputName = "N/A";

    let handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({ ...formData, [inputName]: e.target.value });
        newGame();
    }

    return (
        <div id="everything">
            {
                !loaded ?
                    <div>
                        <div className={styles.centerHori}>
                            <Typography variant="h2">RoboRally</Typography>
                            <br />
                            <Typography variant="h6">Create a new game:</Typography>
                            <FormControl>
                                <InputLabel htmlFor="my-input">enter game</InputLabel>
                                <Input type="input" id="nameInput" aria-describedby="my-helper-text" onChange={handleInputChange} />
                                <Button size="medium" variant="outlined" color="primary" onClick={newGame}>
                                    New Game
                                </Button>
                            </FormControl>
                            <br />
                            <br />
                            <br />
                            <br />
                        </div>
                        <Typography variant="h5">Games</Typography>

                        <div className={styles.container} >
                            {games.map((game, index) =>
                                <GameComponent key={"game" + index} game={game} />
                            )
                            }
                        </div>

                    </div >
                    :
                    <div />
            }
        </div >
    )
}

export default GamesComponent


