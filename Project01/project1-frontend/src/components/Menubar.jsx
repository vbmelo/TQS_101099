import './Menubar.css';
import { useState, useEffect } from 'react';
import { Nav, NavLink, Navbar, NavbarBrand, Container } from 'react-bootstrap';
import { Wind } from 'phosphor-react';

export default function Menubar(){
	return (
		<Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
      <Container>
        <Navbar.Brand href=""><Wind size={32} />Aeris Qualitas</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          {/* <Nav className="me-auto">
          </Nav>
          <Nav>
            <Nav.Link href="#deets">More deets</Nav.Link>
            <Nav.Link eventKey={2} href="#memes">
              Dank memes
            </Nav.Link>
          </Nav> */}
        </Navbar.Collapse>
      </Container>
    </Navbar>
	)
}