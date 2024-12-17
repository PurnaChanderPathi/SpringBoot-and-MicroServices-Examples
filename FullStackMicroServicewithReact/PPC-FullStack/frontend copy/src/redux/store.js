import { configureStore } from "@reduxjs/toolkit";
import scoreSlice from './scoreSlice'
import ResponseRemedaitionSlice from './ResponseRemedaitionSlice'

const store =   configureStore({
    reducer: {
        Score:scoreSlice,
        Response:ResponseRemedaitionSlice
    }
})

export default store