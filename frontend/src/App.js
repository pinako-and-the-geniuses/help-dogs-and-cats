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
import {
  ShelterList,
  ShelterDetail,
} from './pages/shelter';

import { Animal } from "pages/animals";

function App() {
  return (
    <BrowserRouter>
      <Header></Header>
      <Routes>
        <>
          <Route path="signup" element={<Signup />} />
          <Route path="login" element={<Login />} />
          <Route path="/user/findid" element={<FindId />} />
          <Route path="/user/findpwd" element={<FindPwd />} />
          <Route path="/user/resetpwd" element={<ResetPwd />} />
          <Route path="/user/editinfo" element={<EditInfo />} />
        </>
        <>
          <Route path="/shelter/detail" element={<ShelterDetail/>} />
          <Route path="/shelter/list" element={<ShelterList/>} />
        </>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
