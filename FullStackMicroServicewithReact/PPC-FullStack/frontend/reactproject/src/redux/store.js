import { configureStore } from "@reduxjs/toolkit";
import scoreSlice from './scoreSlice'
import ResponseRemedaitionSlice from './ResponseRemedaitionSlice'
import ResposeQueryFetchDetails from "./ResponseQueryFetchDetails";
import MyTaskmultiTableSearch from "./MyTaskMultiTableSearch"

const store =   configureStore({
    reducer: {
        Score:scoreSlice,
        Response:ResponseRemedaitionSlice,
        ResponseQuery:ResposeQueryFetchDetails,
        ResponseMyTask:MyTaskmultiTableSearch
    }
})

export default store