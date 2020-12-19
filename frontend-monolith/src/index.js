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
  Route,
} from "react-router-dom";
import Axios from 'axios';
import { v4 as uuidv4 } from 'uuid'
import { remove } from 'lodash';

class Main extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      cartItems: [],
      categoryItems: [],
      products: [],
    }
  }

  addToCart = (product) => {
    this.setState((state) => {
      product.orderId = uuidv4()
      state.cartItems.push(product);
      return {
        cartItems: state.cartItems
      }
    })
  }

  componentDidMount = () => {
    // Get Category Menu Items
    Axios({
      method: 'GET',
      baseURL: '',
      url: '/Category',
    }).then(response => this.updateCategoryItems(response.data)).catch(error => console.error(error));
  }

  updateCategoryItems = (data) => {
    this.setState({ categoryItems: data })
  }

  getProducts = (id) => {
    Axios({
      method: 'GET',
      url: '/Product',
      params: {
        categoryId: id
      }
    }).then(response => this.setState({ products: response.data }))
      .catch(err => console.error(err));
  }

  deleteFromCart = (orderId) => {
    console.log(orderId);
    this.setState((state) => {
      remove(state.cartItems, function (n) {
        console.log(n)
        console.log(n.orderId === orderId)
        return n.orderId === orderId;
      });
      return {
        cartItems: state.cartItems
      }
    })
  }

  render() {
    console.log(this.state)
    return (
      <React.StrictMode>
        <Router>
          <NavBar cartItems={this.state.cartItems} deleteFromCart={this.deleteFromCart} />
          <Grid container style={{ marginTop: 32 }}>
            <Switch>
              <Route exact path="/">
                <Grid item xs={3}>
                  <LeftMenu categoryItems={this.state.categoryItems} getProducts={this.getProducts} />
                </Grid>
                <Grid item xs={9}>
                  <Catalog addToCart={this.addToCart} items={this.state.products} />
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
