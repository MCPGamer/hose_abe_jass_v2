import React, {useState} from 'react';
import {Button, TextField} from '@material-ui/core';
import './RoomManager.css'
import {type} from "os";

export const RoomManager: React.FC = () => {
    const [username, setUsername] = useState<string>('');
    const [roomCode, setRoomCode] = useState<string>('');
    const [joinError, setJoinError] = useState<string>('');

    const createRoom = () => {
        fetch(`http://localhost:8080/room/${username}`, {method: 'POST'}).then(response => response.json()).then(data => {
            console.log(data);
        });
    }

    const joinRoom = () => {
        fetch(`http://localhost:8080/room/${username}/${roomCode}`, { method: 'POST'}).then(response => response.json())
            .then(data => {
               console.log(data);
               if(data.error){
                   setJoinError(data.error);
               } else {
                   setJoinError('');
                   // Join Game
               }
            });
    }

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.name === 'username') {
            setUsername(e.target.value);
        } else if (e.target.name === 'roomCode') {
            setRoomCode(e.target.value);
        }
    }

    return (
        <form>
            <fieldset>
                <div>
                    <TextField variant="outlined" onChange={handleChange} name='username' value={username}
                               label="Username" type="text" required/>
                </div>
                <div className="join-field">
                    <TextField variant="outlined" onChange={handleChange} name='roomCode' value={roomCode} label="Room"
                               type="text"/>
                    <Button variant="outlined" onClick={joinRoom} disabled={!roomCode}>Join</Button>
                </div>
                {joinError !== '' ? (
                    <div className='join-error'>
                        <p>{joinError}</p>
                    </div>) : ''}
                <div>
                    <Button variant="outlined" onClick={createRoom}>Create</Button>
                </div>
            </fieldset>
        </form>
    );
}
