import ProfileInfo from "./component/ProfileInfo";
import ProfileList from "./component/ProfileList";

import { useNavigate, useParams } from "react-router-dom";
import { useSelector } from "react-redux";
import { useEffect } from "react";

import cn from "classnames";
import st from "./styles/profile.module.scss";
import { CgMoreVerticalAlt } from "react-icons/cg";

export default function Profile() {
  const isLogin = useSelector((state) => state.userInfo.isLoggedIn);
  const { seq } = useParams();
  const navi = useNavigate();

  useEffect(() => {
    // 로그인 안한 사람은 누구 프로필도 볼 수 없음.
    if (!isLogin) {
      alert("로그인 해주세요");
      navi("/login");
    }
  }, []);

  const onEtcBox = () => {
    console.log("클릭");
  };

  return (
    <>
      <div className={st.profile}>
        <div className={st.main}>
          <ProfileInfo seq={seq} isLogin={isLogin} />
          <div className="btn-group dropend">
            <CgMoreVerticalAlt
              className={cn(`${st.etcBtn}`, "dropdown-toggle")}
              data-bs-toggle="dropdown"
              aria-expanded="false"
              size="30"
              onClick={onEtcBox}
            />
            <ul className={cn(`${st.dropdown}`, "dropdown-menu")}>
              <li>
                <a className="dropdown-item" href="#">
                  수정
                </a>
              </li>
              <li>
                <a className="dropdown-item" href="#">
                  신고
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <ProfileList />
    </>
  );
}