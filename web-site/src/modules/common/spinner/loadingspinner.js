import React from 'react';
import { Spinner, Row, Container } from 'react-bootstrap'

const LoadingSpinner = () => (
    <Container>
        <Row className="justify-content-md-center">
            <Spinner
                as="span"
                animation="grow"
                role="status"
                aria-hidden="true"
            />
            Loading...
    </Row>
    </Container>
);

export default LoadingSpinner;