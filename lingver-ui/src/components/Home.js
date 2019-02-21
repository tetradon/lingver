import React, {Component} from 'react';
import {Button, Card, CardActions, CardContent, Grid, Typography} from "@material-ui/core";

class Home extends Component {
    render() {
        return (
            <Grid container justify="center" style={{margin: 20}} spacing={16}>
                <Grid item xs={2}>
                    <Card>
                        <CardContent>
                            <Typography color="textSecondary">
                                Translation - Word
                            </Typography>
                        </CardContent>
                        <CardActions>
                            <Button variant="contained"
                                    color="primary">Start</Button>
                        </CardActions>
                    </Card>
                </Grid>
                <Grid item xs={2}>
                    <Card>
                        <CardContent>
                            <Typography color="textSecondary">
                                Word - Translation
                            </Typography>
                        </CardContent>
                        <CardActions>
                            <Button variant="contained"
                                    color="primary">Start</Button>
                        </CardActions>
                    </Card>
                </Grid>
            </Grid>
        );

    }
}

export default Home;