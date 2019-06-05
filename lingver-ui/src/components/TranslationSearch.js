import React, {Component} from 'react';
import {Input, List, ListItem, Typography, withStyles} from '@material-ui/core';
import {translationService} from "../service/translationService";
import {withSnackbar} from "notistack";
import Fab from "@material-ui/core/Fab";
import Divider from "@material-ui/core/Divider";
import InputAdornment from "@material-ui/core/InputAdornment";
import AddIcon from '@material-ui/icons/Add';
import DoneIcon from '@material-ui/icons/Done';
import Paper from "@material-ui/core/Paper";
import Badge from "@material-ui/core/Badge";
import {Trans, withTranslation} from "react-i18next";
import classNames from 'classnames';

const styles = theme => ({
    fab: {
        marginRight: '5px'
    },
    userNewTranslation: {
        paddingBottom: '10px'
    },
    root: {
        padding: theme.spacing.unit * 4
    },
    padding: {
        padding: `0 ${theme.spacing.unit * 2}px`,
    },
    sticky: {
        position: 'sticky',
        top: 0,
        zIndex: 10,
    }
});

class TranslationSearch extends Component {
    constructor(props) {
        super(props);
        this.state = {
            translations: [],
            userSearch: '',
            userNewTranslation: '',
            typing: false,
            typingTimeout: 0,
        };
    }

    handleSearchChange = (event) => {
        if (this.state.typingTimeout) {
            clearTimeout(this.state.typingTimeout);
        }

        this.setState({
            userSearch: event.target.value,
            typing: false,
            typingTimeout: setTimeout(() => {
                translationService.searchTranslationsByWord(this.state.userSearch)
                    .then(
                        data => this.setState({translations: data}))
            }, 300)
        });
    };

    handleUserNewTranslationChange = (event) => {
        this.setState({userNewTranslation: event.target.value});
    };

    saveNewTranslation = () => {
        const newTranslation = {
            word: this.state.userSearch,
            translation: this.state.userNewTranslation
        };

        translationService.saveNewTranslation(newTranslation)
            .then(() => {
                this.setState({userNewTranslation: '', userSearch: '', translations: []});
                this.props.onNewWord();
            }).catch((error) => {
            error.response.data.forEach((error) => {
                this.props.enqueueSnackbar(error.message);
            });
        });
    };

    addTranslation = (translation) => {
        translationService.addTranslation(translation)
            .then(() => {
                this.props.onNewWord();
                this.setState({userSearch: '', translations: []});
            })
            .catch((error) => {
                error.response.data.forEach((error) => {
                    this.props.enqueueSnackbar(error.message);
                });
            });
    };


    render() {
        const {translations} = this.state;
        const {classes, t} = this.props;

        const translationList = translations.map(translation => {
            return (
                <React.Fragment key={translation.id}>
                    <ListItem>
                        {
                            translation.added ?
                                <Fab size="small"
                                     color="primary"
                                     disabled
                                     className={classes.fab}
                                ><DoneIcon/></Fab>
                                :
                                <Fab size="small"
                                     color="primary"
                                     className={classes.fab}
                                     onClick={() => this.addTranslation(translation)}
                                ><AddIcon/></Fab>

                        }
                        <Badge badgeContent={translation.rating} color="primary">
                            <Typography className={classes.padding}>{translation.value}</Typography>
                        </Badge>
                    </ListItem>
                    <Divider variant={"middle"}/>
                </React.Fragment>
            )
        });

        return (
            <Paper className={classNames(classes.root, classes.sticky)}>
                <Typography variant={"h5"}><Trans>Add new word</Trans></Typography>
                <Input type="text" value={this.state.userSearch} fullWidth
                       onChange={this.handleSearchChange}/>
                <List>{translationList}
                    {
                        this.state.userSearch ?
                            <ListItem key={this.state.userSearch}>
                                <Input
                                    value={this.state.userNewTranslation}
                                    onChange={this.handleUserNewTranslationChange}
                                    fullWidth
                                    placeholder={t("Your variant")}
                                    className={classes.userNewTranslation}
                                    startAdornment={
                                        <InputAdornment position="start">
                                            <Fab size="small"
                                                 color="primary"
                                                 className={classes.fab}
                                                 onClick={this.saveNewTranslation}
                                            > <AddIcon/>
                                            </Fab>
                                        </InputAdornment>
                                    }
                                >
                                </Input>
                            </ListItem>
                            : null}
                </List>
            </Paper>
        );
    }
}

export default withTranslation()(withStyles(styles)(withSnackbar(TranslationSearch)));