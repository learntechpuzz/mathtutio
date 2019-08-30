import React, { Fragment } from 'react'
import { Card, Button, Row, Col } from 'react-bootstrap'
import LoadingSpinner from './../../common/spinner/loadingspinner';

const Courses = (props) => {
    return (
        <Fragment>
            <Row>
                {props.loading ? <LoadingSpinner /> : null }
                {props.courses.map((course, idx) => {
                    return (
                        <Col key={idx}>
                            <Card style={{ width: '18rem' }}>
                                <Card.Img variant="top" src={course.logoFileName} />
                                <Card.Body>
                                    <Card.Title>{course.title}</Card.Title>
                                    <Card.Text>
                                        {course.summary}
                                    </Card.Text>
                                    <Button variant="primary" onClick={() => props.viewCourseDetails(course.courseId)}>Get Started</Button>
                                </Card.Body>
                            </Card>

                        </Col>
                    )
                })}
            </Row>
        </Fragment>
    );
}
export default Courses;
