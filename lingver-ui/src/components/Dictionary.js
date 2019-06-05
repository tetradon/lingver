import React, {Component} from "react";
import TranslationSearch from "./TranslationSearch";
import {Grid, LinearProgress, Typography} from '@material-ui/core';
import {translationService} from "../service/translationService";
import TranslationTable from "./table/TranslationTable";
import Paper from "@material-ui/core/Paper";
import MoodBadIcon from '@material-ui/icons/SentimentVeryDissatisfied';
import withStyles from "@material-ui/core/styles/withStyles";
import {Trans} from "react-i18next";


const styles = theme => ({
    noWordsMessage: {
        padding: theme.spacing.unit * 5,
        width: '50%',
        margin: 'auto'
    },

});


class Dictionary extends Component {
    constructor(props) {
        super(props);
        this.state = {
            dialogIsOpen: false,
            translations: [],
            total: 0,
            params: {
                sortField: 'insertDate',
                sortDirection: 'desc',
                page: 0,
                size: 10,
                search: ''
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
                this.setState({total: response.data.total});
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
        const {classes} = this.props;
        return (
            <div style={{padding: 20}}>
                <Grid container justify="center" spacing={32}>
                    <Grid item xs={10} lg={8}>
                        {this.state.total === 0 && !this.state.params.search && !this.state.isLoading
                            ?
                            <Paper className={classes.noWordsMessage}>
                                <Typography variant={"h5"}>
                                    <MoodBadIcon fontSize={"large"}/>
                                    <Trans>You haven't added any words yet</Trans>
                                </Typography>
                                <Typography align={"right"} variant={"h5"}>
                                    <Trans>Do it now!</Trans>
                                </Typography>
                            </Paper>
                            :
                            <TranslationTable onQueryParamsChange={this.updateParams}
                                              onReload={this.reload}
                                              onRemove={this.remove}
                                              params={this.state.params}
                                              selected={this.state.selected}
                                              totalElements={this.state.total}
                                              translations={this.state.translations}/>}

                        <LinearProgress variant={"query"} hidden={!this.state.isLoading}/>
                    </Grid>
                    <Grid item xs={10} lg={3}>
                        <TranslationSearch
                            onNewWord={this.reload}
                            onRemove={this.remove}
                        />
                    </Grid>
                </Grid>
            </div>
        );
    }
}

export default withStyles(styles)(Dictionary);