const LOGIN = "LOGIN";
const LOGOUT = "LOGOUT";

const initialUserInfo = {
  seq: "",
  email: "",
  nickname: "",
  role: "",
};

const initialState = { isLoggedIn: false, userInfo: initialUserInfo };

export default (state = initialState, action) => {
  switch (action.type) {
    case LOGIN:
      return {
        ...state,
        isLoggedIn: true,
        userInfo: action.userInfo,
      };
    case LOGOUT:
      return {
        ...state,
        isLoggedIn: false,
        userInfo: initialState,
      };

    default:
      return state;
  }
};
