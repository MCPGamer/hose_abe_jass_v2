import React, {useState} from 'react';
import {RoomManager} from '../components/RoomManager';
import './MainMenu.css'
import {Room} from '../models/room';

type Props = {
    setData: (username: string, room: Room) => void;
    backendUrl?: String;
};

export const MainMenuContainer: React.FC<Props> = (props) => {
    const [joinError, setJoinError] = useState<string>('');

    if (!props.backendUrl) {
        props.backendUrl = 'localhost:8080';
    }

    const createRoom = (username: string) => {
        fetch(`http://${props.backendUrl}/room/${username}`, {method: 'POST'}).then(response => response.json()).then(data => {
            console.log(data);
            props.setData(username, data);
        });
    };

    const joinRoom = (username: string, roomCode: string) => {
        fetch(`http://${props.backendUrl}/room/${username}/${roomCode}`, {method: 'POST'}).then(response => response.json())
            .then(data => {
                console.log(data);
                if (data.error) {
                    setJoinError(data.error);
                } else {
                    setJoinError('');
                    // Join Game
                    props.setData(username, data);
                }
            });
    };

    return (
        <div className="center-form">
            <RoomManager errorMsg={joinError} onCreate={createRoom} onJoin={joinRoom}/>
        </div>
    )
};
