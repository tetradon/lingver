import React, {Component} from 'react';
import './App.css';
import Home from './Home';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import TranslationSearch from './TranslationSearch';

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path='/' exact={true} component={Home}/>
                    <Route path='/search' exact={true} component={TranslationSearch}/>
                </Switch>
            </Router>
        )
    }
}

export default App;
