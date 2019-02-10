import React, {Component} from 'react';
import './App.css';
import {Button, Card, CardTitle, Col, Container, Row} from "reactstrap";

class Home extends Component {
    render() {
        return (
            <div>
                <Container>
                    <h1>Train your dictionary</h1>
                    <Row>
                        <Col sm="3">
                            <Card body>
                                <CardTitle>Word-Translation</CardTitle>
                                <Button>Start</Button>
                            </Card>
                        </Col>
                        <Col sm="3">
                            <Card body>
                                <CardTitle>Translation-Word</CardTitle>
                                <Button>Start</Button>
                            </Card>
                        </Col>
                    </Row>
                </Container>
            </div>
        );
    }
}

export default Home;