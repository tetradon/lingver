import React, {Component} from 'react';
import './App.css';
import LingverNavbar from './LingverNavbar';
import {Link} from 'react-router-dom';
import {Container} from 'reactstrap';

class Home extends Component {
    render() {
        return (
            <div>
                <LingverNavbar/>
                <Container fluid>
                    <Link to="/search">Search</Link>
                </Container>
            </div>
        );
    }
}

export default Home;