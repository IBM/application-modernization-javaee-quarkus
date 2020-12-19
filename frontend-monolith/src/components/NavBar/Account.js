import React from 'react'
import { IconButton, List, ListItem, makeStyles, Popover } from '@material-ui/core'
import { AccountCircle } from '@material-ui/icons'
import { Link } from 'react-router-dom';

const useStyles = makeStyles((theme) => ({
  menuItem: {
    textDecoration: 'none',
    cursor: 'pointer'
  }
}));

export default function Account() {
  const classes = useStyles();

  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);

  const handleMenu = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };


  return (
    <React.Fragment>
      <IconButton
        aria-label="account of current user"
        aria-controls="menu-appbar"
        aria-haspopup="true"
        onClick={handleMenu}
        color="inherit"
      >
        <AccountCircle />
      </IconButton>
      <Popover
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'left',
        }}
        transformOrigin={{
          vertical: 'top',
          horizontal: 'center',
        }}
        keepMounted
        id="menu-appbar"
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
      >
        <List>
          <Link className={classes.menuItem} to="/account">
            <ListItem onClick={handleClose}>My account</ListItem>
          </Link>
        </List>
      </Popover>
    </React.Fragment>
  )
}