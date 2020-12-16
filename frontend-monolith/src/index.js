import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import reportWebVitals from './reportWebVitals';
import Catalog from './views/Catalog/Catalog';
import Layout from './views/Layout/Layout';
import NavBar from './components/NavBar/NavBar'

ReactDOM.render(
  <React.StrictMode>
    <NavBar />
    <Layout />
    <Catalog />
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
