import React from 'react';
import axios from 'axios';
import Button from '@material-ui/core/Button';
import Product from '../../components/Product/Product';
import Layout from '../../views/Layout/Layout'

class Catalog extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      items: [{
        price: 29.99,
        name: "Return of the Jedi",
        description: "Episode IV: Return of the Jedi",
        image: "https://whatsondisneyplus.com/wp-content/uploads/2020/04/Jedi.png",
      },{
        price: 29.99,
        name: "Return of the Jedi",
        description: "Episode IV: Return of the Jedi",
        image: "https://whatsondisneyplus.com/wp-content/uploads/2020/04/Jedi.png",
      },{
        price: 29.99,
        name: "Return of the Jedi",
        description: "Episode IV: Return of the Jedi",
        image: "https://whatsondisneyplus.com/wp-content/uploads/2020/04/Jedi.png",
      },{
        price: 29.99,
        name: "Return of the Jedi",
        description: "Episode IV: Return of the Jedi",
        image: "https://whatsondisneyplus.com/wp-content/uploads/2020/04/Jedi.png",
      }],
      loading: false,
      error: "",
    }
  }


  componentDidMount() {
    
  }

  componentWillUnmount() {
  }


  render() {
    const { items } = this.state;
    return <Layout items={items} addToCart={this.props.addToCart} />
    ;
  }
  
}

export default Catalog;