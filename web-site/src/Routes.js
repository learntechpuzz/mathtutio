import React from 'react';
import {Route, Switch} from 'react-router-dom';
import HomeContainer from './modules/home/container/HomeContainer';
import CourseDetailsContainer from './modules/coursedetails/container/CourseDetailsContainer';
import FeedbackContainer from './modules/feedback/container/FeedbackContainer';
import FeedbackConfirm from './modules/feedback/components/FeedbackConfirm';


const Routes = () => (
    <Switch>
        <Route exact path="/" component={HomeContainer} />
        <Route exact path="/feedback" component={FeedbackContainer} />
        <Route exact path="/feedback-confirm" component={FeedbackConfirm} />
        <Route exact path="/course-details" component={CourseDetailsContainer} />
    </Switch>
);

export default Routes;