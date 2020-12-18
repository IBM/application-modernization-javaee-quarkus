import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import reportWebVitals from './reportWebVitals';
import Grid from '@material-ui/core/Grid';
import Catalog from './views/Catalog/Catalog';
import NavBar from './components/NavBar/NavBar';
import LeftMenu from './components/LeftMenu/LeftMenu';
import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom";

class Main extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      cartItems: []
    }
  }

  addToCart = (product) => {
    console.log("added to cart, ", product)
  }

  render() {
    return (
      <React.StrictMode>
        <NavBar cartItems={this.state.cartItems} />
        <Router>
          <Grid container style={{ marginTop: 32 }}>
            <Switch>
              <Route exact path="/">
                <Grid item xs={3}>
                  <LeftMenu />
                </Grid>
                <Grid item xs={9}>
                  <Catalog addToCart={this.addToCart} />
                </Grid>
              </Route>
              <Route path="/about">
                <h1>About</h1>
              </Route>
              <Route path="/account">
                <h1>Account</h1>
              </Route>
            </Switch>
          </Grid>
        </Router>
      </React.StrictMode>
    )
  }
}
ReactDOM.render(
  <Main />,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
