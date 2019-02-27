import React, {Component} from "react";
import TranslationSearch from "./TranslationSearch";
import {Grid} from '@material-ui/core';
import {translationService} from "../service/translationService";
import TranslationList from "./TranslationList";

class Dictionary extends Component {
    constructor(props) {
        super(props);
        this.state = {
            translationList: [],
            totalElements: 0
        };
        this.reloadData = this.reloadData.bind(this);
    }

    reloadData(params) {
        translationService.getTranslations(params)
            .then((response) => {
                this.setState({translationList: response.data});
                this.setState({totalElements: response.headers.total});
            });
    }

    render() {
        return (
            <Grid container justify="center" spacing={40}>
                <Grid item xs={5}>
                    <TranslationList onQueryParamsChange={this.reloadData} totalElements={this.state.totalElements}
                                     translations={this.state.translationList}/>
                </Grid>
                <Grid item xs={5}>
                    <TranslationSearch/>
                </Grid>
            </Grid>
        );
    }
}

export default Dictionary;