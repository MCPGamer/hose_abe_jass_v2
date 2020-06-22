import React, {useState} from 'react';
import {RoomManager} from '../components/RoomManager';
import './MainMenu.css'
import {Room} from '../models/room';
import {RoomCode} from '../components/RoomCode';

type Props = {
  room: Room,
  player: string
};

export const GameScreenContainer: React.FC<Props> = (props) => {
    return (
        <div className="center-form">
          you're in the game
          <RoomCode code={props.room.roomCode}/>
        </div>
    )
};
