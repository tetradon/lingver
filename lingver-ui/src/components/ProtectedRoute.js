import React from "react";
import {Redirect, Route} from "react-router-dom";

export const ProtectedRoute = ({...props}) =>
    localStorage.getItem('user')
        ? <Route {...props}/>
        : <Redirect to={{pathname: '/login', state: {from: props.location}}}/>;



