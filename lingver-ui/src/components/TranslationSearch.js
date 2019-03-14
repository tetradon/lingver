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
                this.props.onNewWord();
                this.setState({userNewTranslation: '', userSearch: '', translations: []});
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
        const {classes, translationIds} = this.props;

        const translationList = translations.map(translation => {
            return (
                <React.Fragment key={translation.id}>
                    <ListItem>
                        {!translationIds.includes(translation.id)
                            ?
                            <Fab size="small"
                                 color="primary"
                                 className={classes.fab}
                                 onClick={() => this.addTranslation(translation)}><AddIcon/></Fab>
                            :
                            <Fab size="small"
                                 color="primary"
                                 disabled
                                 className={classes.fab}
                            ><DoneIcon/></Fab>
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
            <Paper className={classes.root}>
                <Typography variant={"h5"}>Add new word</Typography>
                <Input type="text" value={this.state.userSearch} fullWidth
                       onChange={this.handleSearchChange}/>
                <List>{translationList}
                    <ListItem key={this.state.userSearch} hidden={!this.state.userSearch}>
                        <Input
                            value={this.state.userNewTranslation}
                            onChange={this.handleUserNewTranslationChange}
                            fullWidth
                            placeholder="Your variant"
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
                </List>
            </Paper>
        );
    }
}

export default withStyles(styles)(withSnackbar(TranslationSearch));