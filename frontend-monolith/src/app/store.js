import { configureStore } from '@reduxjs/toolkit';
import counterReducer from '../features/counter/counterSlice';
import catalogReducer from '../slice/catalogSlice';

export default configureStore({
  reducer: {
    counter: counterReducer,
    catalog: catalogReducer,
  },
});
