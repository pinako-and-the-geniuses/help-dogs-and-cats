// 모든 리듀서를 합쳐주는 파일입니다.
import { combineReducers } from "redux";
import UserReducer from "./UserReducer";

const rootReducer = combineReducers({
  UserReducer,
});
export default rootReducer;
