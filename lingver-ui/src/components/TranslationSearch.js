import React, {Component} from 'react';
import {Button, Input, InputGroup, ListGroup, ListGroupItem} from 'reactstrap';

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
                <ListGroupItem key={word.id}>
                    {word.value} <Button className="float-right" size="sm" color="primary">+</Button>
                </ListGroupItem>
            )
        });

        return (
            <div>
                <h2>Add new word</h2>
                    <InputGroup>
                        <Input type="text" value={this.state.userSearch}
                               onChange={this.handleChange}/>
                    </InputGroup>
                    <ListGroup>{translationList}</ListGroup>
            </div>
        );
    }
}

export default TranslationSearch;