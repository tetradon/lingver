import React, {Component} from "react";
import {Redirect, Route} from "react-router-dom";
import Cookies from 'universal-cookie';

class ProtectedRoute extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isAuthenticated: false
        };
    }

    componentDidMount() {
        const cookies = new Cookies();
        let sessionCookie = cookies.get('JSESSIONID');
        if (sessionCookie) {
            this.setState({isAuthenticated: true})
        }
        console.log(sessionCookie);
    }


    render() {
        return ({component: Component, ...rest}) => (
            <Route {...rest} render={(props) => (
                this.state.isAuthenticated === true
                    ? <Component {...props} />
                    : <Redirect to='/login'/>
            )}/>
        )
    }

}

export default ProtectedRoute;