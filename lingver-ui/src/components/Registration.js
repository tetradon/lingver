import React, {Component} from 'react';
import {Button, Grid, Paper, TextField, Typography, withStyles} from "@material-ui/core"
import AlertSnackbar from "./AlertSnackbar";
import {registrationService} from "../service/registrationService"

const styles = theme => ({
    paper: {
        padding: theme.spacing.unit * 3,
    },
    submit: {
        marginTop: theme.spacing.unit * 3,
        padding: theme.spacing.unit * 2,
    },
    marginTop5: {
        marginTop: '5%'
    }
});

class Registration extends Component {
    constructor(props) {
        super(props);
        this.state = {
            user: {
                username: '',
                password: '',
                email: '',
                firstName: '',
                lastName: '',
            },
            loading: false,
            error: false,
            errorMessage: ''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(evt) {
        console.log([evt.target.name] + " " + evt.target.value);
        this.setState({user: {...this.state.user, [evt.target.name]: evt.target.value}});
        console.log(this.state);
    }

    handleSubmit(evt) {
        registrationService.register(this.state.user)
    }

    render() {
        const {classes} = this.props;
        return (
            <div>
                {this.state.error === true ? <AlertSnackbar message={this.state.errorMessage}/> : null}
                <Grid container justify="center">
                    <Grid item xs={10} md={8} lg={6}>
                        <Paper className={classes.paper}>
                            <Typography variant="h2" align={"center"}>
                                Registration
                            </Typography>
                            <Grid className={classes.marginTop5} container spacing={32}>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        onChange={this.handleChange}
                                        label="Username"
                                        name='username'
                                        fullWidth
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        onChange={this.handleChange}
                                        label="Email"
                                        name="email"
                                        fullWidth
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        onChange={this.handleChange}
                                        label="First name"
                                        name="firstName"
                                        fullWidth
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        onChange={this.handleChange}
                                        label="Last Name"
                                        name="lastName"
                                        fullWidth
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        onChange={this.handleChange}
                                        label="Password"
                                        name="password"
                                        fullWidth
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        onChange={this.handleChange}
                                        label="Repeat password"
                                        name="repeatPassword"
                                        fullWidth
                                    />
                                </Grid>
                                <Grid item xs={8} md={10}/>
                                <Grid item xs={4} md={2}>
                                    <Button className={classes.submit}
                                            type="submit"
                                            fullWidth
                                            variant="contained"
                                            color="primary"
                                            onClick={this.handleSubmit}
                                    >
                                        Submit
                                    </Button>
                                </Grid>
                            </Grid>
                        </Paper>
                    </Grid>
                </Grid>
            </div>
        )
    }
}

export default withStyles(styles)(Registration);
