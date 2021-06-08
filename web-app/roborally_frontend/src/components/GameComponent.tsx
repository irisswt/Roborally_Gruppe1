import React, { FunctionComponent, useContext, useState } from "react";
import { Game } from "../types/Game";
import GameContext from "../context/GameContext";
import styles from "../styling/BoardComponent.module.scss" //Import css module
import Card from '@material-ui/core/Card';
import Box from '@material-ui/core/Box';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import ListItemText from '@material-ui/core/ListItemText';
import ListItem from '@material-ui/core/ListItem';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import PlayerIcon from '@material-ui/icons/Person';
import Avatar from '@material-ui/core/Avatar';
import { deepOrange, deepPurple } from '@material-ui/core/colors';
import { makeStyles, createStyles, Theme } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"></link>



export type GameComponentProps = {
    game: Game
}

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        root: {
            display: 'flex',
            '& > *': {
                margin: theme.spacing(1),
            },
        },
        orange: {
            color: theme.palette.getContrastText(deepOrange[500]),
            backgroundColor: deepOrange[500],
        },
        purple: {
            color: theme.palette.getContrastText(deepPurple[500]),
            backgroundColor: deepPurple[500],
        },
    }),
);

export const GameComponent: FunctionComponent<GameComponentProps> = ({ game }) => {
    const { selectGame } = useContext(GameContext)
    let [edit, setEdit] = useState(false);
    let [name, setName] = useState(game.gameName);

    const onClickGame = async () => {
        selectGame(game)
    }

    const classes = useStyles();

    const onSubmit = () => {
        setEdit(false);
    };
    const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        setName(event.target.value);
    };

    return (
        // TODO: Make divs with stylesheets instead

        <div className={styles.container} onClick={onClickGame}>
            <Box m={2}>
                <Card>
                    <CardContent>
                        <Box>
                            {!edit ? <Typography variant="h5" align="center">{name} - {game.gameId}</Typography> :
                                <Typography align="center">
                                    <form onSubmit={onSubmit}>

                                        <TextField variant="outlined" label="Edit name" onChange={onChange} />

                                    </form>
                                </Typography>}
                            {game.gameUsers.map((user, index) => <ListItem>
                                <ListItemAvatar>
                                    <Avatar className={classes.orange}>
                                        <PlayerIcon />
                                    </Avatar>
                                </ListItemAvatar>
                                <ListItemText
                                    key={index}
                                    primary={user.playerName + " (no function yet)"}
                                />
                            </ListItem>)}
                        </Box>

                        <CardActions>
                            {!edit ? <Button className={classes.orange} size="small" color="primary">Join game</Button> : <Button className={classes.orange} size="small" color="primary" type="submit" onClick={onSubmit}>Save game</Button>}
                            {!edit ? <Button className={classes.orange} size="small" color="primary" onClick={() => setEdit(true)}>Edit game</Button> : <Button className={classes.orange} size="small" color="primary" onClick={() => setEdit(false)}>Cancel</Button>}
                            {edit ? <Button className={classes.orange} size="small" color="primary">Delete game</Button> : <div />}
                        </CardActions>
                    </CardContent>
                </Card>
            </Box>
        </div >

    )
}