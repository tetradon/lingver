import React, {Component} from "react";
import TranslationSearch from "./TranslationSearch";
import {Grid, LinearProgress} from '@material-ui/core';
import {translationService} from "../service/translationService";
import TranslationList from "./TranslationList";

class Dictionary extends Component {
    constructor(props) {
        super(props);
        this.state = {
            translationList: [],
            totalElements: 0,
            params: {
                sortField: 'insertDate',
                sortDirection: 'desc',
                page: 0,
                size: 10
            },
            isLoading: false
        };
    }

    componentDidMount() {
        this.reload();
    }

    reload = () => {
        this.setState({isLoading: true});
        translationService.getTranslations(this.state.params)
            .then((response) => {
                this.setState({translationList: response.data});
                this.setState({totalElements: response.headers.total});
            })
            .finally(() => {
                    this.setState({isLoading: false})
                }
            );
    };

    updateParams = (newParams) => {
        this.setState({params: Object.assign(this.state.params, newParams)}, () => {
            this.reload();
        });
    };

    render() {
        return (
            <Grid container
                  justify="center"
                  spacing={40}>
                <Grid item xs={5}>
                    <TranslationList onQueryParamsChange={this.updateParams}
                                     params={this.state.params}
                                     totalElements={this.state.totalElements}
                                     translations={this.state.translationList}/>
                    <LinearProgress variant={"query"} hidden={!this.state.isLoading}/>
                </Grid>
                <Grid item xs={5}>
                    <Grid container justify="center">
                        <TranslationSearch onNewWord={this.reload}/>
                    </Grid>
                </Grid>
            </Grid>
        );
    }
}

export default Dictionary;