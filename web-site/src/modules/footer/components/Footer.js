import React from 'react';
import { Navbar } from "react-bootstrap";

const Footer = () => {
  return (

    <Navbar fixed="bottom" variant="light" bg="light">
      <Navbar.Collapse className="justify-content-end">
        <Navbar.Text>
          Copyright © 2019, mathtutio. All rights reserved
        </Navbar.Text>
      </Navbar.Collapse>
    </Navbar >

  );
}

export default Footer;