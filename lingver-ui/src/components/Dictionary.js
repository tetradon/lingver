import React, {Component} from "react";
import TranslationSearch from "./TranslationSearch";
import {Grid, LinearProgress} from '@material-ui/core';
import {translationService} from "../service/translationService";
import TranslationList from "./TranslationTable";

class Dictionary extends Component {
    constructor(props) {
        super(props);
        this.state = {
            dialogIsOpen: false,
            translations: [],
            translationIds: [],
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
                this.setState({translations: response.data.translations});
                this.setState({translationIds: response.data.allTranslationIds});
            })
            .finally(() => {
                    this.setState({isLoading: false})
                }
            );
    };

    remove = (ids) => {
        translationService.removeTranslations(ids).then(() => this.reload());
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
                <Grid item xs={10} lg={7}>
                    <TranslationList onQueryParamsChange={this.updateParams}
                                     onRemove={this.remove}
                                     params={this.state.params}
                                     selected={this.state.selected}
                                     totalElements={this.state.translationIds.length}
                                     translations={this.state.translations}/>
                    <LinearProgress variant={"query"} hidden={!this.state.isLoading}/>
                </Grid>
                <Grid item xs={10} lg={3}>
                    <TranslationSearch
                        onNewWord={this.reload}
                        translationIds={this.state.translationIds}
                        onRemove={this.remove}
                    />
                </Grid>
            </Grid>
        );
    }
}

export default Dictionary;