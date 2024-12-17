import { createSlice } from "@reduxjs/toolkit";

const initialState = {
    isActive: false,
    score: 0,
    userData: {
      name: "",
      email: "",
    },
  };

// const initialState = false;

const scoreSlice = createSlice({
    name: "Score",
    initialState,
    reducers: {
      // toggle: (state) => {
      //   state.isActive = !state.isActive;
      // },
      setState: (state, action) => {
        state.isActive = action.payload;
      },
      incrementScore: (state) => {
        state.score += 1;
      },
      setUserData: (state, action) => {
        state.userData = action.payload;
      },
    }
    
})

export const {
  // toggle,
   setState, incrementScore, setUserData} = scoreSlice.actions;
export default scoreSlice.reducer;



// // Step 1: Use useSelector to get the value of `isActive` from the store
// const isActive = useSelector((state) => state.Score.isActive);

// // Step 2: Use useDispatch to dispatch actions to update the state
// const dispatch = useDispatch();

// // Step 3: Toggle `isActive` or set it directly using dispatch
// const handleToggle = () => {
//   dispatch(toggle()); // This will toggle the value of `isActive`
// };

// const handleSetActive = (value) => {
//   dispatch(setState(value)); // This will directly set `isActive` to `true` or `false`
// };