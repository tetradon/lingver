import React, {Component} from 'react';
import {userService} from "../service/userService";
import {Box, Button, FormControl, Grid, Input, InputLabel, Paper, Typography} from "@material-ui/core"


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
        /* return (
           {  <div style={{width: '50%'}}>
                 <h2>Login</h2>
                 <br/>
                 <InputGroup>
                     <InputGroupAddon addonType="prepend">
                         <InputGroupText>Username</InputGroupText>
                     </InputGroupAddon>
                     <Input type="text" value={this.state.username} onChange={this.handleUsernameChange}/>
                 </InputGroup>
                 <br/>
                 <InputGroup>
                     <InputGroupAddon addonType="prepend">
                         <InputGroupText>Password</InputGroupText>
                     </InputGroupAddon>
                     <Input type="password" value={this.state.password} onChange={this.handlePasswordChange}/>
                 </InputGroup>
                 <br/>
                 <Button onClick={this.login}>Login</Button>
             </div>})*/
        return (
            <Grid container justify="center">
                <Grid item xs={6}>
                    <Paper>
                        <Typography component="h1" variant="h5">
                            Login
                        </Typography>
                        <FormControl margin="normal" required fullWidth>
                            <InputLabel>Username</InputLabel>
                            <Input value={this.state.username} onChange={this.handleUsernameChange} autoFocus/>
                        </FormControl>
                        <FormControl margin="normal" required fullWidth>
                            <InputLabel>Password</InputLabel>
                            <Input type="password" value={this.state.password} onChange={this.handlePasswordChange}/>
                        </FormControl>
                        <Button
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


export default Login;
