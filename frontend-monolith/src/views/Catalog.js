import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';

import { getCatalogItems } from "../slice/catalogSlice";

function Catalog() {
  const { users, loading, error } = useSelector(state => state.users)
  const dispatch = useDispatch()

  const getItems = async () => {
    try {
      const resultAction = await dispatch(getCatalogItems())
      const catalog = resultAction.payload
      console.log(catalog)
    } catch (err) {
      showToast('error', `Fetch failed: ${err.message}`)
    }
  }
  return (
    <div>
      ok;
    </div>
  );
}

export default Catalog;