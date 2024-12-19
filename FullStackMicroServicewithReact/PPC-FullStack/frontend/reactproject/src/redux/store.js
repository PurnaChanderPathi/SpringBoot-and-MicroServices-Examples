import { configureStore } from "@reduxjs/toolkit";
import scoreSlice from './scoreSlice'
import ResponseRemedaitionSlice from './ResponseRemedaitionSlice'
import ResposeQueryFetchDetails from "./ResponseQueryFetchDetails";

const store =   configureStore({
    reducer: {
        Score:scoreSlice,
        Response:ResponseRemedaitionSlice,
        ResponseQuery:ResposeQueryFetchDetails
    }
})

export default store