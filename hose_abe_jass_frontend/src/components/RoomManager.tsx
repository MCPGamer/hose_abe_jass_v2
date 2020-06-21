import React from 'react';
import {Button, TextField} from '@material-ui/core';
import './RoomManager.css'

export const RoomManager: React.FC = () => {
    let username = '';

    const setUsername = (e: React.ChangeEvent<HTMLInputElement>) => {
        username = e.target.value;
    }

    const createRoom = () => {
        fetch(`http://localhost:8080/room/${username}`, {method: 'POST'}).then(response => response.json()).then(data => {
            console.log(data);
        });
    }

    return (
        <form>
            <fieldset>
                <div>
                    <TextField variant="outlined" onChange={setUsername} label="Username" type="text" required/>
                </div>
                <div>
                    <TextField variant="outlined" label="Room" type="text"/>
                    <Button variant="outlined">Join</Button>
                </div>
                <Button variant="outlined" onClick={createRoom}>Create</Button>
            </fieldset>
        </form>
    );
}
