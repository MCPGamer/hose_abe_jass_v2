import React, {useState} from 'react';
import {Button, TextField} from '@material-ui/core';
import './RoomManager.css';

type Props = {
    onCreate: (username: string) => void;
    onJoin: (username: string, roomCode: string) => void;
    errorMsg: string;
}

export const RoomManager: React.FC<Props> = (props) => {
    const [username, setUsername] = useState<string>('');
    const [roomCode, setRoomCode] = useState<string>('');

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.name === 'username') {
            setUsername(e.target.value);
        } else if (e.target.name === 'roomCode') {
            setRoomCode(e.target.value);
        }
    };

    const handleOnCreate = () => {
        props.onCreate(username);
    };

    const handleOnJoin = () => {
        props.onJoin(username, roomCode);
    };

    return (
        <form>
            <fieldset>
                <div>
                    <TextField variant="outlined" onChange={handleChange} name='username' value={username}
                               label="Benutzername" type="text" required/>
                </div>
                <div className="join-field">
                    <TextField variant="outlined" onChange={handleChange} name='roomCode' value={roomCode} label="Raumcode"
                               type="text"/>
                    <Button variant="outlined" onClick={handleOnJoin} disabled={!roomCode}>Beitreten</Button>
                </div>
                {props.errorMsg !== '' ? (
                    <div className='join-error'>
                        <p>{props.errorMsg}</p>
                    </div>) : ''}
                <div>
                    <Button variant="outlined" onClick={handleOnCreate} disabled={!username}>Erstellen</Button>
                </div>
            </fieldset>
        </form>
    );
};
