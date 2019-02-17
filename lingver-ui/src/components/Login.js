import React, {Component} from 'react';
import axios from "axios";

import {Button, Input, InputGroup, InputGroupAddon, InputGroupText} from "reactstrap";

class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
        };
        this.login = this.login.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);
        this.handleUsernameChange = this.handleUsernameChange.bind(this);
    }

    componentDidMount() {
        axios.get('/profile')
            .then(function (response) {
                console.log(response);
            }).catch(function (res) {
            console.log(res.response);
        })
    }

    handleUsernameChange(event) {
        this.setState({username: event.target.value});
    }

    handlePasswordChange(event) {
        this.setState({password: event.target.value});
    }

    login() {
        let bodyFormData = new FormData();
        bodyFormData.set('username', this.state.username);
        bodyFormData.set('password', this.state.password);
        axios.post('/login', bodyFormData)
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error.response);
            });
    }

    render() {
        return (
            <div style={{width: '50%'}}>
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
            </div>
        )
    }
}

export default Login;
