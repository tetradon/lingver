import React, {Component} from 'react';
import Home from './Home';
import {Redirect, Route, Switch} from 'react-router-dom';
import Dictionary from './Dictionary';
import LingverNavbar from "./LingverNavbar";
import Login from "./Login";
import {ProtectedRoute} from "./ProtectedRoute";
import Register from "./Registration";
import NotFound from "./NotFound";


class App extends Component {

    render() {
        return (
                <div>
                    <LingverNavbar/>
                    <Switch>
                            <ProtectedRoute exact path='/' component={Home}/>
                            <ProtectedRoute path='/dictionary' component={Dictionary}/>
                        <ProtectedRoute path='/404' component={NotFound}/>
                        <Route path='/register' component={Register}/>
                            <Route path='/login' component={Login}/>
                        <Redirect to="/404"/>
                    </Switch>
                </div>
        )
    }
}

export default App;
