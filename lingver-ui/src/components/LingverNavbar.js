import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import {userService} from "../service/userService";
import {AppBar, Button, Toolbar, Typography, withStyles} from '@material-ui/core';
import SchoolIcon from "@material-ui/icons/School";

const styles = {
    grow: {
        flexGrow: 1,
    },
    link: {
        textDecoration: 'none',
        color: 'white'
    },
    appBar: {
        marginBottom: '2em'
    },
    icon: {
        marginLeft: '10px',
        fontSize: '1.4em'
    }
};

class LingverNavbar extends Component {
    render() {
        return <AppBar position="static" style={styles.appBar}>
            <Toolbar>
                <Typography
                    component={Link}
                    to="/"
                    style={styles.link}
                    variant="headline"
                    color="inherit"
                >
                    Lingver
                    <SchoolIcon style={styles.icon}/>
                </Typography>
                <div style={styles.grow}/>
                <Button
                    color="inherit"
                    style={styles.link}
                    component={Link}
                    to="/login"
                    onClick={userService.logout}
                >
                    {userService.getActiveUser() ? 'Logout' : 'Login'}
                </Button>
            </Toolbar>
        </AppBar>;
    }
}

export default withStyles(styles)(LingverNavbar)