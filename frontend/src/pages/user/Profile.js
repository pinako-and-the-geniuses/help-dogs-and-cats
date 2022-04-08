import ProfileInfo from "./component/ProfileInfo";
import ProfileList from "./component/ProfileList";
import { useNavigate, useParams } from "react-router-dom";
import { useSelector } from "react-redux";
import { useEffect } from "react";
import cn from "classnames";
import st from "./styles/profile.module.scss";
import { CgMoreVerticalAlt } from "react-icons/cg";
import swal from "sweetalert";

export default function Profile() {
  const isLogin = useSelector((state) => state.userInfo.isLoggedIn);
  const mySeq = useSelector((state) => state.userInfo.userInfo.seq);
  const { seq } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    if (!isLogin) {
      swal({
        title: "접근 권한 x",
        icon: "error",
        closeOnClickOutside: false,
      });
      navigate("/login", { replace: true });
    }
  }, [seq]);

  if (isLogin) {
    return (
      <>
        <div className={st.profile}>
          <div className={st.main}>
            <ProfileInfo seq={seq} isLogin={isLogin} />
            {parseInt(seq) === mySeq && (
              <div className="btn-group dropend">
                <CgMoreVerticalAlt
                  name="기타버튼"
                  className={cn(`${st.etcBtn}`, "dropdown-toggle")}
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                  size="30"
                />
                <ul className={cn(`${st.dropdown}`, "dropdown-menu")}>
                  <li>
                    <a className="dropdown-item" href="/user/editInfo">
                      수정
                    </a>
                  </li>
                </ul>
              </div>
            )}
          </div>
        </div>

        <ProfileList seq={seq} isLogin={isLogin} mySeq={mySeq} />
      </>
    );
  }
  return <></>;
}
