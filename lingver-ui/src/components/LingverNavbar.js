import React, {Component} from 'react';
import {Link} from 'react-router-dom';
import {userService} from "../service/userService";
import {AppBar, Button, Toolbar, Typography, withStyles} from '@material-ui/core';
import SchoolIcon from "@material-ui/icons/School";
import {Trans} from "react-i18next";
import MenuItem from "@material-ui/core/MenuItem";
import IconButton from "@material-ui/core/IconButton";
import Menu from "@material-ui/core/Menu";
import TranslateIcon from "@material-ui/icons/Translate";

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
        fontSize: '2em'
    }
};


class LingverNavbar extends Component {

    state = {
        lngMenuAnchor: null,
        selectedLng: 'ru'
    };

    handleLngMenuClick = event => {
        this.setState({lngMenuAnchor: event.currentTarget});
    };

    handleLngMenuClose = () => {
        this.setState({lngMenuAnchor: null});
    };

    changeLanguage = (lng) => {
        this.setState({selectedLng: lng});
        this.props.changeLanguage(lng);
        this.handleLngMenuClose();
    };

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
                </Typography>
                <SchoolIcon style={styles.icon}/>
                <div style={styles.grow}/>
                <IconButton style={styles.link} onClick={this.handleLngMenuClick}>
                    <TranslateIcon/>
                </IconButton>
                <Menu
                    id="lang-menu"
                    anchorEl={this.state.lngMenuAnchor}
                    open={Boolean(this.state.lngMenuAnchor)}
                    onClose={this.handleLngMenuClose}
                >
                    <MenuItem selected={this.state.selectedLng === 'ru'}
                              onClick={() => this.changeLanguage("ru")}>
                        RU
                    </MenuItem>
                    <MenuItem selected={this.state.selectedLng === 'en'}
                              onClick={() => this.changeLanguage("en")}>
                        EN
                    </MenuItem>
                </Menu>
                <Button
                    color="inherit"
                    style={styles.link}
                    component={Link}
                    to="/login"
                    onClick={userService.logout}
                >
                    {userService.getActiveUser() ? <Trans>Logout</Trans> : <Trans>Login</Trans>}
                </Button>
            </Toolbar>
        </AppBar>;
    }
}

export default withStyles(styles)(LingverNavbar)