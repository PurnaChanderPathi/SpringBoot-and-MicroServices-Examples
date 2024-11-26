import { configureStore } from "@reduxjs/toolkit";
import scoreSlice from './scoreSlice'

const store =   configureStore({
    reducer: {
        Score:scoreSlice
    }
})

export default store