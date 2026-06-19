import axios from "axios";

export const getAllData = (page) => {

    return async (dispatch) => {
             console.log(page);
        const response = await axios.get(
            `https://rickandmortyapi.com/api/character?page=${page}`
        );

         let action = {
            type: 'GET_ALL',
            payload: response.data
        }  
         dispatch(action)
    };

};