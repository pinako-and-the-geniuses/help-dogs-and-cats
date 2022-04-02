const GETANIMALDATA = "GETANIMALDATA";

const initialState = [];

export default (state = initialState, action) => {
  switch (action.type) {
    case GETANIMALDATA:
      return {
        ...state,
        info: action.info,
      };

    default:
      return state;
  }
};
