import React from 'react';
import Routes from './Routes';
import { BrowserRouter } from 'react-router-dom';
import Header from './modules/header/components/Header';
import Footer from './modules/footer/components/Footer';
import { Container } from 'react-bootstrap';

const App = () => (
  <BrowserRouter>
    <Container>       
      <Header />
      <br/><br/><br/><br/>
      <Routes />
      <Footer />
    </Container>

  </BrowserRouter>
);

export default App;