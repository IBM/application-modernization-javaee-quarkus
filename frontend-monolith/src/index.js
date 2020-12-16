import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import reportWebVitals from './reportWebVitals';
import Grid from '@material-ui/core/Grid';
import Catalog from './views/Catalog/Catalog';
import NavBar from './components/NavBar/NavBar';
import LeftMenu from './components/LeftMenu/LeftMenu';

ReactDOM.render(
  <React.StrictMode>
    <NavBar />
    <Grid container style={{ marginTop: 32 }}>
      <Grid item xs={3}>
        <LeftMenu />
      </Grid>
      <Grid item xs={9}>
        <Catalog />
      </Grid>
    </Grid>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
