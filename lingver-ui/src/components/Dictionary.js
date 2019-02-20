import React, {Component} from "react";
import TranslationSearch from "./TranslationSearch";
import {Grid, Typography} from '@material-ui/core';

class Dictionary extends Component {

    render() {
        return (
            <Grid container justify="center">
                <Grid item xs={4}>
                    <Typography variant={"h3"}>My dictionary</Typography>
                </Grid>
                <Grid item xs={2}>
                    <TranslationSearch/>
                </Grid>
            </Grid>
        );
    }
}

export default Dictionary;