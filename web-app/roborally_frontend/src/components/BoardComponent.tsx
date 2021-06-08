import React, { FunctionComponent, useContext, useState } from "react";
import { SpaceComponent } from "./SpaceComponent";
import styles from "../styling/BoardComponent.module.scss" //Import css module
import GameContext from "../context/GameContext";
import { CardContent, Box, Card, Typography, Button } from "@material-ui/core";
/*
If the board component took any props/arguments they would be declared inside the type below
see the space component for an example.
 */

type BoardComponentProps = {}
const BoardComponent: FunctionComponent<BoardComponentProps> = () => {
    //{...} context is known as object destructuring
    const { board, loaded, unselectGame } = useContext(GameContext) //Hook form of Context.Consumer, used to access the context

    let [join, setJoin] = useState(false);
    let [start, setStart] = useState(false);

    const onSetJoin = () => {
        setJoin(true);
    }

    const onSetLeave = () => {
        setJoin(false);
    }
    const onSetStart = () => {
        setStart(true);
    }
    const onSetEnd = () => {
        setStart(false);
    }
    return (
        loaded ?
            <div>

                {!join ?
                    <Button variant="contained" color="primary" onClick={onSetJoin}  >
                        Join
                </Button>

                    :

                    < Button variant="contained" color="primary" onClick={onSetLeave}  >
                        Leave
                </Button>


                }

                {!start && join ? <Button variant="contained" color="primary" onClick={onSetStart}  >
                    Start Game
                </Button> : join ? <Button variant="contained" color="primary" onClick={onSetEnd}  >
                    End Game
                </Button> : <div />}


                <Button variant="contained" color="secondary">
                    Back to Games
                </Button>




                <Typography variant="h3"> {board.boardName} </Typography>
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



            </div >
            :
            <div />
    )
}

export default BoardComponent


