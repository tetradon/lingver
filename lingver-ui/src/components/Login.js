import React, {Component} from 'react';
import {withRouter} from "react-router-dom";
import {Button, Input, InputGroup, InputGroupAddon, InputGroupText} from "reactstrap";
import {userService} from "../service/userService";

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

    componentDidMount() {
        /* axios.get('/profile')
             .then(function (response) {
                 console.log(response);
             }).catch(function (res) {
             console.log(res.response);
         })*/
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
            .then(response => {
                const {from} = this.props.location.state || {from: {pathname: "/"}};
                this.props.history.push(from);
            })
            .catch(error => {
                alert("WRONG CREDENTIALS!");
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

export default withRouter(Login);
