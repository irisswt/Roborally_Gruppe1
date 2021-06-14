import React, { FunctionComponent, useContext, useState } from 'react';
import GameContext from "../context/GameContext";
import { GameComponent } from "./GameComponent";
import styles from "../styling/BoardComponent.module.scss" //Import css module
import { Typography, Button, FormControl, InputLabel, Input, TextField } from "@material-ui/core";
import GameApi from '../api/GameApi';

/**
 * @Author: Jonathan Zørn
 * @Author: Niklas Jessen
 */


type GamesComponentProps = {

}
const GamesComponent: FunctionComponent<GamesComponentProps> = () => {

    const { games, loaded } = useContext(GameContext)
    const { createGame } = useContext(GameContext)

    const [formData, setFormData] = useState({ inputName: String })

    const [inputName, setinputName] = useState('');

    const handleClick = () => {
        console.log("new game name: " + inputName)
        createGame(inputName)
    };

    return (
        <div id="everything">
            {
                !loaded ?
                    <div>
                        <div className={styles.centerHori}>
                            <Typography variant="h2">RoboRally</Typography>
                            <br />
                            <div>
                                <Typography variant="h6">Create a new game:</Typography>
                                <form className={styles.centerButtons}>

                                    {/* 
                                This snippet of html has been taken from:
                                https://stackoverflow.com/questions/57810595/material-ui-how-to-extract-the-value-of-the-text-field
                                By StackOverflow user: @Ido, https://stackoverflow.com/users/10641422/ido
                                */}
                                    <TextField
                                        variant="outlined"
                                        margin="normal"
                                        required
                                        name="inputName"
                                        label="Game name"
                                        type="inputName"
                                        id="inputName"
                                        autoComplete="current-inputName"
                                        value={inputName}
                                        onChange={(event) => { setinputName(event.target.value) }} //whenever the text field change, you save the value in state
                                    />
                                    <Button
                                        variant="contained"
                                        color="primary"
                                        onClick={handleClick}
                                    >
                                        Create game
                                    </Button>
                                </form>
                            </div>
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


