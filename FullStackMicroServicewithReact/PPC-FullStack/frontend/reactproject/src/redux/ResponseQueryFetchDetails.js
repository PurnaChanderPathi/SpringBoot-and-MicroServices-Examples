import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { useSelector } from "react-redux";
import { setEmptyState } from "./scoreSlice";

const initialState = {
    rows: [],
    totalPages: 0,
    rowsPerPage: 5,
    error: null,
    loading: false,
};

const ResponseQueryFetchDetails = createSlice({
    name: "ResponseQuery",
    initialState,
    reducers: {
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

export const getResponseQueryFetchDetails = (childReviewId, token) => async (dispatch) => {
    const url = `http://localhost:9195/api/QueryObligor/findByChildReviewIdOfResponseQuery/${childReviewId}`;
    dispatch(setLoading(true));

    try {
        const response = await axios.get(url, {
            headers: {
                'Authorization': `Bearer ${token}`,
                "Content-Type": 'application/json'
            }
        });

        if (response.data.status === 200) {
            console.log("Response Query Details Fetched with childReviewId:", childReviewId);
            
            const data = response.data.result;
            console.log("data at ResponseQueryFetchDetails : ",data);
            if(data.length>0){
                dispatch(setEmptyState(true));
            }
            dispatch(setRows(data));
            
            dispatch(setTotalPages(Math.ceil(data.length / 5)));  
        } else {
            dispatch(setError('Failed to fetch response details.'));
        }
    } catch (error) {
        dispatch(setError(error.message));
    } finally {
        dispatch(setLoading(false));
    }
};


export const { setRows, setTotalPages, setRowsPerPage, setError, setLoading } = ResponseQueryFetchDetails.actions;
export default ResponseQueryFetchDetails.reducer;
