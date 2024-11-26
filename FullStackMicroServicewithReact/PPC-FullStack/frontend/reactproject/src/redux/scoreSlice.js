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
      toggle: (state) => {
        state.isActive = !state.isActive;
      },
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

export const {toggle, setState, incrementScore, setUserData} = scoreSlice.actions;
export default scoreSlice.reducer;