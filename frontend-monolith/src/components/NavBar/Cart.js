import React from 'react'
import { Badge, IconButton, List, ListItem, makeStyles, Popover } from '@material-ui/core'
import { ShoppingCart as ShoppingCartIcon, Delete as DeleteIcon } from '@material-ui/icons'

const useStyles = makeStyles((theme) => ({
  cartList: {
    minWidth: 360
  }
}));

export default function Cart({ cartItems, deleteFromCart }) {
  const classes = useStyles();
  const [cartAnchorEl, setCartAnchorEl] = React.useState(null);
  const openCart = Boolean(cartAnchorEl);

  const handleCartMenu = (event) => {
    setCartAnchorEl(event.currentTarget);
  };

  const handleCartClose = () => {
    setCartAnchorEl(null);
  };

  return (
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
    </div>

  )
}