import React, { useContext, useState } from "react";
import Head from "next/head";
import Image from "next/image";
import { Inter } from "next/font/google";
import styles from "@/styles/Home.module.css";
import { Wind, GithubLogo, MagnifyingGlass } from "@phosphor-icons/react";
import { Button } from "react-bootstrap";
import Searchbar from "@/components/Searchbar";
import Menubar from "@/components/Menubar";
import { DataContext } from "@/contexts/DataContext";
import axios from "axios";
import { toast } from "react-toastify";

const inter = Inter({ subsets: ["latin"] });

export default function Home() {
	const { searchQuery, newSearchQuery } = useContext(DataContext);
	const [airQualityResponse, setAairQualityResponse] = useState("");
	const [predictionsResponse, setPredictionsResponse] = useState("");

	const clearResponses = () => {
		setAairQualityResponse("");
		setPredictionsResponse("");
	};

	const searchAirQuality = (e) => {
		clearResponses();
		e.preventDefault();
		axios
			.get("http://localhost:8080/airquality/" + searchQuery)
			.then(function (response) {
				console.log(response.data);
				setAairQualityResponse(response.data.stations[0]);
			})
			.catch(function (error) {
				switch (error?.response?.status) {
					case 400:
						toast(
							<>
								<strong>Erro 400 para: {searchQuery},</strong>
								<br />
								<strong>{error?.response?.data}</strong>
							</>,
							{ hideProgressBar: true, autoClose: 2000, type: "error" }
						);
						break;
					case 404:
						toast(
							<>
								<strong>Erro 404 para: {searchQuery},</strong>
								<br />
								<strong>{error?.response?.data}</strong>
							</>,
							{ hideProgressBar: true, autoClose: 2000, type: "error" }
						);
						break;
					case 500:
						toast(
							<>
								<strong>Erro 500 para: {searchQuery},</strong>
								<br />
								<strong>{error?.response?.data?.message}</strong>
							</>,
							{ hideProgressBar: true, autoClose: 2000, type: "error" }
						);
						break;
					default:
						toast(
							<>
								<strong>Erro para: {searchQuery},</strong>
								<br />
								<strong>{error?.response?.data?.message}</strong>
							</>,
							{ hideProgressBar: true, autoClose: 2000, type: "error" }
						);
						break;
				}
				console.error(error);
			});
		console.log(airQualityResponse);
	};

	const searchPredictions = (e) => {
		e.preventDefault();
		console.log("Search Predictions has been called");
		axios
			.get("http://localhost:8080/airquality/predictions/" + searchQuery)
			.then(function (response) {
				setPredictionsResponse(response.data);
			})
			.catch(function (error) {
				switch (error?.response?.status) {
					case 400:
						toast(
							<>
								<strong>Erro 400 para: {searchQuery},</strong>
								<br />
								<strong>{error?.response?.data}</strong>
							</>,
							{ hideProgressBar: true, autoClose: 2000, type: "error" }
						);
						break;
					case 404:
						toast(
							<>
								<strong>Erro 404 para: {searchQuery},</strong>
								<br />
								<strong>{error?.response?.data}</strong>
							</>,
							{ hideProgressBar: true, autoClose: 2000, type: "error" }
						);
						break;
					case 500:
						toast(
							<>
								<strong>Erro 500 para: {searchQuery},</strong>
								<br />
								<strong>{error?.response?.data?.message}</strong>
							</>,
							{ hideProgressBar: true, autoClose: 2000, type: "error" }
						);
						break;
					default:
						toast(
							<>
								<strong>Erro para: {searchQuery},</strong>
								<br />
								<strong>{error?.response?.data?.message}</strong>
							</>,
							{ hideProgressBar: true, autoClose: 2000, type: "error" }
						);
						break;
				}
				console.error(error);
			});
	};

	return (
		<>
			<Head>
				<title>Aeris</title>
				<meta name="description" content="Generated by create next app" />
				<meta name="viewport" content="width=device-width, initial-scale=1" />
				<link rel="icon" href="/favicon.ico" />
			</Head>
			<main className={styles.main}>
				<div>
					<Menubar searchAirQuality={searchAirQuality} />
				</div>
				{airQualityResponse ? (
					<>
						<div className={styles.center}>
							<div className={styles.airQualityData}>
								<div className={styles.cityName}>
									{airQualityResponse?.city}, {airQualityResponse?.countryCode}
								</div>
								<div className={styles.wrapperInformacoes}>
									<div className={styles.airQualityStatsWrapper}>
										<div className={styles.indiceText}>Qualidade do Ar</div>
										<span>{airQualityResponse?.aqiInfo?.category}</span>
										<span>
											Concentração: {airQualityResponse?.aqiInfo?.concentration}
										</span>
										<span>
											Poluente: {airQualityResponse?.aqiInfo?.pollutant}
										</span>
									</div>
									<div className={styles.airQualityStatsWrapper}>
										<div className={styles.indiceText}>Índices</div>
										<span>AQI: {airQualityResponse?.AQI}</span>
										<span>CO: {airQualityResponse?.CO}</span>
										<span>NO2: {airQualityResponse?.NO2}</span>
										<span>OZONE: {airQualityResponse?.OZONE}</span>
										<span>PM10: {airQualityResponse?.PM10}</span>
										<span>PM25: {airQualityResponse?.PM25}</span>
										<span>SO2: {airQualityResponse?.SO2}</span>
									</div>
									{predictionsResponse ? (
										<>
											<div className={styles.airQualityStatsWrapper}>
												<div className={styles.indiceText}>
													Próximos cinco dias
												</div>
												<div className={styles.diasWrapper}>
													{predictionsResponse &&
														predictionsResponse.slice(1).map((objeto, key) => {
															console.log(predictionsResponse);
															console.log(objeto);
															return (
																<div className={styles.diaWrapper}>
																	<div className={styles.diaText}>
																		Em {key + 2} dias
																	</div>
																	<div>CO: {objeto.components?.co}</div>
																	<div>NO2: {objeto.components?.no2}</div>
																	<div>OZONE: {objeto?.components?.o3}</div>
																	<div>PM10: {objeto.components?.pm10}</div>
																	<div>PM25: {objeto.components?.pm2_5}</div>
																</div>
															);
														})}
												</div>
											</div>
										</>
									) : null}
								</div>
								{predictionsResponse ? null : (
									<Button
										className={styles.btnNext5}
										variant="outline-light"
										onClick={(e) => searchPredictions(e)}
									>
										previsões para os próximos cinco dias
									</Button>
								)}
							</div>
						</div>
					</>
				) : (
					<div className={styles.center}>
						<div className={styles.textSection}>
							<h3>Welcome to your air quality monitoring system</h3>
							<h4>start by searching any city</h4>
						</div>
					</div>
				)}
			</main>
		</>
	);
}
