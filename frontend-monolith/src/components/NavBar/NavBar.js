import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Badge, List, ListItem, Popover, AppBar, Toolbar, Typography, IconButton, MenuItem, Menu, Box } from '@material-ui/core';
import { AccountCircle, ShoppingCart as ShoppingCartIcon, Delete as DeleteIcon } from '@material-ui/icons'
import { Link } from 'react-router-dom';

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
  cartList: {
    minWidth: 360
  },
  link: { textDecoration: 'none', color: 'white', fontWeight: 'bold' },
}));

function NavBar({ cartItems, deleteFromCart }) {
  const classes = useStyles();
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);
  const [cartAnchorEl, setCartAnchorEl] = React.useState(null);
  const openCart = Boolean(cartAnchorEl);

  const handleMenu = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleCartMenu = (event) => {
    setCartAnchorEl(event.currentTarget);
  };

  const handleCartClose = () => {
    setCartAnchorEl(null);
  };

  return (
    <div className={classes.root}>
      <AppBar position="static">
        <Toolbar>
          <Box className={classes.titleContainer}>
            <Typography variant="h6" className={classes.title}>
              Electronic and Movie Depot
            </Typography>
            <Link to="/about" className={classes.link}>About</Link>
          </Box>
          <div>
            <IconButton
              aria-label="current shopping cart"
              aria-controls="menu-cart"
              aria-haspopup="true"
              onClick={handleCartMenu}
              color="inherit"
            >
              <Badge
                badgeContent={cartItems.length}
                anchorOrigin={{
                  vertical: 'top',
                  horizontal: 'right',
                }}
                color='secondary'
              >
                <ShoppingCartIcon />
              </Badge>

            </IconButton>
            <Popover
              id="menu-cart"
              anchorEl={cartAnchorEl}
              anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'left',
              }}
              transformOrigin={{
                vertical: 'top',
                horizontal: 'center',
              }}
              keepMounted
              open={openCart}
              onClose={handleCartClose}
            >
              <List className={classes.cartList}>
                {/* TODO: insert cart items here. */}
                {
                  cartItems.length > 0 ?
                    cartItems.map(i => (
                      <ListItem key={i.orderId}>
                        <img src={i.image} alt="" style={{ marginRight: 8, wdith: 16, height: 16 }} />
                        {i.name}
                        <span style={{ marginLeft: 'auto' }}>${i.price}</span>
                        <IconButton onClick={() => deleteFromCart(i.orderId)}>
                          <DeleteIcon />
                        </IconButton>
                      </ListItem>
                    )) : <ListItem>No items added to the cart</ListItem>
                }
              </List>
            </Popover>








            <IconButton
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleMenu}
              color="inherit"
            >
              <AccountCircle />
            </IconButton>
            <Menu
              id="menu-appbar"
              anchorEl={anchorEl}
              anchorOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
              keepMounted
              transformOrigin={{
                vertical: 'top',
                horizontal: 'right',
              }}
              open={open}
              onClose={handleClose}
            >
              <MenuItem onClick={handleClose}>Profile</MenuItem>
              <MenuItem onClick={handleClose}>My account</MenuItem>
            </Menu>
          </div>
        </Toolbar>
      </AppBar>
    </div>
  );
}

export default NavBar;