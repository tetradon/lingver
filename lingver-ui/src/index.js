import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';
import './index.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'typeface-roboto';
import {BrowserRouter} from "react-router-dom";
import {SnackbarProvider} from "notistack";

ReactDOM.render(
    <SnackbarProvider maxSnack={3}>
        <BrowserRouter>
            <App/>
        </BrowserRouter>
    </SnackbarProvider>
    , document.getElementById('root'));

