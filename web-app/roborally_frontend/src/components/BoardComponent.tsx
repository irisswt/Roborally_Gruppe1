import React, { FunctionComponent, useContext, useState } from "react";
import { SpaceComponent } from "./SpaceComponent";
import styles from "../styling/BoardComponent.module.scss" //Import css module
import GameContext from "../context/GameContext";
import { Typography, Button } from "@material-ui/core";
import { Game } from "../types/Game";
import { User } from "../types/User";
import { deepPurple } from '@material-ui/core/colors';
import { makeStyles, createStyles, Theme } from '@material-ui/core/styles';
/*
If the board component took any props/arguments they would be declared inside the type below
see the space component for an example.
 */

/**
 * @Author: Jonathan Zørn
 * @Author: Niklas Jessen
 */

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        root: {
            display: 'flex',
            '& > *': {
                margin: theme.spacing(1),
            },
        },
        purple: {
            color: theme.palette.getContrastText(deepPurple[500]),
            backgroundColor: deepPurple[500],
        },
    }),
);

type BoardComponentProps = {}
const BoardComponent: FunctionComponent<BoardComponentProps> = () => {
    //{...} context is known as object destructuring
    const { games, board, loaded, unselectGame, startGame, endGame } = useContext(GameContext) //Hook form of Context.Consumer, used to access the context

    let [join, setJoin] = useState(false);
    let [start, setStart] = useState(false); // TODO: Add functionallity so start gets the game value instead of local boolean


    // TODO: Make better
    // Major hack to find out which game is being used in this instance
    let game = games.find(game => game.gameId === board.boardId);
    if (game === undefined) {
        console.log("Game could not be found in board")
        var users: User[] = [];
        game = {
            gameName: "null",
            gameId: 500,
            gameStarted: false,
            gameUsers: users
        };
    }



    const classes = useStyles();

    const onSetJoin = () => {
        setJoin(true);
    }

    const onSetLeave = () => {
        setJoin(false);
    }
    const onSetStart = () => {
        if (game !== undefined) {
            startGame(game);
            setStart(true);
        }
    }
    const onSetEnd = () => {
        let game: Game | undefined;
        game = games.find(game => game.gameId === board.boardId);
        if (game === undefined) {
            console.log("Game could not be found in map")
        } else {
            endGame(game);
            setStart(false);
        }
    }

    const onBack = () => {
        setJoin(false);
        unselectGame();

    }

    return (
        loaded ?
            <div className={styles.centerAll}>

                <Typography variant="h3" align="center" >Roborally </Typography>
                <Typography variant="h5" align="center" >Game name: {game.gameName} </Typography>
                <br />
                <div className={styles.container}>
                    {board.spaceDtos.map((spaceArray, index) =>
                        <div key={"spaceArray" + index}>
                            {
                                spaceArray.map((space, index) => <SpaceComponent key={"space" + index} space={space} />)
                            }
                        </div>
                    )
                    }
                </div>
                <br />
                <br />
                {!join ?
                    <Button className={classes.purple} size="large" variant="text" color="primary" onClick={onSetJoin}  >
                        Join
                    </Button>

                    :

                    < Button className={classes.purple} size="large" variant="text" color="primary" onClick={onSetLeave}  >
                        Leave
                    </Button>
                }

                &emsp;
                <Button className={classes.purple} size="large" variant="text" color="primary" onClick={onBack}>
                    Back to Games
                </Button>
                &emsp;
                <Button className={classes.purple} size="large" variant="text" color="primary" onClick={onSetStart}  >
                    Start Game
                </Button>
                &emsp;
                <Button className={classes.purple} size="large" variant="text" color="primary" onClick={onSetEnd}  >
                    End Game
                </Button>
                <br />
                <br />
                <Typography variant="h5">Game info</Typography>
                <Typography variant="subtitle1">{"Board id: " + board.boardId}</Typography>
                <Typography variant="subtitle1">{"Board name: " + board.boardName}</Typography>
                <Typography variant="subtitle1">Players:</Typography>
                {board.playerDtos.map((user, index) => <Typography variant="subtitle1" key={index}>- {user.playerName}</Typography>)}



            </div >
            :
            <div />
    )
}

export default BoardComponent


