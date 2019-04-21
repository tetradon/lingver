import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';
import './index.css';
import 'typeface-roboto';
import {BrowserRouter} from "react-router-dom";
import {SnackbarProvider} from "notistack";

ReactDOM.render(
    <SnackbarProvider maxSnack={3} anchorOrigin={{
        vertical: 'bottom',
        horizontal: 'right',
    }}>
        <BrowserRouter>
            <App/>
        </BrowserRouter>
    </SnackbarProvider>
    , document.getElementById('root'));

