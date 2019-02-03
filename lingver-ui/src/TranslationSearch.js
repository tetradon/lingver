import React, {Component} from 'react';
import {Container, Input, InputGroup} from 'reactstrap';
import LingverNavbar from './LingverNavbar';


class TranslationSearch extends Component {
    constructor(props) {
        super(props);
        this.state = {
            translations: [],
            value: '',
            typing: false,
            typingTimeout: 0
        };
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        const self = this;

        if (self.state.typingTimeout) {
            clearTimeout(self.state.typingTimeout);
        }

        self.setState({
            value: event.target.value,
            typing: false,
            typingTimeout: setTimeout(function () {
                fetch(`/translation?word=${self.state.value}`)
                    .then(response => response.json())
                    .then(data => self.setState({translations: data}));
            }, 1000)
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
                        <Input type="text" value={this.state.value}
                               onChange={this.handleChange}/>
                    </InputGroup>
                    <ul>{translationList}</ul>
                </Container>
            </div>
        );
    }
}

export default TranslationSearch;