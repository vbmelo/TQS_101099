import styles from "@/styles/Menubar.module.css";
import { Wind, GithubLogo, MagnifyingGlass } from "@phosphor-icons/react";
import { Form, Container, Nav, Navbar } from "react-bootstrap";
import Searchbar from "./Searchbar";

export default function Menubar() {
	return (
		<>
			<Navbar variant="dark" expand="lg" className={styles.navbarWrapper}>
					<Navbar.Brand href="#home"><Wind size={32} /> A E R I S</Navbar.Brand>
					<Navbar.Toggle aria-controls="basic-navbar-nav" />
					<Navbar.Collapse id="basic-navbar-nav" className={styles.navbarLinks}>
						<Searchbar />
						<Nav className={styles.nav}>
							<Nav.Link href="#link" className={styles.linkGithubWrapper}>
									Victor Melo
									<span>
									<GithubLogo size={28} />
									</span>
							</Nav.Link>
						</Nav>
					</Navbar.Collapse>
			</Navbar>
		</>
	);
}
