import React from "react";
import Header from './components/Header';
import Home from './components/Home';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import {
  Signup,
  Login,
  FindId,
  FindPwd,
  ResetPwd,
  EditInfo,
} from "./pages/user";
import { ShelterList, ShelterDetail } from "./pages/shelter";
import { AnimalDetails, Animal } from "./pages/animals";
import { Community, CommunityDetail, CommunityCreate } from "./pages/community";
import NotFound from "./NotFound";
import { VolunteerList, VolunteerDetail } from './pages/volunteer'

function App() {
  return (
    <BrowserRouter>
      <Header></Header>
      <Routes>
        <>
          <Route path="/" element={<Home />} />
        </>
        <>
          <Route path="signup" element={<Signup />} />
          <Route path="login" element={<Login />} />
          <Route path="/user/findid" element={<FindId />} />
          <Route path="/user/findpwd" element={<FindPwd />} />
          <Route path="/user/resetpwd" element={<ResetPwd />} />
          <Route path="/user/editinfo" element={<EditInfo />} />
          <Route path="/animals/animal" element={<Animal />} />
          <Route path="/animals/animaldetails" element={<AnimalDetails />} />
        </>
        <>
          <Route path="/community/community" element={<Community />} />
          <Route path="/community/communitycreate" element={<CommunityCreate />} />
          <Route
            path="/community/communitydetail/:id"
            element={<CommunityDetail />}
          />
        </>
        <>
          <Route path="/shelter/detail" element={<ShelterDetail />} />
          <Route path="/shelter/list" element={<ShelterList />} />
        </>
        <>
          <Route path="/volunteer/list" element={<VolunteerList />} />
          <Route path="/volunteer/detail/:id" element={<VolunteerDetail />} />
        </>
        <>
          <Route path="*" element={<NotFound />} />
        </>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
