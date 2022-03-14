import React from "react";
import Header from "components/Header";

import { BrowserRouter, Route, Routes } from "react-router-dom";
import {
  Signup,
  Login,
  FindId,
  FindPwd,
  ResetPwd,
  EditInfo,
} from "./pages/user";
function App() {
  return (
    <BrowserRouter>
      <Header></Header>
      <Routes>
        <>
          <Route path="/user/signup" element={<Signup />} />
          <Route path="/user/login" element={<Login />} />
          <Route path="/user/findid" element={<FindId />} />
          <Route path="/user/findpwd" element={<FindPwd />} />
          <Route path="/user/resetpwd" element={<ResetPwd />} />
          <Route path="/user/editinfo" element={<EditInfo />} />
        </>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
