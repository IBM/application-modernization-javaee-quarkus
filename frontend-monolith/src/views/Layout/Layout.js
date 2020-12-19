import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Grid, Container } from '@material-ui/core';
import Product from '../../components/Product/Product';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  paper: {
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary,
  },
}));

export default function CenteredGrid({ items, addToCart }) {
  const classes = useStyles();
  return (
    <div className={classes.root}>
      <Grid container spacing={3}>
        {
          items.map(i => (
            <Grid item xs={4} key={i.id}>
              <Container>
                <Product name={i.name} description={i.description} image={i.image} price={i.price} addToCart={() => addToCart(i)} />
              </Container>
            </Grid>
          ))
        }
      </Grid>
    </div>
  );
}
