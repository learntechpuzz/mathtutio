import React, { Component } from 'react';
import { withRouter } from 'react-router-dom'
import Courses from './../components/Courses'
import axios from './../../common/axios/axios-api'
import withErrorHandler from './../../common/withErrorHandler/withErrorHandler'
import queryString from 'query-string'


class HomeContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            loading: false,
            id_token: null,
            access_token: null,
            courses: [],
        }
    }

    getAllCourses() {
        const value = queryString.parse(this.props.location.search);
        const idToken = value.id_token;
        const accessToken = value.access_token;
        this.setState({
            id_token: idToken,
            access_token: accessToken,
        });
        this.setState({ loading: true }, () => {
            axios.get('/courses', { headers: { Authorization: idToken } })
                .then(response => {

                    this.setState({ loading: false, courses: response.data })
                })
                .catch(err => console.log(err))
        });
    }


    componentDidMount() {
        this.getAllCourses();
    }

    viewCourseDetails = (courseId) => {

        this.props.history.push('/course-details?courseId=' + courseId + '&id_token=' + this.state.id_token + '&access_token=' + this.state.access_token);
    }
    render() {
        return (
            <Courses courses={this.state.courses} viewCourseDetails={(courseId) => this.viewCourseDetails(courseId)} loading={this.state.loading} />
        );
    }
}

export default withRouter(withErrorHandler(HomeContainer, axios));