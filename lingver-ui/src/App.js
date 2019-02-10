import React, {Component} from 'react';
import './App.css';
import Home from './Home';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import Dictionary from './Dictionary';
import LingverNavbar from "./LingverNavbar";

class App extends Component {
    render() {
        return (
            <Router>
                <div>
                    <LingverNavbar/>
                    <Switch>
                        <Route exact path='/' component={Home}/>
                        <Route path='/dictionary' component={Dictionary}/>
                    </Switch>
                </div>
            </Router>
        )
    }
}

export default App;
