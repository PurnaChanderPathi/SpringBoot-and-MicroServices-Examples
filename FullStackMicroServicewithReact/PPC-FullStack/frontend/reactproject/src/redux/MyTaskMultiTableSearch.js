import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { setMyReloadPage } from "./scoreSlice";


const initialState = {
    rows: [],
    totalPages: 0,
    rowsPerPage: 5,
    error: null,
    loading: false,
};

const MyTaskMultiTableSearch = createSlice({
    name : "MyTaskTable",
    initialState,
    reducers : {
        setRows: (state, action) => {
            state.rows = action.payload;
        },
        setTotalPages: (state, action) => {
            state.totalPages = action.payload;
        },
        setRowsPerPage: (state, action) => {
            state.rowsPerPage = action.payload;
        },
        setError: (state, action) => {
            state.error = action.payload;
        },
        setLoading: (state, action) => {
            state.loading = action.payload;
        }
    }    
});

export const getMyTaskTableFetchDetails = (assignedTo,token) => async (dispatch) => {
    const url = `http://localhost:9195/api/query/multiTableSearch?assignedTo=${assignedTo}`;
    dispatch(setLoading(true));

    try {
        const response = await axios.get(url, {
            headers: {
                'Authorization': `Bearer ${token}`,
                "Content-Type": 'application/json'
            }
        });

        if(response.data.status === 200){
            console.log("MyTaskMultiTableSearch Details Fetched with assignedTo:", assignedTo);

            const data = response.data.result;
            console.log("data at ResponseQueryFetchDetails : ",data);
            if(data.length>0){
                dispatch(setMyReloadPage(true));
            }
            dispatch(setRows(data));
            
            dispatch(setTotalPages(Math.ceil(data.length / 5)));
        }
        
    } catch (error) {
        dispatch(setError(error.message));
    } finally {
        dispatch(setLoading(false));
    }
};

export const { setRows, setTotalPages, setRowsPerPage, setError, setLoading } = MyTaskMultiTableSearch.actions;
export default MyTaskMultiTableSearch.reducer;