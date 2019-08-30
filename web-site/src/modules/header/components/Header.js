import React, { useState, useEffect } from 'react';
import { Navbar, Nav, Button } from "react-bootstrap";
import axios from './../../common/axios/axios-cognito'
import logo from './../../../logo.png'
import queryString from 'query-string'


const Header = () => {

    const [user, setUser] = useState(null);
    const [token, setToken] = useState(null);
    
    const logout = () => {
        if (typeof window !== 'undefined') {
              window.location.href = "https://mathtutio.auth.us-east-1.amazoncognito.com/logout?client_id=6df5hjknc1j65qbbi8kp5tjr6s&logout_uri=https://s3.amazonaws.com/mathtutio/public.html";
            //window.location.href = "CognitoAuthLogout-URL";
        }
    }

    useEffect(() => {

        const value = queryString.parse(window.location.search);
        const accessToken = value.access_token;
        const idToken = value.id_token;
        setToken('?id_token=' + idToken + '&access_token=' + accessToken);
        if (accessToken == null) {
            logout();
        }

        const auth = 'Bearer '.concat(accessToken);
        axios.get('/oauth2/userInfo', { headers: { Authorization: auth } })
            .then(response => {
                setUser(response.data.username);
            })
            .catch((error) => {
                console.log('error ' + error);
                logout();
            });

    }, []);

    return (
        <Navbar fixed="top" expand="lg" variant="light" bg="light">
            <Navbar.Brand href="/">
                <img
                    alt="mathtutio"
                    src={logo}
                    className="d-inline-block align-top"
                    height="35"
                />
            </Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="mr-auto">
                    <Nav.Link href={`/${token}`}>Home</Nav.Link>
                </Nav>
                <Navbar.Collapse className="justify-content-center">
                    <Navbar.Text>
                        Welcome back, <a href="#user">{user}!</a>
                    </Navbar.Text>
                </Navbar.Collapse>
                <Nav>
                    <Button variant="outline-secondary" onClick={() => logout()}>logout</Button>
                </Nav>
            </Navbar.Collapse>
        </Navbar>
    );
}

export default Header;
