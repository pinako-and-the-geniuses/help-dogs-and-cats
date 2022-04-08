// Action types
const LOGIN = "LOGIN";
const LOGOUT = "LOGOUT";

// Action creators
function loginAction(userInfo) {
  return {
    type: LOGIN,
    userInfo: userInfo,
  };
}

function logoutAction() {
  return {
    type: LOGOUT,
    userInfo: {},
  };
}

export { loginAction, logoutAction, LOGIN, LOGOUT };
