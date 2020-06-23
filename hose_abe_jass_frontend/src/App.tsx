import React, {useState} from 'react';
import {MainMenuContainer} from './containers/MainMenu';
import {createMuiTheme} from '@material-ui/core';
import {ThemeProvider} from '@material-ui/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import {defaultRoom, Room} from './models/room';
import {GameScreenContainer} from './containers/GameScreen';
import StompClient from 'react-stomp-client';
import { Message } from "@stomp/stompjs";

function App() {
    const [username, setUsername] = useState<string>('');
    const [room, setRoom] = useState<Room>(defaultRoom);

    const theme = createMuiTheme({
        palette: {
            type: 'dark',
        },
    });

    const setData = (username: string, room: Room) => {
        setUsername(username);
        setRoom(room);
    };

    const handleMessage = (stompMessage:Message) => {
        console.log(stompMessage);
        const updatedRoom:Room = JSON.parse(stompMessage.body);
        if(room.roomCode === updatedRoom.roomCode){
            setRoom(updatedRoom);
        }
    }

    return (
        <ThemeProvider theme={theme}>
            <CssBaseline/>
            <StompClient endpoint={'ws://localhost:8080/room'} topic={'/room'} onMessage={handleMessage}>
                <div className="App">
                    {room === defaultRoom ?
                        (<MainMenuContainer setData={setData}/>)
                        :
                        (<GameScreenContainer room={room} player={username}/>)}
                </div>
            </StompClient>
        </ThemeProvider>
    );
}

export default App;
