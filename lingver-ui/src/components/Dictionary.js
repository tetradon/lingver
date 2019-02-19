import React, {Component} from "react";
import {Col, Container, Row} from "reactstrap";
import TranslationSearch from "./TranslationSearch";

class Dictionary extends Component {

    render() {
        return (
            <div>
                <Container>
                    <Row>
                        <Col md="8"><h1>My dictionary</h1></Col>
                        <Col md="4"> <TranslationSearch/> </Col>
                    </Row>
                </Container>
            </div>
        );
    }
}

export default Dictionary;