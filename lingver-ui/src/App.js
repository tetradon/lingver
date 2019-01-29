import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';

class App extends Component {
    state = {
        isLoading: true,
        words: []
    };

    async componentDidMount() {
        const response = await fetch('/words');
        const body = await response.json();
        this.setState({words: body, isLoading: false});
    }

    render() {
        const {words, isLoading} = this.state;
        if (isLoading) {
            return <p>Loading...</p>;
        }
        return (
            <div className="App">
                <header className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <p>
                        Edit <code>src/App.js</code> and save to reload.
                    </p>
                    <a
                        className="App-link"
                        href="https://reactjs.org"
                        target="_blank"
                        rel="noopener noreferrer"
                    >
                        Learn React
                    </a>
                    <div className="App-intro">
                        <h2>Words</h2>
                        {words.map(word =>
                            <div>
                                {word.value}
                            </div>
                        )}
                    </div>
                </header>
            </div>
        );
    }
}

export default App;
