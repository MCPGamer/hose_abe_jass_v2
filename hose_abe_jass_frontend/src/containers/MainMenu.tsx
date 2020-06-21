import React, {useState} from 'react';
import {RoomManager} from '../components/RoomManager';
import './MainMenu.css'

export const MainMenuContainer: React.FC = () => {
    const [joinError, setJoinError] = useState<string>('');

    const createRoom = (username : string) => {
        fetch(`http://localhost:8080/room/${username}`, {method: 'POST'}).then(response => response.json()).then(data => {
            // TODO: Redirect to gamescreen using the returned "Room" Object
            console.log(data);
        });
    }

    const joinRoom = (username: string, roomCode: string) => {
        fetch(`http://localhost:8080/room/${username}/${roomCode}`, { method: 'POST'}).then(response => response.json())
            .then(data => {
                console.log(data);
                if(data.error){
                    setJoinError(data.error);
                } else {
                    setJoinError('');
                    // TODO: Redirect to gamescreen using the returned "Room" Object
                    // Join Game
                }
            });
    }

    return (
        <div className="center-form">
        <RoomManager errorMsg={joinError} onCreate={createRoom} onJoin={joinRoom}/>
        </div>
    )
}
