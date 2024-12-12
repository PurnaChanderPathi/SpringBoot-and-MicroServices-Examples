import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

const initialState = {
    rows: [],
    totalPages: 0,
    error: null,
    loading: false,
  };

const ResponseRemedaitionSlice = createSlice({
    name: 'Response',
    initialState,
    reducers : {
        setRows: (state, action) => {
            state.rows = action.payload;
          },
          setTotalPages: (state, action) => {
            state.totalPages = action.payload;
          },
          setError: (state, action) => {
            state.error = action.payload;
          },
          setLoading: (state, action) => {
            state.loading = action.payload;
          }
        }
      });

      export const getResponseRemediationDetailsByReviewId = (reviewId, token) => async (dispatch) => {
        const url = `http://localhost:9195/api/QueryObligor/getResponseByReviewId?reviewId=${reviewId}`;
        dispatch(setLoading(true)); // Start loading
        
        try {
          const response = await axios.get(url, {
            headers: {
              'Authorization': `Bearer ${token}`,
              'Content-Type': 'application/json',
            },
          });
      
          if (response.data.status === 200) {
            const data = response.data.result;
            dispatch(setRows(data));
            dispatch(setTotalPages(Math.ceil(data.length / 10))); 
          } else {
            dispatch(setError('Failed to fetch response details.'));
          }
        } catch (error) {
          dispatch(setError(error.message));
        } finally {
          dispatch(setLoading(false));
        }
      };
      
      // Export reducers to use in the store
      export const { setRows, setTotalPages, setError, setLoading } = ResponseRemedaitionSlice.actions;
      
      // Export the reducer to be used in store configuration
      export default ResponseRemedaitionSlice.reducer;