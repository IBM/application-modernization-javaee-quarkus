import React from 'react';
import Layout from '../../views/Layout/Layout'
import Axios from 'axios';
import { withRouter } from 'react-router-dom';
import PropTypes from 'prop-types';
import { isEqual } from 'lodash';
class Catalog extends React.Component {
  static propTypes = {
    match: PropTypes.object.isRequired,
    location: PropTypes.object.isRequired,
    history: PropTypes.object.isRequired
  };

  constructor(props) {
    super(props);
    this.state = {
      items: [],
      loading: false,
      error: "",
    }
  }


  componentDidMount() {
    const param = this.props.location.search || "?categoryId=2"
    Axios({
      method: 'GET',
      url: '/Product' + param,
    }).then(response => this.setState({ items: response.data }))
      .catch(err => console.error(err));
  }

  componentDidUpdate(prevProps, prevState) {
    if (!isEqual(prevState.items, this.props.items) && !isEqual(prevProps.items, this.props.items)) {
      const param = this.props.location.search || "?categoryId=2"
      Axios({
        method: 'GET',
        url: '/Product' + param,
      }).then(response => this.setState({ items: response.data }))
        .catch(err => console.error(err));
    }
  }

  componentWillUnmount() {
  }


  render() {
    const { items } = this.state;
    return <Layout items={items} addToCart={this.props.addToCart} />
      ;
  }

}

export default withRouter(Catalog);