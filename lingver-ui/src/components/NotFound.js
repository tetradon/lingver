import React, {Component} from 'react';
import {Button, Divider, Grid, Paper, Typography, withStyles} from "@material-ui/core";

const styles = theme => ({

    paper: {
        padding: `${theme.spacing.unit * 10}px ${theme.spacing.unit * 30}px 0`,
    },
    subtitle: {
        marginTop: '10%',
    },
    button: {
        margin: '30% 0 10%',
    }
});

class NotFound extends Component {

    render() {
        const {classes} = this.props;
        return (
            <Grid container justify="center">
                <Grid item xs={10}>
                    <Grid
                        container
                        alignItems="center"
                        justify="center"
                    >
                        <Grid item>
                            <Paper className={classes.paper}>
                                <Typography className={classes.heading} variant="h1" align="center">
                                    404
                                    <Divider variant="middle"/>
                                </Typography>
                                <Typography className={classes.subtitle} variant="h2">
                                    Not Found
                                </Typography>
                                <Button className={classes.button}
                                        size="large"
                                        color="primary"
                                        variant="outlined"
                                        onClick={this.props.history.goBack}
                                        fullWidth>
                                    Back
                                </Button>
                            </Paper>
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        );
    }
}

export default withStyles(styles)(NotFound);