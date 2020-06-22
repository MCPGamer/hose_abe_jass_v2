import React, {useState} from 'react';
import {MainMenuContainer} from './containers/MainMenu';
import {createMuiTheme} from '@material-ui/core';
import {ThemeProvider} from '@material-ui/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import {defaultRoom, Room} from './models/room';
import {GameScreenContainer} from './containers/GameScreen';

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

  return (
      <ThemeProvider theme={theme}>
        <CssBaseline/>
        <div className="App">
          {room === defaultRoom ?
              (<MainMenuContainer setData={setData}/>)
              :
              (<GameScreenContainer room={room} player={username}/>)}
        </div>
      </ThemeProvider>
  );
}

export default App;
