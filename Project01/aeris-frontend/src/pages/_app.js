import Head from "next/head";
import "@/styles/globals.css";
// add bootstrap css
import "bootstrap/dist/css/bootstrap.css";
import DataContextProvider from "../contexts/DataContext";
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer } from 'react-toastify';

export default function App({ Component, pageProps }) {
	return (
		<DataContextProvider>
			<Head>
				<meta name="viewport" content="width=device-width, initial-scale=1" />
			</Head>
			<Component {...pageProps} />
			<ToastContainer />
		</DataContextProvider>
	);
}
