import React, {Component} from 'react';
import {Button, Grid, Paper, TextField, Typography, withStyles} from "@material-ui/core"
import AlertSnackbar from "./AlertSnackbar";
import {registrationService} from "../service/registrationService"
import {withSnackbar} from 'notistack';
import LinearProgress from "@material-ui/core/LinearProgress";

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
            repeatPassword: '',
        };
        this.handleUserChange = this.handleUserChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleRepeatPasswordChange = this.handleRepeatPasswordChange.bind(this)
        this.isValid = this.isValid.bind(this);
    }

    handleUserChange(evt) {
        this.setState({user: {...this.state.user, [evt.target.name]: evt.target.value}});
    }

    handleSubmit() {
        this.setState({loading: true});
        registrationService.register(this.state.user)
            .then(() => {
                this.props.history.push("/");
            })
            .catch((error) => {
                error.response.data.forEach((error) => {
                    this.props.enqueueSnackbar(error.message, {
                        variant: 'warning'
                    });
                });
            }).finally(() => {
            this.setState({loading: false});
        });
    }

    handleRepeatPasswordChange(evt) {
        this.setState({repeatPassword: evt.target.value});
    }

    isValid() {
        return this.state.repeatPassword !== ''
            && this.state.user.password !== ''
            && this.state.repeatPassword === this.state.user.password
            && this.state.user.username !== ''
            && this.state.user.email !== ''
    }

    render() {
        const {classes} = this.props;
        return (
            <div>
                <LinearProgress hidden={!this.state.loading} variant="query"/>
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
                                        onChange={this.handleUserChange}
                                        label="Username"
                                        required
                                        name='username'
                                        fullWidth
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        onChange={this.handleUserChange}
                                        label="Email"
                                        name="email"
                                        fullWidth
                                        required
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        onChange={this.handleUserChange}
                                        label="First name"
                                        name="firstName"
                                        fullWidth
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        onChange={this.handleUserChange}
                                        label="Last Name"
                                        name="lastName"
                                        fullWidth
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        onChange={this.handleUserChange}
                                        label="Password"
                                        name="password"
                                        fullWidth
                                        required
                                        type="password"
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        onChange={this.handleRepeatPasswordChange}
                                        error={this.state.repeatPassword !== this.state.user.password}
                                        label={"Repeat password"}
                                        name="repeatPassword"
                                        fullWidth
                                        required
                                        type="password"
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
                                            disabled={
                                                !this.isValid()
                                            }
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

export default withStyles(styles)(withSnackbar(Registration));
