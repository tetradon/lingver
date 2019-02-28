import React, {Component} from 'react';
import {userService} from "../service/userService";
import {
    Button,
    FormControl,
    Grid,
    Input,
    InputLabel,
    LinearProgress,
    Paper,
    Typography,
    withStyles
} from "@material-ui/core"
import {Link} from "react-router-dom";
import {withSnackbar} from "notistack";

const styles = theme => ({
    paper: {
        marginTop: '5%',
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
        this.setState({loading: true});
        userService.login(this.state.username, this.state.password)
            .then((user) => {
                this.props.enqueueSnackbar(`Welcome, ${user.username}`);
                this.props.history.push("/");
            })
            .catch(() => {
                this.props.enqueueSnackbar("Wrong credentials!");
                this.setState({loading: false})
            })
    }

    render() {
        const {classes} = this.props;
        return (
            <div>
                <Grid container justify="center">
                    <Grid item lg={3} md={5} s={8} xs={10}>
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
                        <LinearProgress hidden={!this.state.loading}/>
                    </Grid>
                </Grid>
            </div>
        )
    }
}

export default withStyles(styles)(withSnackbar(Login));
