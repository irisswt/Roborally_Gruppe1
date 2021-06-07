import React, {FunctionComponent, useContext} from 'react';
import {SpaceComponent} from "./SpaceComponent";
import styles from "../styling/BoardComponent.module.scss" //Import css module
import GameContext from "../context/GameContext";
/*
If the board component took any props/arguments they would be declared inside the type below
see the space component for an example.
 */

type BoardComponentProps = {}
const BoardComponent: FunctionComponent<BoardComponentProps> = () => {
    //{...} context is known as object destructuring
    const {board, loaded, unselectGame} = useContext(GameContext) //Hook form of Context.Consumer, used to access the context

    return (
        loaded ?
        <div className={styles.container}>
            <h1 onClick={unselectGame}>{board.boardName} (click to unselect)</h1>
            { 

                board.spaceDtos.map((spaceArray, index) =>
                    <div key={"spaceArray" + index}>
                        {
                            spaceArray.map((space, index) => <SpaceComponent key={"space" + index} space={space}/>)
                        }
                    </div>
                )
                }


        </div>
        :
        <div/>
    )
}

export default BoardComponent


