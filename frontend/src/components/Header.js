import { useNavigate } from "react-router-dom";
import "./styles/Header.css";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import { logoutAction } from "../actions/UserAction";
import styled from "styled-components";

export default function Header() {
  const dispatch = useDispatch();
  const navi = useNavigate();

  const CustomLabel = styled.label`
    font-size: 14px;
    color: #7e7d7d;
    &:hover {
      color: #b8a07e;
      cursor: pointer;
    }
  `;
  const goToUnder = () => {
    window.scrollTo(0, window.outerHeight);
  };
  const isLogin = useSelector((state) => state.userInfo.isLoggedIn);
  const nickname = useSelector((state) => state.userInfo.userInfo.nickname);
  const seq = useSelector((state) => state.userInfo.userInfo.seq);
  const role = useSelector((state) => state.userInfo.userInfo.role);

  // 로그아웃
  const onlogout = () => {
    if (isLogin) {
      dispatch(logoutAction());
      sessionStorage.removeItem("jwt");
      navi("/");
      alert("로그아웃 성공!");
    } else {
      alert("로그인 정보가 없습니다.");
    }
  };

  const onSinup = () => {
    navi("/signup");
  };
  const onLogin = () => {
    navi("/login");
  };

  // 로그인 상태에 따라 보이기 (회원가입/로그인/로그아웃/MY)
  // 관리자일때 관리자페이지 보이기
  const isLogged = () => {
    if (role === "ADMIN") {
      return (
        <div className="logout">
          <a className="user me-4" onClick={onlogout}>
            로그아웃
          </a>
          <a className="user" onClick={() => navi(`/manage`)}>
            관리자
          </a>
        </div>
      );
    }
    if (isLogin) {
      return (
        <div className="logout">
          <a className="user me-4" onClick={onlogout}>
            로그아웃
          </a>
          <a className="user" onClick={() => navi(`/user/profile/${seq}`)}>
            {nickname}
          </a>
        </div>
      );
    } else {
      return (
        <div>
          {/* 로그아웃 눌렀을때 자동으로 밑에 a태그의 href로 이동함 그래서 onClick사용 */}
          <a className="user me-4" onClick={onSinup}>
            회원가입
          </a>
          <a className="user" onClick={onLogin}>
            로그인
          </a>
        </div>
      );
    }
  };

  return (
    <div id="header" className="fixed-top header-inner-pages">
      <div className="container d-flex align-items-center justify-content-between">
        <a href="/" className="logo">
          <img src="/favicon.png" alt="logo" />
          도와주개냥
        </a>

        <nav id="navbar" className="navbar">
          <ul>
            <li>
              {/* <a
                className="nav-link scrollto active"
                href="javascript:return false;"
              >
                유기동물현황
              </a> */}
              <CustomLabel onClick={goToUnder}>유기동물현황</CustomLabel>
            </li>
            <li className="dropdown">
              <a href="#">
                <span>동물 프로필</span> <i className="bi bi-chevron-down"></i>
              </a>
              <ul>
                <li>
                  <a href="/guide">입양 가이드</a>
                </li>
                <li>
                  <a href="/animals/list">유기동물 조회</a>
                </li>
                <li>
                  <a href="/shelter/list">보호소 조회</a>
                </li>
              </ul>
            </li>
            <li>
              <a className="nav-link scrollto" href="/volunteer/list">
                봉사활동
              </a>
            </li>
            <li>
              <a className="nav-link scrollto" href="/community/community">
                커뮤니티
              </a>
            </li>
          </ul>
          <i className="bi bi-list mobile-nav-toggle"></i>
        </nav>
        {isLogged()}
      </div>
    </div>
  );
}
