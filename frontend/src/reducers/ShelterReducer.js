const GETSHELTERLDATA = "GETSHELTERLDATA";

const initialState = [];

export default (state = initialState, action) => {
  switch (action.type) {
    case GETSHELTERLDATA:
      return {
        ...state,
        info: action.info,
      };

    default:
      return state;
  }
};
