import React, {Component} from 'react';
import {Button, Input, List, ListItem, Typography, withStyles} from '@material-ui/core';

const styles = {};

class TranslationSearch extends Component {
    constructor(props) {
        super(props);
        this.state = {
            translations: [],
            userSearch: '',
            typing: false,
            typingTimeout: 0
        };
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        if (this.state.typingTimeout) {
            clearTimeout(this.state.typingTimeout);
        }

        this.setState({
            userSearch: event.target.value,
            typing: false,
            typingTimeout: setTimeout(() => {
                fetch(`api/translation?word=${this.state.userSearch}`)
                    .then(response => response.json())
                    .then(data => this.setState({translations: data}));
            }, 300)
        });
    }


    render() {
        const {translations} = this.state;

        const translationList = translations.map(word => {
            return (
                <ListItem key={word.id}>
                    {word.value} <Button>+</Button>
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