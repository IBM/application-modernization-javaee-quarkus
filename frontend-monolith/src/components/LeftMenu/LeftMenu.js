import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import { Collapse, List, ListItem, ListItemText } from '@material-ui/core';
import { ExpandLess, ExpandMore } from '@material-ui/icons';
import { NavLink } from 'react-router-dom';

const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
    maxWidth: 360,
    backgroundColor: theme.palette.background.paper,
  },
}));



export default function LeftMenu({ categoryItems, getProducts }) {
  const classes = useStyles();
  const [open, setOpen] = React.useState(0);

  const handleClick = (id) => {
    open === parseInt(id) ? setOpen(0) : setOpen(id);
  };

  return (
    categoryItems.length > 0 ?
      <div className={classes.root} >
        <List component="nav" aria-label="main mailbox folders">
          {
            categoryItems.map(c => (
              <React.Fragment key={c.id}>
                <ListItem button onClick={() => handleClick(c.id)}>
                  <ListItemText primary={c.name} />
                  {open === c.id ? <ExpandLess /> : <ExpandMore />}
                </ListItem>
                <Collapse in={open === c.id} timeout="auto" unmountOnExit>
                  <List component="div" disablePadding>
                    {
                      c.subCategories.map(sc => (
                        <NavLink key={sc.id} to={"?categoryId=" + sc.id} onClick={() => getProducts(sc.id)}>
                          <ListItem button className={classes.nested}>
                            <ListItemText primary={sc.name} inset />
                          </ListItem>
                        </NavLink>
                      ))
                    }
                  </List>
                </Collapse>
              </React.Fragment>
            ))
          }
        </List>
      </div > : <div>Loading</div>
  );
}
