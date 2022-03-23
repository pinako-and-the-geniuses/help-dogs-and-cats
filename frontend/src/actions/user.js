// Action types
export const LOGIN = "count/LOGIN";
export const LOGOUT = "count/LOGOUT";

// Action creators
export const login = () => {
  return {
    type: LOGIN,
  };
};

export const logout = () => {
  return {
    type: LOGOUT,
  };
};
