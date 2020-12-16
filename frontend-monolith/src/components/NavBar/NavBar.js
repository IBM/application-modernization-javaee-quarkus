import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Badge, List, ListItem, Popover, AppBar , Toolbar, Typography, IconButton, Switch, FormControlLabel, FormGroup, MenuItem, Menu} from '@material-ui/core';
import { AccountCircle, ShoppingCart as ShoppingCartIcon, Delete as DeleteIcon } from '@material-ui/icons'

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
  },
  menuButton: {
    marginRight: theme.spacing(2),
  },
  title: {
    flexGrow: 1,
  },
  cartList: {
    minWidth: 360
  }
}));

function MenuAppBar() {
  const classes = useStyles();
  const [auth, setAuth] = React.useState(true);
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);
  const [cartAnchorEl, setCartAnchorEl] = React.useState(null);
  const openCart = Boolean(cartAnchorEl);

  const handleChange = (event) => {
    setAuth(event.target.checked);
  };

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
      <FormGroup>
        <FormControlLabel
          control={<Switch checked={auth} onChange={handleChange} aria-label="login switch" />}
          label={auth ? 'Logout' : 'Login'}
        />
      </FormGroup>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" className={classes.title}>
            Electronic and Movie Depot
          </Typography>
          {auth && (
            <div>
              <IconButton
                aria-label="current shopping cart"
                aria-controls="menu-cart"
                aria-haspopup="true"
                onClick={handleCartMenu}
                color="inherit"
              >
                <Badge
                badgeContent={3}
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
                  <ListItem>
                    <img src='https://whatsondisneyplus.com/wp-content/uploads/2020/04/Jedi.png' alt="" style={{ marginRight: 8, wdith: 16, height: 16}}/>
                    Return of the Jedi 
                    <span style={{ marginLeft: 'auto'}}>$29.99</span>
                    <IconButton>
                      <DeleteIcon />
                    </IconButton>
                  </ListItem>
                  <ListItem>
                    <img src='https://whatsondisneyplus.com/wp-content/uploads/2020/04/Jedi.png' alt="" style={{ marginRight: 8, wdith: 16, height: 16}}/>
                    Return of the Jedi 
                    <span style={{ marginLeft: 'auto'}}>$29.99</span>
                    <IconButton>
                      <DeleteIcon />
                    </IconButton>
                  </ListItem>
                  <ListItem>
                    <img src='https://whatsondisneyplus.com/wp-content/uploads/2020/04/Jedi.png' alt="" style={{ marginRight: 8, wdith: 16, height: 16}}/>
                    Return of the Jedi 
                    <span style={{ marginLeft: 'auto'}}>$29.99</span>
                    <IconButton>
                      <DeleteIcon />
                    </IconButton>
                  </ListItem>
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
          )}
        </Toolbar>
      </AppBar>
    </div>
  );
}

export default MenuAppBar;