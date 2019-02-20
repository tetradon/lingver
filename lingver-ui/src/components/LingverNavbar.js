import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import {userService} from "../service/userService";

import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';

export default class LingverNavbar extends Component {
    constructor(props) {
        super(props);
        this.state = {isOpen: false};
        this.toggle = this.toggle.bind(this);
    }

    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }

    render() {
        return <AppBar position="static">
            <Toolbar>
                <Typography component={Link} to="/" variant="headline" color="inherit">
                    Lingver
                </Typography>
                <Button color="inherit" component={Link} to="/dictionary">Dictionary</Button>
                <Button color="inherit" component={Link} to="/login">Login</Button>
                <Button color="inherit" component={Link} to="/login" onClick={() => {
                    userService.logout()
                }}>Logout</Button>
            </Toolbar>
        </AppBar>;
    }
}