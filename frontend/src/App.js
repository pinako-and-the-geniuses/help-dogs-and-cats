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
import { One } from "./pages/statistics";
import { ShelterList, ShelterDetail } from "./pages/shelter";
import { AnimalDetail, Animal, AnimalList } from "./pages/animals";
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
  VolunteerUpdate,
} from "./pages/volunteer";
import { ManageMain, VolunteerManage, AdoptManage, AdoptManageDetail, VolunteerManageDetail } from "./pages/manager";
import { Guide } from "pages/guide";
import Footer from "components/Footer";

function App() {
  return (
    <BrowserRouter>
      <Header></Header>
      <Routes>
        <>
          <Route path="" element={<Home />} />
          <Route path="/" element={<One />} />
        </>
        <>
          <Route path="/signup" element={<Signup />} />
          <Route path="/login" element={<Login />} />
          <Route path="/user/findid" element={<FindId />} />
          <Route path="/user/findpwd" element={<FindPwd />} />
          <Route path="/user/resetpwd/:jwt" element={<ResetPwd />} />
          <Route path="/user/editinfo" element={<EditInfo />} />
        </>
        <>
          <Route path="/user/profile/:seq" element={<Profile />} />
        </>
        <>
          <Route path="/guide" element={<Guide />} />
        </>
        <>
          <Route path="/animals/animal" element={<Animal />} />
          <Route path="/animals/list" element={<AnimalList />} />
          <Route path="/animals/detail" element={<AnimalDetail />} />
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
          <Route path="/shelter/detail/:id" element={<ShelterDetail />} />
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
          <Route
            path="/volunteer/update/:id"
            element={<VolunteerUpdate />}
          ></Route>
        </>
        <>
          <Route path="*" element={<NotFound />} />
        </>
        <>
          <Route path="/manage" element={<ManageMain />}></Route>
          <Route path="/volunteermanage" element={<VolunteerManage />}></Route>
          <Route path="/volunteermanage/detail/:id" element={<VolunteerManageDetail />}></Route>
          <Route path="/adoptmanage" element={<AdoptManage />}></Route>
          <Route path="/adoptmanage/detail/:adoptSeq" element={<AdoptManageDetail />}></Route>
        </>
      </Routes>
      <Footer></Footer>
    </BrowserRouter>
  );
}

export default App;
