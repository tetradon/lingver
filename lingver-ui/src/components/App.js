import React, {Component} from 'react';
import './App.css';
import Home from './Home';
import {Route, Switch} from 'react-router-dom';
import Dictionary from './Dictionary';
import LingverNavbar from "./LingverNavbar";
import Login from "./Login";
import {Container} from "reactstrap";
import {ProtectedRoute} from "./ProtectedRoute";


class App extends Component {

    componentDidMount() {

        /* axios.interceptors.response.use(function (response) {
             return response;
         }, function (error) {

             if (error.status === 401) {

             }
             console.log("REDIRECT");
             return <Redirect to='/' />
             //return Promise.reject(error);
         });*/

    }

    render() {
        return (
                <div>
                    <LingverNavbar/>
                    <Container>
                        <Switch>
                            <ProtectedRoute exact path='/' component={Home}/>
                            <ProtectedRoute path='/dictionary' component={Dictionary}/>
                            <Route path='/login' component={Login}/>
                        </Switch>
                    </Container>
                </div>
        )
    }
}

export default App;
