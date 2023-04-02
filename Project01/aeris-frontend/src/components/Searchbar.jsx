import styles from "@/styles/Searchbar.module.css";
import { Wind, GithubLogo, MagnifyingGlass } from "@phosphor-icons/react";
import { Form, Button } from "react-bootstrap";

export default function Searchbar() {
	return (
		<>
			<Form className={styles.searchBarWrapper}>
				<Form.Control maxLength={60} type="text" placeholder="Search City" />
				<Button variant="" type="submit" className={styles.searchButton}>
					<MagnifyingGlass size={32} />
				</Button>
			</Form>
		</>
	);
}
