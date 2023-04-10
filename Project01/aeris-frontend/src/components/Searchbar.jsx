import styles from "@/styles/Searchbar.module.css";
import React, {useContext} from "react";
import { Wind, GithubLogo, MagnifyingGlass } from "@phosphor-icons/react";
import { Form, Button } from "react-bootstrap";
import { DataContext } from "@/contexts/DataContext";

export default function Searchbar({searchAirQuality}) {
	const {searchQuery, newSearchQuery} = useContext(DataContext);
	return (
		<>
			<Form className={styles.searchBarWrapper}>
				<Form.Control maxLength={60} type="text" placeholder="Search City" onChange={e => newSearchQuery(e.target.value)}/>
				<Button variant="" type="submit" className={styles.searchButton} onClick={e=>searchAirQuality(e)} onKeyPress={
					(e) => {
						if (e.key === "Enter") {
							searchAirQuality(e);
						}
					}
				}>
					<MagnifyingGlass size={32} />
				</Button>
			</Form>
		</>
	);
}
