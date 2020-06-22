import React, {useState} from 'react';
import './GameScreen.css';
import {Room} from '../models/room';
import {RoomCode} from '../components/RoomCode';
import {PlayerTable} from '../components/PlayerTable';

type Props = {
  room: Room,
  player: string
};

export const GameScreenContainer: React.FC<Props> = (props) => {
    const [enabled, setEnabled] = useState<boolean>(props.room.players[props.room.playerturn].name === props.player);

    return (
        <div className="center-form">
          <PlayerTable room={props.room} player={props.player}/>
          you're in the game
          <RoomCode code={props.room.roomCode}/>
        </div>
    )
};
