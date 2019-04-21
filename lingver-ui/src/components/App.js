import React, {Suspense} from 'react';
import {Redirect, Route, Switch} from 'react-router-dom';
import Dictionary from './Dictionary';
import LingverNavbar from "./LingverNavbar";
import Login from "./Login";
import {ProtectedRoute} from "./ProtectedRoute";
import Register from "./Registration";
import NotFound from "./NotFound";
import axios from "axios";
import {userService} from '../service/userService'
import {useTranslation} from 'react-i18next';


export default function App() {
    const {i18n} = useTranslation();
    const changeLanguage = lng => {
        i18n.changeLanguage(lng);
    };

    axios.interceptors.response.use(
        response => {
            return response
        },
        error => {
            if (error.response.status === 401) {
                userService.removeUserFromStorage();
                this.props.history.push('/login');
            }
            return Promise.reject(error);
        });


    return (
        <React.Fragment>
            <Suspense>
                <LingverNavbar changeLanguage={changeLanguage}/>
                <Switch>
                    <ProtectedRoute exact path='/' component={Dictionary}/>
                    <ProtectedRoute path='/dictionary' component={Dictionary}/>
                    <ProtectedRoute path='/404' component={NotFound}/>
                    <Route path='/register' component={Register}/>
                    <Route path='/login' component={Login}/>
                    <Redirect to="/404"/>
                </Switch>
            </Suspense>
        </React.Fragment>
    )

}

//export default (withRouter(App));
