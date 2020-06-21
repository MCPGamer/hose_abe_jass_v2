import React from 'react';
import {MainMenuContainer} from './containers/MainMenu';
import {createMuiTheme} from "@material-ui/core";
import {ThemeProvider} from "@material-ui/styles"
import CssBaseline from '@material-ui/core/CssBaseline';

function App() {
    const theme = createMuiTheme({
        palette: {
            type: 'dark',
        },
    });

    return (
        <ThemeProvider theme={theme}>
            <CssBaseline/>
            <div className="App">
                <MainMenuContainer/>
            </div>
        </ThemeProvider>
);
}

export default App;
