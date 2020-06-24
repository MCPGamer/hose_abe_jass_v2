import React, {useEffect, useState} from 'react';
import './GameScreen.css';
import {Room} from '../models/room';
import {RoomCode} from '../components/RoomCode';
import {PlayerTable} from '../components/PlayerTable';
import {Button} from '@material-ui/core';

type Props = {
    handleSetRoom: (room: Room) => void
    room: Room,
    player: string
    backendUrl?: String;
};

export const GameScreenContainer: React.FC<Props> = (props) => {
    const [enabled, setEnabled] = useState<boolean>(props.room.players[props.room.playerturn].name === props.player);

    useEffect(() => {
        setEnabled(props.room.players[props.room.playerturn].name === props.player);
    }, [props.room, props.player])

    if (!props.backendUrl) {
        props.backendUrl = 'localhost:8080';
    }

    const startGame = () => {
        fetch('http://' + props.backendUrl + '/room/start/' + props.room.roomCode).then(response => response.json())
            .then(room => props.handleSetRoom(room))
    }

    return (
        <div className="center-form">
            <PlayerTable room={props.room} player={props.player}/>
            {props.room.table && props.room.host.name !== props.player ? 'Waiting on Host to start the game' :
                <div id="start-display">
                    <p>Start the game as soon as everybody joined</p>
                    {props.room.players.filter(player => player != null).length > 1 ?
                        <Button variant="outlined" id="start-button" onClick={startGame}>Start Game</Button> : ''}
                </div>
            }
            <RoomCode code={props.room.roomCode}/>
        </div>
    )
};
