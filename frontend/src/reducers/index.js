// 모든 리듀서를 합쳐주는 파일입니다.
import { combineReducers } from "redux";
import { persistReducer } from "redux-persist";
import storageSession from "redux-persist/lib/storage/session";
import userInfo from "./UserReducer";

// 새로고침해도 기존 정보 유지하도록!
const persistConfig = {
  key: "root",
  storage: storageSession, // session저장소를 저장합니다.
  whitelist: ["userInfo"], // reducer 중에 userInfo reducer만 localstorage에 저장합니다.
};

const rootReducer = combineReducers({
  userInfo,
});

export default persistReducer(persistConfig, rootReducer);
