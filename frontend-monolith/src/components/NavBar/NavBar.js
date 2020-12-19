import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { AppBar, Toolbar, Typography, Box } from '@material-ui/core';
import { Link } from 'react-router-dom';
import Cart from './Cart';
import Account from './Account';

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  titleContainer: {
    flexGrow: 1,
  },
  title: {
    display: 'inline-block',
    marginRight: 64
  },
  link: { textDecoration: 'none', color: 'white', fontWeight: 'bold' },
}));

function NavBar({ cartItems, deleteFromCart }) {
  const classes = useStyles();

  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Toolbar>
          <Box className={classes.titleContainer}>
            <Link className={classes.link} to="/">
              <Typography variant="h6" className={classes.title}>
                Electronic and Movie Depot
            </Typography>
            </Link>
            <Link to="/about" className={classes.link}>About</Link>
          </Box>
          <Cart cartItems={cartItems} deleteFromCart={deleteFromCart} />
          <Account />
        </Toolbar>
      </AppBar>
    </div>
  );
}

export default NavBar;