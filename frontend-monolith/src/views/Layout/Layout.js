import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Paper from '@material-ui/core/Paper';
import Grid from '@material-ui/core/Grid';
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

export default function CenteredGrid({ items }) {
  const classes = useStyles();

  return (
    <div className={classes.root}>
      <Grid container spacing={3}>
        {
          items.map(i => (
            <Grid item xs={3}>
              <Product name={items[0].name} description={items[0].description} image={items[0].image} price={items[0].price} />
            </Grid>
          ))
        }
      </Grid>
    </div>
  );
}
