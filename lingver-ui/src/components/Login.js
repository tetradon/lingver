import React, {Component} from 'react';
import {userService} from "../service/userService";
import {Button, FormControl, Grid, Input, InputLabel, Paper, Typography, withStyles} from "@material-ui/core"

const styles = theme => ({
    paper: {
        marginTop: theme.spacing.unit * 8,
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        padding: `${theme.spacing.unit * 2}px ${theme.spacing.unit * 3}px ${theme.spacing.unit * 3}px`,
    },
    submit: {
        marginTop: theme.spacing.unit * 3,
        padding: `${theme.spacing.unit * 2}px`,
    },
    input: {
        paddingLeft: `${theme.spacing.unit}px`,
    }
});

class Login extends Component {
    constructor(props) {
        super(props);
        userService.logout();
        this.state = {
            username: '',
            password: '',
            submitted: false,
            loading: false,
            error: ''
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
        this.setState({submitted: true});

        if (!(this.state.username && this.state.password)) {
            return;
        }
        this.setState({loading: true});

        userService.login(this.state.username, this.state.password)
            .then(() => {
                const {from} = this.props.location.state || {from: {pathname: "/"}};
                this.props.history.push(from);
            })
            .catch(() => {
                alert("WRONG CREDENTIALS!");
            });
    }

    render() {
        const {classes} = this.props;
        return (
            <Grid container justify="center">
                <Grid item xs={3}>
                    <Paper className={classes.paper}>
                        <Typography component="h1" variant="h5">
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
                    </Paper>
                </Grid>
            </Grid>
        )

    }
}

export default withStyles(styles)(Login);
