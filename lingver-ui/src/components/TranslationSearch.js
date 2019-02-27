import React, {Component} from 'react';
import {Button, Input, List, ListItem, Typography, withStyles} from '@material-ui/core';
import {translationService} from "../service/translationService";

const styles = {};

class TranslationSearch extends Component {
    constructor(props) {
        super(props);
        this.state = {
            translations: [],
            userSearch: '',
            typing: false,
            typingTimeout: 0,
        };
        this.handleChange = this.handleChange.bind(this);
        this.saveTranslation = this.saveTranslation.bind(this);
    }

    handleChange(event) {
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
    }

    saveTranslation(translation) {
        translationService.addTranslation(translation);
    }


    render() {
        const {translations} = this.state;

        const translationList = translations.map(translation => {
            return (
                <ListItem key={translation.id}>
                    {translation.value} <Button onClick={() => this.saveTranslation(translation)}>+</Button>
                </ListItem>
            )
        });

        return (
            <div>
                <Typography variant={"h3"}>Add new word</Typography>
                <Input type="text" value={this.state.userSearch} fullWidth
                       onChange={this.handleChange}/>
                <List>{translationList}</List>
            </div>
        );
    }
}

export default withStyles(styles)(TranslationSearch);