import React, {Component} from 'react';
import './App.css';
import LingverNavbar from './LingverNavbar';
import {Link} from 'react-router-dom';
import {Button, Container} from 'reactstrap';

class Home extends Component {
    render() {
        return (
            <div>
                <LingverNavbar/>
                <Container fluid>
                    <Button color="link"><Link to="/search">Search</Link></Button>
                </Container>
            </div>
        );
    }
}

export default Home;