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
  const mySeq = useSelector((state) => state.userInfo.userInfo.seq);
  const { seq } = useParams();
  const navi = useNavigate();

  useEffect(() => {
    // 로그인 안한 사람은 누구 프로필도 볼 수 없음.
    if (!isLogin) {
      alert("로그인 해주세요");
      navi("/login");
    }
  }, [isLogin]);

  return (
    <>
      <div className={st.profile}>
        <div className={st.main}>
          <ProfileInfo seq={seq} isLogin={isLogin} />
          <div className="btn-group dropend">
            <CgMoreVerticalAlt
              name="기타버튼"
              className={cn(`${st.etcBtn}`, "dropdown-toggle")}
              data-bs-toggle="dropdown"
              aria-expanded="false"
              size="30"
            />
            <ul className={cn(`${st.dropdown}`, "dropdown-menu")}>
              {seq === mySeq ? (
                <li>
                  <a className="dropdown-item" href="/user/editInfo">
                    수정
                  </a>
                </li>
              ) : (
                <li>
                  <button className="dropdown-item" href="#">
                    신고
                  </button>
                </li>
              )}
            </ul>
          </div>
        </div>
      </div>

      <ProfileList seq={seq} isLogin={isLogin} mySeq={mySeq} />
    </>
  );
}
