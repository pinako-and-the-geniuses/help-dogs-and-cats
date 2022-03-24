import ProfileImage from "./component/ProfileImage";
import ProfileInfo from "./component/ProfileInfo";
import ProfileList from "./component/ProfileList";

import { useNavigate, useParams } from "react-router-dom";
import { useSelector } from "react-redux";
import { useEffect } from "react";

import st from "./styles/profile.module.scss";
import { CgMoreVerticalAlt } from "react-icons/cg";

export default function Profile() {
  const navi = useNavigate();
  const isLogin = useSelector((state) => state.userInfo.isLoggedIn);
  const { seq } = useParams();

  useEffect(() => {
    console.log(`해당 프로필은 ${seq} 번호꺼`);
    // 로그인 안한 사람은 누구 프로필도 볼 수 없음.
    if (!isLogin) {
      alert("로그인 해주세요");
      navi("/login");
    } else if (seq) {
    }
  }, []);

  return (
    <>
      <div className={st.profile}>
        <div className={st.main}>
          <ProfileImage />
          <ProfileInfo />
          <CgMoreVerticalAlt style={{ size: "large" }} />
        </div>
      </div>

      <ProfileList />
    </>
  );
}
