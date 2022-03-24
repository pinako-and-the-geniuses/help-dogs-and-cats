// Action types
const LOGIN = "LOGIN";
const LOGOUT = "LOGOUT";

// Action creators
function login(userInfo) {
  return {
    type: LOGIN,
    payload: userInfo,
  };
}

export { login, LOGIN, LOGOUT };
