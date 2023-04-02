import Head from "next/head";
import "@/styles/globals.css";
// add bootstrap css
import "bootstrap/dist/css/bootstrap.css";

export default function App({ Component, pageProps }) {
	return (
		<>
			<Head>
				<meta name="viewport" content="width=device-width, initial-scale=1" />
			</Head>
			<Component {...pageProps} />
		</>
	);
}
