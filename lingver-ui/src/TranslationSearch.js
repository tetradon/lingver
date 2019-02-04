import React, {Component} from 'react';
import {Container, Input, InputGroup} from 'reactstrap';
import LingverNavbar from './LingverNavbar';


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
                fetch(`/translation?word=${this.state.userSearch}`)
                    .then(response => response.json())
                    .then(data => this.setState({translations: data}));
            }, 300)
        });
    }


    render() {
        const {translations} = this.state;

        const translationList = translations.map(word => {
            return <li key={word.id}>
                {word.value}
            </li>
        });

        return (
            <div>
                <LingverNavbar/>
                <Container fluid>
                    <InputGroup>
                        <Input type="text" value={this.state.userSearch}
                               onChange={this.handleChange}/>
                    </InputGroup>
                    <ul>{translationList}</ul>
                </Container>
            </div>
        );
    }
}

export default TranslationSearch;