import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';
import './index.css';
import 'typeface-roboto';
import {BrowserRouter} from "react-router-dom";
import {SnackbarProvider} from "notistack";
import i18n from './i18n';
import {I18nextProvider} from "react-i18next";

ReactDOM.render(
    <I18nextProvider i18n={i18n}>
        <SnackbarProvider maxSnack={3} anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'right',
        }}>
            <BrowserRouter>
                <App/>
            </BrowserRouter>
        </SnackbarProvider>
    </I18nextProvider>
    , document.getElementById('root'));

