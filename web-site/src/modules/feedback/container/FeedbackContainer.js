import React, { Component, Fragment } from 'react';
import { Form, Col, Button } from "react-bootstrap";

class FeedbackContainer extends Component {
    constructor(...args) {
        super(...args);
        this.state = { validated: false };
    }

    handleSubmit(event) {
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
        }
        this.setState({ validated: true });
        this.props.history.push('/feedback-confirm')
    }

    render() {
        const { validated } = this.state;
        return (
            <Fragment>
                <h5>Feedback</h5>
                <Form
                    noValidate
                    validated={validated}
                    onSubmit={e => this.handleSubmit(e)}
                >
                    <Form.Row>
                        <Form.Group as={Col} md="4" controlId="firstName">
                            <Form.Label>First name</Form.Label>
                            <Form.Control
                                required
                                type="text"
                                placeholder="First name"
                                defaultValue=""
                            />
                        </Form.Group>
                        <Form.Group as={Col} md="4" controlId="lastName">
                            <Form.Label>Last name</Form.Label>
                            <Form.Control
                                required
                                type="text"
                                placeholder="Last name"
                                defaultValue=""
                            />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} md="4" controlId="email">
                            <Form.Label>Email address</Form.Label>
                            <Form.Control type="email" required placeholder="Enter email" />
                            <Form.Text className="text-muted">
                                We'll never share your email with anyone else.
                            </Form.Text>
                        </Form.Group>
                        <Form.Group as={Col} md="4" controlId="mobile">
                            <Form.Label>Mobile</Form.Label>
                            <Form.Control required type="number" placeholder="Enter mobile" />
                            <Form.Text className="text-muted">
                                We'll never share your mobile with anyone else.
                            </Form.Text>
                        </Form.Group>
                    </Form.Row>
                    <Form.Group controlId="comments">
                        <Form.Label>Comments</Form.Label>
                        <Form.Control required as="textarea" rows="3" />
                    </Form.Group>
                    <Button type="submit" id="submit" >Submit</Button>
                </Form>
            </Fragment>
        );
    }
}
export default FeedbackContainer;