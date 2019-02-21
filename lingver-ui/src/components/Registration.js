import React, {Component} from 'react';
import {Button, Grid, Paper, TextField, Typography, withStyles} from "@material-ui/core"
import AlertSnackbar from "./AlertSnackbar";

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
            username: '',
            password: '',
            loading: false,
            error: false,
            errorMessage: ''
        };
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
                                        required
                                        label="Username"
                                        fullWidth
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        required
                                        label="Email"
                                        fullWidth
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        required
                                        label="First name"
                                        fullWidth
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        required
                                        label="Last Name"
                                        fullWidth
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        required
                                        label="Password"
                                        fullWidth
                                    />
                                </Grid>
                                <Grid item xs={12} sm={6}>
                                    <TextField
                                        required
                                        label="Repeat password"
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
