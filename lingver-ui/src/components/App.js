import React, {Component} from 'react';
import './App.css';
import Home from './Home';
import {Route, Switch} from 'react-router-dom';
import Dictionary from './Dictionary';
import LingverNavbar from "./LingverNavbar";
import {Cookies, withCookies} from 'react-cookie';
import Login from "./Login";
import {Container} from "reactstrap";


class App extends Component {
    constructor(props) {
        super(props);
        const {cookies} = props;
        this.state = {
            sessionCookie: cookies.get('JSESSIONID')
        };
    }

    componentDidMount() {
        console.log(this.state.sessionCookie);

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
                            <Route exact path='/' component={Home}/>
                            <Route path='/dictionary' component={Dictionary}/>
                            <Route path='/login' component={Login}/>
                        </Switch>
                    </Container>
                </div>
        )
    }
}

export default withCookies(App);
