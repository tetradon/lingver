import React, {Component} from "react";
import TranslationSearch from "./TranslationSearch";
import {Grid} from '@material-ui/core';
import {translationService} from "../service/translationService";
import TranslationList from "./TranslationList";

class Dictionary extends Component {
    constructor(props) {
        super(props);
        this.state = {
            translationList: []
        };
    }

    componentDidMount() {
        translationService.getTranslations()
            .then((response) => {
                this.setState({translationList: response})
            });
    }

    render() {

        return (
            <Grid container justify="center" spacing={40}>
                <Grid item xs={5}>
                    <TranslationList translations={this.state.translationList}/>
                </Grid>
                <Grid item xs={5}>
                    <TranslationSearch/>
                </Grid>
            </Grid>
        );
    }
}

export default Dictionary;