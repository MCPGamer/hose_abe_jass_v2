import React, {useState} from 'react';
import './GameScreen.css';
import {Room} from '../models/room';
import {RoomCode} from '../components/RoomCode';
import {PlayerTable} from '../components/PlayerTable';

type Props = {
    room: Room,
    player: string
    backendUrl?: String;
};

export const GameScreenContainer: React.FC<Props> = (props) => {
    const [enabled, setEnabled] = useState<boolean>(props.room.players[props.room.playerturn].name === props.player);

    if (!props.backendUrl) {
        props.backendUrl = 'localhost:8080';
    }

    return (
        <div className="center-form">
            <PlayerTable room={props.room} player={props.player}/>
            {props.room.table && props.room.host.name !== props.player ? 'Waiting on Host to start the game' : 'Start the game as soon as everybody joined'}
            <RoomCode code={props.room.roomCode}/>
        </div>
    )
};
