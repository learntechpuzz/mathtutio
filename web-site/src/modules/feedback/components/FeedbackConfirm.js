import React, {Fragment} from 'react';
import { Alert } from "react-bootstrap";


const FeedbackConfirm = () => {
    return (
        <Fragment>
        <h5>Feedback</h5>
        <Alert variant="success">
            <p id="feedbackConfirmMsg">Thank you very much for the valuable feedback!</p>
        </Alert>
        </Fragment>
    );
}

export default FeedbackConfirm;