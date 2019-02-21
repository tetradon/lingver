import React, {Component} from 'react';
import {userService} from "../service/userService";
import {Button, FormControl, Grid, Input, InputLabel, Paper, Typography, withStyles} from "@material-ui/core"
import AlertSnackbar from "./AlertSnackbar";
import {Link} from "react-router-dom";

const styles = theme => ({
    paper: {
        marginTop: theme.spacing.unit * 8,
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        padding: theme.spacing.unit * 3,
        paddingTop: theme.spacing.unit * 2
    },
    submit: {
        marginTop: theme.spacing.unit * 3,
        padding: theme.spacing.unit * 2,
    },
    input: {
        paddingLeft: theme.spacing.unit,
    },
    margin: {
        margin: theme.spacing.unit,
    },
});

class Login extends Component {
    constructor(props) {
        super(props);
        userService.logout();
        this.state = {
            username: '',
            password: '',
            loading: false,
            error: false,
            errorMessage: ''
        };
        this.login = this.login.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.handleUsernameChange = this.handleUsernameChange.bind(this);
    }

    handleUsernameChange(event) {
        this.setState({username: event.target.value});
    }

    handlePasswordChange(event) {
        this.setState({password: event.target.value});
    }

    login() {
        this.setState({error: false});
        userService.login(this.state.username, this.state.password)
            .then(() => {
                const {from} = this.props.location.state || {from: {pathname: "/"}};
                this.props.history.push(from);
            })
            .catch(() => {
                this.setState({errorMessage: 'Wrong Credentials!', error: true});
            });
    }

    render() {
        const {classes} = this.props;
        return (
            <div>
                {this.state.error === true ? <AlertSnackbar message={this.state.errorMessage}/> : null}
                <Grid container justify="center">
                    <Grid item lg={3} md={5} s={6} xs={8}>
                        <Paper className={classes.paper}>
                            <Typography variant="h2">
                                Login
                            </Typography>
                            <FormControl margin="normal" required fullWidth>
                                <InputLabel>Username</InputLabel>
                                <Input className={classes.input} value={this.state.username}
                                       onChange={this.handleUsernameChange} autoFocus/>
                            </FormControl>
                            <FormControl margin="normal" required fullWidth>
                                <InputLabel>Password</InputLabel>
                                <Input className={classes.input} type="password" value={this.state.password}
                                       onChange={this.handlePasswordChange}/>
                            </FormControl>
                            <Button className={classes.submit}
                                    onClick={this.login}
                                    type="submit"
                                    fullWidth
                                    variant="contained"
                                    color="primary"
                            >
                                Sign in
                            </Button>
                            <Typography variant="caption" className={classes.margin}>
                                Do not have an account?
                            </Typography>
                            <Button component={Link} to={'/register'} fullWidth variant="outlined" size="small"
                                    color="primary">
                                Sign Up
                            </Button>
                        </Paper>
                    </Grid>
                </Grid>
            </div>
        )
    }
}

export default withStyles(styles)(Login);
