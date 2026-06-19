const initialState = {characters: [], pages: 0};

export const characterReducer = (state = initialState, action) => {

         if(action.type === 'GET_ALL'){
            return {
                ...state,
                characters: action.payload.results,
                pages: action.payload.info.pages
            };

    }
     return state;
};