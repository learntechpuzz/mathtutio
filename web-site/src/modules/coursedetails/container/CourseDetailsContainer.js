import React, { Component } from 'react';
import CourseDetails from '../components/CourseDetails'
import { withRouter } from 'react-router-dom'
import axios from './../../common/axios/axios-api'
import withErrorHandler from './../../common/withErrorHandler/withErrorHandler'
import queryString from 'query-string'

class CourseDetailsContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            rating: 4.5,
            id_token: null,
            access_token: null,
            course: null,
            courseMaterials: null,
        }
    }

    getCourseDetails() {
        const value = queryString.parse(this.props.location.search);
        const idToken = value.id_token;
        const accessToken = value.access_token;
        const courseId = value.courseId;

        this.setState({
            id_token: idToken,
            access_token: accessToken,
        });

        this.setState({ loading: true }, () => {
            axios.get('/courses/' + courseId, { headers: { Authorization: idToken } })
                .then(response => {
                    this.setState({ loading: false, course: response.data.value })
                })
                .catch(err => console.log(err))

            axios.get('/coursematerials/' + courseId, { headers: { Authorization: idToken } })
                .then(response => {
                    this.setState({ loading: false, courseMaterials: response.data })
                })
                .catch(err => console.log(err))
        });
    }

    componentDidMount() {
        this.getCourseDetails();
    }

    render() {
        return (<CourseDetails courseMaterials={this.state.courseMaterials} course={this.state.course} rating={this.state.rating} loading={this.state.loading} />);
    }

}

export default withRouter(withErrorHandler(CourseDetailsContainer, axios));
