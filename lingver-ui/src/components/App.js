import React, {Component} from 'react';
import {Redirect, Route, Switch, withRouter} from 'react-router-dom';
import Dictionary from './Dictionary';
import LingverNavbar from "./LingverNavbar";
import Login from "./Login";
import {ProtectedRoute} from "./ProtectedRoute";
import Register from "./Registration";
import NotFound from "./NotFound";
import axios from "axios";
import {userService} from '../service/userService'
import {withTranslation} from 'react-i18next';
import dotenv from "dotenv";

dotenv.config();
axios.defaults.withCredentials = true;

class App extends Component {
    constructor(props) {
        super(props);
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
    }

    changeLanguage = lng => {
        const {i18n} = this.props;
        i18n.changeLanguage(lng);
    };

    render() {
        return (
            <React.Fragment>
                <LingverNavbar changeLanguage={this.changeLanguage}/>
                <Switch>
                    <ProtectedRoute exact path='/' component={Dictionary}/>
                    <ProtectedRoute path='/dictionary' component={Dictionary}/>
                    <ProtectedRoute path='/404' component={NotFound}/>
                    <Route path='/register' component={Register}/>
                    <Route path='/login' component={Login}/>
                    <Redirect to="/404"/>
                </Switch>
            </React.Fragment>
        )
    }
}

export default withTranslation()(withRouter(App));