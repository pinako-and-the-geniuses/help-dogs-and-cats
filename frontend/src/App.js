import React from "react";
import Header from "./components/Header";
import Home from "./components/Home";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import {
  Signup,
  Login,
  FindId,
  FindPwd,
  ResetPwd,
  EditInfo,
  Profile,
} from "./pages/user";
import { ShelterList, ShelterDetail } from "./pages/shelter";
import { AnimalDetails, Animal } from "./pages/animals";
import {
  Community,
  CommunityDetail,
  CommunityCreate,
  CommunityUpdate,
} from "./pages/community";
import NotFound from "./NotFound";
import {
  VolunteerList,
  VolunteerDetail,
  VolunteerWrite,
} from "./pages/volunteer";
import { ManageMain } from "./pages/manager";

function App() {
  return (
    <BrowserRouter>
      <Header></Header>
      <Routes>
        <>
          <Route path="" element={<Home />} />
        </>
        <>
          <Route path="signup" element={<Signup />} />
          <Route path="login" element={<Login />} />
          <Route path="/user/findid" element={<FindId />} />
          <Route path="/user/findpwd" element={<FindPwd />} />
          <Route path="/user/resetpwd/:jwt" element={<ResetPwd />} />
          <Route path="/user/editinfo" element={<EditInfo />} />
        </>
        <>
          <Route path="user/profile/:seq" element={<Profile />} />
        </>
        <>
          <Route path="/animals/animal" element={<Animal />} />
          <Route path="/animals/animaldetails" element={<AnimalDetails />} />
        </>
        <>
          <Route path="/community/community" element={<Community />} />
          <Route
            path="/community/communitycreate"
            element={<CommunityCreate />}
          />
          <Route
            path="/community/communitydetail/:seq"
            element={<CommunityDetail />}
          />
          <Route
            path="/community/communityupdate/:seq"
            element={<CommunityUpdate />}
          />
        </>
        <>
          <Route path="/shelter/detail" element={<ShelterDetail />} />
          <Route path="/shelter/list" element={<ShelterList />} />
        </>
        <>
          <Route path="/volunteer/list" element={<VolunteerList />} />
          <Route path="/volunteer/detail/:id" element={<VolunteerDetail />} />
          <Route path="/volunteer/write" element={<VolunteerWrite />}></Route>
          <Route
            path="/volunteer/write/:id"
            element={<VolunteerWrite />}
          ></Route>
        </>
        <>
          <Route path="*" element={<NotFound />} />
        </>
        <>
          <Route path="/manage" element={<ManageMain />}></Route>
        </>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
